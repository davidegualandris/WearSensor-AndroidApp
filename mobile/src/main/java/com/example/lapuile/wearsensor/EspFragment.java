package com.example.lapuile.wearsensor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class EspFragment extends Fragment {

    public EspFragment() {
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
        View MyView = inflater.inflate(R.layout.fragment_esp, container, false);

        Button espButton = MyView.findViewById(R.id.query_endpoints_button);
        espButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(getContext(), QueryRemoteDevicesActivity.class);
                startActivity(intent);

            }
        });

        return MyView;
    }

}
