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


        backgroundThread = new Thread(new WearHandler(choice));
        backgroundThread.start();


        return super.onStartCommand(intent, flags, startId);

    }


    public class WearHandler implements Runnable {

        Handler mHandler = new Handler(Looper.getMainLooper());
        String choice;

        private static final String NAME_KEY = "name";
        private static final String TYPE_KEY = "type";


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

