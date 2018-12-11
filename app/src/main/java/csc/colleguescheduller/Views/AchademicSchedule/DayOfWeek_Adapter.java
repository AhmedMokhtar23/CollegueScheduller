package csc.colleguescheduller.Views.AchademicSchedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import csc.colleguescheduller.R;

import org.threeten.bp.DayOfWeek;


import java.util.ArrayList;

import csc.colleguescheduller.Views.ActivitytoFragment_interface;

public class DayOfWeek_Adapter extends ArrayAdapter<DayOfWeek> {

    ActivitytoFragment_interface activity;

    public DayOfWeek_Adapter(ActivitytoFragment_interface activity, ArrayList<DayOfWeek> s) {
        super((Context) activity, R.layout.achademicschedule_listitem_day,s);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from((Context) activity).inflate(R.layout.achademicschedule_listitem_day,parent,false);
        ((TextView)v.findViewById(R.id.achademicschedule_lstitm_txtv_day)).setText(getItem(position).toString());
        return v;
    }
}
