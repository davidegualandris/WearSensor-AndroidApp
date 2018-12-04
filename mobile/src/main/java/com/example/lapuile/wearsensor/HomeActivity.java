package com.example.lapuile.wearsensor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Button motion = (Button) findViewById(R.id.motion_button);
        motion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(HomeActivity.this, SelectActivity.class);
                intent.putExtra("Type", "Motion");
                startActivity(intent);

            }
        });

        Button environmental = (Button) findViewById(R.id.environmental_button);
        environmental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(HomeActivity.this, SelectActivity.class);
                intent.putExtra("Type", "Environmental");
                startActivity(intent);

            }
        });

        Button position = (Button) findViewById(R.id.position_button);
        position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(HomeActivity.this, SelectActivity.class);
                intent.putExtra("Type", "Position");
                startActivity(intent);

            }
        });

        Button sensorList = (Button) findViewById(R.id.sensor_list_button);
        sensorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(HomeActivity.this, SelectActivity.class);
                intent.putExtra("Type", "SensorList");
                startActivity(intent);

            }
        });
    }
}
