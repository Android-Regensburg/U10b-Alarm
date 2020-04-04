package de.ur.mi.android.base;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    private final static String CLEAR = "";
    private final static int RESET_INPUT_DELAY = 1000;
    private final static int COUNTDOWN = 4;
    private final static int PASSWORD_LENGTH = 4;

    private EditText input;
    private AlarmSystem alarm;
    private MediaPlayer mediaPlayer;
    private String password;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_table);
        initAlarm();
        initInput();
        initNumpad();
    }

    private void initAlarm() {
        alarm = new AlarmSystem(this, this);
    }

    private void initInput() {
        input = findViewById(R.id.input_numbers);
    }


    // Initialisiert alle Buttons
    private void initNumpad() {
        TableLayout table = findViewById(R.id.alarm_table);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/led_real.ttf");
        int count = 0;

        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                if (row.getChildAt(j) instanceof Button) {
                    Button button = (Button) row.getChildAt(j);
                    button.setText(String.valueOf(count + 1));
                    button.setTypeface(face);
                    button.setOnClickListener(this);
                    count++;
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        handleButtonClick(v);
    }

    private void handleButtonClick(View v) {
        // Wenn der Alarm läuft, wird dieser Teil des Codes ausgeführt
        // Nach jedem Button Click wird die Länge des daraus resultierenden Stringd mit der Passwortlänge verglichen
        // Wenn isCorrectPasswortd true liefert, wird der Alarm beendet
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            String currentInput = setNewInput(v);
            if (currentInput.length() == PASSWORD_LENGTH) {
                if (isCorrectPassword()) {
                    stopAlarm();
                    clearPassword();
                }
                clearInput();
            }
        } else if(!isRunning) {
            // wenn kein Alarm läuft, wird dieser Teil des Codes ausgeführt
            // Nach jedem Button Click wird die Länge des daraus resultierenden Strings mit der Passwortlänge verglichen
            // Ist die Passwortlänge erreich,t wird das Passwort gespeichert und der Alarm gestartet
            String currentInput = setNewInput(v);
            if (currentInput.length() == PASSWORD_LENGTH) {
                showCountdown();
                saveInput();
                clearInput();
            }
        }
    }

    // Vergleicht das gespeicherte Passwort mit dem Input
    private boolean isCorrectPassword() {
        return input.getText().toString().equals(password);
    }

    // Liest aus, welcher Button geklickt wurde und speichert den Wert in das Input Textview
    private String setNewInput(View v) {
        String clickedNumber = (String) ((TextView) v).getText();
        String oldInput = input.getText().toString();
        input.setText(oldInput + clickedNumber);
        return oldInput + clickedNumber;
    }

    // Hängt den Listener an das Alarmsystem oder entfernt ihn
    private void setPhoneOnAlarm(boolean on) {
        if (on) {
            alarm.start();
        } else {
            alarm.stop();
        }

    }

    // Erstellt einen neuen CountDownTimer
    // Bei jedem Tick wird die Zeit per Toast dargestellt bis der Alarm aktiviert wurde
    // Bei onFinish wird AlarmSystem gestartet, um darauf zu lauschen ob sich das Handy bewegt
    private void showCountdown() {
        isRunning = true;
        CountDownTimer countDown = new CountDownTimer(COUNTDOWN * RESET_INPUT_DELAY, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_countdown) + (int) (millisUntilFinished / 1000), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {
                setPhoneOnAlarm(true);
            }
        };

        countDown.start();

    }

    // Überschriebene Methode, die ausgeführt wird, wenn die Sensoren Änderungen feststellen
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (alarm.hasBeenMoved(event.values.clone())) {
                startAlarm();
                setPhoneOnAlarm(false);
            }

        }

    }

    // Speichert das Passwort aus dem TextView in den String
    private void saveInput() {
        password = input.getText().toString();
    }

    // Setzt den TextView zurück
    private void clearInput() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                input.setText(CLEAR);
            }
        }, RESET_INPUT_DELAY);

    }

    private void clearPassword() {
        password = null;
    }

    // Alarm Sound wird gestartet
    private void startAlarm() {
        Toast.makeText(getApplicationContext(), "Alarm start", Toast.LENGTH_SHORT).show();
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    // MediaPlayer wird wieder gestoppt
    private void stopAlarm() {
        Toast.makeText(getApplicationContext(), "Alarm stopp", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
        mediaPlayer.reset();
        isRunning = false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}