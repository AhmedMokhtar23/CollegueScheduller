package csc.colleguescheduller.Views.Entry_Views.StaffMemebers;

/*
Ahmed Mokhtar Hassanin
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import csc.colleguescheduller.Controllers.StaffMembers.Staffmemebers_Controller;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.PartTimeMember;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class StaffMemeber_View_Fragment extends Entry_Fragment implements StaffMembers_Interface {

    StaffMember staffmember;
    Staffmemebers_Controller controller;

    public StaffMemeber_View_Fragment() {
    }

    public StaffMemeber_View_Fragment(ActivitytoFragment_interface activity, StaffMember staffmember, MyFragment previous) {
        super(activity, Main_Activity.STAFFMEMBER_DETAIL_TITLE, previous);
        this.staffmember = staffmember;
        controller = new Staffmemebers_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v;
        if(staffmember.getHiringType() == HiringType.Full_Time){
            v = inflater.inflate(R.layout.staffmember_fulltime_view_layout, container, false);
        }else{
            v = inflater.inflate(R.layout.staffmember_parttime_view_layout, container, false);
        }

        v.findViewById(R.id.staffmemeber_btn_teachinginfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teachinginfo_click();
            }
        });
        return v;
    }


    private void teachinginfo_click() {
        activity.move_to_fragment(new TeachinInfo_Edit_Fragment(activity, staffmember.copy(), this));
    }

    @Override
    protected void apply_model() {
        View v = getView();
        ((TextView) v.findViewById(R.id.staffmember_view_txtv_id)).setText(staffmember.getUID());
        ((TextView) v.findViewById(R.id.staffmember_view_txtv_accesscode)).setText(staffmember.getAccessCode());
        ((TextView) v.findViewById(R.id.staffmember_view_txtv_fullname)).setText(staffmember.getName());
        ((TextView) v.findViewById(R.id.staffmember_view_txtv_scidegree)).setText(staffmember.getScientificDegree().toString().replace("_", " "));
        ((TextView) v.findViewById(R.id.staffmember_view_txtv_scidepartment)).setText(staffmember.getScientificDepartment().toString().replace("_", " "));
        ((TextView) v.findViewById(R.id.staffmember_view_txtv_accesspermission)).setText(staffmember.getPermission().toString());
        ((TextView) v.findViewById(R.id.staffmember_view_txtv_workours)).setText(staffmember.getWorkHours() + " ");
        ((TextView) v.findViewById(R.id.staffmember_view_txtv_hiringdate)).setText((new SimpleDateFormat("dd/MM/yyyy")).format(staffmember.getHiringDate()));
        if(staffmember.getHiringType() == HiringType.Part_Time){
            ((TextView) v.findViewById(R.id.staffmember_view_txtv_collegue)).setText(((PartTimeMember)staffmember).getcollege());
            ((TextView) v.findViewById(R.id.staffmember_view_txtv_university)).setText(((PartTimeMember)staffmember).getuniversity());
        }
    }

    @Override
    protected void set_buttons() {

        ImageButton btn1 = new ImageButton((Context) activity);
        btn1.setImageResource(R.drawable.ic_delete);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        ImageButton btn2 = new ImageButton((Context) activity);
        btn2.setImageResource(R.drawable.ic_edit);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

        activity.set_buttons(btn2, btn1);
    }

    private void edit() {
        activity.move_to_fragment(new StaffMemeber_Edit_Fragment(activity, staffmember.copy(), this));
    }

    private void delete() {
        activity.on_connecting();
        controller.remove_staffmember(staffmember);
    }

    @Override
    public void on_members_loaded(ArrayList<StaffMember> members) {

    }

    @Override
    public void on_member_removed() {
        activity.abort_connection(null);
        if(getTargetfragment_onhold() != null){
            activity.move_to_fragment(getTargetfragment_onhold());
        }else {
            activity.onBackPressed();
        }
    }

    @Override
    public void on_save_success() {

    }
}
