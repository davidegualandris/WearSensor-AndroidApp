package com.example.lapuile.wearsensor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class WearSensorService extends WearableListenerService implements SensorEventListener {


    private static final String TAG = "Basic";

    private static final String START_ACTIVITY_PATH = "/start-activity";
    private static final String DATA_ITEM_RECEIVED_PATH = "/data-item-received";
    public static final String SENSOR_PATH = "/sensor";




    private static final String SENSOR_KEY ="sensor";
    private static final String NAME_KEY = "name";
    private static final String TYPE_KEY = "type";

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private String choice;







    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        try {
            choice = new String(messageEvent.getData(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "onMessageReceived: " + choice);

        // Check to see if the message is to start an activity
        if (messageEvent.getPath().equals(SENSOR_PATH)) {
            Log.i(TAG, "onMessageReceived: " + messageEvent);

            switch (choice) {
                case "stop":
                    mSensorManager.unregisterListener(this);
                case "WearAccelerometer":

                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearMagnetometer":

                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;

                case "WearGravity":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearGyroscope":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearLinearAcceleration":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;

                case "WearLight":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearProximity":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearAmbientTemperature":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearPressure":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                    else
                        Log.i(TAG, "EELLLLLSEEEEETEN");

                    break;
                case "WearHumidity":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearRotationVector":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearTemperature":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();

                case "WearGame":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearGeoVector":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;

                case "WearOrientation":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearAccelerometerUncalibrated":

                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();

                    break;
                case "WearGyroscopeUncalibrated":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearStepCounter":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearMagnetometerUncalibrated":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;

                case "WearPose6Dof":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearHeartRate":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;
                case "WearHeartBeat":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT) != null){
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                    break;


                default:
                    break;
            }
        }


        else
            super.onMessageReceived(messageEvent);
    }



    private void sensorData(float [] values, int type, String name) {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sensor");

        putDataMapReq.getDataMap().putLong("Time",System.currentTimeMillis());
        switch (choice) {
            case "stop":
                mSensorManager.unregisterListener(this);
            case "WearAccelerometer":

                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
                    if(type == Sensor.TYPE_ACCELEROMETER) {
                        putDataMapReq.getDataMap().putFloatArray("Accelerometer", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }


                break;
            case "WearMagnetometer":

                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
                    if(type == Sensor.TYPE_MAGNETIC_FIELD) {
                        putDataMapReq.getDataMap().putFloatArray("Magnetometer", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;

            case "WearGravity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
                    if(type == Sensor.TYPE_GRAVITY) {
                        putDataMapReq.getDataMap().putFloatArray("Gravity", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearGyroscope":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
                    if(type == Sensor.TYPE_GYROSCOPE) {
                        putDataMapReq.getDataMap().putFloatArray("Gyroscope", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearLinearAcceleration":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
                    if(type == Sensor.TYPE_LINEAR_ACCELERATION){
                        putDataMapReq.getDataMap().putFloatArray("LinearAcceleration", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

            }
                break;

            case "WearLight":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
                    if(type == Sensor.TYPE_LIGHT) {
                        putDataMapReq.getDataMap().putFloatArray("Light", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearProximity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
                    if(type == Sensor.TYPE_PROXIMITY) {
                        putDataMapReq.getDataMap().putFloatArray("Proximity", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearAmbientTemperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
                    if(type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                        putDataMapReq.getDataMap().putFloatArray("AmbientTemperature", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearPressure":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
                    if(type == Sensor.TYPE_PRESSURE) {
                        putDataMapReq.getDataMap().putFloatArray("Pressure", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }


                break;
            case "WearHumidity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
                    if(type == Sensor.TYPE_RELATIVE_HUMIDITY) {
                        putDataMapReq.getDataMap().putFloatArray("Humidity", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearRotationVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null)
                    if(type == Sensor.TYPE_ROTATION_VECTOR) {
                        putDataMapReq.getDataMap().putFloatArray("RotationVector", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearTemperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null)
                    if(type == Sensor.TYPE_TEMPERATURE) {
                        putDataMapReq.getDataMap().putFloatArray("Temperature", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

            case "WearGame":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null)
                    if(type == Sensor.TYPE_GAME_ROTATION_VECTOR) {
                        putDataMapReq.getDataMap().putFloatArray("Game", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearGeoVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null)
                    if(type == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
                        putDataMapReq.getDataMap().putFloatArray("Geo", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;

            case "WearOrientation":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
                    if(type == Sensor.TYPE_ORIENTATION) {
                        putDataMapReq.getDataMap().putFloatArray("Orientation", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearAccelerometerUncalibrated":

                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) != null)
                    if(type == Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) {
                        putDataMapReq.getDataMap().putFloatArray("AccelerometerUncalibrated", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }


                break;
            case "WearGyroscopeUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null)
                    if(type == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
                        putDataMapReq.getDataMap().putFloatArray("GyroscopeUncalibrated", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearStepCounter":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
                    if(type == Sensor.TYPE_STEP_COUNTER) {
                        putDataMapReq.getDataMap().putFloatArray("StepCounter", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearMagnetometerUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null)
                    if(type == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
                        putDataMapReq.getDataMap().putFloatArray("MagnetometerUncalibrated", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;

            case "WearPose6Dof":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF) != null)
                    if(type == Sensor.TYPE_POSE_6DOF) {
                        putDataMapReq.getDataMap().putFloatArray("Pose6Dof", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearHeartRate":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null)
                    if(type == Sensor.TYPE_HEART_RATE) {
                        putDataMapReq.getDataMap().putFloatArray("HeartRate", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                break;
            case "WearHeartBeat":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT) != null)
                    if(type == Sensor.TYPE_HEART_BEAT) {
                        putDataMapReq.getDataMap().putFloatArray("HeartBeat", values);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                    }

                    break;


                    default:
                        break;

                }

        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = Wearable.getDataClient(this).putDataItem(putDataReq);
        putDataTask.addOnSuccessListener(
                new OnSuccessListener<DataItem>() {
                    @Override
                    public void onSuccess(DataItem dataItem) {
                        Log.i(TAG, "Sending VALUES was successful: " + dataItem + mSensor);
                    }

                });

    }




    @Override
    public void onSensorChanged(SensorEvent event) {


        sensorData(event.values, event.sensor.getType(), event.sensor.getName());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
