package csc.colleguescheduller.Views.CollectingInformation;

/*
Mohamed Mostafa Mohamed
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csc.colleguescheduller.Controllers.CollectingInformation.CollectingInformation_Controller;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.MyFragment;

public class Fragment_not_ready extends MyFragment {
    CollectingInformation_Controller controller;

    Boolean time_expired;

    public Fragment_not_ready() {
    }

    public Fragment_not_ready(ActivitytoFragment_interface activity,Boolean time_expired) {
        super(activity);
        this.time_expired = time_expired;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ViewGroup v;

        if(time_expired){
            v = (ViewGroup) inflater.inflate(R.layout.collectinginformation_fragment_time_expired, container, false);
        }else{
            v = (ViewGroup) inflater.inflate(R.layout.collectinginformation_fragment_not_ready, container, false);
        }


        v.findViewById(R.id.btn_fragment_faile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_CollectingFragment_LogOut();
            }
        });

        activity.set_animation(v);
        activity.set_font(v);


        return v;
    }

    public void btn_CollectingFragment_LogOut()
    {
        activity.onBackPressed();
    }
}
