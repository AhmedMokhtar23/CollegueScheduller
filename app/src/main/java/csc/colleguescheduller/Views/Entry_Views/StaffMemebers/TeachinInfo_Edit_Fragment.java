package csc.colleguescheduller.Views.Entry_Views.StaffMemebers;

/*
Ahmed Mokhtar Hassanin
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.StaffMembers.Staffmemebers_Controller;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.TeachingInfo.TeachingInfo_Fragment;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;


public class TeachinInfo_Edit_Fragment extends Entry_Fragment implements StaffMembers_Interface {

    private StaffMember user;
    private TeachingInfo_Fragment teachingInfo_fragment;
    Staffmemebers_Controller controller;

    public TeachinInfo_Edit_Fragment() {
    }

    public TeachinInfo_Edit_Fragment(ActivitytoFragment_interface activity, StaffMember user, MyFragment previous) {
        super(activity, Main_Activity.TEACHING_INFO_TITLE, previous);
        teachingInfo_fragment = new TeachingInfo_Fragment(activity,user,this);
        this.user = user;
        controller = new Staffmemebers_Controller(this,user.copy());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.staffmember_edit_teachinginfo_layout,container,false);
        v.findViewById(R.id.staffmember_edit_teachinginfo_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getRequiredToSave()){
                    Save();
                }else{
                    activity.onBackPressed();
                }
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getFragmentManager().beginTransaction().replace(R.id.staffmember_edit_teachinginfo_fragment,teachingInfo_fragment).commit();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void Save() {
        activity.on_connecting();
        controller.persist_staffMember(user);
    }

    @Override
    public Boolean getRequiredToSave() {
        return teachingInfo_fragment.getRequiredToSave();
    }

    @Override
    public void setRequiredToSave(Boolean requiredToSave) {
        teachingInfo_fragment.setRequiredToSave(requiredToSave);
    }

    @Override
    public void on_members_loaded(ArrayList<StaffMember> members) {

    }

    @Override
    public void on_member_removed() {

    }

    @Override
    public void on_save_success() {
        super.on_save_success();
        ((StaffMemeber_View_Fragment)previous).staffmember = user;
    }
}
