package csc.colleguescheduller.Views.Entry_Views.StaffMemebers;

/*
Ahmed Mokhtar Hassanin
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;

public class StaffMemebrs_Adapter extends ArrayAdapter<StaffMember> {

    ActivitytoFragment_interface activity;

    public StaffMemebrs_Adapter(@NonNull Context context, ArrayList<StaffMember> list, ActivitytoFragment_interface activity) {
        super(context, R.layout.staffmember_listitem,list);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.staffmember_listitem,parent,false);
        StaffMember member = getItem(position);
        ((TextView)v.findViewById(R.id.staffmember_listitem_txtv_name)).setText(member.getName());
        ((TextView)v.findViewById(R.id.staffmember_listitem_txtv_degree)).setText(
                member.getHiringDegree().toString().replace("_"," ") + " " + member.getScientificDegree().toString().replace("_"," ")
        );
        activity.set_font( v);
        activity.set_animation( v);
        return v;
    }
}
