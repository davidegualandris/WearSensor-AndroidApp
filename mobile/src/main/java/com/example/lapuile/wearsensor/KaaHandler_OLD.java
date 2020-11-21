package com.example.lapuile.wearsensor;

import android.content.Context;
import android.util.Log;

import org.kaaproject.kaa.client.AndroidKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.KaaClientProperties;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.configuration.base.ConfigurationListener;
import org.kaaproject.kaa.client.configuration.base.SimpleConfigurationStorage;
import org.kaaproject.kaa.client.context.ExecutorContext;
import org.kaaproject.kaa.client.context.SimpleExecutorContext;
import org.kaaproject.kaa.client.logging.BucketInfo;
import org.kaaproject.kaa.client.logging.LogDeliveryListener;
import org.kaaproject.kaa.client.logging.strategies.PeriodicLogUploadStrategy;
import org.kaaproject.kaa.schema.wearsensor.Configuration;
import org.kaaproject.kaa.schema.wearsensor.DataCollection;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.kaaproject.kaa.configuration.accelerometer.Configuration;
// Utility libraries

public class KaaHandler_OLD {

	private static KaaClient kaaClient; // Basic interface to operate with Kaa library
    private static final String KAA_STATUS_TAG = "KAA_STATUS";
    private static String STATUS; // Register the Kaa Client status: STARTED, STOPPED, PAUSED
	private static int DEFAULT_START_DELAY = 2000; // Initial delay before starting sending data to Kaa
    private static int COLLECTION_FREQUENCY; // Frequency of sensor data collection given by Kaa
    private static int COLLECTION_PERIOD; // Frequency of data submit given by Kaa. May not be the actual broadcasting period
    private static int sent_data = 0; // # data sent to Kaa
    private static int send_data = 0; // # data sending to Kaa
    private static int srec_data = 0; // # data received from Kaa

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

    /** Change real period of data broadcast. DOESN'T change COLLECTION_PERIOD */
    public static void setPeriod(int per) {
        PeriodicLogUploadStrategy strategy = new PeriodicLogUploadStrategy(per, TimeUnit.MILLISECONDS);
        strategy.setMaxParallelUploads(1);
        // Set user implementation of a log upload strategy.
        kaaClient.setLogUploadStrategy(strategy);
    }

	/** Returns initial delay before starting sending data to Kaa */
    public static int getDefaultStartDelay(){
	    return DEFAULT_START_DELAY;
    }

    public static String getSTATUS(){return STATUS;}
	/** boolean collectData(string sensor_name, List<Float> sensor_data)
	 *  Add sensor_name and sensor_data in Kaa log record.
	 *  Return TRUE if the operation succeed, FALSE otherwise.
	 */
	public static boolean collectData(String device_type, String sensor_name, List<Float> sensor_data){
		if(sensor_data == null || sensor_name == "" || device_type == "" )
		    return false;
		DataCollection record = new DataCollection(device_type, sensor_name, sensor_data);
		Log.i("ADD_LOG_RECORD", record.toString());
		kaaClient.addLogRecord(record);
		sent_data++;
		send_data++;
		return true;
	}

	/** Contructor for KaaHandler. Context has to be given by the caller. */
	public KaaHandler_OLD(Context context){
		// Kaa Configuration
        int threadsCount = 2;
        ExecutorContext executorContext = new SimpleExecutorContext(
                threadsCount, // lifeCycleThreadCount
                threadsCount, // apiThreadCount
                threadsCount, // callbackThreadCount
                threadsCount  // scheduledThreadCount
        );
        AndroidKaaPlatformContext androidKaaPlatformContext = null;
		try {
            androidKaaPlatformContext = new AndroidKaaPlatformContext(
                    context,
                    new KaaClientProperties(),
                    executorContext
            );
            //Log.i("CREATE: ","!!! KAA CREATION !!!");
            kaaClient = Kaa.newClient(
                    androidKaaPlatformContext,
                    new KaaCSListener(), // Definition starts at line ~140.
                    true
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

		// Sets the configuration storage that will be used to persist configuration.
        kaaClient.setConfigurationStorage(
                new SimpleConfigurationStorage(androidKaaPlatformContext,
                        "saved_config.cfg")
        );

		// Sets a listener which receives a delivery status of each log bucket.
        kaaClient.setLogDeliveryListener(new LogDeliveryListener() {
            @Override
            public void onLogDeliverySuccess(BucketInfo bucketInfo) {
                Log.i("LOG_DELIVERY_LISTENER: ", "SUCCESS");
                srec_data+= send_data;
                send_data = 0;
            }
            @Override
            public void onLogDeliveryFailure(BucketInfo bucketInfo) {
                Log.e("LOG_DELIVERY_LISTENER: ", "FAILURE");
            }
            @Override
            public void onLogDeliveryTimeout(BucketInfo bucketInfo) {
                Log.e("LOG_DELIVERY_LISTENER: ", "TIMEOUT");
            }
        });

		// Sets a listener which receives configuration updates
		kaaClient.addConfigurationListener(new ConfigurationListener() {

			// Every time the configuration changes <<onConfigurationUpdate>> is called.
			@Override
            public void onConfigurationUpdate(Configuration configuration) {
                Log.i("CONFIGURATION_UPDATE", configuration.toString());
                COLLECTION_FREQUENCY = configuration.getFrequency();

				// If the frequency of data submit change a new strategy is set.
                if (COLLECTION_PERIOD != configuration.getPeriod()){
                    setPeriod(configuration.getPeriod());
					COLLECTION_PERIOD = configuration.getPeriod();
				}
            }
        });

        kaaClient.start();
        this.STATUS = "START";
	}

	private static class KaaCSListener extends SimpleKaaClientStateListener {

        // On successful start of Kaa client
        @Override
        public void onStarted() {
            super.onStarted();
            Log.i(KAA_STATUS_TAG,"Kaa client started");
            Configuration configuration = kaaClient.getConfiguration();
            Log.i("CONFIGURATION",configuration.toString());
            COLLECTION_FREQUENCY = configuration.getFrequency();
            COLLECTION_PERIOD = configuration.getPeriod();

			/* Periodic strategy uploads the logs after COLLECTION_PERIOD
			 * milliseconds passes since the last upload. The decision on
			 * whether to upload the collected logs is taken each time a new
			 * log record is added. This means that a log uplod will be
			 * triggered by the next log record added after the specified
			 * period of time. */
            PeriodicLogUploadStrategy strategy = new PeriodicLogUploadStrategy(COLLECTION_PERIOD, TimeUnit.MILLISECONDS);
            strategy.setMaxParallelUploads(1);
            // Set user implementation of a log upload strategy.
            kaaClient.setLogUploadStrategy(strategy);
        }

        @Override
        public void onStopped() {
            super.onStopped();
            Log.i(KAA_STATUS_TAG,"Kaa client stopped");
        }

        @Override
        public void onPaused() {
            super.onPaused();
            Log.i(KAA_STATUS_TAG,"Kaa client paused");
        }

        @Override
        public void onResume(){
            super.onResume();
            Log.i(KAA_STATUS_TAG,"Kaa client resumed");
        }
    }

    public void pause(){
	    if (this.STATUS != "PAUSED") {
            this.STATUS = "PAUSED";
            Log.i("KAA_STATUS", "PAUSED");
	        kaaClient.pause();
        }
    }

    public void start(){
	    if (this.STATUS != "STARTED"){
            Log.i("METHOD_KAA_STATUS", KaaHandler_OLD.getSTATUS());
	        kaaClient.start();
	        kaaClient.resume();
	        this.STATUS = "STARTED";
        }
    }

    public void resume(){
        if (this.STATUS != "STARTED") {
            kaaClient.resume();
            this.STATUS = "STARTED";
        }
    }

    public void stop(){
        if (this.STATUS != "STOP"){
            kaaClient.stop();
            this.STATUS = "STOP";
        }
    }
	
}