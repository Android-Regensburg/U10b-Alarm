package de.ur.mi.android.base;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    private final static String CLEAR = "";
    private final static int DELAY = 1000;
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

    }

    private void initInput() {
        input = (EditText) findViewById(R.id.input_numbers);
    }

    private void initNumpad() {
        TableLayout table = (TableLayout) findViewById(R.id.alarm_table);
        int count = 0;

        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                if (row.getChildAt(j) instanceof Button) {
                    Button button = (Button) row.getChildAt(j);
                    button.setText(String.valueOf(count + 1));
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
        // if Alarm is still playing
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {

        } else if(!isRunning) {
            // if no alarm is currently playing
        }
    }

    private String setNewInput(View v) {
        return new String();
    }

    private void showCountdown() {


    }

    private void saveInput() {

    }

    private void clearInput() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                input.setText(CLEAR);
            }
        }, DELAY);

    }

    private void clearPassword() {

    }

    private boolean isCorrectPassword() {
        return false;
    }

    private void setPhoneOnAlarm(boolean on) {

    }

    private void startAlarm() {
        Toast.makeText(getApplicationContext(), "Alarm start", Toast.LENGTH_SHORT).show();

    }

    private void stopAlarm() {
        Toast.makeText(getApplicationContext(), "Alarm stopp", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
