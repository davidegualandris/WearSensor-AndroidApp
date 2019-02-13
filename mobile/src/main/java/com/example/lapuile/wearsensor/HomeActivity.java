package com.example.lapuile.wearsensor;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Type"));

        choice = getIntent().getStringExtra("Type");


        Button motion = (Button) findViewById(R.id.motion_button);
        motion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(HomeActivity.this, SelectActivity.class);
                if (choice.equals("Phone"))
                    intent.putExtra("Type", "Motion");
                else if (choice.equals("Wear"))
                    intent.putExtra("Type", "WearMotion");
                else
                    intent.putExtra("Type", "BluetoothMotion");
                startActivity(intent);

            }
        });

        Button environmental = (Button) findViewById(R.id.environmental_button);
        environmental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(HomeActivity.this, SelectActivity.class);
                if (choice.equals("Phone"))
                    intent.putExtra("Type", "Environmental");
                else if (choice.equals("Wear"))
                    intent.putExtra("Type", "WearEnvironmental");
                else
                    intent.putExtra("Type", "BluetoothMotion");

                startActivity(intent);

            }
        });


        Button position = (Button) findViewById(R.id.position_button);
        position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(HomeActivity.this, SelectActivity.class);
                if (choice.equals("Phone"))
                    intent.putExtra("Type", "Position");
                else if (choice.equals("Wear"))
                    intent.putExtra("Type", "WearPosition");
                else
                    intent.putExtra("Type", "BluetoothMotion");
                startActivity(intent);

            }
        });

        Button sensorList = (Button) findViewById(R.id.sensor_list_button);
        sensorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Phone")) {

                    intent = new Intent(HomeActivity.this, SensorData.class);
                    intent.putExtra("Type", "SensorList");
                    startActivity(intent);

                } else if (choice.equals("Wear")) {
                    intent = new Intent(HomeActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearSensorList");
                    startActivity(intent);

                }


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
