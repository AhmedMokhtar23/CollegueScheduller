package csc.colleguescheduller.Views.ScheduleManagment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csc.colleguescheduller.Controllers.ScheduleManagment.ScheduleManagment_Controller;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Schemas.SchemasList_Fragment;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class ScheduleManagment_Fragment extends MyFragment implements ScheduleManagment_Interface {

    ScheduleManagment_Controller controller;

    public ScheduleManagment_Fragment() {
        super();
    }

    public ScheduleManagment_Fragment(ActivitytoFragment_interface activity, MyFragment previous) {
        super(activity, Main_Activity.STAFF_CATEGORIES_TITLE, previous);
        controller = new ScheduleManagment_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.schedulemanagment_layout,container,false);
        v.findViewById(R.id.schedulemanage_btn_schedulegenerate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generate_schedule();
            }
        });
        v.findViewById(R.id.schedulemanage_btn_shemamanage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_schemamanagment();
            }
        });
        return v;
    }

    private void goto_schemamanagment(){
        activity.move_to_fragment(new SchemasList_Fragment(activity,this));
    }

    private void generate_schedule(){
        activity.show_confirm_message(
                "This Will Delete All Previous Schedules data , Your Data Can't Be Recovered After Geneating",
                "Are You Sure You want To Generate New Schedules"
                );
    }

    @Override
    public void on_confirm_authentication(Boolean yes, String password) {
        if(yes){
            activity.on_connecting();
            controller.generate_schedule(password);
        }
    }

    @Override
    public void on_generate_success() {
        activity.abort_connection(null);
        show_message("Schedules Are Generated Successfully",R.drawable.ic_robot_ready);
    }

    @Override
    public void on_generate_failed(String message) {
        activity.abort_connection(null);
        show_message(message,R.drawable.ic_robot_ready);
    }
}
