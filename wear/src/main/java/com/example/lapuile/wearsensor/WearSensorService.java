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

    private  boolean register;

    private int type;
    private String name;


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

            if (choice.equals("stop"))
                stopThread();
            else
                startThread(choice);

        }

         else
            super.onMessageReceived(messageEvent);
    }


    private void startThread(String choice) {

        register = true;
        backgroundThread = new Thread(new WearHandler(choice));
        backgroundThread.start();


    }

    private void stopThread() {
       // backgroundThread.interrupt();
        register = false;
        Log.i(TAG, "REGISTER"+ register);

    }


    public class WearHandler implements Runnable, SensorEventListener {



        Handler mHandler = new Handler(Looper.getMainLooper());
        String choice;
        private SensorManager mSensorManager;
        private Sensor mSensor;
        private float[] copyvalue;


        private static final String NAME_KEY = "name";
        private static final String TYPE_KEY = "type";


        public WearHandler(String intent) {

            choice = intent;

        }


        public void handleData() {

            Log.i(TAG, "wear"+ register);


            if(!choice.equals("stop"))
                mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


            switch (choice) {
                case "stop":
                    mSensorManager.unregisterListener(this);
                case "WearAccelerometer":

                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    break;
                case "WearMagnetometer":

                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    break;

                case "WearGravity":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    break;
                case "WearGyroscope":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearLinearAcceleration":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    break;

                case "WearLight":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearProximity":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearAmbientTemperature":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearPressure":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    break;
                case "WearHumidity":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearRotationVector":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearTemperature":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                case "WearGame":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearGeoVector":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;

                case "WearOrientation":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearAccelerometerUncalibrated":

                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearGyroscopeUncalibrated":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                    break;
                case "WearStepCounter":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearMagnetometerUncalibrated":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;

                case "WearPose6Dof":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearHeartRate":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }


                    break;
                case "WearHeartBeat":
                    if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT) != null) {
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
                        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }

                    break;


                default:
                    break;
            }


            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sensor");

                    putDataMapReq.getDataMap().putLong("Time", System.currentTimeMillis());
                    switch (choice) {

                        case "WearAccelerometer":


                            if (type == Sensor.TYPE_ACCELEROMETER) {
                                putDataMapReq.getDataMap().putFloatArray("Accelerometer", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }


                            break;
                        case "WearMagnetometer":


                            if (type == Sensor.TYPE_MAGNETIC_FIELD) {
                                putDataMapReq.getDataMap().putFloatArray("Magnetometer", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;

                        case "WearGravity":

                            if (type == Sensor.TYPE_GRAVITY) {
                                putDataMapReq.getDataMap().putFloatArray("Gravity", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearGyroscope":

                            if (type == Sensor.TYPE_GYROSCOPE) {
                                putDataMapReq.getDataMap().putFloatArray("Gyroscope", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearLinearAcceleration":

                            if (type == Sensor.TYPE_LINEAR_ACCELERATION) {
                                putDataMapReq.getDataMap().putFloatArray("LinearAcceleration", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);
                            }
                            break;

                        case "WearLight":

                            if (type == Sensor.TYPE_LIGHT) {
                                putDataMapReq.getDataMap().putFloatArray("Light", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearProximity":
                            if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
                                if (type == Sensor.TYPE_PROXIMITY) {
                                    putDataMapReq.getDataMap().putFloatArray("Proximity", copyvalue);
                                    putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                    putDataMapReq.getDataMap().putString(NAME_KEY, name);

                                }

                            break;
                        case "WearAmbientTemperature":

                            if (type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                                putDataMapReq.getDataMap().putFloatArray("AmbientTemperature", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearPressure":

                            if (type == Sensor.TYPE_PRESSURE) {
                                putDataMapReq.getDataMap().putFloatArray("Pressure", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }


                            break;
                        case "WearHumidity":

                            if (type == Sensor.TYPE_RELATIVE_HUMIDITY) {
                                putDataMapReq.getDataMap().putFloatArray("Humidity", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearRotationVector":

                            if (type == Sensor.TYPE_ROTATION_VECTOR) {
                                putDataMapReq.getDataMap().putFloatArray("RotationVector", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearTemperature":

                            if (type == Sensor.TYPE_TEMPERATURE) {
                                putDataMapReq.getDataMap().putFloatArray("Temperature", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                        case "WearGame":

                            if (type == Sensor.TYPE_GAME_ROTATION_VECTOR) {
                                putDataMapReq.getDataMap().putFloatArray("Game", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);
                            }

                            break;
                        case "WearGeoVector":

                            if (type == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
                                putDataMapReq.getDataMap().putFloatArray("Geo", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;

                        case "WearOrientation":

                            if (type == Sensor.TYPE_ORIENTATION) {
                                putDataMapReq.getDataMap().putFloatArray("Orientation", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearAccelerometerUncalibrated":


                            if (type == Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) {
                                putDataMapReq.getDataMap().putFloatArray("AccelerometerUncalibrated", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }


                            break;
                        case "WearGyroscopeUncalibrated":

                            if (type == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
                                putDataMapReq.getDataMap().putFloatArray("GyroscopeUncalibrated", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearStepCounter":

                            if (type == Sensor.TYPE_STEP_COUNTER) {
                                putDataMapReq.getDataMap().putFloatArray("StepCounter", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearMagnetometerUncalibrated":

                            if (type == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
                                putDataMapReq.getDataMap().putFloatArray("MagnetometerUncalibrated", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;

                        case "WearPose6Dof":

                            if (type == Sensor.TYPE_POSE_6DOF) {
                                putDataMapReq.getDataMap().putFloatArray("Pose6Dof", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearHeartRate":

                            if (type == Sensor.TYPE_HEART_RATE) {
                                putDataMapReq.getDataMap().putFloatArray("HeartRate", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;
                        case "WearHeartBeat":

                            if (type == Sensor.TYPE_HEART_BEAT) {
                                putDataMapReq.getDataMap().putFloatArray("HeartBeat", copyvalue);
                                putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                                putDataMapReq.getDataMap().putString(NAME_KEY, name);

                            }

                            break;


                        default:
                            break;

                    }

                    PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                    putDataReq.setUrgent();
                    Task<DataItem> putDataTask = Wearable.getDataClient(getApplicationContext()).putDataItem(putDataReq);
                    putDataTask.addOnSuccessListener(
                            new OnSuccessListener<DataItem>() {
                                @Override
                                public void onSuccess(DataItem dataItem) {
                                    Log.i("SERVICE", "Sending VALUES was successful: " + copyvalue);
                                }

                            });
                    if(register) {
                        Log.i(TAG, "postdelay" + register);
                        mHandler.postDelayed(this, 1000);

                    }
                    else {
                        Log.i(TAG, "STOOOOOP"+ register);
                       stop();
                    }

                }

            });


        }


        @Override
        public void run() {
            if(register)
                handleData();

        }

        public void stop(){
            Log.i(TAG, "STOOOOOP  " + register);
            mHandler.removeCallbacks(this);
            mSensorManager.unregisterListener(this);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            copyvalue = event.values;
            type = event.sensor.getType();
            name = event.sensor.getName();


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


}
