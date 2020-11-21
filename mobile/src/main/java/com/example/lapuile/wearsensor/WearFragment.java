package com.example.lapuile.wearsensor;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class WearFragment extends Fragment {


    public WearFragment() {
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



        View myFragmentView = inflater.inflate(R.layout.fragment_wear, container, false);
        Button motion = (Button) myFragmentView.findViewById(R.id.motion_button);
        motion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(getContext(), SelectActivity.class);

                    intent.putExtra("Type", "WearMotion");

                startActivity(intent);

            }
        });

        Button environmental = (Button) myFragmentView.findViewById(R.id.environmental_button);
        environmental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(getContext(), SelectActivity.class);

                    intent.putExtra("Type", "WearEnvironmental");

                startActivity(intent);

            }
        });


        Button position = (Button) myFragmentView.findViewById(R.id.position_button);
        position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(getContext(), SelectActivity.class);

                    intent.putExtra("Type", "WearPosition");

                startActivity(intent);

            }
        });

        Button sensorList = (Button) myFragmentView.findViewById(R.id.sensor_list_button);
        sensorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;




                    intent = new Intent(getContext(), WatchDataActivity.class);
                    intent.putExtra("Type", "WearSensorList");
                    startActivity(intent);

                }



        });
        return myFragmentView;

    }
}
