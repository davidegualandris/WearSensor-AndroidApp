package com.example.lapuile.wearsensor.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.lapuile.wearsensor.library.models.KaaApplication;
import com.example.lapuile.wearsensor.library.models.KaaEndpointConfiguration;
import com.example.lapuile.wearsensor.services.KaaApplicationsService;
import com.example.lapuile.wearsensor.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Repository to allow the user to retrieve the interrogable sensors of the endpoints he wants
 */
public class KaaApplicationRepository {

    private static final String TAG = "ESPActivity";

    private static KaaApplicationRepository instance;
    private final KaaApplicationsService kaaApplicationsService;

    private KaaApplicationRepository() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEAR_SENSOR_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        kaaApplicationsService = retrofit.create(KaaApplicationsService.class);

    }

    public static synchronized KaaApplicationRepository getInstance(){
        if (instance==null)
            instance = new KaaApplicationRepository();
        return instance;
    }

    public void getKaaEndpointConfiguration(MutableLiveData<List<KaaEndpointConfiguration>> kaaEndpointConfigurations, String endpointId){
        Call<KaaApplication> call = kaaApplicationsService.getTimeSeriesConfig(endpointId);

        call.enqueue(new Callback<KaaApplication>() {
            @Override
            public void onResponse(Call<KaaApplication> call, Response<KaaApplication> response) {
                try {
                    if(response.isSuccessful()) {
                        KaaApplication ka = response.body();
                        assert ka != null;
                        List<KaaEndpointConfiguration> config = ka.getEndpoints();
                        kaaEndpointConfigurations.postValue(config);
                    }else{
                        Log.d(TAG, "Call wasn't successful");
                    }
                }catch(Exception e){
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<KaaApplication> call, Throwable t) {
                // if there is an error i create a list with just a fake element
                kaaEndpointConfigurations.postValue(Arrays.asList(new KaaEndpointConfiguration("error", new ArrayList<>())));
                Log.d(TAG, "Error: " + t.getMessage());
            }
        });
    }
}
