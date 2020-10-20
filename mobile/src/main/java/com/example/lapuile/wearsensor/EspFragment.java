package com.example.lapuile.wearsensor;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class BluetoothFragment extends Fragment {

    public BluetoothFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View MyView = inflater.inflate(R.layout.fragment_bluetooth, container, false);

        Button watch = MyView.findViewById(R.id.sensor_bluetooth_button);
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;


                intent = new Intent(getContext(), DeviceScanActivity.class);

                startActivity(intent);


            }
        });


        return MyView;


    }

    }



