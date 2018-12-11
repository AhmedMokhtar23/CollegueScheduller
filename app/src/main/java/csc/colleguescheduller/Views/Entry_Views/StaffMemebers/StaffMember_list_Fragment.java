package csc.colleguescheduller.Views.Entry_Views.StaffMemebers;

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

import csc.colleguescheduller.Controllers.StaffMembers.Staffmemebers_Controller;
import csc.colleguescheduller.Models.StaffMembers.HiringDegree;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.MyFragment;

public class StaffMember_list_Fragment extends Entry_Fragment implements StaffMembers_Interface {

    private Staffmemebers_Controller controller;
    private HiringType hiringType;
    private HiringDegree hiringDegree;
    private ArrayList<StaffMember> staffMembers;


    public StaffMember_list_Fragment() {
    }

    public StaffMember_list_Fragment(ActivitytoFragment_interface activity, HiringType hiringType, HiringDegree hiringDegree, MyFragment previous) {
        super(activity,
                hiringType.toString().replace("_"," ") + " " + hiringDegree.toString(),
                previous);
        this.hiringType = hiringType;
        this.hiringDegree = hiringDegree;
        controller = new Staffmemebers_Controller(this);
        staffMembers = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ListView lst = new ListView((Context)activity);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item_selected((StaffMember) adapterView.getItemAtPosition(i));
            }
        });
        return lst;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //if(!updated){
            activity.on_connecting();
            controller.get_staffmembers(hiringType,hiringDegree);
        //}
    }

    @Override
    protected void apply_model() {
        View v = getView();
        if(v != null){
        ((ListView)getView()).setAdapter(new StaffMemebrs_Adapter((Context)activity,staffMembers,activity));}
    }

    @Override
    protected void set_buttons() {
        ImageButton btn1 = new ImageButton((Context)activity);
        btn1.setImageResource(R.drawable.ic_add);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        activity.set_buttons(null,btn1);
    }

    private void add(){
        activity.move_to_fragment(new StaffMemeber_Edit_Fragment(activity,this,hiringDegree,hiringType));
    }


    private void item_selected(StaffMember staffMember){
        activity.move_to_fragment(new StaffMemeber_View_Fragment(activity,staffMember.copy(),this));
    }

    @Override
    public void on_members_loaded(ArrayList<StaffMember> members) {
        activity.abort_connection(null);
        this.staffMembers = members;
        apply_model();
        Toast.makeText((Context) activity,"StaffMembers List Refreshed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void on_member_removed() {

    }

    @Override
    public void on_save_success() {
        activity.abort_connection(new View((Context)activity));
        activity.move_to_fragment(getTargetfragment_onhold());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //controller.detachStaffmembersListener();
    }
}
