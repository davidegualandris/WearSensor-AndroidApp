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

        Button phoneSensor = (Button) findViewById(R.id.sensor_phone_button);
        phoneSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("Type", "Phone");
                startActivity(intent);

            }
        });

        Button watchSensor = (Button) findViewById(R.id.sensor_wear_button);
        watchSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("Type", "Wear");
                startActivity(intent);

            }
        });
    }
    }

