package com.example.lapuile.wearsensor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;


public class WearDataService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float[] copyvalue;
    private int type;
    private String name;
    private Thread backgroundThread;
    volatile boolean kill;

    private float maxRange;
    private float power;
    private float resolution;
    private String vendor;
    private int version;





    public WearDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        kill = true;
        mSensorManager.unregisterListener(this);

        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        String choice = intent.getStringExtra("Type");


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

                break;
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
        if(!choice.equals("SensorList")&& mSensor != null){

            maxRange = mSensor.getMaximumRange();
            power = mSensor.getPower();
            resolution = mSensor.getResolution();
            version = mSensor.getVersion();
            vendor = mSensor.getVendor();

        }



        backgroundThread = new Thread(new WearHandler(choice));
        backgroundThread.start();


        return super.onStartCommand(intent, flags, startId);

    }


    public class WearHandler implements Runnable {

        Handler mHandler = new Handler(Looper.getMainLooper());
        String choice;

        private static final String NAME_KEY = "name";
        private static final String TYPE_KEY = "type";

        private static final String MAX_RANGE_KEY = "MaxRange";
        private static final String POWER_KEY = "Power";
        private static final String VENDOR_KEY = "Vendor";
        private static final String VERSION_KEY = "Version";
        private static final String RESOLUTION_KEY= "Resolution";


        private WearHandler(String intent) {

            choice = intent;
            kill = false;

        }


        private void handleData() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    if (!kill) {

                        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sensor");

                        putDataMapReq.getDataMap().putLong("Time", System.currentTimeMillis());

                        putDataMapReq.getDataMap().putFloatArray(choice, copyvalue);
                        putDataMapReq.getDataMap().putInt(TYPE_KEY, type);
                        putDataMapReq.getDataMap().putString(NAME_KEY, name);

                        putDataMapReq.getDataMap().putFloat(MAX_RANGE_KEY, maxRange);
                        putDataMapReq.getDataMap().putInt(VERSION_KEY, version);
                        putDataMapReq.getDataMap().putString(VENDOR_KEY, vendor);
                        putDataMapReq.getDataMap().putFloat(RESOLUTION_KEY, resolution);
                        putDataMapReq.getDataMap().putFloat(POWER_KEY, power);



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

                        mHandler.postDelayed(this, 300);

                    }
                }

            });


        }


        @Override
        public void run() {

            handleData();

        }

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

