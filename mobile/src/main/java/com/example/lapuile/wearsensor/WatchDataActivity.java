package com.example.lapuile.wearsensor;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.renderscript.Element;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

    public static final String TAG = "BasicSensorsApi";

    private static final String SENSOR_KEY = "sensor";

    private static final String LIST_PATH = "/list";
    private static final String LIST_KEY = "list";

    private static final String CAPABILITY_NAME = "watch_server";
    public static final String SENSOR_REQUEST_MESSAGE_PATH = "/sensor";
    private static final String NAME_KEY = "name";
    private static final String TYPE_KEY = "type";
    private String transcriptionNodeId = null;

    private String intentChoice;

    private float [] copyValue;

    private String sensorName;
    private int sensorType;

    final ArrayList<String> exceList = new ArrayList<String>();

    ArrayList<String> listGlobal;

    ListView sensor_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_data);


        sensor_list = findViewById(R.id.sensor_list_wear);

        intentChoice = getIntent().getStringExtra("Type");

        if (intentChoice.equals("WatchList")) {

            ImageButton play = (findViewById(R.id.play_button));
            play.setVisibility(View.INVISIBLE);
            ImageButton pause = (findViewById(R.id.pause_button));
            pause.setVisibility(View.INVISIBLE);

            new StartWearableActivityTask().execute();


        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        Wearable.getDataClient(this).addListener(this);
        Wearable.getMessageClient(this).addListener(this);
        Wearable.getCapabilityClient(this)
                .addListener(this, Uri.parse("wear://"), CapabilityClient.FILTER_REACHABLE);
    }

    @Override
    protected void onPause() {
        super.onPause();

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
                if (item.getUri().getPath().equals(SENSOR_REQUEST_MESSAGE_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    item.getData();
                    updateSensorWear(dataMap.getFloatArray(SENSOR_KEY));
                    sensorName = dataMap.getString(NAME_KEY);
                    sensorType = dataMap.getInt(TYPE_KEY);

                }

                if (item.getUri().getPath().equals(LIST_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    item.getData();
                    listGlobal = dataMap.getStringArrayList(LIST_KEY);
                    updateListWear(listGlobal);

                }

            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }


    private void updateListWear(ArrayList<String> lista) {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.my_layout, lista);

        sensor_list.setAdapter(adapter);
    }


    private void updateSensorWear(float[] values) {
        copyValue = values;
        final ArrayList<String> listp = new ArrayList<String>();

        switch (sensorType) {

            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_GRAVITY:
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_ROTATION_VECTOR:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
            case Sensor.TYPE_ORIENTATION:
                listp.add(getResources().getString(R.string.x_text, values[0]));
                listp.add(getResources().getString(R.string.y_text, values[1]));
                listp.add(getResources().getString(R.string.z_text, values[2]));
                exceList.add(getResources().getString(R.string.excel_x_text));
                exceList.add(getResources().getString(R.string.excel_y_text));
                exceList.add(getResources().getString(R.string.excel_z_text));
                break;

            case Sensor.TYPE_LIGHT:
            case Sensor.TYPE_PROXIMITY:
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_PRESSURE:
            case Sensor.TYPE_RELATIVE_HUMIDITY:
            case Sensor.TYPE_STEP_COUNTER:
            case Sensor.TYPE_TEMPERATURE:
            case Sensor.TYPE_HEART_RATE:
            case Sensor.TYPE_HEART_BEAT:
                listp.add(getResources().getString(R.string.onedimension_text, values[0]));
                exceList.add(getResources().getString(R.string.excel_onedimension_text));
                break;

            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                listp.add(getResources().getString(R.string.x_text, values[0]));
                listp.add(getResources().getString(R.string.y_text, values[1]));
                listp.add(getResources().getString(R.string.z_text, values[2]));
                exceList.add(getResources().getString(R.string.excel_x_text));
                exceList.add(getResources().getString(R.string.excel_y_text));
                exceList.add(getResources().getString(R.string.excel_z_text));
                listp.add(getResources().getString(R.string.acc_unc_x, values[3]));
                listp.add(getResources().getString(R.string.acc_unc_y, values[4]));
                listp.add(getResources().getString(R.string.acc_unc_x, values[5]));
                exceList.add(getResources().getString(R.string.excel_acc_unc_x));
                exceList.add(getResources().getString(R.string.excel_acc_unc_y));
                exceList.add(getResources().getString(R.string.excel_acc_unc_x));
                break;

            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                listp.add(getResources().getString(R.string.x_text, values[0]));
                listp.add(getResources().getString(R.string.y_text, values[1]));
                listp.add(getResources().getString(R.string.z_text, values[2]));
                exceList.add(getResources().getString(R.string.excel_x_text));
                exceList.add(getResources().getString(R.string.excel_y_text));
                exceList.add(getResources().getString(R.string.excel_z_text));
                listp.add(getResources().getString(R.string.gyrosc_unc_x, values[3]));
                listp.add(getResources().getString(R.string.gyrosc_unc_y, values[4]));
                listp.add(getResources().getString(R.string.gyrosc_unc_z, values[5]));
                exceList.add(getResources().getString(R.string.excel_gyrosc_unc_x));
                exceList.add(getResources().getString(R.string.excel_gyrosc_unc_y));
                exceList.add(getResources().getString(R.string.excel_gyrosc_unc_z));
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                listp.add(getResources().getString(R.string.x_text, values[0]));
                listp.add(getResources().getString(R.string.y_text, values[1]));
                listp.add(getResources().getString(R.string.z_text, values[2]));
                exceList.add(getResources().getString(R.string.excel_x_text));
                exceList.add(getResources().getString(R.string.excel_y_text));
                exceList.add(getResources().getString(R.string.excel_z_text));
                listp.add(getResources().getString(R.string.magnet_unc_x, values[3]));
                listp.add(getResources().getString(R.string.magnet_unc_y, values[4]));
                listp.add(getResources().getString(R.string.magnet_unc_z, values[5]));
                exceList.add(getResources().getString(R.string.excel_magnet_unc_x));
                exceList.add(getResources().getString(R.string.excel_magnet_unc_y));
                exceList.add(getResources().getString(R.string.excel_magnet_unc_z));

                break;

            case Sensor.TYPE_POSE_6DOF:
                listp.add(getResources().getString(R.string.x_text, values[0]));
                listp.add(getResources().getString(R.string.y_text, values[1]));
                listp.add(getResources().getString(R.string.z_text, values[2]));
                listp.add(getResources().getString(R.string.pose_6_dof_cos, values[3]));
                listp.add(getResources().getString(R.string.translation_along_x, values[4]));
                listp.add(getResources().getString(R.string.translation_along_y, values[5]));
                listp.add(getResources().getString(R.string.translation_along_z, values[6]));
                listp.add(getResources().getString(R.string.delta_quat_x, values[7]));
                listp.add(getResources().getString(R.string.delta_quat_y, values[8]));
                listp.add(getResources().getString(R.string.delta_quat_z, values[9]));
                listp.add(getResources().getString(R.string.delta_quat_rot_cos, values[10]));
                listp.add(getResources().getString(R.string.delta_transl_x, values[11]));
                listp.add(getResources().getString(R.string.delta_transl_y, values[12]));
                listp.add(getResources().getString(R.string.delta_transl_z, values[13]));
                listp.add(getResources().getString(R.string.sequence_number, values[14]));


                exceList.add(getResources().getString(R.string.excel_x_text));
                exceList.add(getResources().getString(R.string.excel_y_text));
                exceList.add(getResources().getString(R.string.excel_z_text));
                exceList.add(getResources().getString(R.string.excel_pose_6_dof_cos));
                exceList.add(getResources().getString(R.string.excel_translation_along_x));
                exceList.add(getResources().getString(R.string.excel_translation_along_y));
                exceList.add(getResources().getString(R.string.excel_translation_along_z));
                exceList.add(getResources().getString(R.string.excel_delta_quat_x));
                exceList.add(getResources().getString(R.string.excel_delta_quat_y));
                exceList.add(getResources().getString(R.string.excel_delta_quat_z));
                exceList.add(getResources().getString(R.string.excel_delta_quat_rot_cos));
                exceList.add(getResources().getString(R.string.excel_delta_transl_x));
                exceList.add(getResources().getString(R.string.excel_delta_transl_y));
                exceList.add(getResources().getString(R.string.excel_delta_transl_z));
                exceList.add(getResources().getString(R.string.excel_sequence_number));
                break;

            default:
                break;
        }




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
        new StartWearableActivityTask().execute();

    }

    public void pauseSensorData(View view){
        
    }


    @WorkerThread
    private void sendStartActivityMessage(String node) {


        String msg = intentChoice;
        byte[] msgByte = msg.getBytes();
        Task<Integer> sendMessageTask;

           if(intentChoice.equals("WearSensorList"))
               sendMessageTask = Wearable.getMessageClient(this).sendMessage(node, LIST_PATH, msgByte);
           else
               sendMessageTask = Wearable.getMessageClient(this).sendMessage(node, SENSOR_REQUEST_MESSAGE_PATH, msgByte);



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


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void saveSensorData(View view) {
        if (intentChoice.equals("WatchList")) {

            if (isExternalStorageWritable() && listGlobal != null) {
                ExcelSheet listSheet = new ExcelSheet(listGlobal);
                listSheet.exportListWearToExcel();

            } else
                Toast.makeText(this, "External Storage isn't writable",
                        Toast.LENGTH_LONG).show();
        }
        else
        if (isExternalStorageWritable()){
            ExcelSheet dataSheet = new ExcelSheet(sensorName, copyValue, exceList);
            dataSheet.exportWearToExcel();
        }

        else
            Toast.makeText(this, "External Storage isn't writable",
                    Toast.LENGTH_LONG).show();

    }
}









