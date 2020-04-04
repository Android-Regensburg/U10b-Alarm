package de.ur.mi.android.base;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AlarmSystem {

    private final static int MOTION_DETECTION_Z = 15;
    private final static int Z_AXIS = 2;
    private final Context context;
    private final SensorEventListener listener;
    private SensorManager manager;
    private Sensor sensor;

    public AlarmSystem(Context context, SensorEventListener listener) {
        this.context = context;
        this.listener = listener;
        init();
    }

    // Erstellt den SensorManager
    // Erstellt den Sensor
    private void init() {
        manager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    // Hängt den Listener aus der MainActivity an den Manager
    public void start() {
        manager.registerListener(listener, sensor,
                SensorManager.SENSOR_DELAY_UI);
    }

    // Löst den Listener vom Manager
    public void stop() {
        manager.unregisterListener(listener);
    }

    // überprüft, ob der Schwellwert erreicht wird
    public boolean hasBeenMoved(final float[] values) {

        if (values[Z_AXIS] > MOTION_DETECTION_Z) {
            return true;
        }
        return false;
    }
}
