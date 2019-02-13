package com.example.lapuile.wearsensor;


import android.content.Intent;

import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;

import com.google.android.gms.wearable.WearableListenerService;

import java.io.UnsupportedEncodingException;


public class WearSensorService extends WearableListenerService {


    private static final String TAG = "Basic";


    public static final String SENSOR_PATH = "/sensor";


    private String choice;


    @Override
    public void onCreate() {
        super.onCreate();


    }


    @Override
    public void onDestroy() {

        super.onDestroy();


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

            Intent intent = new Intent(this, WearDataService.class);
            intent.putExtra("Type", choice);


            if (choice.equals("stop"))
                stopService(intent);
            else
                startService(intent);


        } else
            super.onMessageReceived(messageEvent);
    }


}
















