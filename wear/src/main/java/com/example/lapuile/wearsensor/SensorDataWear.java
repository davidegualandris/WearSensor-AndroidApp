package com.example.lapuile.wearsensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SensorDataWear extends WearableActivity implements SensorEventListener {


    private  SensorManager mSensorManager;


    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private Sensor mGravity;
    private Sensor mGyroscope;
    private Sensor mLinearAcceleration;
    private Sensor mLightSensor;
    private Sensor mProximity;
    private Sensor mAmbientTemperature;
    private Sensor mPressure;
    private Sensor mHumidity;
    private Sensor mRotationVector;
    private Sensor mTemperature;

    ListView sensor_list_wear;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data_wear);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensor_list_wear= (findViewById(R.id.sensor_list_wear));
        final ArrayList<String> listp = new ArrayList<String>();


        switch (getStringIntent()) {

            case "list":
                getList();
                break;
            case "Accelerometer":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
                    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                else {
                    getStringError(listp);
                }
                break;
            case "Magnetometer":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
                    mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                else {
                    getStringError(listp);
                }
                break;
            case "Gravity":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
                    mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                else
                    getStringError(listp);
                break;
            case "Gyroscope":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
                    mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                else
                    getStringError(listp);
                break;
            case "LinearAcceleration":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
                    mLinearAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                else
                    getStringError(listp);
                break;

            case "Light":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
                    mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                else
                    getStringError(listp);
                break;
            case "Proximity":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
                    mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                else
                    getStringError(listp);
                break;
            case "AmbientTemperature":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
                    mAmbientTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                else
                    getStringError(listp);
                break;
            case "Pressure":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
                    mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                else
                    getStringError(listp);
                break;
            case "Humidity":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
                    mHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                else
                    getStringError(listp);
                break;
            case "RotationVector":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null)
                    mRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                else
                    getStringError(listp);
                break;
                // DEPRECATED
            case "Temperature":
                if(mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null)
                    mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
                else
                    getStringError(listp);
                break;





                   /* List<Sensor> list = mSensorManager.getSensorList(Sensor.TYPE_ALL);
                    ListView sensorlist = findViewById(R.id.sensor_list);

                    final ArrayList<String> listp = new ArrayList<String>();
                for (Sensor s : list) {
                    if (s != null)
                        listp.add(s.getName());
                    else
                        listp.add(getResources().getString(R.string.error_string));
                }

                final ArrayAdapter <String> adapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, listp);

                sensorlist.setAdapter(adapter);
                break;
*/
            default:
                break;
        }

    }


    private String getStringIntent(){

        Intent choice=getIntent();
        String type = choice.getStringExtra("Type");
        return type;
    }

    private void getStringError(ArrayList<String> lista){
        lista.add(getResources().getString(R.string.error_string));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, lista);
        sensor_list_wear.setAdapter(adapter);
    }



    private void getList(){
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        ListView sensorText = findViewById(R.id.sensor_list_wear);
        final ArrayList<String> listp = new ArrayList<String>();
        for (Sensor currentSensor : sensorList ) {
            listp.add(currentSensor.getName());
        }


        final ArrayAdapter <String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listp);

        sensorText.setAdapter(adapter);

    }


    private void printData(float [] sensorData, final ArrayList<String> listp , boolean single) {

        if (single)
            listp.add(getResources().getString(R.string.x_text, sensorData[0]));
        else {
            listp.add(getResources().getString(R.string.x_text, sensorData[0]));
            listp.add(getResources().getString(R.string.y_text, sensorData[1]));
            listp.add(getResources().getString(R.string.z_text, sensorData[2]));

        }

    }
    /*
        private void getAccelerometerData(){

            mSensorManager= (SensorManager)getSystemService(SENSOR_SERVICE);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        }
    */
    protected void onResume() {
        super.onResume();

        switch (getStringIntent()) {

            case "Accelerometer":
                mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "Magnetometer":
                mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "Gravity":
                mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "Gyroscope":
                mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "LinearAcceleration":
                mSensorManager.registerListener(this, mLinearAcceleration, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "Light":
                mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "Proximity":
                mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "AmbientTemperature":
                mSensorManager.registerListener(this, mAmbientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "Pressure":
                mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "Humidity":
                mSensorManager.registerListener(this, mHumidity, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "RotationVector":
                mSensorManager.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "Temperature":
                mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
                break;


            default:
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //sensor_list_wear = findViewById(R.id.sensor_list_wear);
        final ArrayList<String> listp = new ArrayList<String>();
        boolean single = false;
        switch (getStringIntent()) {

            case "Accelerometer":
                listp.add(mAccelerometer.getName());
                printData(event.values, listp, single);
                break;

            case "Magnetometer":
                listp.add(mMagnetometer.getName());
                printData(event.values, listp, single);
                break;

            // IMPORTANTE QUESTO SENSOR PUÒ ESSERE IMPLEMENTATO IN MODO SOFTWARE DA ACCELEROMETER
            case "Gravity":
                listp.add(mGravity.getName());
                printData(event.values, listp, single);

                break;
            case "Gyroscope":
                listp.add(mGyroscope.getName());
                printData(event.values, listp, single);
                break;
            // IMPORTANTE QUESTO SENSOR PUÒ ESSERE IMPLEMENTATO IN MODO SOFTWARE DA ACCELEROMETER
            case "LinearAcceleration":
                listp.add(mLinearAcceleration.getName());
                printData(event.values, listp, single);
                break;
            case "RotationVector":
                listp.add(mRotationVector.getName());
                printData(event.values, listp, single);
                break;
            case "Light":
                listp.add(mLightSensor.getName());
                single = true;
                printData(event.values, listp, single);
                break;
            case "Proximity":
                listp.add(mProximity.getName());
                single = true;
                printData(event.values, listp, single);
                break;
            case "AmbientTemperature":
                listp.add(mAmbientTemperature.getName());
                single = true;
                printData(event.values, listp, single);
                break;
            case "Pressure":
                listp.add(mPressure.getName());
                single = true;
                printData(event.values, listp, single);
                break;
            case "Humidity":
                listp.add(mHumidity.getName());
                single = true;
                printData(event.values, listp, single);
                break;
            case "Temperature":
                listp.add(mTemperature.getName());
                single = true;
                printData(event.values, listp, single);
                break;
            default:
                break;
        }
        final ArrayAdapter <String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listp);

        sensor_list_wear.setAdapter(adapter);

    }

        /*
        ListView sensor_list = findViewById(R.id.sensor_list);
        final ArrayList<String> listp = new ArrayList<String>();
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        for(Sensor s : sensorList) {
                if (event.values.length == 1)
                    listp.add(s.getName() + getResources().getString(R.string.onedimension_text, event.values[0]));
                if (event.values.length == 3) {
                    listp.add(s.getName() + getResources().getString(R.string.x_text, event.values[SensorManager.DATA_X]));
                    listp.add(getResources().getString(R.string.y_text, event.values[SensorManager.DATA_Y]));
                    listp.add(getResources().getString(R.string.y_text, event.values[SensorManager.DATA_Z]));


                }
            }
        final ArrayAdapter <String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listp);

        sensor_list.setAdapter(adapter);

/*
        int sensorType = event.sensor.getType();

        switch (sensorType){
            case Sensor.TYPE_ACCELEROMETER:
                // In this example, alpha is calculated as t / (t + dT),
                // where t is the low-pass filter's time-constant and
                // dT is the event delivery rate.

                final float alpha = (float)0.8;
                float[] gravity=new float[3];
                float[] linear_acceleration = new float[3];

                // Isolate the force of gravity with the low-pass filter.
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                // Remove the gravity contribution with the high-pass filter.
                linear_acceleration[0] = event.values[0] - gravity[0];
                linear_acceleration[1] = event.values[1] - gravity[1];
                linear_acceleration[2] = event.values[2] - gravity[2];

                TextView acceleration_text = findViewById(R.id.sensor_list);
                StringBuilder acc_text = new StringBuilder();
                acc_text.append(getResources().getString(R.string.accelerometerx_text, linear_acceleration[0])).append(
                        System.getProperty("line.separator"))
                        .append(getResources().getString(R.string.accelerometery_text, linear_acceleration[1])).append(
                    System.getProperty("line.separator")).append(
               getResources().getString(R.string.accelerometerz_text, linear_acceleration[2]));

                acceleration_text.setText(acc_text);

        }
    */


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}