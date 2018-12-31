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

    private static final String LIST_PATH = "/list";
    private static final String LIST_KEY = "list";

    private static final String MOTION_KEY = "motion";
    private static final String ENVIRONMENTAL_KEY = "environmental";
    private static final String POSITION_KEY = "position";


    private static final String SENSOR_KEY = "sensor";
    private static final String NAME_KEY = "name";
    private static final String TYPE_KEY = "type";

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private String choice;



    private float[] values;


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

        if (messageEvent.getPath().equals(LIST_PATH))
            switch (choice) {
                case "WearMotion":
                    sendMotionSensors();
                    break;
                case "WearSensorList":
                    sensorList();
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




        // Check to see if the message is to start an activity
        if (messageEvent.getPath().equals(SENSOR_PATH)) {
            Log.i(TAG, "onMessageReceived: " + messageEvent);

            switch (choice) {
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


                default:
                    break;
            }
        }


        else
            super.onMessageReceived(messageEvent);
    }

    private void sendMotionSensors(){
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        final ArrayList<Integer> listType = new ArrayList<Integer>();


        for (Sensor currentSensor : sensorList) {

            if(currentSensor.getType() == Sensor.TYPE_ACCELEROMETER ||
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
        putDataMapReq.getDataMap().putLong("Time",System.currentTimeMillis());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = Wearable.getDataClient(this).putDataItem(putDataReq);
        putDataTask.addOnSuccessListener(
                new OnSuccessListener<DataItem>() {
                    @Override
                    public void onSuccess(DataItem dataItem) {
                        Log.i(TAG, "Sending VALUES was successful: " + dataItem + listType);
                    }
                });


    }

    private void sensorData() {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sensor");
        putDataMapReq.getDataMap().putLong("Time",System.currentTimeMillis());
        putDataMapReq.getDataMap().putFloatArray(SENSOR_KEY, values);
        putDataMapReq.getDataMap().putInt(TYPE_KEY, mSensor.getType());
        putDataMapReq.getDataMap().putString(NAME_KEY, mSensor.getName());
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



    private void sendPositionSensors(){
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        final ArrayList<Integer> listType = new ArrayList<Integer>();




        for (Sensor currentSensor : sensorList) {

            if(currentSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD ||
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
        putDataMapReq.getDataMap().putLong("Time",System.currentTimeMillis());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = Wearable.getDataClient(this).putDataItem(putDataReq);
        putDataTask.addOnSuccessListener(
                new OnSuccessListener<DataItem>() {
                    @Override
                    public void onSuccess(DataItem dataItem) {
                        Log.i(TAG, "Sending VALUES was successful: " + dataItem + listType);
                    }
                });


    }

    private void sendEnvironmentalSensors(){
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        final ArrayList<Integer> listType = new ArrayList<Integer>();




        for (Sensor currentSensor : sensorList) {

            if(currentSensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE ||
                    currentSensor.getType() == Sensor.TYPE_LIGHT ||
                    currentSensor.getType() == Sensor.TYPE_PRESSURE ||
                    currentSensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY ||
                    currentSensor.getType() == Sensor.TYPE_TEMPERATURE )
                listType.add(currentSensor.getType());



        }

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/list");
        putDataMapReq.getDataMap().putIntegerArrayList(ENVIRONMENTAL_KEY, listType);
        putDataMapReq.getDataMap().putLong("Time",System.currentTimeMillis());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = Wearable.getDataClient(this).putDataItem(putDataReq);
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

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/list");
        putDataMapReq.getDataMap().putStringArrayList(LIST_KEY, listp);
        putDataMapReq.getDataMap().putLong("Time",System.currentTimeMillis());
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = Wearable.getDataClient(this).putDataItem(putDataReq);
        putDataTask.addOnSuccessListener(
                new OnSuccessListener<DataItem>() {
                    @Override
                    public void onSuccess(DataItem dataItem) {
                        Log.i(TAG, "Sending VALUES was successful: " + dataItem+ listp);
                    }
                });
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        values = event.values;
        sensorData();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
