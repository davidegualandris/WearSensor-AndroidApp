package com.example.lapuile.wearsensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SelectActivity extends AppCompatActivity implements DataClient.OnDataChangedListener,
        MessageClient.OnMessageReceivedListener {

    public static final String TAG = "SelectActivity";

    private ArrayList<Integer> selectList = new ArrayList<Integer>();

    public static final String EXTRA_MESSAGE =
            "com.example.android.wearsensor.extra.MESSAGE";
    private SensorManager mSensorManager;
    private static final int PERMISSION_REQUEST_CODE = 200;

    final ArrayList<String> listp = new ArrayList<String>();

    private String choice;
    private static final String LIST_PATH = "/list";

    private static final String MOTION_KEY = "motion";
    private static final String ENVIRONMENTAL_KEY = "environmental";
    private static final String POSITION_KEY = "position";

    Button accelerometer;
    Button accelerometerUncalibrated;
    Button magnetometer;
    Button magnetometerUncalibrated;
    Button gravity;
    Button gyroscope;
    Button gyroscopeUncalibrated;
    Button linear_acceleration;
    Button light;
    Button proximity;
    Button ambient_temperature;
    Button pressure;
    Button humidity;
    Button rotation_vector;
    Button temperature;
    Button gameRotation;
    Button geoMagneticVector;
    Button orientation;
    Button stepCounter;
    Button poseSix;
    Button heartRate;
    Button heartBeat;

    // SISTEMARE PARTENDO DA QUA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        choice = getIntent().getStringExtra("Type");
        Log.i(TAG, "CHOICE : " + choice);


        accelerometer = (Button) findViewById(R.id.accelerometer_button);
        accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Motion")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Accelerometer");
                    startActivity(intent);
                } else if (choice.equals("WearMotion")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearAccelerometer");
                    startActivity(intent);
                }
            }

        });
        accelerometerUncalibrated = (Button) findViewById(R.id.accelerometer_uncalibrated_button);
        accelerometerUncalibrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Motion")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "AccelerometerUncalibrated");
                    startActivity(intent);
                } else if (choice.equals("WearMotion")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearAccelerometerUncalibrated");
                    startActivity(intent);
                }
            }

        });
        magnetometer = (Button) findViewById(R.id.magnetometer_button);
        magnetometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Position")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Magnetometer");
                    startActivity(intent);
                } else if (choice.equals("WearPosition")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearMagnetometer");
                    startActivity(intent);
                }
            }
        });
        magnetometerUncalibrated = (Button) findViewById(R.id.magnetometer_uncalibrated_button);
        magnetometerUncalibrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Position")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "MagnetometerUncalibrated");
                    startActivity(intent);
                } else if (choice.equals("WearPosition")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearMagnetometerUncalibrated");
                    startActivity(intent);
                }
            }
        });

        gravity = (Button) findViewById(R.id.gravity_button);
        gravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Motion")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Gravity");
                    startActivity(intent);
                } else if (choice.equals("WearMotion")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearGravity");
                    startActivity(intent);
                }
            }


        });
        gyroscope = (Button) findViewById(R.id.gyroscope_button);
        gyroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Motion")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Gyroscope");
                    startActivity(intent);
                } else if (choice.equals("WearMotion")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearGyroscope");
                    startActivity(intent);
                }
            }
        });

        gyroscopeUncalibrated = (Button) findViewById(R.id.gyroscope_uncalibrated_button);
        gyroscopeUncalibrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Motion")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "GyroscopeUncalibrated");
                    startActivity(intent);
                } else if (choice.equals("WearMotion")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearGyroscopeUncalibrated");
                    startActivity(intent);
                }
            }
        });
        linear_acceleration = (Button) findViewById(R.id.linear_acc_button);
        linear_acceleration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Motion")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "LinearAcceleration");
                    startActivity(intent);
                } else if (choice.equals("WearMotion")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearLinearAcceleration");
                    startActivity(intent);
                }
            }
        });

        light = (Button) findViewById(R.id.light_button);
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Environmental")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Light");
                    startActivity(intent);
                } else if (choice.equals("WearEnvironmental")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearLight");
                    startActivity(intent);
                }
            }
        });

        proximity = (Button) findViewById(R.id.proximity_button);
        proximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Position")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Proximity");
                    startActivity(intent);
                } else if (choice.equals("WearPosition")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearProximity");
                    startActivity(intent);
                }
            }
        });

        ambient_temperature = (Button) findViewById(R.id.ambient_temperature_button);
        ambient_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Environmental")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "AmbientTemperature");
                    startActivity(intent);
                } else if (choice.equals("WearEnvironmental")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearAmbientTemperature");
                    startActivity(intent);
                }
            }
        });

        pressure = (Button) findViewById(R.id.pressure_button);
        pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Environmental")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Pressure");
                    startActivity(intent);
                } else if (choice.equals("WearEnvironmental")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearPressure");
                    startActivity(intent);
                }
            }
        });

        humidity = (Button) findViewById(R.id.humidity_button);
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Environmental")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Humidity");
                    startActivity(intent);
                } else if (choice.equals("WearEnvironmental")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearHumidity");
                    startActivity(intent);
                }
            }
        });
        rotation_vector = (Button) findViewById(R.id.rotation_vector_button);
        rotation_vector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Motion")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "RotationVector");
                    startActivity(intent);
                } else if (choice.equals("WearMotion")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearRotationVector");
                    startActivity(intent);
                }
            }
        });
        temperature = (Button) findViewById(R.id.temperature_button);
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Environmental")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Temperature");
                    startActivity(intent);
                } else if (choice.equals("WearEnvironmental")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearTemperature");
                    startActivity(intent);
                }
            }
        });

        gameRotation = (Button) findViewById(R.id.game_rotation_button);
        gameRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Position")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Game");
                    startActivity(intent);
                } else if (choice.equals("WearPosition")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearGame");
                    startActivity(intent);
                }
            }
        });

        geoMagneticVector = (Button) findViewById(R.id.geomagnetic_vector_button);
        geoMagneticVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                if (choice.equals("Position")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "GeoVector");
                    startActivity(intent);
                } else if (choice.equals("WearPosition")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearGeoVector");
                    startActivity(intent);
                }
            }
        });

        //DEPRECATED
        orientation = (Button) findViewById(R.id.orientation_button);
        orientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Position")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Orientation");
                    startActivity(intent);
                } else if (choice.equals("WearPosition")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearOrientation");
                    startActivity(intent);
                }
            }
        });

        stepCounter = (Button) findViewById(R.id.step_counter_button);
        stepCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Motion")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "StepCounter");
                    startActivity(intent);
                } else if (choice.equals("WearMotion")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearStepCounter");
                    startActivity(intent);
                }
            }
        });

        poseSix = (Button) findViewById(R.id.pose_6_dof_button);
        poseSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Position")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "Pose6Dof");
                    startActivity(intent);
                } else if (choice.equals("WearPosition")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearPose6Dof");
                    startActivity(intent);
                }
            }
        });

        heartRate = (Button) findViewById(R.id.heart_rate_button);
        heartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Position")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "HeartRate");
                    startActivity(intent);
                } else if (choice.equals("WearPosition")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearHeartRate");
                    startActivity(intent);
                }
            }
        });

        heartBeat = (Button) findViewById(R.id.pose_6_dof_button);
        heartBeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

                if (choice.equals("Position")) {

                    intent = new Intent(SelectActivity.this, SensorData.class);
                    intent.putExtra("Type", "HeartBeat");
                    startActivity(intent);
                } else if (choice.equals("WearPosition")) {
                    intent = new Intent(SelectActivity.this, WatchDataActivity.class);
                    intent.putExtra("Type", "WearHeartBeat");
                    startActivity(intent);
                }
            }
        });


//        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        switch (choice) {


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
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) != null)
                    accelerometerUncalibrated.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null)
                    gyroscopeUncalibrated.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
                    stepCounter.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null)
                    stepCounter.setVisibility(View.VISIBLE);
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT) != null)
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
                if (mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF) != null)
                    poseSix.setVisibility(View.VISIBLE);

                break;


            default:
                break;
        }
        if (choice.equals("WearMotion") || choice.equals("WearEnvironmental") || choice.equals("WearPosition")) {

            new StartWearableActivityTask().execute();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();

        Wearable.getDataClient(this).addListener(this);
        Wearable.getMessageClient(this).addListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        Wearable.getDataClient(this).removeListener(this);
        Wearable.getMessageClient(this).removeListener(this);


    }


    @WorkerThread
    private void sendStartActivityMessage(String node) {


        String msg = choice;
        byte[] msgByte = msg.getBytes();
        Task<Integer> sendMessageTask;

        sendMessageTask = Wearable.getMessageClient(this).sendMessage(node, LIST_PATH, msgByte);


        try {
            // Block on a task and get the result synchronously (because this is on a background
            // thread). sendMessage(START_PHONE_ACTIVITY, "");
            Integer result = Tasks.await(sendMessageTask);
            Log.i(TAG, "Message sent: " + result + " to " + LIST_PATH + node);

        } catch (ExecutionException exception) {
            Log.e(TAG, "Task failed: " + exception);

        } catch (InterruptedException exception) {
            Log.e(TAG, "Interrupt occurred: " + exception);
        }
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {

        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();

                if (item.getUri().getPath().equals(LIST_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    item.getData();
                    switch(choice){
                        case "WearMotion":
                            selectList = dataMap.getIntegerArrayList(MOTION_KEY);
                            createList(selectList);
                            break;
                        case"WearEnvironmental":
                            selectList = dataMap.getIntegerArrayList(ENVIRONMENTAL_KEY);
                            createList(selectList);
                            break;
                        case "WearPosition":
                            selectList = dataMap.getIntegerArrayList(POSITION_KEY);
                            createList(selectList);
                            break;
                        default:
                            break;
                    }



                }

            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }


    private void createList(final ArrayList<Integer> listWatch) {


        if (choice.equals("WearMotion")) {
            for (int i = 0; i < listWatch.size(); i++) {
                if (Sensor.TYPE_ACCELEROMETER == selectList.get(i))
                    accelerometer.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_GRAVITY == selectList.get(i))
                    gravity.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_GYROSCOPE == selectList.get(i))
                    gyroscope.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_LINEAR_ACCELERATION == selectList.get(i))
                    linear_acceleration.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_ROTATION_VECTOR == selectList.get(i))
                    rotation_vector.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_ACCELEROMETER_UNCALIBRATED == selectList.get(i))
                    accelerometerUncalibrated.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_GYROSCOPE_UNCALIBRATED == selectList.get(i))
                    gyroscopeUncalibrated.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_STEP_COUNTER == selectList.get(i))
                    stepCounter.setVisibility(View.VISIBLE);
            }
        }

        if (choice.equals("WearEnvironmental")) {
            for (int i = 0; i < listWatch.size(); i++) {
                if (Sensor.TYPE_AMBIENT_TEMPERATURE == selectList.get(i))
                    ambient_temperature.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_LIGHT == selectList.get(i))
                    light.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_PRESSURE == selectList.get(i))
                    pressure.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_RELATIVE_HUMIDITY == selectList.get(i))
                    humidity.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_TEMPERATURE == selectList.get(i))
                    temperature.setVisibility(View.VISIBLE);

            }
        }

        if (choice.equals("WearPosition")) {
            for (int i = 0; i < listWatch.size(); i++) {
                if (Sensor.TYPE_MAGNETIC_FIELD == selectList.get(i))
                    magnetometer.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED == selectList.get(i))
                    magnetometerUncalibrated.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR == selectList.get(i))
                    geoMagneticVector.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_GAME_ROTATION_VECTOR == selectList.get(i))
                    gameRotation.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_PROXIMITY == selectList.get(i))
                    proximity.setVisibility(View.VISIBLE);
                if (Sensor.TYPE_ORIENTATION == selectList.get(i))
                    orientation.setVisibility(View.VISIBLE);

            }
        }


    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {

    }


    private class StartWearableActivityTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            Collection<String> nodes = getNodes();
            for (String node : nodes) {

                sendStartActivityMessage(node);
            }
            return null;
        }
    }

    @WorkerThread
    private Collection<String> getNodes() {
        HashSet<String> results = new HashSet<>();

        Task<List<Node>> nodeListTask =
                Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();

        try {
            // Block on a task and get the result synchronously (because this is on a background
            // thread).
            List<Node> nodes = Tasks.await(nodeListTask);

            for (Node node : nodes) {
                results.add(node.getId());
            }

        } catch (ExecutionException exception) {
            Log.e(TAG, "Task failed: " + exception);

        } catch (InterruptedException exception) {
            Log.e(TAG, "Interrupt occurred: " + exception);
        }

        return results;
    }


}
