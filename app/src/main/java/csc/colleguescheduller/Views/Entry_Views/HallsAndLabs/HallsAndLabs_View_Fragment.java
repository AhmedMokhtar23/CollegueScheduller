package csc.colleguescheduller.Views.Entry_Views.HallsAndLabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.HallsAndLabs.HallsAndLabs_Controller;
import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class HallsAndLabs_View_Fragment extends Entry_Fragment implements HallsAndLabs_Interface {

    Room room;
    HallsAndLabs_Controller controller;


    public HallsAndLabs_View_Fragment(ActivitytoFragment_interface activity, Room room, MyFragment previous) {
        super(activity, Main_Activity.HALLSANDLABS_View_TITLE, previous);
        this.room = room;
        this.controller = new HallsAndLabs_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hallsandlabs_view,container,false);
    }

    @Override
    protected void apply_model() {
        View v = getView();
        if(v != null){
        ((TextView)v.findViewById(R.id.hallandlabs_txtv_id)).setText(room.getRoomid());
        ((TextView)v.findViewById(R.id.hallandlabs_txtv_capacity)).setText(room.getCapacity() + "");
        ((TextView)v.findViewById(R.id.hallandlabs_txtv_status)).setText(room.getStatus().toString());
        ((TextView)v.findViewById(R.id.hallandlabs_txtv_type)).setText(room.getType().toString());}
    }

    @Override
    protected void set_buttons() {
        ImageButton btn1 = new ImageButton((Context)activity);
        btn1.setImageResource(R.drawable.ic_delete);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.on_connecting();
                controller.remove(room);
            }
        });
        ImageButton btn2 = new ImageButton((Context)activity);
        btn2.setImageResource(R.drawable.ic_edit);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });
        activity.set_buttons(btn2,btn1);
    }

    private void edit(){
        activity.move_to_fragment(new HallsAndLabs_Edit_Fragment(activity,room.copy(),this));
    }

    @Override
    public void on_rooms_loaded(ArrayList<Room> rooms) {

    }

    @Override
    public void on_room_removed() {
        activity.abort_connection(null);
        activity.onBackPressed();
    }

    @Override
    public void on_save_success() {

    }
}
