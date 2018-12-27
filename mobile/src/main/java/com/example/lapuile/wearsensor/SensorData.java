package com.example.lapuile.wearsensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SensorData extends AppCompatActivity implements SensorEventListener {

    private  SensorManager mSensorManager;

    private Sensor mSensor;

    ListView sensor_list;


    final ArrayList<String> exceList = new ArrayList<String>();
    private float[] copyValue;

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
                       Toast.makeText(this, "Sensor you requested is probably",
                               Toast.LENGTH_LONG).show();
                break;
            case "Magnetometer":

                    if(mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
                        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    else
                        Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();
                break;

            case "Gravity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "Gyroscope":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "LinearAcceleration":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;

            case "Light":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "Proximity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "AmbientTemperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "Pressure":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "Humidity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "RotationVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "Temperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();

            case "Game":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "GeoVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;

            case "Orientation":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "AccelerometerUncalibrated":

                if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED)!= null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                                Toast.LENGTH_LONG).show();

                break;
            case "GyroscopeUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "StepCounter":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;
            case "MagnetometerUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
                break;

            case "Pose6Dof":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF) != null)
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF);
                else
                    Toast.makeText(this, "Sensor you requested is probably",
                            Toast.LENGTH_LONG).show();
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

    public void saveSensorData(View view) {

        if(isExternalStorageWritable()) {


            if (copyValue != null) {
                ExcelSheet Sheet = new ExcelSheet(mSensor.getName(), copyValue, exceList);
                Sheet.exportToExcel();
            } else
                Toast.makeText(this, "Play sensor data before!",
                        Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "External Storage isn't writable",
                    Toast.LENGTH_LONG).show();


    }

}