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
        this.context = null;
        this.listener = null;
    }

    private void init() {

    }

    public void start() {

    }

    public void stop() {

    }

    public boolean hasBeenMoved(final float[] values) {
        return false;
    }
}