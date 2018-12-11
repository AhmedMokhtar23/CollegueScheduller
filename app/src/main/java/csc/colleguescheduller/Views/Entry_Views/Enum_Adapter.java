package csc.colleguescheduller.Views.Entry_Views;


/*
Ahmed Mokhtar Hassanin
 */


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import csc.colleguescheduller.Views.ActivitytoFragment_interface;

public class Enum_Adapter<T> extends ArrayAdapter<T> {

    private T[] data;
    ActivitytoFragment_interface activity;

    public Enum_Adapter(ActivitytoFragment_interface activity, T[] s) {
        super((Context) activity, android.R.layout.simple_spinner_dropdown_item,s);
        this.activity = activity;
        data = s;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item,parent,false);
        TextView txt = v.findViewById(android.R.id.text1);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,9);
        txt.setText(getItem(position).toString().replace("_"," "));
        activity.set_font(v);
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position,convertView,parent);
    }

    public T[] get_data(){
        return data;
    }
}
