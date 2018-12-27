package com.example.lapuile.wearsensor;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

    private static final String CAPABILITY_NAME =   "watch_server";
    public static final String SENSOR_REQUEST_MESSAGE_PATH = "/sensor";
    private String transcriptionNodeId = null;

    String intentChoice;






    ListView sensor_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_data);



        sensor_list = findViewById(R.id.sensor_list_wear);



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


        // capabilityInfo has the reachable nodes with the transcription capability
       // updateTranscriptionCapability(capabilityInfo);

        // capabilityInfo has the reachable nodes with the transcription capability








/*

    private void showNodes(final String... capabilityNames) {
        Task<Map<String, CapabilityInfo>> capabilitiesTask =
                Wearable.getCapabilityClient(this)
                        .getAllCapabilities(CapabilityClient.FILTER_REACHABLE);
        capabilitiesTask.addOnSuccessListener(new OnSuccessListener<Map<String, CapabilityInfo>>() {
            @Override
            public void onSuccess(Map<String, CapabilityInfo> capabilityInfoMap) {
                Set<Node> nodes = new HashSet<>();
                if (capabilityInfoMap.isEmpty()) {
                    showDiscoveredNodes(nodes);
                    return;
                }
                for (String capabilityName : capabilityNames) {
                    CapabilityInfo capabilityInfo = capabilityInfoMap.get(capabilityName);
                    if (capabilityInfo != null) {
                        nodes.addAll(capabilityInfo.getNodes());
                    }
                }
                showDiscoveredNodes(nodes);
            }
        });
    }

    /*
    private void showDiscoveredNodes(Set<Node> nodes) {
        List<String> nodesList = new ArrayList<>();
        for (Node node : nodes) {
            nodesList.add(node.getDisplayName());
        }
        Log.d(
                TAG,
                "Connected Nodes: "
                        + (nodesList.isEmpty()
                        ? "No connected device was found for the given capabilities"
                        : TextUtils.join(",", nodesList)));
        String msg;
        if (!nodesList.isEmpty()) {
            msg = getString(R.string.connected_nodes, TextUtils.join(", ", nodesList));
        } else {
            msg = getString(R.string.no_device);
        }
        Toast.makeText(WatchDataActivity.this, msg, Toast.LENGTH_LONG).show();
    }

*/
    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo) {
        Log.i(TAG, "onCapabilityChanged: " + capabilityInfo);

    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        Log.i(TAG, "DATACHAGED");

        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().equals(SENSOR_REQUEST_MESSAGE_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    item.getData();
                    updateSensorWear(dataMap.getFloatArray(SENSOR_KEY));
                }
/*
                if (item.getUri().getPath().equals(LIST_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    item.getData();
                    updateListWear(dataMap.getStringArrayList(LIST_KEY));
                }
*/
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }
/*
    private void updateListWear(ArrayList<String> lista){

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.my_layout, lista);

        sensor_list.setAdapter(adapter);
    }
    */

    private void updateSensorWear(float[] values) {
        final ArrayList<String> listp = new ArrayList<String>();
        listp.add(getResources().getString(R.string.onedimension_text, values[0]));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,R.layout.my_layout, listp);

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


    @WorkerThread
    private void sendStartActivityMessage(String node) {

        Task<Integer> sendMessageTask =
                    Wearable.getMessageClient(this).sendMessage(node, SENSOR_REQUEST_MESSAGE_PATH, new byte[0]);





        try {
            // Block on a task and get the result synchronously (because this is on a background
            // thread). sendMessage(START_PHONE_ACTIVITY, "");
            Integer result = Tasks.await(sendMessageTask);
            Log.i(TAG, "Message sent: " + result+ " to "+ LIST_PATH + node);

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
}









