package com.example.lapuile.wearsensor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button phoneSensor = (Button) findViewById(R.id.sensor_list_button);
        phoneSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("Type", "SensorList");
                startActivity(intent);

            }
        });

        Button watchSensor = (Button) findViewById(R.id.watch_sensor_button);
        watchSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivity.this, SelectActivity.class);
                intent.putExtra("Type", "WatchSensor");
                startActivity(intent);

            }
        });
    }
    }

