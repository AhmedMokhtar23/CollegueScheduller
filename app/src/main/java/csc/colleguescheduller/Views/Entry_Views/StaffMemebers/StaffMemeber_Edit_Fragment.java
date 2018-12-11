package csc.colleguescheduller.Views.Entry_Views.StaffMemebers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import csc.colleguescheduller.Controllers.StaffMembers.Staffmemebers_Controller;
import csc.colleguescheduller.Models.StaffMembers.HiringDegree;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.PartTimeMember;
import csc.colleguescheduller.Models.StaffMembers.Permission;
import csc.colleguescheduller.Models.StaffMembers.SciDegree;
import csc.colleguescheduller.Models.StaffMembers.SciDepartment;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.DatePicker_Activity;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.Enum_Adapter;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class StaffMemeber_Edit_Fragment extends Entry_Fragment implements StaffMembers_Interface {

    private StaffMember staffmember;
    private HiringDegree hiringDegree;
    private HiringType hiringType;
    private Boolean Add;
    Staffmemebers_Controller controller;

    public StaffMemeber_Edit_Fragment() {
    }

    public StaffMemeber_Edit_Fragment(ActivitytoFragment_interface activity, StaffMember user, MyFragment previous) {
        super(activity, Main_Activity.STAFFMEMBER_Edit_TITLE, previous);
        this.staffmember = user;
        controller = new Staffmemebers_Controller(this,staffmember.copy());
        Add = false;
    }

    public StaffMemeber_Edit_Fragment(ActivitytoFragment_interface activity, MyFragment previous, HiringDegree hiringDegree, HiringType hiringType) {
        super(activity, Main_Activity.STAFFMEMBER_ADD_TITLE, previous);
        this.hiringDegree = hiringDegree;
        this.hiringType = hiringType;
        controller = new Staffmemebers_Controller(this);
        Add = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v;
        if((staffmember == null && hiringType == HiringType.Full_Time) || (staffmember != null && staffmember.getHiringType() == HiringType.Full_Time)){
            v = inflater.inflate(R.layout.staffmember_fulltime_edit_layout, container, false);
        }else{
            v = inflater.inflate(R.layout.staffmember_parttime_edit_layout, container, false);
        }
        if (Add) {
            Button b = v.findViewById(R.id.staffmember_edit_btn_submit);
            b.setText("ADD");
            if(hiringType == HiringType.Full_Time){
                staffmember = new StaffMember();
            }else{
                staffmember = new PartTimeMember();
            }

            staffmember.setHiringDegree(hiringDegree);
            staffmember.setHiringType(hiringType);
            ((EditText) v.findViewById(R.id.staffmember_edit_txte_id)).addTextChangedListener(new TextWatcher() {


                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    staffmember.setUId(charSequence.toString().toLowerCase());
                    setRequiredToSave(true);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else{
            v.findViewById(R.id.staffmember_edit_txte_id).setEnabled(false);

        }
        v.findViewById(R.id.staffmember_edit_btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
            }
        });
        Spinner spinner = v.findViewById(R.id.staffmember_edit_spinner_scidegree);
        spinner.setAdapter(new Enum_Adapter<>(activity, SciDegree.values()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                staffmember.setScientificDegree((SciDegree) adapterView.getItemAtPosition(i));
                setRequiredToSave(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner = v.findViewById(R.id.staffmember_edit_spinner_scidepartment);
        spinner.setAdapter(new Enum_Adapter<>(activity, SciDepartment.values()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                staffmember.setScientificDepartment((SciDepartment) adapterView.getItemAtPosition(i));
                setRequiredToSave(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner = v.findViewById(R.id.staffmember_edit_spinner_permission);
        spinner.setAdapter(new Enum_Adapter<>(activity,Permission.values()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                staffmember.setPermission((Permission) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((EditText) v.findViewById(R.id.staffmember_edit_txte_accesscode)).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                staffmember.setAccessCode(charSequence.toString());
                setRequiredToSave(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((EditText) v.findViewById(R.id.staffmember_edit_txte_fullname)).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                staffmember.setName(charSequence.toString());
                setRequiredToSave(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        ((EditText) v.findViewById(R.id.staffmember_edit_txte_workours)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    staffmember.setWorkHours(Integer.parseInt(charSequence.toString()));
                }
                setRequiredToSave(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if(staffmember instanceof PartTimeMember){
            ((EditText) v.findViewById(R.id.staffmember_edit_txte_collegue)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals("")) {
                        ((PartTimeMember)staffmember).setcollege(charSequence.toString());
                    }
                    setRequiredToSave(true);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            ((EditText) v.findViewById(R.id.staffmember_edit_txte_university)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals("")) {
                        ((PartTimeMember)staffmember).setuniversity(charSequence.toString());
                    }
                    setRequiredToSave(true);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }


        TextView txtv_hiringdate = v.findViewById(R.id.staffmember_edit_txte_hiringdate);
        txtv_hiringdate.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SimpleDateFormat f = new SimpleDateFormat("d/m/yyyy");
                try {
                    staffmember.setHiringDate(f.parse(charSequence.toString()));
                } catch (Exception e) {

                }

                setRequiredToSave(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtv_hiringdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent((Context) activity, DatePicker_Activity.class), 0);
            }
        });

        return v;
    }


    @Override
    protected void apply_model() {
        View v = getView();
        if (v != null) {
            ((EditText) v.findViewById(R.id.staffmember_edit_txte_id)).setText(staffmember.getUID());
            ((EditText) v.findViewById(R.id.staffmember_edit_txte_accesscode)).setText(staffmember.getAccessCode());
            ((EditText) v.findViewById(R.id.staffmember_edit_txte_fullname)).setText(staffmember.getName());

            ((Spinner) v.findViewById(R.id.staffmember_edit_spinner_scidegree)).setSelection(staffmember.getScientificDegree().ordinal());

            ((Spinner) v.findViewById(R.id.staffmember_edit_spinner_scidepartment)).setSelection(staffmember.getScientificDepartment().ordinal());

            ((EditText) v.findViewById(R.id.staffmember_edit_txte_workours)).setText(staffmember.getWorkHours() + "");
            ((TextView) v.findViewById(R.id.staffmember_edit_txte_hiringdate)).setText((new SimpleDateFormat("dd/MM/yyyy")).format(staffmember.getHiringDate()));
            if(staffmember instanceof PartTimeMember){
                ((EditText) v.findViewById(R.id.staffmember_edit_txte_collegue)).setText(((PartTimeMember)staffmember).getcollege());
                ((EditText) v.findViewById(R.id.staffmember_edit_txte_university)).setText(((PartTimeMember)staffmember).getuniversity());
            }
        }
    }

    @Override
    public void Save() {
        String validation = Validate();
        if(validation.equals("")){
            activity.on_connecting();
            if(Add){
                controller.add_staffMember(staffmember);
            }else {
                controller.persist_staffMember(staffmember);
            }
        }else{
            show_message(validation,R.drawable.ic_robot_unready);
        }
    }

    private String Validate() {
        if (staffmember.getUID().equals("")) {
            return "Member's Id Can't Be Empty";
        } else if (staffmember.getAccessCode().equals("")) {
            return "Member's Access Code Can't Be Empty";
        } else if (staffmember.getUID().equals("")) {
            return "Member's Name Can't Be Empty";
        } else if (((EditText) getView().findViewById(R.id.staffmember_edit_txte_workours)).getText().toString().equals("")) {
            return "Work Hours Can't Be Empty";
        } else {
            return "";
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            ((TextView) getView().findViewById(R.id.staffmember_edit_txte_hiringdate)).setText(data.getStringExtra("date"));
        }
    }

    @Override
    public void on_members_loaded(ArrayList<StaffMember> members) {

    }

    @Override
    public void on_member_removed() {

    }

    @Override
    public void on_save_success() {
        if(((TextView)getView().findViewById(R.id.staffmember_edit_btn_submit)).getText().toString().equals("SAVE")){
            ((StaffMemeber_View_Fragment)previous).staffmember = staffmember;
        }
        super.on_save_success();
    }
}
