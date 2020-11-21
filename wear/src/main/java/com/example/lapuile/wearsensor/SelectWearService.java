package com.example.lapuile.wearsensor;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.wearable.Wearable.getDataClient;

public class SelectWearService extends WearableListenerService {

    private static final String TAG = "Basic";
    private static final String LIST_PATH = "/list";

    private static final String SENSOR_LIST_PATH = "/sensorList";

    private static final String LIST_KEY = "list";

    private static final String MOTION_KEY = "motion";
    private static final String ENVIRONMENTAL_KEY = "environmental";
    private static final String POSITION_KEY = "position";

    private SensorManager mSensorManager;

    public SelectWearService() {
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        super.onDataChanged(dataEventBuffer);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        try {
            String choice = new String(messageEvent.getData(), "UTF-8");

            Log.i(TAG, "onMessageReceived: " + choice);

            if (messageEvent.getPath().equals(LIST_PATH))
                switch (choice) {
                    case "WearMotion":
                        sendMotionSensors();
                        break;
                    case "WearEnvironmental":
                        sendEnvironmentalSensors();
                        break;
                    case "WearPosition":
                        sendPositionSensors();
                        break;
                    default:
                        break;


                }

            if (messageEvent.getPath().equals(SENSOR_LIST_PATH))
                sensorList();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        super.onMessageReceived(messageEvent);
    }

    private void sendMotionSensors() {
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        final ArrayList<Integer> listType = new ArrayList<Integer>();


        for (Sensor currentSensor : sensorList) {

            if (currentSensor.getType() == Sensor.TYPE_ACCELEROMETER ||
                    currentSensor.getType() == Sensor.TYPE_ACCELEROMETER_UNCALIBRATED ||
                    currentSensor.getType() == Sensor.TYPE_GYROSCOPE ||
                    currentSensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED ||
                    currentSensor.getType() == Sensor.TYPE_GRAVITY ||
                    currentSensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION ||
                    currentSensor.getType() == Sensor.TYPE_ROTATION_VECTOR ||
                    currentSensor.getType() == Sensor.TYPE_STEP_COUNTER ||
                    currentSensor.getType() == Sensor.TYPE_HEART_RATE ||
                    currentSensor.getType() == Sensor.TYPE_HEART_BEAT)
                listType.add(currentSensor.getType());


        }

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/list");
        putDataMapReq.getDataMap().putIntegerArrayList(MOTION_KEY, listType);
        putDataMapReq.getDataMap().putLong("Time", System.currentTimeMillis());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = getDataClient(this).putDataItem(putDataReq);
        putDataTask.addOnSuccessListener(
                new OnSuccessListener<DataItem>() {
                    @Override
                    public void onSuccess(DataItem dataItem) {
                        Log.i(TAG, "Sending VALUES was successful: " + dataItem + listType);
                    }
                });


    }


    private void sendPositionSensors() {
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        final ArrayList<Integer> listType = new ArrayList<Integer>();


        for (Sensor currentSensor : sensorList) {

            if (currentSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD ||
                    currentSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED ||
                    currentSensor.getType() == Sensor.TYPE_PROXIMITY ||
                    currentSensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR ||
                    currentSensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR ||
                    currentSensor.getType() == Sensor.TYPE_ORIENTATION ||
                    currentSensor.getType() == Sensor.TYPE_POSE_6DOF)
                listType.add(currentSensor.getType());


        }

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/list");
        putDataMapReq.getDataMap().putIntegerArrayList(POSITION_KEY, listType);
        putDataMapReq.getDataMap().putLong("Time", System.currentTimeMillis());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = getDataClient(this).putDataItem(putDataReq);
        putDataTask.addOnSuccessListener(
                new OnSuccessListener<DataItem>() {
                    @Override
                    public void onSuccess(DataItem dataItem) {
                        Log.i(TAG, "Sending VALUES was successful: " + dataItem + listType);
                    }
                });


    }

    private void sendEnvironmentalSensors() {
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        final ArrayList<Integer> listType = new ArrayList<Integer>();


        for (Sensor currentSensor : sensorList) {

            if (currentSensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE ||
                    currentSensor.getType() == Sensor.TYPE_LIGHT ||
                    currentSensor.getType() == Sensor.TYPE_PRESSURE ||
                    currentSensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY ||
                    currentSensor.getType() == Sensor.TYPE_TEMPERATURE)
                listType.add(currentSensor.getType());


        }

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/list");
        putDataMapReq.getDataMap().putIntegerArrayList(ENVIRONMENTAL_KEY, listType);
        putDataMapReq.getDataMap().putLong("Time", System.currentTimeMillis());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = getDataClient(this).putDataItem(putDataReq);
        putDataTask.addOnSuccessListener(
                new OnSuccessListener<DataItem>() {
                    @Override
                    public void onSuccess(DataItem dataItem) {
                        Log.i(TAG, "Sending VALUES was successful: " + dataItem + listType);
                    }
                });


    }


    private void sensorList() {
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        final ArrayList<String> listp = new ArrayList<String>();


        for (Sensor currentSensor : sensorList) {

            listp.add(currentSensor.getName());


        }

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sensorList");
        putDataMapReq.getDataMap().putStringArrayList(LIST_KEY, listp);
        putDataMapReq.getDataMap().putLong("Time", System.currentTimeMillis());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = getDataClient(this).putDataItem(putDataReq);
        putDataTask.addOnSuccessListener(
                new OnSuccessListener<DataItem>() {
                    @Override
                    public void onSuccess(DataItem dataItem) {
                        Log.i(TAG, "Sending VALUES was successful: " + dataItem + listp);
                    }
                });
    }
}
