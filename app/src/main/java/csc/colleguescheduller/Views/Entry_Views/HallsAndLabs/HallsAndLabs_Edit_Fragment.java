package csc.colleguescheduller.Views.Entry_Views.HallsAndLabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.HallsAndLabs.HallsAndLabs_Controller;
import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Rooms.RoomStatus;
import csc.colleguescheduller.Models.Rooms.RoomType;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.Enum_Adapter;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class HallsAndLabs_Edit_Fragment extends Entry_Fragment implements HallsAndLabs_Interface {

    Room room;
    HallsAndLabs_Controller controller;

    public HallsAndLabs_Edit_Fragment() {

    }

    public HallsAndLabs_Edit_Fragment(ActivitytoFragment_interface activity,Room room, MyFragment previous) {
        super(activity, Main_Activity.HALLSANDLABS_Edit_TITLE, previous);
        this.room = room;
        controller = new HallsAndLabs_Controller(this);
    }
    public HallsAndLabs_Edit_Fragment(ActivitytoFragment_interface activity, MyFragment previous) {
        super(activity, Main_Activity.HALLSANDLABS_Edit_TITLE, previous);
        controller = new HallsAndLabs_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hallandlabs_edit,container,false);
        if(room == null){
            room = new Room();
            ((TextView)v.findViewById(R.id.hallandlabs_btn)).setText("ADD");
            ((EditText)v.findViewById(R.id.hallandlabs_txte_id)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    room.setRoomid(charSequence.toString());
                    setRequiredToSave(true);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else{
            v.findViewById(R.id.hallandlabs_txte_id).setEnabled(false);
        }
        v.findViewById(R.id.hallandlabs_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
        ((EditText)v.findViewById(R.id.hallandlabs_txte_id)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                room.setRoomid(charSequence.toString());
                setRequiredToSave(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((EditText)v.findViewById(R.id.hallandlabs_txte_capacity)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    room.setCapacity(Integer.parseInt(charSequence.toString()));
                    setRequiredToSave(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Spinner spinner = v.findViewById(R.id.hallandlabs_sp_type);
        spinner.setAdapter(new Enum_Adapter<>(activity, RoomType.values()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                room.setType((RoomType) adapterView.getItemAtPosition(i));
                setRequiredToSave(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner = v.findViewById(R.id.hallandlabs_sp_status);
        spinner.setAdapter(new Enum_Adapter<>(activity, RoomStatus.values()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                room.setStatus((RoomStatus) adapterView.getItemAtPosition(i));
                setRequiredToSave(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }


    @Override
    protected void apply_model() {
        View v = getView();
        if(v != null){
            ((EditText)v.findViewById(R.id.hallandlabs_txte_id)).setText(room.getRoomid());
            ((EditText)v.findViewById(R.id.hallandlabs_txte_capacity)).setText(room.getCapacity() + "");
            ((Spinner)v.findViewById(R.id.hallandlabs_sp_type)).setSelection(room.getType().ordinal());
            ((Spinner)v.findViewById(R.id.hallandlabs_sp_status)).setSelection(room.getStatus().ordinal());
        }
    }

    @Override
    public void Save() {
        String validation = Validate();
        if(validation.equals("")){
            activity.on_connecting();
            if(((TextView)getView().findViewById(R.id.hallandlabs_btn)).getText().toString().equals("ADD")){
                controller.add_room(room);
            }else{
                controller.persist_room(room);
            }
        }else{
            show_message(validation,R.drawable.ic_robot_unready);
        }
    }

    private String Validate(){
        View v = getView();
        if(((EditText)v.findViewById(R.id.hallandlabs_txte_id)).getText().toString().equals("")){
            return "ID Can't Be Empty";
        }else if(((EditText)v.findViewById(R.id.hallandlabs_txte_capacity)).getText().toString().equals("")){
            return "Capacity Can't Be Empty";
        }else{
            return "";
        }
    }

    @Override
    public void on_rooms_loaded(ArrayList<Room> rooms) {

    }

    @Override
    public void on_room_removed() {

    }

    public void on_save_success(){
        if(((TextView)getView().findViewById(R.id.hallandlabs_btn)).getText().toString().equals("SAVE")){
            ((HallsAndLabs_View_Fragment)previous).room = room;
        }
        super.on_save_success();
    }


}
