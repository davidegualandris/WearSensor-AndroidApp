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

public class WearSensorService extends WearableListenerService implements SensorEventListener {


    private static final String TAG = "Basic";

    private static final String START_ACTIVITY_PATH = "/start-activity";
    private static final String DATA_ITEM_RECEIVED_PATH = "/data-item-received";
    public static final String SENSOR_PATH = "/sensor";


    private static final String SENSOR_KEY = "sensor";

    private SensorManager mSensorManager;
    private Sensor mSensor;



    private float[] values;


    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);



    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.i(TAG, "onMessageReceived: " + messageEvent);

        // Check to see if the message is to start an activity
        if (messageEvent.getPath().equals(SENSOR_PATH)) {
            Log.i(TAG, "onMessageReceived: " + messageEvent);
            sensorData();

        }
        else
            super.onMessageReceived(messageEvent);
    }


    private void sensorData() {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sensor");
        putDataMapReq.getDataMap().putFloatArray(SENSOR_KEY, values);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> putDataTask = Wearable.getDataClient(this).putDataItem(putDataReq);
        putDataTask.addOnSuccessListener(
                new OnSuccessListener<DataItem>() {
                    @Override
                    public void onSuccess(DataItem dataItem) {
                        Log.i(TAG, "Sending VALUES was successful: " + dataItem);
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
