package csc.colleguescheduller.Views.Entry_Views;

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

import csc.colleguescheduller.Models.StaffMembers.MemberSubject;
import csc.colleguescheduller.Models.Subjects.Subject;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.TeachingInfo.TeachingInfo_Fragment;


public class Subject_listview_Adapter<T extends Subject> extends ArrayAdapter<T> {
    private SubjectToListView_Interface parent_fragment;
    private ActivitytoFragment_interface activity;

    public Subject_listview_Adapter(SubjectToListView_Interface parent_fragment, @NonNull Context context, ArrayList<T> lst) {
        super(context, R.layout.profile_listitem,lst);
        this.parent_fragment = parent_fragment;
        activity = (ActivitytoFragment_interface)context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from((Context) activity).inflate(R.layout.profile_listitem, parent, false);
        ((TextView) convertView.findViewById(R.id.profile_listitem_txtv)).setText(getItem(position).toString());
        final Subject item = getItem(position);
        (convertView.findViewById(R.id.profile_listitem_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent_fragment.remove_subject(item);
            }
        });
        activity.set_font((ViewGroup) convertView);
        activity.set_animation((ViewGroup) convertView);
        return convertView;
    }
}
