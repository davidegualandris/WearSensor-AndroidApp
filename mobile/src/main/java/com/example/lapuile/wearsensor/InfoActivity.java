package com.example.lapuile.wearsensor;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {


    private TextView vendor;
    private TextView version;
    private TextView resolution;
    private TextView maxRange;
    private TextView power;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
       //GESTIRE CON GLI INTENT

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle("Info");



        vendor = findViewById(R.id.sensor_vendor);
        version = findViewById(R.id.version);
        resolution = findViewById(R.id.sensor_resolution);
        maxRange = findViewById(R.id.max_range);
        power = findViewById(R.id.sensor_power);
        description = findViewById(R.id.sensor_description);

        setInfo();


    }



    private void setInfo(){

        vendor.setText(getIntent().getStringExtra("Vendor"));


        version.setText(String.valueOf(getIntent().getIntExtra("Version", 0)));
        resolution.setText(String.valueOf(getIntent().getFloatExtra("Resolution", 0.0f)));
        power.setText(String.valueOf(getIntent().getFloatExtra("Power", 0.0f)+ " mA"));
        maxRange.setText(String.valueOf(getIntent().getFloatExtra("MaxRange", 0.0f)));
        description.setText(getIntent().getStringExtra("Description"));

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
