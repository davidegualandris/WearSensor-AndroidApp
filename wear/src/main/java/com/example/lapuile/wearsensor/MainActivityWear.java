package com.example.lapuile.wearsensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivityWear extends WearableActivity {

    public static final String EXTRA_MESSAGE =
            "com.example.android.wearsensor.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wear);

        Button listSensor = (Button) findViewById(R.id.sensor_button_wear);
        listSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "list");
                startActivity(intent);

            }
        });

        Button accelerometer = (Button) findViewById(R.id.accelerometer_button_wear);
        accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "Accelerometer");
                startActivity(intent);

            }
        });
        Button magnetometer = (Button) findViewById(R.id.magnetometer_button_wear);
        magnetometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "Magnetometer");
                startActivity(intent);

            }
        });

        Button gravity = (Button) findViewById(R.id.gravity_button_wear);
        gravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "Gravity");
                startActivity(intent);

            }
        });
        Button gyroscope = (Button) findViewById(R.id.gyroscope_button_wear);
        gyroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "Gyroscope");
                startActivity(intent);

            }
        });
        Button linear_acceleration = (Button) findViewById(R.id.linear_acc_button_wear);
        linear_acceleration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "LinearAcceleration");
                startActivity(intent);

            }
        });
        Button light = (Button) findViewById(R.id.light_button_wear);
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "Light");
                startActivity(intent);

            }
        });
        Button proximity = (Button) findViewById(R.id.promixity_button_wear);
        proximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "Proximity");
                startActivity(intent);

            }
        });

        Button ambient_temperature = (Button) findViewById(R.id.ambient_temperature_button_wear);
        ambient_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "AmbientTemperature");
                startActivity(intent);

            }
        });

        Button pressure = (Button) findViewById(R.id.pressure_button_wear);
        pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "Pressure");
                startActivity(intent);

            }
        });

        Button humidity = (Button) findViewById(R.id.humidity_button_wear);
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "Humidity");
                startActivity(intent);

            }
        });
        Button rotation_vector = (Button) findViewById(R.id.rotation_vector_button_wear);
        rotation_vector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "RotationVector");
                startActivity(intent);

            }
        });
        Button temperature = (Button) findViewById(R.id.temperature_button_wear);
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivityWear.this, SensorDataWear.class);
                intent.putExtra("Type", "Temperature");
                startActivity(intent);

            }
        });

    }
}


