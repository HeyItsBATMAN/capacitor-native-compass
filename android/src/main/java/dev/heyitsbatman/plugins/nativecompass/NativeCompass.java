package dev.heyitsbatman.plugins.nativecompass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;
import android.hardware.SensorEventListener;
import android.view.Display;
import android.view.Surface;

import androidx.appcompat.app.AppCompatActivity;

public class NativeCompass implements SensorEventListener {
    private static final String TAG = "NativeCompass";
    private AppCompatActivity activity;
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private Sensor accelerometer;
    private float[] gravityValues = new float[3];
    private float[] magneticValues = new float[3];

    public NativeCompass(AppCompatActivity activity) {
        Log.d(TAG, "Initializing NativeCompass");
        this.activity = activity;
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.magnetometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        this.accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Log.d(TAG, "SensorManager: " + String.valueOf(this.sensorManager != null) + " Magnetometer" + String.valueOf(this.magnetometer != null));

        if (accelerometer == null || magnetometer == null) {
            Log.e(TAG, "Accelerometer or magnetometer sensor not found on this device.");
        }
    }

    public float getCurrentHeading() {
        return this.calculateCurrentHeading();
    }

    public void registerListeners() {
        this.sensorManager.registerListener(this, this.magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        this.sensorManager.registerListener(this, this.accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListeners() {
        this.sensorManager.unregisterListener(this);
    }

    private DisplayRotation getDisplayRotation() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int rotation = display.getRotation();
        return switch (rotation) {
            case Surface.ROTATION_90 -> DisplayRotation.ROTATION_90;
            case Surface.ROTATION_180 -> DisplayRotation.ROTATION_180;
            case Surface.ROTATION_270 -> DisplayRotation.ROTATION_270;
            case Surface.ROTATION_0 -> DisplayRotation.ROTATION_0;
            default -> DisplayRotation.ROTATION_0;
        };
    }

    private float calculateCurrentHeading() {
        float bearing;

        Vector fieldVector = new Vector(this.magneticValues.clone());
        Vector gravityVector = new Vector(this.gravityValues.clone());
        gravityVector.normalize();
        Vector gravityDownVector = new Vector(0.0f, 0.0f, 1.0f);
        Vector axisVector = gravityVector.crossProduct(gravityDownVector);
        axisVector.normalize();
        double angle = Math.acos(gravityVector.dotProduct(gravityDownVector));

        Vector fieldRotatedVector = new Vector(axisVector);
        fieldRotatedVector.multiply(axisVector.dotProduct(fieldVector));
        Vector axisCrossProductField = new Vector(axisVector).crossProduct(fieldVector);
        Vector axisCrossProductFieldCosAngle = new Vector(axisCrossProductField);
        axisCrossProductFieldCosAngle.multiply(Math.cos(angle));
        Vector axisCrossProductFieldSinAngle = new Vector(axisCrossProductField);
        axisCrossProductFieldSinAngle.multiply(Math.sin(angle));
        fieldRotatedVector.add(axisCrossProductFieldCosAngle.crossProduct(axisVector));
        fieldRotatedVector.add(axisCrossProductFieldSinAngle);

        bearing = fieldRotatedVector.getYaw() - 90.0f;

        DisplayRotation displayRotation = getDisplayRotation();
        switch (displayRotation) {
            case ROTATION_0:
                break;
            case ROTATION_90:
                bearing += 90.0f;
                break;
            case ROTATION_180:
                bearing += 180.0f;
                break;
            case ROTATION_270:
                bearing += 270.0f;
                break;
        }

        float normalized = (bearing + 360.0f) % 360.0f;

        Log.d(TAG, "Bearing: " + String.valueOf(normalized));
        return normalized;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {
            gravityValues = event.values.clone();
        } else if (event.sensor == magnetometer) {
            magneticValues = event.values.clone();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
