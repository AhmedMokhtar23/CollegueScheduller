package csc.colleguescheduller.Views.Entry_Views.Schemas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;

public class HallsAndRooms_Adapter extends ArrayAdapter<Room> {

    ActivitytoFragment_interface activity;

    public HallsAndRooms_Adapter(ActivitytoFragment_interface activity, ArrayList<Room> rooms) {
        super((Context)activity,android.R.layout.simple_list_item_1,rooms);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView txt = (TextView) LayoutInflater.from((Context)activity).inflate(android.R.layout.simple_list_item_1,parent,false);
        txt.setText(getItem(position).getRoomid());
        activity.set_font(txt);
        return txt;
    }


}
