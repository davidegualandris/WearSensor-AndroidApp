package com.example.lapuile.wearsensor.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lapuile.wearsensor.library.models.KaaEndpointConfiguration;
import com.example.lapuile.wearsensor.repositories.KaaApplicationRepository;

import java.util.List;

// ViewModel to save the data from the class KaaApplication
public class KaaApplicationViewModel extends ViewModel {

    private static final String TAG = "ESPActivity";

    private MutableLiveData<List<KaaEndpointConfiguration>> kaaEndpointConfigurations;
    public LiveData<List<KaaEndpointConfiguration>> getKaaEndpointConfigurations(String endpointId) {
        if (kaaEndpointConfigurations == null) {
            kaaEndpointConfigurations = new MutableLiveData<>();
            try{
                KaaApplicationRepository.getInstance().getKaaEndpointConfiguration(kaaEndpointConfigurations, endpointId);
            }catch(Exception e){
                Log.d(TAG, "getKaaEndpointConfigurations: " + e.getMessage());
            }
        }
        return kaaEndpointConfigurations;
    }

}
