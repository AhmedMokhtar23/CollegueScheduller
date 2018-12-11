package csc.colleguescheduller.Views.CollectingInformation;

/*
Mohamed Mostafa Mohamed
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.MyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_WatingTime extends MyFragment {

    Calendar date;

    public Fragment_WatingTime() {
        // Required empty public constructor
    }

    public Fragment_WatingTime(ActivitytoFragment_interface activity, Calendar date) {
        super(activity);
        this.date = date;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.collectinginformation_fragment_wating_time, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DisplayWatingTime();
    }

    public void DisplayWatingTime() {
        Calendar c = Calendar.getInstance();
        String days = String.format("%02d",date.get(Calendar.DAY_OF_MONTH));
        String hours = String.format("%02d",date.get(Calendar.HOUR_OF_DAY));
        String minuets = String.format("%02d",date.get(Calendar.MINUTE));

         TextView daysP1 = getView().findViewById(R.id.collecting_information_witting_time_days_p1);
         TextView daysP2 = getView().findViewById(R.id.collecting_information_witting_time_days_p2);
         daysP1.setText(" " + days.charAt(0) + " ");
         daysP2.setText(" " + days.charAt(1) + " ");

        TextView hoursP1 = getView().findViewById(R.id.collecting_information_witting_time_hours_p1);
        TextView hoursP2 = getView().findViewById(R.id.collecting_information_witting_time_hours_p2);
        hoursP1.setText(" " + hours.charAt(0) + " ");
        hoursP2.setText(" " + hours.charAt(1) + " ");

        TextView minuetsP1 = getView().findViewById(R.id.collecting_information_witting_time_minutes_p1);
        TextView minuetsP2 = getView().findViewById(R.id.collecting_information_witting_time_minutes_p2);
        minuetsP1.setText(" " + minuets.charAt(0) + " ");
        minuetsP2.setText(" " + minuets.charAt(1) + " ");
    }

}
