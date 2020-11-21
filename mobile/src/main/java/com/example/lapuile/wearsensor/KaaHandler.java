package com.example.lapuile.wearsensor;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lapuile.wearsensor.library.models.KaaEndpoint;
import com.example.lapuile.wearsensor.library.models.KaaValueMulti;
import com.example.lapuile.wearsensor.library.models.KaaValueSingle;
import com.example.lapuile.wearsensor.library.models.interfaces.KaaValue;
import com.example.lapuile.wearsensor.utils.Constants;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KaaHandler {

    public static class SandDataToKaa extends AsyncTask<String, String, Integer> {

        HttpURLConnection urlConnection;

        @Override
        protected Integer doInBackground(String... params) {
            String data = params[0]; //data to post
            OutputStream out = null;

            try {
                URL url = new URL(Constants.WEAR_SENSOR_API_BASE_URL + "kaa/time-series/data");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                out = new BufferedOutputStream(urlConnection.getOutputStream());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF_8"));
                writer.write(data);
                writer.flush();
                writer.close();
                out.close();

                urlConnection.connect();
                return urlConnection.getResponseCode();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return 500;
        }

        @Override
        protected void onPostExecute(Integer s) {
            if(s == 200){
                // if successful
                srec_data += send_data;
                send_data = 0;
            }
            urlConnection.disconnect();
        }
    }


    private static final String KAA_STATUS_TAG = "KAA_STATUS";
    private static final String TAG = "KaaHandler";
    private static String STATUS; // Register the KaaHandler status: STARTED, STOPPED, PAUSED
	private static final int DEFAULT_START_DELAY = 2000; // Initial delay before starting sending data to Kaa
    private static int COLLECTION_FREQUENCY; // Frequency of sensor data collection given by Kaa
    private static int COLLECTION_PERIOD; // Frequency of data submit given by Kaa.
    private static int sent_data = 0; // # data sent to Kaa
    private static int send_data = 0; // # data sending to Kaa
    private static int srec_data = 0; // # data received from Kaa
    private static long lastSampled;
    private static KaaEndpoint kaaEndpointMobile;
    private static KaaEndpoint kaaEndpointWatch;

    public KaaHandler(){
        kaaEndpointMobile = new KaaEndpoint(Constants.KAA_MOBILE_ENDPOINT_ID, new HashMap<>());
        kaaEndpointWatch = new KaaEndpoint(Constants.KAA_WATCH_ENDPOINT_ID, new HashMap<>());
        COLLECTION_FREQUENCY = 2000;
        COLLECTION_PERIOD = 5000;
        this.start();
    }

    /** Returns frequency of sensor data collection */
	public static int getFrequency(){
		return COLLECTION_FREQUENCY;
	}

	/** Returns frequency of data submit */
    public static int getPeriod(){
        return COLLECTION_PERIOD;
    }

    /** Returns number of data sent to Kaa */
    public static int getSent_data(){ return sent_data; }

    /** Returns number of data received from Kaa */
    public static int getSrec_data(){ return srec_data; }

    /** Change period of data broadcast */
    public static void setPeriod(int per) {
        COLLECTION_PERIOD = per;
    }

	/** Returns initial delay before starting sending data to Kaa */
    public static int getDefaultStartDelay(){ return DEFAULT_START_DELAY; }

    public static String getSTATUS(){ return STATUS; }

	/** boolean collectData(string sensor_name, List<Float> sensor_data)
	 *  Add sensor_name and sensor_data in Kaa log record.
	 *  Return TRUE if the operation succeed, FALSE otherwise.
	 */
	public static boolean collectData(String device_type, String sensor_name, List<String> sensor_names, List<Float> sensor_data){
		if(sensor_data == null || sensor_name.equals("") || device_type.equals("") || sensor_data.size()==0 || sensor_names == null || sensor_names.size()==0)
		    return false;
		try{
		    switch (device_type){
                case "watch":
                    addLogRecord(Constants.KAA_WATCH_ENDPOINT_ID, sensor_name, sensor_names, sensor_data);
                    break;
                    
                case "mobile":
                    addLogRecord(Constants.KAA_MOBILE_ENDPOINT_ID, sensor_name, sensor_names, sensor_data);
                    break;
            }
            Log.d(TAG, "collectData: " + device_type + sensor_name);
            sent_data++;
            send_data++;
            return true;
        }catch (Exception e){
		    return false;
        }
	}

	private static void addLogRecord(String endpointId, String sensor_name, List<String> sensor_names, List<Float> sensor_data){

	    // Add to kaaEndpoint
        Map<String, List<KaaValue>> valuesMap;

        switch (endpointId){
            case Constants.KAA_WATCH_ENDPOINT_ID:
                valuesMap = kaaEndpointWatch.getValues();
                break;

            case Constants.KAA_MOBILE_ENDPOINT_ID:
                valuesMap = kaaEndpointMobile.getValues();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + endpointId);
        }
        
        List<KaaValue> valuesList;
        if(valuesMap.containsKey(sensor_name)){
            valuesList = valuesMap.get(sensor_name);
        }else{
            valuesList = new ArrayList<>();
        }

        if(sensor_names.size()==1){
            valuesList.add(new KaaValueSingle(new Date(), sensor_data.get(0)));
        }else{
            Map<String, Object> values = new HashMap<>();
            for (int i=0; i<sensor_names.size(); i++)
                values.put(sensor_names.get(i), sensor_data.get(i));
            valuesList.add(new KaaValueMulti(new Date(), values));
        }
        valuesMap.put(sensor_name, valuesList);

	    // Send to the API
        if(new Date().getTime() - lastSampled >= COLLECTION_PERIOD){
            lastSampled = new Date().getTime();
            String data = null;
            switch (endpointId){
                case Constants.KAA_WATCH_ENDPOINT_ID:
                    kaaEndpointWatch.setValues(valuesMap);
                    data = kaaEndpointWatch.toJson();
                    break;
                case Constants.KAA_MOBILE_ENDPOINT_ID:
                    kaaEndpointMobile.setValues(valuesMap);
                    data = kaaEndpointMobile.toJson();
                    break;
            }

            // Send data to WearSensorAPI
            new KaaHandler.SandDataToKaa().execute(data);
            valuesMap.remove(endpointId);
        }
    }

    public void pause(){
	    if (STATUS != null && STATUS.equals("STARTED")) {
            STATUS = "PAUSED";
            Log.i("KAA_STATUS", STATUS);
        }
    }

    public void start(){

	    if(STATUS == null || STATUS.equals("STOP")){
            Log.i("METHOD_KAA_STATUS", "STARTED");
            this.resume();
        }
    }

    public void resume(){
        if (STATUS == null || STATUS.equals("PAUSED")) {
            lastSampled = new Date().getTime();
            STATUS = "STARTED";
        }

	}

    public void stop(){
        if (STATUS != null && !STATUS.equals("STOP")){
            STATUS = "STOP";
        }
    }
	
}