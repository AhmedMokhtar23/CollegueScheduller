package csc.colleguescheduller.Views.StaffMemebrsSettings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import csc.colleguescheduller.Controllers.StaffMemebersSettings.StaffMemebrsSettings_Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Globals.WaitingTime;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.SciDegree;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.DatePicker_Activity;
import csc.colleguescheduller.Views.Entry_Views.Enum_Adapter;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class StaffMemebrsSettings_Fragment extends MyFragment implements StaffMemebersSettings_Interface {

    StaffMemebrsSettings_Controller controller;

    public StaffMemebrsSettings_Fragment(ActivitytoFragment_interface activity, MyFragment previous) {
        super(activity, Main_Activity.STAFFMEBERS_SETTINGS_TITLE, previous);
        controller = new StaffMemebrsSettings_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.staffmembers_settings_layout, container, false);

        ((Spinner) v.findViewById(R.id.staffmembers_settings_spinner_category)).setAdapter(new Enum_Adapter<>(activity, HiringType.values()));
        ((Spinner) v.findViewById(R.id.staffmembers_settings_spinner_category)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                set_dates();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Spinner) v.findViewById(R.id.staffmembers_settings_spinner_scidegree)).setAdapter(new Enum_Adapter<>(activity, SciDegree.values()));
        ((Spinner) v.findViewById(R.id.staffmembers_settings_spinner_scidegree)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                set_dates();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        TextView txtv = v.findViewById(R.id.staffmembers_settings_txtv_startdate);
        txtv.setText(simpleDateFormat.format(new Date()));
        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent((Context) activity, DatePicker_Activity.class), 0);
            }
        });

        txtv = v.findViewById(R.id.staffmembers_settings_txtv_enddate);
        txtv.setText(simpleDateFormat.format(new Date()));
        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent((Context) activity, DatePicker_Activity.class), 1);
            }
        });

        v.findViewById(R.id.staffmembers_settings_btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        set_dates();
    }

    private void set_dates(){
        View v = getView();
        String path = ((Spinner)v.findViewById(R.id.staffmembers_settings_spinner_category)).getSelectedItem().toString() + ","
                + ((Spinner)v.findViewById(R.id.staffmembers_settings_spinner_scidegree)).getSelectedItem().toString();
        WaitingTime time = Globals.session.systemSettings.getWaiting_time().get(path);
        ((TextView)v.findViewById(R.id.staffmembers_settings_txtv_startdate)).setText(Globals.simpleDateFormat.format(time.getStart_date()));
        ((TextView)v.findViewById(R.id.staffmembers_settings_txtv_enddate)).setText(Globals.simpleDateFormat.format(time.getEnd_date()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case 0:
                    ((TextView) getView().findViewById(R.id.staffmembers_settings_txtv_startdate)).setText(data.getStringExtra("date"));
                    break;
                case 1:
                    ((TextView) getView().findViewById(R.id.staffmembers_settings_txtv_enddate)).setText(data.getStringExtra("date"));
                    break;
            }

        }
    }

    private void Save(){
        try{
            activity.on_connecting();
            View v = getView();
            controller.Save(
                    (HiringType) ((Spinner)v.findViewById(R.id.staffmembers_settings_spinner_category)).getSelectedItem(),
                    (SciDegree) ((Spinner)v.findViewById(R.id.staffmembers_settings_spinner_scidegree)).getSelectedItem(),
                    new SimpleDateFormat("dd/MM/yyyy").parse(((TextView)v.findViewById(R.id.staffmembers_settings_txtv_startdate)).getText().toString()),
                    new SimpleDateFormat("dd/MM/yyyy").parse(((TextView)v.findViewById(R.id.staffmembers_settings_txtv_enddate)).getText().toString())
                    );
        }catch (Exception e){

        }

    }

    @Override
    public void on_save_success() {
        activity.abort_connection(null);
        Toast.makeText((Context)activity,"Save Success",Toast.LENGTH_SHORT).show();
        activity.onBackPressed();
    }
}
