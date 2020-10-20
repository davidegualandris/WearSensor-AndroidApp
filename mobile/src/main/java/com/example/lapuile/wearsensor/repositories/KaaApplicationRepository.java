package com.example.lapuile.wearsensor.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.lapuile.wearsensor.models.KaaApplication;
import com.example.lapuile.wearsensor.services.KaaApplicationsService;
import com.example.lapuile.wearsensor.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KaaApplicationRepository {

    private static KaaApplicationRepository instance;
    private KaaApplicationsService kaaApplicationsService;

    private KaaApplicationRepository() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.KAA_EPTS_API_BASE_URL )
                //.addConverterFactory(GsonConverterFactory.create())
                .build();

        kaaApplicationsService = retrofit.create(KaaApplicationsService.class);

    }

    public static synchronized KaaApplicationRepository getInstance(){
        if (instance==null)
            instance = new KaaApplicationRepository();
        return instance;
    }

    public void getKaaApplications(MutableLiveData<List<KaaApplication>> kaaApplications){
        Call<Object> call = kaaApplicationsService.getTimeSeriesConfig(Constants.KAA_EPTS_API_BEARER_TOKEN);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                response.body().toString();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
