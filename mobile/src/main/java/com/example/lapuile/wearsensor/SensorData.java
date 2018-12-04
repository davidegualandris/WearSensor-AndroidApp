package com.example.lapuile.wearsensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SensorData extends AppCompatActivity implements SensorEventListener {

    private  SensorManager mSensorManager;

    private Sensor mSensor;


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
    private Sensor mGameRotationVector;
    private Sensor mGeoMagneticVector;
    private Sensor mOrientation;
    private Sensor mAccelerometerUncalibrated;
    private Sensor mGyroscopeUncalibrated;
    private Sensor mStepCounter;
    private Sensor mMagnetometerUncalibrated;



    ListView sensor_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensor_list = (findViewById(R.id.sensor_list));
        final ArrayList<String> listp = new ArrayList<String>();



        switch (getStringIntent()) {


            case "Accelerometer":

                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


                break;
            case "Magnetometer":
                try {
                    mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                }
                catch(SensorAbsentException e){
                    listp.add(e.getError());
                }
                break;

            case "Gravity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                else
                    getStringError(listp);
                break;
            case "Gyroscope":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
                    mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                else
                    getStringError(listp);
                break;
            case "LinearAcceleration":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
                    mLinearAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                else
                    getStringError(listp);
                break;

            case "Light":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
                    mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                else
                    getStringError(listp);
                break;
            case "Proximity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
                    mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                else
                    getStringError(listp);
                break;
            case "AmbientTemperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
                    mAmbientTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                else
                    getStringError(listp);
                break;
            case "Pressure":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
                    mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                else
                    getStringError(listp);
                break;
            case "Humidity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
                    mHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                else
                    getStringError(listp);
                break;
            case "RotationVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null)
                    mRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                else
                    getStringError(listp);
                break;
            case "Temperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null)
                    mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
                else
                    getStringError(listp);
                break;

            case "Game":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null)
                    mGameRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
                else
                    getStringError(listp);
                break;
            case "GeoVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null)
                    mGeoMagneticVector = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
                else
                    getStringError(listp);
                break;

            case "Orientation":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
                    mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                else
                    getStringError(listp);
                break;
            case "AccelerometerUncalibrated":

                    if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED)!= null)
                        mAccelerometerUncalibrated = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();

                break;
            case "GyroscopeUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null)
                    mGyroscopeUncalibrated = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
                else
                    getStringError(listp);
                break;
            case "StepCounter":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
                    mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                else
                    getStringError(listp);
                break;
            case "MagnetometerUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null)
                    mMagnetometerUncalibrated = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
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

/*
    public  boolean checkIt() {

        switch (getStringIntent()) {
            case "Accelerometer":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null);


            case "Magnetometer":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null);


            case "Gravity":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null);

            case "Gyroscope":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null);


            case "LinearAcceleration":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null);


            case "Light":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null);


            case "Proximity":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null);


            case "AmbientTemperature":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null);


            case "Pressure":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null);

            case "Humidity":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null);

            case "RotationVector":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null);

            case "Temperature":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null);

            case "Game":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null);

            case "GeoVector":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null);


            case "Orientation":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null);


            case "AccelerometerUncalibrated":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) != null);


            case "GyroscopeUncalibrated":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null);


            case "StepCounter":
                return(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null);


            case "MagnetometerUncalibrated":
                return (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null);

            default: break;

        }
        return false;
    }
    */

    private String getStringIntent() {

        Intent choice = getIntent();
        String type = choice.getStringExtra("Type");
        return type;
    }

    private void getStringError(ArrayList<String> lista) {
        lista.add(getResources().getString(R.string.error_string));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, lista);
        sensor_list.setAdapter(adapter);
    }

/*
    private void getList() {
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        ListView sensorText = findViewById(R.id.sensor_list);
        final ArrayList<String> listp = new ArrayList<String>();
        for (Sensor currentSensor : sensorList) {
            listp.add(currentSensor.getName());
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listp);

        sensorText.setAdapter(adapter);

    }

*/


    private void printData(float[] sensorData, final ArrayList<String> listp, boolean single) {

        if (single)
            listp.add(getResources().getString(R.string.onedimension_text, sensorData[0]));
        else {
            listp.add(getResources().getString(R.string.x_text, sensorData[0]));
            listp.add(getResources().getString(R.string.y_text, sensorData[1]));
            listp.add(getResources().getString(R.string.z_text, sensorData[2]));

        }

    }

    private void printDataUncalibrated(float[] sensorData, final ArrayList<String> listp) {

        listp.add(getResources().getString(R.string.x_text, sensorData[0]));
        listp.add(getResources().getString(R.string.y_text, sensorData[1]));
        listp.add(getResources().getString(R.string.z_text, sensorData[2]));
        if (getStringIntent().equals("AccelerometerUncalibrated")) {
            listp.add(getResources().getString(R.string.acc_unc_x, sensorData[3]));
            listp.add(getResources().getString(R.string.acc_unc_y, sensorData[4]));
            listp.add(getResources().getString(R.string.acc_unc_x, sensorData[5]));
        } else if (getStringIntent().equals("GyroscopeUncalibrated")) {
            listp.add(getResources().getString(R.string.gyrosc_unc_x, sensorData[3]));
            listp.add(getResources().getString(R.string.gyrosc_unc_y, sensorData[4]));
            listp.add(getResources().getString(R.string.gyrosc_unc_z, sensorData[5]));

        } else {
            listp.add(getResources().getString(R.string.magnet_unc_x, sensorData[3]));
            listp.add(getResources().getString(R.string.magnet_unc_y, sensorData[4]));
            listp.add(getResources().getString(R.string.magnet_unc_z, sensorData[5]));

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
        //sensor_list= findViewById(R.id.sensor_list);
        final ArrayList<String> listp = new ArrayList<String>();
        boolean single = false;
        switch (getStringIntent()) {

            case "Accelerometer":
                listp.add(mSensor.getName());
                printData(event.values, listp, single);
                break;

            case "Magnetometer":
                listp.add(mSensor.getName());
                printData(event.values, listp, single);

                break;
            // IMPORTANTE QUESTO SENSOR PUÒ ESSERE IMPLEMENTATO IN MODO SOFTWARE DA ACCELEROMETER
            case "Gravity":
                listp.add(mSensor.getName());
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
            case "Game":
                listp.add(mGameRotationVector.getName());
                printData(event.values, listp, single);
                break;
            case "GeoVector":
                listp.add(mGeoMagneticVector.getName());
                printData(event.values, listp, single);
                break;
            case "Orientation":
                listp.add(mOrientation.getName());
                printData(event.values, listp, single);
                break;
            case "AccelerometerUncalibrated":
                listp.add(mAccelerometerUncalibrated.getName());
                printDataUncalibrated(event.values, listp);
                break;
            case "GyroscopeUncalibrated":
                listp.add(mGyroscopeUncalibrated.getName());
                printDataUncalibrated(event.values, listp);
                break;
            case "StepCounter":
                listp.add(mStepCounter.getName());
                single = true;
                printData(event.values, listp, single);
                break;
            case "MagnetometerUncalibrated":
                listp.add(mMagnetometerUncalibrated.getName());
                printDataUncalibrated(event.values, listp);
                break;
            default:
                break;
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,R.layout.my_layout, listp);

        sensor_list.setAdapter(adapter);

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void playSensorData(View view) {


        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
/*
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
            case "Game":
                mSensorManager.registerListener(this, mGameRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "GeoVector":
                mSensorManager.registerListener(this, mGeoMagneticVector, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "Orientation":
                mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "AccelerometerUncalibrated":
                if(mAccelerometerUncalibrated != null)
                    mSensorManager.registerListener(this, mAccelerometerUncalibrated, SensorManager.SENSOR_DELAY_NORMAL);
                else
                    Toast.makeText(this, "The sensor that you request may be broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "GyroscopeUncalibrated":
                mSensorManager.registerListener(this, mGyroscopeUncalibrated, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "StepCounter":
                mSensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case "MagnetometerUncalibrated":
                mSensorManager.registerListener(this, mMagnetometerUncalibrated, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            default:
                break;
        }

    }
*/
    }
    public void pauseSensorData(View view) {
        mSensorManager.unregisterListener(this);
    }
}