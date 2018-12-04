package com.example.lapuile.wearsensor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {


    public static final String EXTRA_MESSAGE =
            "com.example.android.wearsensor.extra.MESSAGE";
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button accelerometer = (Button) findViewById(R.id.accelerometer_button);
        accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Accelerometer");
                startActivity(intent);

            }

        });

        Button accelerometerUncalibrated = (Button) findViewById(R.id.accelerometer_uncalibrated_button);
        accelerometerUncalibrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "AccelerometerUncalibrated");
                startActivity(intent);

            }

        });
        Button magnetometer = (Button) findViewById(R.id.magnetometer_button);
        magnetometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Magnetometer");
                startActivity(intent);

            }
        });
        Button magnetometerUncalibrated = (Button) findViewById(R.id.magnetometer_uncalibrated_button);
        magnetometerUncalibrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "MagnetometerUncalibrated");
                startActivity(intent);

            }
        });

        Button gravity = (Button) findViewById(R.id.gravity_button);
        gravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Gravity");
                startActivity(intent);

            }
        });
        Button gyroscope = (Button) findViewById(R.id.gyroscope_button);
        gyroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Gyroscope");
                startActivity(intent);

            }
        });

        Button gyroscopeUncalibrated = (Button) findViewById(R.id.gyroscope_uncalibrated_button);
        gyroscopeUncalibrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "GyroscopeUncalibrated");
                startActivity(intent);

            }
        });
        Button linear_acceleration = (Button) findViewById(R.id.linear_acc_button);
        linear_acceleration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "LinearAcceleration");
                startActivity(intent);

            }
        });

        Button light = (Button) findViewById(R.id.light_button);
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Light");
                startActivity(intent);

            }
        });

        Button proximity = (Button) findViewById(R.id.proximity_button);
        proximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Proximity");
                startActivity(intent);

            }
        });

        Button ambient_temperature = (Button) findViewById(R.id.ambient_temperature_button);
        ambient_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "AmbientTemperature");
                startActivity(intent);

            }
        });

        Button pressure = (Button) findViewById(R.id.pressure_button);
        pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Pressure");
                startActivity(intent);

            }
        });

        Button humidity = (Button) findViewById(R.id.humidity_button);
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Humidity");
                startActivity(intent);

            }
        });
        Button rotation_vector = (Button) findViewById(R.id.rotation_vector_button);
        rotation_vector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "RotationVector");
                startActivity(intent);

            }
        });
        Button temperature = (Button) findViewById(R.id.temperature_button);
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Temperature");
                startActivity(intent);

            }
        });

        Button gameRotation = (Button) findViewById(R.id.game_rotation_button);
        gameRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Game");
                startActivity(intent);

            }
        });

        Button geoMagneticVector = (Button) findViewById(R.id.geomagnetic_vector_button);
        geoMagneticVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "GeoVector");
                startActivity(intent);

            }
        });

    //DEPRECATED
        Button orientation = (Button) findViewById(R.id.orientation_button);
        orientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "Orientation");
                startActivity(intent);

            }
        });

        Button stepCounter = (Button) findViewById(R.id.step_counter_button);
        stepCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(SelectActivity.this, SensorData.class);
                intent.putExtra("Type", "StepCounter");
                startActivity(intent);

            }
        });



//        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent choice = getIntent();
        String type = choice.getStringExtra("Type");
        switch (type){

            case "SensorList":
                setContentView(R.layout.activity_sensor_data);
                ImageButton play = (findViewById(R.id.play_button));
                play.setVisibility(View.INVISIBLE);
                ImageButton pause = (findViewById(R.id.pause_button));
                pause.setVisibility(View.INVISIBLE);
                List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
                ListView sensorText = findViewById(R.id.sensor_list);
                final ArrayList<String> listp = new ArrayList<String>();

                for (Sensor currentSensor : sensorList) {
                    if ((currentSensor.getType() == Sensor.TYPE_ACCELEROMETER ||
                            currentSensor.getType() == Sensor.TYPE_ACCELEROMETER_UNCALIBRATED ||
                            currentSensor.getType() == Sensor.TYPE_GRAVITY ||
                            currentSensor.getType() == Sensor.TYPE_GYROSCOPE ||
                            currentSensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED ||
                            currentSensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION ||
                            currentSensor.getType() == Sensor.TYPE_ROTATION_VECTOR ||
                            currentSensor.getType() == Sensor.TYPE_STEP_COUNTER)) {
                        if (!(listp.contains("MOTION: ")))
                            listp.add("MOTION: ");
                        listp.add(currentSensor.getName());
                    }
                }
                for(Sensor currentSensor : sensorList) {

                    if (currentSensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE ||
                            currentSensor.getType() == Sensor.TYPE_LIGHT ||
                            currentSensor.getType() == Sensor.TYPE_PRESSURE ||
                            currentSensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY ||
                            currentSensor.getType() == Sensor.TYPE_TEMPERATURE) {
                        if (!(listp.contains("ENVIRONMENTAL: ")))
                            listp.add("ENVIRONMENTAL: ");
                        listp.add(currentSensor.getName());
                    }
                }
                for(Sensor currentSensor : sensorList){

                    if((currentSensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR ||
                            currentSensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR ||
                            currentSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD ||
                            currentSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED ||
                            currentSensor.getType() == Sensor.TYPE_PROXIMITY ||
                            currentSensor.getType() == Sensor.TYPE_ORIENTATION )) {
                        if (!(listp.contains("POSITION: ")))
                            listp.add("POSITION: ");
                        listp.add(currentSensor.getName());
                    }
                    }







                final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (this, R.layout.my_layout, listp);

                sensorText.setAdapter(adapter);

                break;

            case "Motion":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
                    accelerometer.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
                    gravity.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
                    gyroscope.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
                    linear_acceleration.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null)
                    rotation_vector.setVisibility(View.VISIBLE);
                //if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) != null)
                    accelerometerUncalibrated.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null)
                    gyroscopeUncalibrated.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
                    stepCounter.setVisibility(View.VISIBLE);
                break;

            case "Environmental":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
                    ambient_temperature.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
                    light.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
                    pressure.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
                    humidity.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null)
                    temperature.setVisibility(View.VISIBLE);
                break;

            case "Position":
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
                    magnetometer.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null)
                    magnetometerUncalibrated.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
                    proximity.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null)
                    gameRotation.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null)
                    geoMagneticVector.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
                    orientation.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }




    }

}
