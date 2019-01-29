package com.example.lapuile.wearsensor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Handler;
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


public class WearSensorService extends WearableListenerService {


    private static final String TAG = "Basic";

    private static final String START_ACTIVITY_PATH = "/start-activity";
    private static final String DATA_ITEM_RECEIVED_PATH = "/data-item-received";
    public static final String SENSOR_PATH = "/sensor";


    private static final String SENSOR_KEY = "sensor";
    private static final String NAME_KEY = "name";
    private static final String TYPE_KEY = "type";


    private String choice;

    private Thread backgroundThread;


    private boolean register;


    private boolean running;


    Handler mHandler;


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
            intent.putExtra("Type" , choice);


            if (choice.equals("stop"))
                stopService(intent);
            else
                startService(intent);



        } else
            super.onMessageReceived(messageEvent);
    }


}
















