package com.example.lapuile.wearsensor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lapuile.wearsensor.library.formatters.KaaEndpointsConfigurationsFormatter;
import com.example.lapuile.wearsensor.library.models.KaaEndpointConfiguration;
import com.example.lapuile.wearsensor.utils.Constants;
import com.example.lapuile.wearsensor.utils.DateInputMask;
import com.example.lapuile.wearsensor.utils.DateToGmt;
import com.example.lapuile.wearsensor.utils.GetPathUtils;
import com.example.lapuile.wearsensor.utils.NumberUtils;
import com.example.lapuile.wearsensor.utils.TimeInputMask;
import com.example.lapuile.wearsensor.viewmodels.KaaApplicationViewModel;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static com.example.lapuile.wearsensor.utils.Constants.KAA_ESP_FIRST_REMOTE_ENDPOINT_ID;
import static com.example.lapuile.wearsensor.utils.Constants.KAA_ESP_SECOND_REMOTE_ENDPOINT_ID;

public class DownloadEspDataFragment extends Fragment {

    private static final String TAG = "ESPActivity";

    public DownloadEspDataFragment() {}

    /**
     * Background Async Task to download file
     * */
    @SuppressLint("StaticFieldLeak")
    class CallToWearSensorAPI extends AsyncTask<String, String, String> {

        private static final String TAG = "CallToWearSensorAPI";

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... params){
            try {

                String APIRequest = Constants.WEAR_SENSOR_API_BASE_URL +"play?kaaEndpointConfigurations="
                        +params[0]+"&fromDate="+params[1]+"&toDate="+params[2]+"&includeTime="+params[3]+
                        "&sort="+params[4]+"&format="+params[5]+"&samplePeriod="+params[6];

                URL url = new URL(APIRequest);
                URLConnection connection = url.openConnection();
                connection.connect();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // by default the download directory
                String directoryName = params[7];
                if(directoryName.isEmpty())
                    directoryName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

                // by default the name of the params
                String fileName = params[8];
                if(fileName.isEmpty())
                    fileName = DownloadEspDataFragment.this.activity.getResources().getString(R.string.stringEditTextDownloadFileName);

                // directory/file.format
                String downloadPath = directoryName + "/" + fileName + "." + params[5];

                // Output stream
                OutputStream output = new FileOutputStream(downloadPath);

                byte data[] = new byte[1024];
                int count;
                while ((count = input.read(data)) != -1) {
                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }

            return "Download successful!";
        }

        protected void onPostExecute(String result) {
            Toast.makeText(DownloadEspDataFragment.this.activity,
                    result,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*private List<String> endpointChecked;
    private String endpointId;
    private List<String> dataChecked;
    private String timeSeriesName;*/
    private Map<String, KaaEndpointConfiguration> kaaEndpointConfigurationsMap;
    private String format;
    private String includeTime;
    private String sort;
    // From 24 hours ago
    private Long fromDate = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000)).getTime();
    // To now
    private Long toDate = new Date(System.currentTimeMillis()).getTime();
    private int samplingPeriod;
    private String directoryName;
    private String fileName;
    private Activity activity;

    private final int READ_REQUEST_CODE = 0;

    private TextView textViewFolderDownload;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        // Initialize parameters
        activity = getActivity();
        /*// By default everyone
        endpointChecked = new ArrayList<>(Arrays.asList(KAA_ESP_FIRST_ENDPOINT_ID, Constants.KAA_ESP_SECOND_ENDPOINT_ID));
        endpointId = "";
        dataChecked = new ArrayList<>();
        timeSeriesName = "";*/
        format = "json";
        includeTime = "both";
        sort = "ASC";
        samplingPeriod = 1000;
        fileName = "";
        directoryName="";
        // To now
        toDate = new Date().getTime();
        // From 24 hours ago
        fromDate = toDate - (24 * 60 * 60 * 1000);
        kaaEndpointConfigurationsMap = new HashMap<>();

        View MyView = inflater.inflate(R.layout.fragment_download_esp_data, container, false);

        //View binding
        RadioGroup radioGroupFormat = (RadioGroup) MyView.findViewById(R.id.radioGroupSelectDownloadFormat);
        RadioGroup radioGroupIncludeTime = (RadioGroup) MyView.findViewById(R.id.radioGroupIncludeTime);
        RadioGroup radioGroupSort = (RadioGroup) MyView.findViewById(R.id.radioGroupOrder);
        Button chooseDownloadFolder = (Button) MyView.findViewById(R.id.buttonChooseDownloadFolder);
        Button downloadDataButton = (Button) MyView.findViewById(R.id.buttonDownloadDataFromWearSensorAPI);
        EditText editTextSamplingPeriod = (EditText) MyView.findViewById(R.id.editTextDownloadSamplingPeriod);
        EditText editTextFromDate = (EditText) MyView.findViewById(R.id.editTextChooseDownloadFromDate);
        new DateInputMask(editTextFromDate);
        EditText editTextFromTime = (EditText) MyView.findViewById(R.id.editTextChooseDownloadFromTime);
        new TimeInputMask(editTextFromTime);
        EditText editTextToDate = (EditText) MyView.findViewById(R.id.editTextChooseDownloadToDate);
        new DateInputMask(editTextToDate);
        EditText editTextToTime = (EditText) MyView.findViewById(R.id.editTextChooseDownloadToTime);
        new TimeInputMask(editTextToTime);
        EditText editTextFileName = (EditText) MyView.findViewById(R.id.editTextDownloadFileName);
        textViewFolderDownload = (TextView) MyView.findViewById(R.id.textViewDownloadFolderChosen);
        TextView textViewChooseDataToRetrieveLoading = (TextView) MyView.findViewById(R.id.textViewChooseDataToRetrieveLoading);

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        // Set default date/time
        String df = dateFormat.format(fromDate);
        editTextFromDate.setText(df.substring(0,10));
        try{
            String d = df.substring(10,19);
            editTextFromTime.setText(df.substring(10,19));
        }catch(Exception ex){
            String d = df.substring(11,19);
            editTextFromTime.setText(df.substring(11,19));
        }
        df = dateFormat.format(toDate);
        editTextToDate.setText(df.substring(0,10));
        editTextToTime.setText(df.substring(10,19));

        // Retrieve the queryable sensors -----------------

        // Get the ViewModel.
        KaaApplicationViewModel model = new ViewModelProvider(this).get(KaaApplicationViewModel.class);

        // Create the observer which updates the UI.
        Context context = this.getContext();
        final Observer<List<KaaEndpointConfiguration>> observer = new Observer<List<KaaEndpointConfiguration>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(@Nullable final List<KaaEndpointConfiguration> kaaEndpointConfigurations) {
                try{
                    if(kaaEndpointConfigurations == null ||
                            (kaaEndpointConfigurations.size() == 1 &&
                                    kaaEndpointConfigurations.get(0).getendpointId().equals("error"))){
                        throw new Exception("error");
                    }else{

                        textViewChooseDataToRetrieveLoading.setVisibility(View.GONE);

                        // if everything is fine, for each endpoint, i append to the layout a new card view
                        LinearLayout linearLayout = (LinearLayout)MyView.findViewById(R.id.linearLayoutEndpointSensorsContainer);

                        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, // Width
                                LinearLayout.LayoutParams.WRAP_CONTENT); // Height

                        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, // Width
                                LinearLayout.LayoutParams.WRAP_CONTENT); // Height
                        linearLayoutParams.setMargins(0,0,0,8);
                        linearLayoutParams.setMarginStart(8);

                        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, // Width
                                LinearLayout.LayoutParams.WRAP_CONTENT); // Height

                        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, // Width
                                LinearLayout.LayoutParams.WRAP_CONTENT); // Height
                        checkBoxParams.setMargins(8,8,8,0);
                        checkBoxParams.setMarginStart(8);

                        for (int i = 0; i < kaaEndpointConfigurations.size(); i++) {
                            KaaEndpointConfiguration endpoint = kaaEndpointConfigurations.get(i);

                            // Add the endpoint to the hashmap
                            kaaEndpointConfigurationsMap.put(endpoint.getendpointId(),
                                    new KaaEndpointConfiguration(endpoint.getendpointId(), new ArrayList<>()));

                            // Card view
                            CardView cardView = new CardView(context);
                            cardView.setLayoutParams(cardViewParams);

                            // Linear Layout
                            LinearLayout linearLayout1 = new LinearLayout(context);
                            linearLayout1.setLayoutParams(linearLayoutParams);
                            linearLayout1.setOrientation(LinearLayout.VERTICAL);
                            cardView.addView(linearLayout1);

                            // TextView
                            TextView textView = new TextView(context);
                            textView.setLayoutParams(textViewParams);
                            textView.setTextSize(15);
                            textView.setText(NumberUtils.ordinal(i+1)+" endpoint");

                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            linearLayout1.addView(textView);

                            List<String> dataNames = endpoint.getDataNames();
                            for(int x = 0; x < dataNames.size(); x++){
                                // Checkbox
                                CheckBox checkBox = new CheckBox(context);

                                checkBox.setLayoutParams(checkBoxParams);
                                checkBox.setTextColor(getResources().getColor(R.color.black_text));

                                String dataName = dataNames.get(x);
                                checkBox.setText(dataName);

                                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            List<String> dNames = kaaEndpointConfigurationsMap
                                                                    .get(endpoint.getendpointId()).getDataNames();
                                            if(isChecked){
                                                // If the data wasn't in the list i add it
                                                dNames.add(dataName);
                                            }else{
                                                // If the data was in the list i remove it
                                                dNames.remove(dataName);
                                            }

                                            kaaEndpointConfigurationsMap.get(endpoint.getendpointId())
                                                    .setDataNames(dNames);
                                        }
                                    }
                                );
                                linearLayout1.addView(checkBox);
                            }
                            // Append the cardView
                            linearLayout.addView(cardView);
                        }
                    }
                }catch (Exception e){
                    textViewChooseDataToRetrieveLoading.setText(getResources().getString(R.string.error_while_retrieving_the_data));
                    textViewChooseDataToRetrieveLoading.setTextColor(Color.RED);
                    textViewChooseDataToRetrieveLoading.setAllCaps(true);
                    textViewChooseDataToRetrieveLoading.setTextSize(18);
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getKaaEndpointConfigurations(KAA_ESP_FIRST_REMOTE_ENDPOINT_ID+","+KAA_ESP_SECOND_REMOTE_ENDPOINT_ID).observe(this, observer);

        radioGroupFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonDownloadFormatXML:
                        format = "xml";
                        break;
                    case R.id.radioButtonDownloadFormatJSON:
                        format = "json";
                        break;
                    case R.id.radioButtonDownloadFormatCSV:
                        format = "csv";
                        break;
                }
            }
        });

        radioGroupIncludeTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonIncludeTimeBothDate:
                        format = "both";
                        break;
                    case R.id.radioButtonIncludeTimeNoneDate:
                        format = "none";
                        break;
                    case R.id.radioButtonIncludeTimeFromDate:
                        format = "from";
                        break;
                    case R.id.radioButtonIncludeTimeToDate:
                        format = "to";
                        break;
                }
            }
        });

        radioGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonChooseDowloadSortASC:
                        format = "ASC";
                        break;
                    case R.id.radioButtonChooseDowloadSortDESC:
                        format = "DESC";
                        break;
                }
            }
        });

        chooseDownloadFolder.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

        downloadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Setting toast message
                Context context = activity.getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                CharSequence text = null;
                Toast toast;

                // Getting input value
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                sdf.setTimeZone(TimeZone.getDefault());
                try {
                    try {
                        Date fromD = sdf.parse(editTextFromDate.getText().toString() + " "
                                + editTextFromTime.getText().toString());
                        fromD = DateToGmt.convertToGmt(fromD);
                        fromDate = fromD.getTime();
                    }catch(Exception e){
                        throw new Exception("Error in fromDate or fromTime");
                    }

                    try {
                        Date toD = sdf.parse(editTextToDate.getText().toString() + " "
                                + editTextToTime.getText().toString());
                        toD = DateToGmt.convertToGmt(toD);
                        toDate = toD.getTime();
                    }catch(Exception e){
                        throw new Exception("Error in toDate or toTime");
                    }

                    if(toDate < fromDate)
                        throw new Exception("fromDate has to be earlier than toDate");

                    try {
                        samplingPeriod = Integer.parseInt(editTextSamplingPeriod.getText().toString());
                    }catch(Exception e){
                        throw new Exception("Error in the ample period");
                    }

                    fileName = editTextFileName.getText().toString();


                    // Now for every endpoint i check which sensor i should query

                    /*timeSeriesName = "";
                    for(String data : dataChecked)
                        timeSeriesName += data + ",";
                    // If not empty, remove the last comma
                    if(!timeSeriesName.isEmpty())
                        timeSeriesName = timeSeriesName.substring(0, timeSeriesName.length() - 1);
                    if(timeSeriesName.isEmpty())
                        throw new Exception("Choose at least one data to retrieve");

                    endpointId = "";
                    for(String endpoint : endpointChecked)
                        endpointId += endpoint + ",";
                    // If not empty, remove the last comma
                    if(!endpointId.isEmpty())
                        endpointId = endpointId.substring(0, endpointId.length() - 1);
                    if(endpointId.isEmpty())
                        throw new Exception("Choose at least one endpoint to query");*/

                    if(samplingPeriod >= 1000){

                        List<KaaEndpointConfiguration> config = new ArrayList<>();
                        for(String k : kaaEndpointConfigurationsMap.keySet())
                            if(kaaEndpointConfigurationsMap.get(k).getDataNames().size()>0)
                                config.add(kaaEndpointConfigurationsMap.get(k));
                        String json = KaaEndpointsConfigurationsFormatter.
                                KaaEndpointConfigurationsToJSON(config);
                        // Call to API
                        new DownloadEspDataFragment.CallToWearSensorAPI().execute(json, fromDate.toString(), toDate.toString(),
                                includeTime, sort, format, String.valueOf(samplingPeriod), directoryName, fileName);
                    }else{
                        text = "Sample period must be greater than 1000!";
                    }
                } catch (Exception e) {
                    text = e.getMessage();
                }
                if(text!=null){
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        return MyView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try{
                    Uri folderUri = data.getData();

                    if (folderUri != null) {
                        String path = GetPathUtils.getDirectoryPathFromUri(this.activity, folderUri);

                        if (!TextUtils.isEmpty(path)) {
                            directoryName = path;
                            textViewFolderDownload.setText(directoryName);
                            textViewFolderDownload.setVisibility(View.VISIBLE);
                        }
                    }

                }catch(Exception e){
                    e.fillInStackTrace();
                }
            }
        }
    }
}
