package com.example.lapuile.wearsensor.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.lapuile.wearsensor.models.KaaApplication;
import com.example.lapuile.wearsensor.repositories.KaaApplicationRepository;

import java.util.List;

// ViewModel to save the data from the class KaaApplication
public class KaaApplicationViewModel extends ViewModel {

    private MutableLiveData<List<KaaApplication>> kaaApplications;
    public LiveData<List<KaaApplication>> getKaaApplications() {
        if (kaaApplications == null) {
            kaaApplications = new MutableLiveData<>();
            //loadKaaApplications();
            KaaApplicationRepository.getInstance().getKaaApplications(kaaApplications);
        }
        return kaaApplications;
    }

    /*private void loadKaaApplications() {
        // Do an asynchronous operation to fetch kaaApplications.
    }*/

}
