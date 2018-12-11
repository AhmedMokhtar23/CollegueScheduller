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

import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;

public class ListItem_Adapter<T> extends ArrayAdapter<T> {

    ActivitytoFragment_interface activity;

    public ListItem_Adapter(ActivitytoFragment_interface c, ArrayList<T> data) {
        super((Context) c, R.layout.databasemanage_listitem, data);
        activity = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.databasemanage_listitem, parent, false);
        ((TextView) v.findViewById(R.id.databasemanage_listitem_txtv)).setText(getItem(position).toString());
        activity.set_font((ViewGroup) v);
        return v;
    }
}
