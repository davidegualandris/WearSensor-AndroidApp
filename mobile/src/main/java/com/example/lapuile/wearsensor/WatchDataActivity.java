package com.example.lapuile.wearsensor;


import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.renderscript.Element;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static android.util.Config.LOGD;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class WatchDataActivity extends AppCompatActivity implements DataClient.OnDataChangedListener,
        MessageClient.OnMessageReceivedListener,
        CapabilityClient.OnCapabilityChangedListener {

    private final static int INTERVAL = 1000;


    boolean god;

    public static final String TAG = "BasicSensorsApi";

    private static final String SENSOR_KEY = "sensor";

    private static final String LIST_PATH = "/sensorList";
    private static final String LIST_KEY = "list";

    private static final String CAPABILITY_NAME = "watch_server";
    public static final String SENSOR_REQUEST_MESSAGE_PATH = "/sensor";
    private static final String NAME_KEY = "name";
    private static final String TYPE_KEY = "type";
    private String transcriptionNodeId = null;

    private String intentChoice;

    private float[] copyValue;

    private String sensorName;
    private int sensorType;
    String decision;

    ArrayList<String> exceList = new ArrayList<String>();

    ArrayList<String> listGlobal;


    ListView sensor_list;
    private static final int PERMISSION_REQUEST_CODE = 200;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_data);

        decision = "";

        sensor_list = findViewById(R.id.sensor_list_wear);

        intentChoice = getIntent().getStringExtra("Type");

        if (intentChoice.equals("WearSensorList")) {

            Button play = (findViewById(R.id.play_button));
            play.setVisibility(View.INVISIBLE);
            Button pause = (findViewById(R.id.pause_button));
            pause.setVisibility(View.INVISIBLE);
            Wearable.getDataClient(this).addListener(this);
            Wearable.getMessageClient(this).addListener(this);
            Wearable.getCapabilityClient(this)
                    .addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE);




            new StartWearableActivityTask().execute();


        }

    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

        decision = "stop";
        Wearable.getDataClient(this).removeListener(this);
        Wearable.getMessageClient(this).removeListener(this);
        Wearable.getCapabilityClient(this).removeListener(this);

    }


    @Override
    protected void onStop() {
        super.onStop();
        Wearable.getDataClient(this).removeListener(this);
        Wearable.getMessageClient(this).removeListener(this);
        Wearable.getCapabilityClient(this).removeListener(this);
    }

    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo) {
        Log.i(TAG, "onCapabilityChanged: " + capabilityInfo);

    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        Log.i(TAG, "DATACHANGED");

        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();

                if (item.getUri().getPath().equals(LIST_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    item.getData();
                    listGlobal = dataMap.getStringArrayList(LIST_KEY);
                    updateListWear(listGlobal);

                }
                if (item.getUri().getPath().equals(SENSOR_REQUEST_MESSAGE_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    item.getData();
                    switch (intentChoice) {
                        case "WearAccelerometer":
                            if (dataMap.getFloatArray("Accelerometer") != null) {
                                updateSensorWear(dataMap.getFloatArray("Accelerometer"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearMagnetometer":
                            if (dataMap.getFloatArray("Magnetometer") != null)
                                updateSensorWear(dataMap.getFloatArray("Magnetometer"));
                            sensorName = dataMap.getString(NAME_KEY);
                            sensorType = dataMap.getInt(TYPE_KEY);
                            break;
                        case "WearGravity":
                            if (dataMap.getFloatArray("Gravity") != null) {
                                updateSensorWear(dataMap.getFloatArray("Gravity"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearGyroscope":
                            if (dataMap.getFloatArray("Gyroscope") != null) {
                                updateSensorWear(dataMap.getFloatArray("Gyroscope"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearLinearAcceleration":
                            if (dataMap.getFloatArray("LinearAcceleration") != null) {
                                updateSensorWear(dataMap.getFloatArray("LinearAcceleration"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearRotationVector":
                            if (dataMap.getFloatArray("RotationVector") != null) {
                                updateSensorWear(dataMap.getFloatArray("RotationVector"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearGame":
                            if (dataMap.getFloatArray("Game") != null) {
                                updateSensorWear(dataMap.getFloatArray("Game"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearGeoVector":
                            if (dataMap.getFloatArray("Geo") != null) {
                                updateSensorWear(dataMap.getFloatArray("Geo"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearOrientation":
                            if (dataMap.getFloatArray("Orientation") != null) {
                                updateSensorWear(dataMap.getFloatArray("Orientation"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearPose6Dof":
                            if (dataMap.getFloatArray("Pose6Dof") != null) {
                                updateSensorWear(dataMap.getFloatArray("Pose6Dof"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;

                        case "WearLight":
                            if (dataMap.getFloatArray("Light") != null){
                                updateSensorWear(dataMap.getFloatArray("Light"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                    }
                            break;

                        case "WearProximity":
                            if(dataMap.getFloatArray("Proximity")!= null) {
                                updateSensorWear(dataMap.getFloatArray("Proximity"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearAmbientTemperature":
                            if(dataMap.getFloatArray("AmbientTemperature")!= null) {
                                updateSensorWear(dataMap.getFloatArray("AmbientTemperature"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearPressure":
                            if(dataMap.getFloatArray("Pressure")!= null) {
                                updateSensorWear(dataMap.getFloatArray("Pressure"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearHumidity":
                            if(dataMap.getFloatArray("Humidity")!= null) {
                                updateSensorWear(dataMap.getFloatArray("Humidity"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearStepCounter":
                            if(dataMap.getFloatArray("StepCounter")!= null) {
                                updateSensorWear(dataMap.getFloatArray("StepCounter"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearTemperature":
                            if(dataMap.getFloatArray("Temperature")!= null) {
                                updateSensorWear(dataMap.getFloatArray("Temperature"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearHeartRate":
                            if(dataMap.getFloatArray("HeartRate")!= null) {
                                updateSensorWear(dataMap.getFloatArray("HeartRate"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearHeartBeat":
                            if(dataMap.getFloatArray("HeartBeat")!= null)
                            updateSensorWear(dataMap.getFloatArray("HeartBeat"));
                            sensorName = dataMap.getString(NAME_KEY);
                            sensorType = dataMap.getInt(TYPE_KEY);
                            break;


                        case "WearAccelerometerUncalibrated":
                            if(dataMap.getFloatArray("AccelerometerUncalibrated")!= null) {
                                updateSensorWear(dataMap.getFloatArray("AccelerometerUncalibrated"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;
                        case "WearGyroscopeUncalibrated":
                            if(dataMap.getFloatArray("GyroscopeUncalibrated")!= null) {
                                updateSensorWear(dataMap.getFloatArray("GyroscopeUncalibrated"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                        }
                            break;
                        case "WearMagnetometerUncalibrated":
                            if(dataMap.getFloatArray("MagnetometerUncalibrated")!= null) {
                                updateSensorWear(dataMap.getFloatArray("MagnetometerUncalibrated"));
                                sensorName = dataMap.getString(NAME_KEY);
                                sensorType = dataMap.getInt(TYPE_KEY);
                            }
                            break;


                        default:
                            break;
                    }


                }



            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                Log.i(TAG, "DATADELEETED");
            }
        }
    }


    private void updateListWear(ArrayList<String> lista) {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.my_layout, lista);

        sensor_list.setAdapter(adapter);
    }


    private void updateSensorWear(float[] sensorData) {
        copyValue = sensorData;
        final ArrayList<String> listp = new ArrayList<String>();

        switch (sensorType) {

            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_GRAVITY:

                listp.add(getString(R.string.acc_grav_x_text, sensorData[0]));
                listp.add(getResources().getString(R.string.acc_grav_y_text, sensorData[1]));
                listp.add(getResources().getString(R.string.acc_grav_z_text, sensorData[2]));
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                listp.add(getString(R.string.magnetic_field_x_text, sensorData[0]));
                listp.add(getString(R.string.magnetic_field_y_text, sensorData[1]));
                listp.add(getString(R.string.magnetic_field_z_text, sensorData[2]));
                break;

            case Sensor.TYPE_GYROSCOPE:
                listp.add(getString(R.string.gyroscope_x_text, sensorData[0]));
                listp.add(getString(R.string.gyroscope_y_text, sensorData[1]));
                listp.add(getString(R.string.gyroscope_z_text, sensorData[2]));
                break;

            case Sensor.TYPE_ROTATION_VECTOR:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:

                listp.add(getResources().getString(R.string.rotation_vector_text_x, sensorData[0]));
                listp.add(getResources().getString(R.string.rotation_vector_text_y, sensorData[1]));
                listp.add(getResources().getString(R.string.rotation_vector_text_z, sensorData[2]));
                listp.add(getResources().getString(R.string.rotation_vector_cos, sensorData[3]));



                if(Sensor.TYPE_GAME_ROTATION_VECTOR != sensorType)
                    listp.add(getResources().getString(R.string.rotation_vector_estimated, sensorData[4]));

                break;
            case Sensor.TYPE_ORIENTATION:
                listp.add(getString(R.string.orientation_x_text, sensorData[0]));
                listp.add(getString(R.string.orientation_y_text, sensorData[1]));
                listp.add(getString(R.string.orientation_z_text, sensorData[2]));

                break;

            case Sensor.TYPE_LIGHT:
                listp.add(getString(R.string.light_text, sensorData[0]));
                break;
            case Sensor.TYPE_PROXIMITY:
                listp.add(getString(R.string.proximity, sensorData[0]));
                break;

            case Sensor.TYPE_PRESSURE:
                listp.add(getString(R.string.pressure_text, sensorData[0]));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                listp.add(getString(R.string.humidity_text, sensorData[0])+ "%");
                break;
            case Sensor.TYPE_STEP_COUNTER:
                listp.add(getString(R.string.step_counter_text, sensorData[0]));
                break;
            case Sensor.TYPE_TEMPERATURE:
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                listp.add(getString(R.string.ambient_temperature, sensorData[0]));
                break;
            case Sensor.TYPE_HEART_RATE:
                listp.add(getString(R.string.heart_rate_text, sensorData[0]));
                break;
            case Sensor.TYPE_HEART_BEAT:
                listp.add(getString(R.string.onedimension_text, sensorData[0]));
                break;

            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                listp.add(getResources().getString(R.string.acc_unc_x, sensorData[0]));
                listp.add(getResources().getString(R.string.acc_unc_y, sensorData[1]));
                listp.add(getResources().getString(R.string.acc_unc_z, sensorData[2]));

                listp.add(getResources().getString(R.string.acc_unc_x_2, sensorData[3]));
                listp.add(getResources().getString(R.string.acc_unc_y_2, sensorData[4]));
                listp.add(getResources().getString(R.string.acc_unc_z_2, sensorData[5]));

                break;

            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                listp.add(getResources().getString(R.string.gyrosc_unc_x, sensorData[0]));
                listp.add(getResources().getString(R.string.gyrosc_unc_y, sensorData[1]));
                listp.add(getResources().getString(R.string.gyrosc_unc_z, sensorData[2]));


                listp.add(getResources().getString(R.string.gyrosc_unc_x_2, sensorData[3]));
                listp.add(getResources().getString(R.string.gyrosc_unc_y_2, sensorData[4]));
                listp.add(getResources().getString(R.string.gyrosc_unc_z_2, sensorData[5]));

            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                listp.add(getResources().getString(R.string.magnet_unc_x, sensorData[0]));
                listp.add(getResources().getString(R.string.magnet_unc_y, sensorData[1]));
                listp.add(getResources().getString(R.string.magnet_unc_z, sensorData[2]));

                listp.add(getResources().getString(R.string.magnet_unc_x_2, sensorData[3]));
                listp.add(getResources().getString(R.string.magnet_unc_y_2, sensorData[4]));
                listp.add(getResources().getString(R.string.magnet_unc_z_2, sensorData[5]));


                break;

            case Sensor.TYPE_POSE_6DOF:
                listp.add(getResources().getString(R.string.acc_grav_y_text, sensorData[0]));
                listp.add(getResources().getString(R.string.acc_grav_z_text, sensorData[1]));
                listp.add(getResources().getString(R.string.acc_grav_x_text, sensorData[2]));
                listp.add(getResources().getString(R.string.pose_6_dof_cos, sensorData[3]));
                listp.add(getResources().getString(R.string.translation_along_x, sensorData[4]));
                listp.add(getResources().getString(R.string.translation_along_y, sensorData[5]));
                listp.add(getResources().getString(R.string.translation_along_z, sensorData[6]));
                listp.add(getResources().getString(R.string.delta_quat_x, sensorData[7]));
                listp.add(getResources().getString(R.string.delta_quat_y, sensorData[8]));
                listp.add(getResources().getString(R.string.delta_quat_z, sensorData[9]));
                listp.add(getResources().getString(R.string.delta_quat_rot_cos, sensorData[10]));
                listp.add(getResources().getString(R.string.delta_transl_x, sensorData[11]));
                listp.add(getResources().getString(R.string.delta_transl_y, sensorData[12]));
                listp.add(getResources().getString(R.string.delta_transl_z, sensorData[13]));
                listp.add(getResources().getString(R.string.sequence_number, sensorData[14]));

                break;

            default:
                break;
        }
        exceList = listp;


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.my_layout, listp);

        sensor_list.setAdapter(adapter);
    }


    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        Log.i(
                TAG,
                "onMessageReceived() A message from watch was received:"
                        + messageEvent.getRequestId()
                        + " "
                        + messageEvent.getPath());

    }

    public void playSensorDataWear(View view) {
        // mDataClient = Wearable.getDataClient(this);
        decision = "start";
        Wearable.getDataClient(this).addListener(this);
        Wearable.getMessageClient(this).addListener(this);
        Wearable.getCapabilityClient(this)
                .addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE);
        new StartWearableActivityTask().execute();

    }

    public void pauseSensorDataWear(View view) {

        decision = "stop";
        new StartWearableActivityTask().execute();
        Wearable.getDataClient(this).removeListener(this);
        Wearable.getMessageClient(this).removeListener(this);
        Wearable.getCapabilityClient(this).removeListener(this);


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
            Toast.makeText(this, "You have to connect your wear",
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, "Task failed: " + exception);

        } catch (InterruptedException exception) {
            Log.e(TAG, "Interrupt occurred: " + exception);
        }

        return results;
    }


    @WorkerThread
    private void sendStartActivityMessage(String node) {

        Task<Integer> sendMessageTask;
        if (decision.equals("stop")) {
            String msg = decision;
            byte[] msgByte = msg.getBytes();
            sendMessageTask = Wearable.getMessageClient(this).sendMessage(node, SENSOR_REQUEST_MESSAGE_PATH, msgByte);
            try {
                // Block on a task and get the result synchronously (because this is on a background
                // thread). sendMessage(START_PHONE_ACTIVITY, "");
                Integer result = Tasks.await(sendMessageTask);
                Log.i(TAG, "Message sent: " + result + " to " + node + msg);

            } catch (ExecutionException exception) {
                Log.e(TAG, "Task failed: " + exception);

            } catch (InterruptedException exception) {
                Log.e(TAG, "Interrupt occurred: " + exception);
            }
        } else {


            String msg = intentChoice;
            byte[] msgByte = msg.getBytes();


            if (intentChoice.equals("WearSensorList"))
                sendMessageTask = Wearable.getMessageClient(this).sendMessage(node, LIST_PATH, msgByte);
            else
                sendMessageTask = Wearable.getMessageClient(this).sendMessage(node, SENSOR_REQUEST_MESSAGE_PATH, msgByte);


            try {
                // Block on a task and get the result synchronously (because this is on a background
                // thread). sendMessage(START_PHONE_ACTIVITY, "");
                Integer result = Tasks.await(sendMessageTask);
                Log.i(TAG, "Message sent: " + result + " to " + msg);

            } catch (ExecutionException exception) {
                Log.e(TAG, "Task failed: " + exception);

            } catch (InterruptedException exception) {
                Log.e(TAG, "Interrupt occurred: " + exception);
            }
        }
    }



    private class StartWearableActivityTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... args) {

            Collection<String> nodes = getNodes();
            for (String node : nodes) {

                Log.i(TAG, "ISCANCELLED  " + isCancelled());
                if (!isCancelled()) {

                    sendStartActivityMessage(node);
                }
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getApplicationContext(), "Stopping...",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



        }
    }








    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save_action:
                saveSensorData();
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


            if (intentChoice.equals("WearSensorList")) {

                if (isExternalStorageWritable() && listGlobal != null) {
                    ExcelSheet listSheet = new ExcelSheet(listGlobal);
                    listSheet.exportListWearToExcel();

                } else
                    Toast.makeText(this, "External Storage isn't writable",
                            Toast.LENGTH_LONG).show();
            } else if (isExternalStorageWritable() && sensorName != null && copyValue != null && exceList != null) {

                ExcelSheet dataSheet = new ExcelSheet(sensorName, copyValue, exceList);
                dataSheet.exportWearToExcel();
                Toast.makeText(this, "Saved",
                        Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, "External Storage isn't writable",
                        Toast.LENGTH_LONG).show();

        }
    }
}




