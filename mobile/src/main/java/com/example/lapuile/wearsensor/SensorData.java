package com.example.lapuile.wearsensor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;

public class SensorData extends AppCompatActivity implements SensorEventListener {

    private  SensorManager mSensorManager;

    private Sensor mSensor;

    ListView sensor_list;

    private static final int PERMISSION_REQUEST_CODE = 200;


    final ArrayList<String> exceList = new ArrayList<String>();
    private float[] copyValue;
    final ArrayList<String> listList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensor_list = (findViewById(R.id.sensor_list));





        switch (getStringIntent()) {

            case "Accelerometer":

                   if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                   else
                       Toast.makeText(this, "Sensor you requested is probably broken",
                               Toast.LENGTH_LONG).show();
                break;
            case "Magnetometer":

                    if(mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    else
                        Toast.makeText(this, "Sensor you requested is probably broken",
                                Toast.LENGTH_LONG).show();
                break;

            case "Gravity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Gyroscope":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "LinearAcceleration":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;

            case "Light":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Proximity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "AmbientTemperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Pressure":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Humidity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "RotationVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Temperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
                else

                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();

            case "Game":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "GeoVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;

            case "Orientation":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "AccelerometerUncalibrated":

                if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED)!= null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                                Toast.LENGTH_LONG).show();

                break;
            case "GyroscopeUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "StepCounter":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "MagnetometerUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;

            case "Pose6Dof":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF);

                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;

            case "HeartRate":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                }

                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "HeartBeat":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
                }

                else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;


            case "SensorList":
                setContentView(R.layout.activity_sensor_data);
                Button play = (findViewById(R.id.play_button));
                play.setVisibility(View.INVISIBLE);
                Button pause = (findViewById(R.id.pause_button));
                pause.setVisibility(View.INVISIBLE);
                List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
                ListView sensorText = findViewById(R.id.sensor_list);


                for (Sensor currentSensor : sensorList) {

                    listList.add(currentSensor.getName());
                }


                final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (this, R.layout.my_layout, listList);

                sensorText.setAdapter(adapter);


                break;

            default:
                break;
        }




    }

    private String getStringIntent() {

        Intent choice = getIntent();
        String type = choice.getStringExtra("Type");
        return type;
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

        if(getStringIntent().equals("Pose6Dof")){
            listp.add(getResources().getString(R.string.x_text, sensorData[0]));
            listp.add(getResources().getString(R.string.y_text, sensorData[1]));
            listp.add(getResources().getString(R.string.z_text, sensorData[2]));
            listp.add(getResources().getString(R.string.pose_6_dof_cos, sensorData[3]));
            listp.add(getResources().getString(R.string.translation_along_x, sensorData[4]));
            listp.add(getResources().getString(R.string.translation_along_y, sensorData[5]));
            listp.add(getResources().getString(R.string.translation_along_z, sensorData[6]));
            listp.add(getResources().getString(R.string.delta_quat_x, sensorData[7]));
            listp.add(getResources().getString(R.string.delta_quat_y, sensorData[8]));
            listp.add(getResources().getString(R.string.delta_quat_z, sensorData[9]));
            listp.add(getResources().getString(R.string.delta_quat_rot_cos, sensorData[10]));
            listp.add(getResources().getString(R.string.delta_transl_x, sensorData[11]));
            listp.add(getResources().getString(R.string.delta_transl_y, sensorData[12]));
            listp.add(getResources().getString(R.string.delta_transl_z, sensorData[13]));
            listp.add(getResources().getString(R.string.sequence_number, sensorData[14]));


            exceList.add(getResources().getString(R.string.excel_x_text));
            exceList.add(getResources().getString(R.string.excel_y_text));
            exceList.add(getResources().getString(R.string.excel_z_text));
            exceList.add(getResources().getString(R.string.excel_pose_6_dof_cos));
            exceList.add(getResources().getString(R.string.excel_translation_along_x));
            exceList.add(getResources().getString(R.string.excel_translation_along_y));
            exceList.add(getResources().getString(R.string.excel_translation_along_z));
            exceList.add(getResources().getString(R.string.excel_delta_quat_x));
            exceList.add(getResources().getString(R.string.excel_delta_quat_y));
            exceList.add(getResources().getString(R.string.excel_delta_quat_z));
            exceList.add(getResources().getString(R.string.excel_delta_quat_rot_cos));
            exceList.add(getResources().getString(R.string.excel_delta_transl_x));
            exceList.add(getResources().getString(R.string.excel_delta_transl_y));
            exceList.add(getResources().getString(R.string.excel_delta_transl_z));
            exceList.add(getResources().getString(R.string.excel_sequence_number));

        }


        if (single) {
            listp.add(getResources().getString(R.string.onedimension_text, sensorData[0]));
            exceList.add(getResources().getString(R.string.excel_onedimension_text));
        }
        else {
            listp.add(getResources().getString(R.string.x_text, sensorData[0]));
            listp.add(getResources().getString(R.string.y_text, sensorData[1]));
            listp.add(getResources().getString(R.string.z_text, sensorData[2]));
            exceList.add(getResources().getString(R.string.excel_x_text));
            exceList.add(getResources().getString(R.string.excel_y_text));
            exceList.add(getResources().getString(R.string.excel_z_text));

        }

    }

    private void printDataUncalibrated(float[] sensorData, final ArrayList<String> listp) {


        listp.add(getResources().getString(R.string.x_text, sensorData[0]));
        listp.add(getResources().getString(R.string.y_text, sensorData[1]));
        listp.add(getResources().getString(R.string.z_text, sensorData[2]));
        exceList.add(getResources().getString(R.string.excel_x_text));
        exceList.add(getResources().getString(R.string.excel_y_text));
        exceList.add(getResources().getString(R.string.excel_z_text));
        if (getStringIntent().equals("AccelerometerUncalibrated")) {
            listp.add(getResources().getString(R.string.acc_unc_x, sensorData[3]));
            listp.add(getResources().getString(R.string.acc_unc_y, sensorData[4]));
            listp.add(getResources().getString(R.string.acc_unc_x, sensorData[5]));
            exceList.add(getResources().getString(R.string.excel_acc_unc_x));
            exceList.add(getResources().getString(R.string.excel_acc_unc_y));
            exceList.add(getResources().getString(R.string.excel_acc_unc_x));
        } else if (getStringIntent().equals("GyroscopeUncalibrated")) {
            listp.add(getResources().getString(R.string.gyrosc_unc_x, sensorData[3]));
            listp.add(getResources().getString(R.string.gyrosc_unc_y, sensorData[4]));
            listp.add(getResources().getString(R.string.gyrosc_unc_z, sensorData[5]));
            exceList.add(getResources().getString(R.string.excel_gyrosc_unc_x));
            exceList.add(getResources().getString(R.string.excel_gyrosc_unc_y));
            exceList.add(getResources().getString(R.string.excel_gyrosc_unc_z));

        } else {
            listp.add(getResources().getString(R.string.magnet_unc_x, sensorData[3]));
            listp.add(getResources().getString(R.string.magnet_unc_y, sensorData[4]));
            listp.add(getResources().getString(R.string.magnet_unc_z, sensorData[5]));
            exceList.add(getResources().getString(R.string.excel_magnet_unc_x));
            exceList.add(getResources().getString(R.string.excel_magnet_unc_y));
            exceList.add(getResources().getString(R.string.excel_magnet_unc_z));

        }
    }


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
            case "Magnetometer":
            case "Gravity":
            case "Gyroscope":
            case "LinearAcceleration":
            case "RotationVector":
            case "Game":
            case "GeoVector":
            case "Orientation":
            case "Pose6Dof":


                listp.add(mSensor.getName());
                printData(event.values, listp, single);
                copyValue = event.values;
                break;

            case "Light":
            case "Proximity":
            case "AmbientTemperature":
            case "Pressure":
            case "Humidity":
            case "StepCounter":
            case "Temperature":
            case "HeartRate":
            case "HeartBeat":
                single = true;
                listp.add(mSensor.getName());
                printData(event.values, listp, single);
                copyValue = event.values;
                break;

            case "AccelerometerUncalibrated":
            case "GyroscopeUncalibrated":
            case "MagnetometerUncalibrated":
                listp.add(mSensor.getName());
                printDataUncalibrated(event.values, listp);
                copyValue = event.values;
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

        if(accuracy == mSensorManager.SENSOR_STATUS_NO_CONTACT){
            Toast.makeText(this, "Tighten band and try again",
                    Toast.LENGTH_LONG).show();

        }

        if(accuracy == mSensorManager.SENSOR_STATUS_UNRELIABLE){
            Toast.makeText(this, "The value returned by this sensor cannot be trusted",
                    Toast.LENGTH_LONG).show();

        }


    }



    public void playSensorData(View view) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        }




    public void pauseSensorData(View view) {
        mSensorManager.unregisterListener(this);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.menu.menu_main:
                saveSensorData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveSensorData() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            if(isExternalStorageWritable()) {

                if (getStringIntent().equals("SensorList")) {
                    ExcelSheet Sheet = new ExcelSheet(listList);
                    Sheet.exportListToExcel();
                } else {
                    if (copyValue != null) {
                        ExcelSheet Sheet = new ExcelSheet(mSensor.getName(), copyValue, exceList);
                        Sheet.exportToExcel();
                    } else
                        Toast.makeText(this, "Play sensor data before!",
                                Toast.LENGTH_LONG).show();
                }
            }
            else
                Toast.makeText(this, "External Storage isn't writable",
                        Toast.LENGTH_LONG).show();



        }

        }



}