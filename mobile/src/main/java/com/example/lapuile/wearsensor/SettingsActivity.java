package com.example.lapuile.wearsensor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private String sensorName;

    private Switch mSwitch;
    private EditText editFrequency;
    private EditText editPeriod;
    private Button saveButton;
    private TextView currentFrequency;
    private TextView currentPeriod;
    private TextView sensorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        sensorName = getIntent().getStringExtra("sensor_type");

        mSwitch = findViewById(R.id.switchDefault);
        editFrequency = findViewById(R.id.editFrequency);
        editPeriod = findViewById(R.id.editPeriod);
        saveButton = findViewById(R.id.saveChanges);
        currentFrequency = findViewById(R.id.textCurrentFrequency);
        currentPeriod = findViewById(R.id.textCurrentPeriod);
        sensorType = findViewById(R.id.text_sensor_type);
        sensorType.setText(getResources().getString(R.string.sensor_name_settings) + " " + sensorName);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int freInt = Integer.parseInt(editFrequency.getText().toString());
                int perInt = Integer.parseInt(editPeriod.getText().toString());
                if (freInt <= perInt) {
                    String freKey = getString(R.string.frequencyKaa) + sensorName;
                    String perKey = getString(R.string.periodKaa) + sensorName;
                    mEditor.putInt(freKey, freInt);
                    mEditor.putInt(perKey, perInt);
                    mEditor.apply();
                }
                editFrequency.setText("");
                editPeriod.setText("");
                showParameters();
            }
        });

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String prefKey = getString(R.string.defaultKaa) + sensorName;
                mEditor.putBoolean(prefKey, mSwitch.isChecked());
                mEditor.commit();
                showParameters();
            }
        });

    }

    private void checkSharedPreferences (){
        // checks the current preferences
        String prefKey = getString(R.string.defaultKaa) + sensorName;
        boolean switchDefault = mPreferences.getBoolean(prefKey, true);
        // sets the current preferences
        mSwitch.setChecked(switchDefault);
        showParameters();
    }

    private void showParameters(){
        int frequency;
        int period;
        if (!mSwitch.isChecked()){
            String freKey = getString(R.string.frequencyKaa) + sensorName;
            String perKey = getString(R.string.periodKaa) + sensorName;
            frequency = mPreferences.getInt(freKey, 1000);
            period = mPreferences.getInt(perKey, 5000);
        } else {
            Intent intent = getIntent();
            frequency = intent.getIntExtra("frequency", 1);
            period = intent.getIntExtra("period",5);
        }
        currentFrequency.setText(getResources().getString(R.string.current_frequency_text) + " " + frequency);
        currentPeriod.setText(getResources().getString(R.string.current_period_text) + " " + period);
    }

}