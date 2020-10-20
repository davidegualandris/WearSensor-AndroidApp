package com.example.lapuile.wearsensor.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KaaApplicationsService {
    @GET("time-series/config")
    Call<Object> getTimeSeriesConfig(@Header("Authorization") String bearer);
}