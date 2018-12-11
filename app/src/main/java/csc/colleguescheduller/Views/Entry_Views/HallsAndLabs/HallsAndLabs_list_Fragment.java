package csc.colleguescheduller.Views.Entry_Views.HallsAndLabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.HallsAndLabs.HallsAndLabs_Controller;
import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.ListItem_Adapter;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class HallsAndLabs_list_Fragment extends Entry_Fragment implements HallsAndLabs_Interface {

    ArrayList<Room> rooms ;
    HallsAndLabs_Controller controller;

    public HallsAndLabs_list_Fragment(){

    }

    public HallsAndLabs_list_Fragment(ActivitytoFragment_interface activity, MyFragment previous) {
        super(activity, Main_Activity.HALLSANDLABS_LIST_TITLE, previous);
        controller = new HallsAndLabs_Controller(this);
        rooms = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ListView list = new ListView((Context)activity);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                open((Room) adapterView.getItemAtPosition(i));
            }
        });
        return list;
    }

    @Override
    protected void apply_model() {
        View v = getView();
        if(v != null){
            activity.abort_connection(null);
            if(!this.isDetached()){
                ((ListView)v).setAdapter(new ListItem_Adapter<>(activity,rooms));
            }
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            activity.on_connecting();
            controller.get_Rooms();

    }

    @Override
    protected void set_buttons() {
        ImageButton btn1 = new ImageButton((Context) activity);
        btn1.setImageResource(R.drawable.ic_add);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        activity.set_buttons(null,btn1);
    }


    void open(Room room){
        activity.move_to_fragment(new HallsAndLabs_View_Fragment(activity,room.copy(),this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //controller.detachRoomsListener();
    }

    public void on_rooms_loaded(ArrayList<Room> rooms){
        this.rooms = rooms;
        if(!isDetached()){
            apply_model();
        }
        Toast.makeText((Context) activity,"Halls and Labs List Refreshed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void on_room_removed() {

    }

    @Override
    public void on_save_success() {

    }

    private void add(){
        activity.move_to_fragment(new HallsAndLabs_Edit_Fragment(activity,this));
    }
}
