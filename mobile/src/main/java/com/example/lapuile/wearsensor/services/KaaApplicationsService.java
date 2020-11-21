package com.example.lapuile.wearsensor.services;

import com.example.lapuile.wearsensor.library.models.KaaApplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KaaApplicationsService {
    @GET("kaa/time-series/config")
    Call<KaaApplication> getTimeSeriesConfig(@Query("endpointId") String endpointId);
}