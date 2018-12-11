package csc.colleguescheduller.Views.AdminPanel;

/*
Mohamed Mostafa Mohamed
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import csc.colleguescheduller.Controllers.AdminPanel.AdminPanel_Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.StaffMembers.Permission;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.DataBaseManagment.DataBaseManage_Fragment;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;
import csc.colleguescheduller.Views.ScheduleManagment.ScheduleManagment_Fragment;
import csc.colleguescheduller.Views.StaffManagment.StaffManagment_Fragment;


public class AdminPanel_Fragment extends MyFragment implements AdminPanel_Interface {

    AdminPanel_Controller controller;

    public AdminPanel_Fragment() {

    }

    public AdminPanel_Fragment(ActivitytoFragment_interface activity) {
        super(activity, Main_Activity.ADMIN_PANEL_TITLE);
        controller = new AdminPanel_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v;
        if (Globals.session.user.getPermission() == Permission.Admin) {
            v = inflater.inflate(R.layout.adminpanel_layout, container, false);
            v.findViewById(R.id.adminpanel_btn_staffmanagment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToStaffManage();
                }
            });
            v.findViewById(R.id.adminpanel_btn_systemenable).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toggle_System_Enable();
                }
            });
            v.findViewById(R.id.adminpanel_btn_systemreset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ResetSystem();
                }
            });
        } else {
            v = inflater.inflate(R.layout.adminassistant_layout, container, false);
        }
        v.findViewById(R.id.adminpanel_btn_databasemanagment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDataBaseManagment();
            }
        });
        v.findViewById(R.id.adminpanel_btn_schedulemanage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScheduleManage();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Globals.session.user.getPermission() == Permission.Admin)
            on_system_toggled();
    }

    private void goToDataBaseManagment() {
        activity.move_to_fragment(new DataBaseManage_Fragment(activity, this));
    }

    private void goToSchudleMange() {
        //activity.move_to_fragment(new ScheduleManage_Fragment(activity));
    }

    private void goToStaffManage() {
        activity.move_to_fragment(new StaffManagment_Fragment(activity, this));
    }

    private void goToReportsSchedule() {
        //activity.move_to_fragment(new ReportsSchedule_Fragment(activity));
    }

    private void goToScheduleManage() {
        activity.move_to_fragment(new ScheduleManagment_Fragment(activity, this));
    }

    private void Toggle_System_Enable() {
        activity.on_connecting();
        controller.Toggle_System_Enability();
    }

    public void ResetSystem() {
        activity.show_confirm_message(
                "This Will Reset The System To Its Default Settings. Your Data Can't Be Recovered After Resetting",
                "Are You Sure You Want To Reset"
        );
    }

    @Override
    public void on_confirm_authentication(Boolean yes, String password) {
        if (yes) {
            activity.on_connecting();
            controller.Reset_System(password);
        }
    }

    @Override
    public void on_system_toggled() {
        activity.abort_connection(null);
        if (Globals.session.systemSettings.getIs_Ready()) {
            ((ImageView) getView().findViewById(R.id.adminpanel_img_systemenable)).setImageResource(R.drawable.ic_system_disabled);
            ((TextView) getView().findViewById(R.id.adminpanel_txtv_systemenable)).setText("DISABLE");
        } else {
            ((ImageView) getView().findViewById(R.id.adminpanel_img_systemenable)).setImageResource(R.drawable.ic_system_enabled);
            ((TextView) getView().findViewById(R.id.adminpanel_txtv_systemenable)).setText("ENABLE");
        }
    }

    @Override
    public void on_system_reset() {
        activity.abort_connection(null);
        Toast.makeText((Context) activity, "System Was Reset Successfully Re sign In Required", Toast.LENGTH_LONG).show();
        activity.onBackPressed();
    }


}
