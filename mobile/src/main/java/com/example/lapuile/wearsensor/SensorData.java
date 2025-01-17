package com.example.lapuile.wearsensor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class SensorData extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    private Sensor mSensor;
    private String mSensorName;

    private float maxRange;
    private float power;
    private float resolution;
    private String vendor;
    private int version;

    private List<String> dataNames;
    private float[] copyValue;
    private String description;

    ListView sensor_list;

    private static final int PERMISSION_REQUEST_CODE = 200;

    ArrayList<String> exceList = new ArrayList<String>();

    public static final String TAG = "SENSORDATA";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private ScheduledFuture scheduledFuture;
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private KaaHandler kaaHandler;

    private int frequency;
    private int period;

    private Runnable sendToKaa = new Runnable() {
        @Override
        public void run() {

            Log.i("COLLECTION_DATA", "ENTER");
            String preKey = getString(R.string.defaultKaa) + getStringIntent();
            List<Float> sensor_data = new ArrayList<>(copyValue.length);

            for (float f : copyValue) {
                sensor_data.add(Float.valueOf(f));
            }

            KaaHandler.collectData("mobile", mSensorName, dataNames, sensor_data);
            //KaaHandler.collectData(mSensorName, sensor_data);
            //CSVHandler.writeData("Test", sensor_data);

            if(kaaHandler.getFrequency() != frequency && mPreferences.getBoolean(preKey, true)) {
                Log.i("FREQUENCY_CHANGED", String.valueOf(kaaHandler.getFrequency()));
                sendLogToKaaHandler();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change current look of an Activity. If the Activity change it must have a design to show.
        setContentView(R.layout.activity_sensor_data);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensor_list = (findViewById(R.id.sensor_list));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Type"));

        //Context context = this;
        kaaHandler = new KaaHandler();

        if(getStringIntent().equals("SensorList")){
            kaaHandler.pause();
        }

        frequency = kaaHandler.getFrequency();
        period = kaaHandler.getPeriod();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        switch (getStringIntent()) { // TODO: Risucire ad inviare Intent anche dalle attività di setting e info

            case "Accelerometer":

                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.accelerometer_description);


                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Magnetometer":

                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.magnetometer_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;

            case "Gravity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.gravity_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Gyroscope":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.gyroscope_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "LinearAcceleration":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.linear_acceleration_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;

            case "Light":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.light_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Proximity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.proximity_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "AmbientTemperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.ambient_t_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Pressure":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.pressure_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Humidity":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.humidity_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "RotationVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.rotation_vector_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "Temperature":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.temperature_description);
                } else

                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();

            case "Game":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.game_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "GeoVector":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.geo_vector_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;

            case "Orientation":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {

                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.orientation_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "AccelerometerUncalibrated":

                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.acc_unc_description);

                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();

                break;
            case "GyroscopeUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.gyrosc_unc_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "StepCounter":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.step_counter_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "MagnetometerUncalibrated":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.magnet_unc_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;

            case "Pose6Dof":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.pose_6dof_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;

            case "HeartRate":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.heart_rate_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;
            case "HeartBeat":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
                    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
                    mSensorName = mSensor.getName();
                    description = getString(R.string.heart_beat_description);
                } else
                    Toast.makeText(this, "Sensor you requested is probably broken",
                            Toast.LENGTH_LONG).show();
                break;


            case "SensorList":
                LinearLayout mLinearLayout = findViewById(R.id.linearLayout2);
                mLinearLayout.setVisibility(View.GONE);

                Button play = (findViewById(R.id.play_button));
                play.setVisibility(View.GONE);
                Button pause = (findViewById(R.id.pause_button));
                pause.setVisibility(View.GONE);
                List<Sensor> sensorText = mSensorManager.getSensorList(Sensor.TYPE_ALL);


                for (Sensor currentSensor : sensorText) {

                    exceList.add(currentSensor.getName());
                }


                final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (this, R.layout.my_layout, exceList);

                sensor_list.setAdapter(adapter);



                break;

            default:
                break;
        }
        if(!getStringIntent().equals("SensorList")&& mSensor != null){
            maxRange = mSensor.getMaximumRange();

            power = mSensor.getPower();
            resolution = mSensor.getResolution();
            version = mSensor.getVersion();
            vendor = mSensor.getVendor();

        }


    }

    private String getStringIntent() {

        Intent choice = getIntent();
        String type = choice.getStringExtra("Type");
        return type;
    }

    private void printData(float[] sensorData, final ArrayList<String> listp) {

        listp.add(mSensorName);
        listp.add(getResources().getString(R.string.sent_data_text) + " " + kaaHandler.getSent_data());
        listp.add(getResources().getString(R.string.received_data_text) + " " + kaaHandler.getSrec_data());
        listp.add("");

        switch (getStringIntent()) {
            case "Pose6Dof":

                listp.add(getResources().getString(R.string.acc_grav_y_text, sensorData[0]));
                listp.add(getResources().getString(R.string.acc_grav_z_text, sensorData[1]));
                listp.add(getResources().getString(R.string.acc_grav_x_text, sensorData[2]));

                dataNames.add("yGravitationalAcceleration");
                dataNames.add("zGravitationalAcceleration");
                dataNames.add("xGravitationalAcceleration");

                listp.add(getResources().getString(R.string.pose_6_dof_cos, sensorData[3]));
                dataNames.add("CosHalfTeta");

                listp.add(getResources().getString(R.string.translation_along_x, sensorData[4]));
                listp.add(getResources().getString(R.string.translation_along_y, sensorData[5]));
                listp.add(getResources().getString(R.string.translation_along_z, sensorData[6]));
                dataNames.add("translationAlongX");
                dataNames.add("translationAlongY");
                dataNames.add("translationAlongZ");

                listp.add(getResources().getString(R.string.delta_quat_x, sensorData[7]));
                listp.add(getResources().getString(R.string.delta_quat_y, sensorData[8]));
                listp.add(getResources().getString(R.string.delta_quat_z, sensorData[9]));
                listp.add(getResources().getString(R.string.delta_quat_rot_cos, sensorData[10]));
                dataNames.add("deltaQuaternionRotationXTimesSinHalfTeta");
                dataNames.add("deltaQuaternionRotationYTimesSinHalfTeta");
                dataNames.add("deltaQuaternionRotationZTimesSinHalfTeta");
                dataNames.add("deltaQuaternionRotationCosHalfTeta");

                listp.add(getResources().getString(R.string.delta_transl_x, sensorData[11]));
                listp.add(getResources().getString(R.string.delta_transl_y, sensorData[12]));
                listp.add(getResources().getString(R.string.delta_transl_z, sensorData[13]));
                dataNames.add("deltaTranslationAlongX");
                dataNames.add("deltaTranslationAlongY");
                dataNames.add("deltaTranslationAlongZ");

                listp.add(getResources().getString(R.string.sequence_number, sensorData[14]));
                dataNames.add("sequenceNumber");

                break;
            case "Accelerometer":
            case "Gravity":
            case "LinearAcceleration":


                listp.add(getString(R.string.acc_grav_x_text, sensorData[0]));
                listp.add(getResources().getString(R.string.acc_grav_y_text, sensorData[1]));
                listp.add(getResources().getString(R.string.acc_grav_z_text, sensorData[2]));

                dataNames.add("xGravitationalAcceleration");
                dataNames.add("yGravitationalAcceleration");
                dataNames.add("zGravitationalAcceleration");

                break;
            case "RotationVector":
            case "Game":
            case "GeoVector":

                listp.add(getResources().getString(R.string.rotation_vector_text_x, sensorData[0]));
                listp.add(getResources().getString(R.string.rotation_vector_text_y, sensorData[1]));
                listp.add(getResources().getString(R.string.rotation_vector_text_z, sensorData[2]));
                listp.add(getResources().getString(R.string.rotation_vector_cos, sensorData[3]));

                dataNames.add("xTimesSinHalfTeta");
                dataNames.add("yTimesSinHalfTeta");
                dataNames.add("zTimesSinHalfTeta");
                dataNames.add("CosHalfTeta");

                if (!getStringIntent().equals("Game")) {
                    listp.add(getResources().getString(R.string.rotation_vector_estimated, sensorData[4]));
                    dataNames.add("estimatedAccuracy");
                }

                break;

            case "Gyroscope":
                listp.add(getString(R.string.gyroscope_x_text, sensorData[0]));
                listp.add(getString(R.string.gyroscope_y_text, sensorData[1]));
                listp.add(getString(R.string.gyroscope_z_text, sensorData[2]));

                dataNames.add("x");
                dataNames.add("y");
                dataNames.add("z");

                break;
            case "Magnetometer":
                listp.add(getString(R.string.magnetic_field_x_text, sensorData[0]));
                listp.add(getString(R.string.magnetic_field_y_text, sensorData[1]));
                listp.add(getString(R.string.magnetic_field_z_text, sensorData[2]));

                dataNames.add("x");
                dataNames.add("y");
                dataNames.add("z");

                break;
            case "Pressure":
                listp.add(getString(R.string.pressure_text, sensorData[0]));

                dataNames.add("pressure");

                break;
            case "Humidity":
                listp.add(getString(R.string.humidity_text, sensorData[0]) + "%");

                dataNames.add("humidity");

                break;

            case "Orientation":
                listp.add(getString(R.string.orientation_x_text, sensorData[0]));
                listp.add(getString(R.string.orientation_y_text, sensorData[1]));
                listp.add(getString(R.string.orientation_z_text, sensorData[2]));

                dataNames.add("x");
                dataNames.add("y");
                dataNames.add("z");

                break;
            case "Proximity":
                listp.add(getString(R.string.proximity, sensorData[0]));

                dataNames.add("proximity");

                break;
            case "StepCounter":
                listp.add(getString(R.string.step_counter_text, sensorData[0]));

                dataNames.add("steps");

                break;
            case "AmbientTemperature":
            case "Temperature":
                listp.add(getString(R.string.ambient_temperature, sensorData[0]));

                dataNames.add("celsiusDegrees");

                break;
            case "Light":
                listp.add(getString(R.string.light_text, sensorData[0]));

                dataNames.add("lx");

                break;
            case "HeartRate":
                listp.add(getString(R.string.heart_rate_text, sensorData[0]));

                dataNames.add("bpm");

                break;
            case "HeartBeat":
                listp.add(getString(R.string.onedimension_text, sensorData[0]));

                dataNames.add("value");

                break;
            default:
                break;


        }

        exceList.addAll(listp.subList(4, listp.size() - 1));

    }


    private void printDataUncalibrated(float[] sensorData, final ArrayList<String> listp) {

        listp.add(getResources().getString(R.string.sent_data_text) + " " + kaaHandler.getSent_data());
        listp.add(getResources().getString(R.string.received_data_text) + " " + kaaHandler.getSrec_data());
        listp.add("");

        if (getStringIntent().equals("AccelerometerUncalibrated")) {

            listp.add(getResources().getString(R.string.acc_unc_x, sensorData[0]));
            listp.add(getResources().getString(R.string.acc_unc_y, sensorData[1]));
            listp.add(getResources().getString(R.string.acc_unc_z, sensorData[2]));

            dataNames.add("x");
            dataNames.add("y");
            dataNames.add("z");

            listp.add(getResources().getString(R.string.acc_unc_x_2, sensorData[3]));
            listp.add(getResources().getString(R.string.acc_unc_y_2, sensorData[4]));
            listp.add(getResources().getString(R.string.acc_unc_z_2, sensorData[5]));

            dataNames.add("estimatedBiasCompensationOnX");
            dataNames.add("estimatedBiasCompensationOnY");
            dataNames.add("estimatedBiasCompensationOnZ");

        } else if (getStringIntent().equals("GyroscopeUncalibrated")) {
            listp.add(getResources().getString(R.string.gyrosc_unc_x, sensorData[0]));
            listp.add(getResources().getString(R.string.gyrosc_unc_y, sensorData[1]));
            listp.add(getResources().getString(R.string.gyrosc_unc_z, sensorData[2]));

            dataNames.add("angularSpeedAroundX");
            dataNames.add("angularSpeedAroundY");
            dataNames.add("angularSpeedAroundZ");

            listp.add(getResources().getString(R.string.gyrosc_unc_x_2, sensorData[3]));
            listp.add(getResources().getString(R.string.gyrosc_unc_y_2, sensorData[4]));
            listp.add(getResources().getString(R.string.gyrosc_unc_z_2, sensorData[5]));

            dataNames.add("estimatedDriftAroundX");
            dataNames.add("estimatedDriftAroundY");
            dataNames.add("estimatedDriftAroundZ");


        } else {
            listp.add(getResources().getString(R.string.magnet_unc_x, sensorData[0]));
            listp.add(getResources().getString(R.string.magnet_unc_y, sensorData[1]));
            listp.add(getResources().getString(R.string.magnet_unc_z, sensorData[2]));

            dataNames.add("alongX");
            dataNames.add("alongY");
            dataNames.add("alongZ");


            listp.add(getResources().getString(R.string.magnet_unc_x_2, sensorData[3]));
            listp.add(getResources().getString(R.string.magnet_unc_y_2, sensorData[4]));
            listp.add(getResources().getString(R.string.magnet_unc_z_2, sensorData[5]));

            dataNames.add("ironBiasAlongX");
            dataNames.add("ironBiasAlongY");
            dataNames.add("ironBiasAlongZ");

        }


        listp.add(description);
        exceList.addAll(listp.subList(4, listp.size() - 1));


    }


    protected void onResume() {
        super.onResume();
        //kaaHandler.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        kaaHandler.stop();
        //CSVHandler.stop();
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        kaaHandler.pause();
        if (scheduledFuture != null)
            scheduledFuture.cancel(false);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        final ArrayList<String> listp = new ArrayList<String>();
        dataNames = new ArrayList<>();

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
            case "Light":
            case "Proximity":
            case "AmbientTemperature":
            case "Pressure":
            case "Humidity":
            case "StepCounter":
            case "Temperature":
            case "HeartRate":
            case "HeartBeat":

                printData(event.values, listp);
                copyValue = event.values;
                break;

            case "AccelerometerUncalibrated":
            case "GyroscopeUncalibrated":
            case "MagnetometerUncalibrated":

                printDataUncalibrated(event.values, listp);
                copyValue = event.values;
                break;


            default:
                break;
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.my_layout, listp);

        sensor_list.setAdapter(adapter);

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

        if (accuracy == mSensorManager.SENSOR_STATUS_NO_CONTACT) {
            Toast.makeText(this, "Tighten band and try again",
                    Toast.LENGTH_LONG).show();

        }

        if (accuracy == mSensorManager.SENSOR_STATUS_UNRELIABLE) {
            Toast.makeText(this, "The value returned by this sensor cannot be trusted",
                    Toast.LENGTH_LONG).show();

        }

    }


    public void playSensorData(View view) {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        Log.i("ADD_LOG_RECORD","sendLogToKaaHandler()");
        Log.i("KAA_STATUS", KaaHandler.getSTATUS());
        if(kaaHandler.getSTATUS() != "START"){
            kaaHandler.resume();
        }
        sendLogToKaaHandler();
    }


    public void pauseSensorData(View view) {
        mSensorManager.unregisterListener(this);
        kaaHandler.pause();
        if (scheduledFuture != null)
            scheduledFuture.cancel(false);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    // You use onCreateOptionsMenu() to specify the options menu for an activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.menu_phone_image).setVisible(true);
        if(getStringIntent().equals("SensorList")) {
            menu.findItem(R.id.info_action).setVisible(false);
            menu.findItem(R.id.settings_action).setVisible(false);
            menu.findItem(R.id.save_action).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        Intent intent;

        switch (item.getItemId()) {
            case R.id.save_action:
                saveSensorData();
                return true;
            case R.id.info_action:
                intent = new Intent(this, InfoActivity.class);

                intent.putExtra("MaxRange", maxRange);

                intent.putExtra("Power", power);
                intent.putExtra("Version", version);
                intent.putExtra("Vendor", vendor);
                intent.putExtra("Resolution", resolution);
                intent.putExtra("Description", description);
                startActivity(intent);
                return true;
            case R.id.settings_action:
                intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("sensor_type", getStringIntent());
                intent.putExtra("frequency", kaaHandler.getFrequency());
                intent.putExtra("period", kaaHandler.getPeriod());
                startActivity(intent);
                return true;
            case android.R.id.home:
            finish();
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

            if (isExternalStorageWritable()) {

                if (getStringIntent().equals("SensorList")) {
                    ExcelSheet Sheet = new ExcelSheet(exceList);
                    Sheet.exportListToExcel();
                    Toast.makeText(this, "Saved",
                            Toast.LENGTH_LONG).show();

                } else {
                    if (copyValue != null) {
                        //Log.i("exceList", exceList.toString());
                        ExcelSheet Sheet = new ExcelSheet(mSensor.getName(), copyValue, exceList,
                                description, maxRange, power, resolution, version, vendor);
                        Sheet.exportToExcel();
                        Toast.makeText(this, "Saved",
                                Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, "Play sensor data before!",
                                Toast.LENGTH_LONG).show();
                }
            } else
                Toast.makeText(this, "External Storage isn't writable",
                        Toast.LENGTH_LONG).show();


        }

    }

    public void sendLogToKaaHandler() {
        //Log.i("ENTER_METHOD","collectData()");
        String preKey = getString(R.string.defaultKaa) + getStringIntent();
        String freKey = getString(R.string.frequencyKaa) + getStringIntent();
        String perKey = getString(R.string.periodKaa) + getStringIntent();
        frequency = kaaHandler.getFrequency();
        period = kaaHandler.getPeriod();
        int fr = frequency;
        int pr = period;
        if (!mPreferences.getBoolean(preKey, true)){
            fr = mPreferences.getInt(freKey, frequency);
            pr = mPreferences.getInt(perKey, period);
            kaaHandler.setPeriod(pr);
        }

        if(fr > 0 && pr >= fr) {
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(
                    sendToKaa, kaaHandler.getDefaultStartDelay(), fr, TimeUnit.MILLISECONDS);
        }
    }
}