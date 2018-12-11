package csc.colleguescheduller.Views.CollectingInformation;

/*
Mohamed Mostafa Mohamed
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Ready extends MyFragment {


    public Fragment_Ready() {
        // Required empty public constructor
    }

    public Fragment_Ready(ActivitytoFragment_interface activity) {
        super(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.collectinginformation_fragment_ready, container, false);
        v.findViewById(R.id.collectinginformation_ready_btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_click();
            }
        });
        return v;
    }

    private void start_click(){
        startActivity(new Intent((Context)activity,Main_Activity.class));
    }

    public void btn_Start(View v) {
        Intent intent = new Intent(getActivity(), Main_Activity.class);
        startActivity(intent);
    }

}
