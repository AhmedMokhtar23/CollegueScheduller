package csc.colleguescheduller.Views.Entry_Views.TeachingInfo;

/*
Ahmed Mokhtar Hassanin
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.StaffMembers.MemberSubject;
import csc.colleguescheduller.Models.StaffMembers.MemberSubjectType;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Models.Subjects.Subject;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.Enum_Adapter;
import csc.colleguescheduller.Views.Entry_Views.SubjectToListView_Interface;
import csc.colleguescheduller.Views.Entry_Views.Subject_Spinner_Adapter;
import csc.colleguescheduller.Views.Entry_Views.Subject_listview_Adapter;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class TeachingInfo_Fragment extends Entry_Fragment implements SubjectToListView_Interface {

    private StaffMember user;
    Controller controller;

    public TeachingInfo_Fragment() {

    }

    public TeachingInfo_Fragment(ActivitytoFragment_interface activity, StaffMember user, MyFragment previous) {
        super(activity, Main_Activity.TEACHING_INFO_TITLE, previous);
        this.user = user;
        controller = new Controller();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.teachinginfo_layout, container, false);
        ((Spinner)v.findViewById(R.id.teachinginfo_spinner_type)).setAdapter(new Enum_Adapter<>(activity, MemberSubjectType.values()));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.teachinginfo_btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_subject();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void apply_model() {
        View v = getView();
        if (v != null) {
            set_days();
            refresh_subjectlist();
            load_spinner();
            ((TextView) getView().findViewById(R.id.teachinginfo_txtv_techingsubject_2)).setText(user.getTeachingSubjects().size() + "/" + user.getMax_subjects());
        }
    }

    private void set_days() {
        View v = getView();
        ViewGroup teaching_subjects_v = v.findViewById(R.id.teachinginfo_lyt_teachingdays);

        for (int i = 0; i < teaching_subjects_v.getChildCount(); i++) {
            TextView txt = (TextView) teaching_subjects_v.getChildAt(i);
            for (DayOfWeek day : user.getWorkdays()) {
                if (txt.getText().toString().equals(day.toString().toLowerCase().substring(0, 3))) {
                    txt.setBackgroundResource(R.drawable.ic_txt_backgrount);
                }
            }
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select_workday(view);
                }
            });
        }
        ((TextView) v.findViewById(R.id.teachinginfo_txtv_nodays)).setText(user.getWorkdays().size() + "");

    }

    private void refresh_subjectlist() {
        View main_view = getView();
        ListView lst = main_view.findViewById(R.id.teachinginfo_lstv_teachingsubjects);
        lst.setAdapter(new Subject_listview_Adapter(this, (Context) activity, user.getTeachingSubjects()));
    }

    private void load_spinner() {
        Spinner subject_spinner = getView().findViewById(R.id.teachinginfo_spinner);
        subject_spinner.setAdapter(new Subject_Spinner_Adapter((Context) activity, getSubjects()));
    }

    private ArrayList<Subject> getSubjects() {
        ArrayList<Subject> subjects = new ArrayList<>();
        for (Subject subject : Globals.session.subjects) {
            MemberSubject memberSubject = new MemberSubject();
            memberSubject.setSubject(subject);
            subjects.add(memberSubject);
        }
        for (int i = 0; i < user.getTeachingSubjects().size(); i++) {
            MemberSubject s = user.getTeachingSubjects().get(i);
            for (int j = 0; j < subjects.size(); j++) {
                if (s.getSubject().getSubjectId().equals(subjects.get(j).getSubjectId())) {
                    subjects.remove(j);
                    j = subjects.size();
                }
            }
        }
        return subjects;
    }

    @Override
    public void remove_subject(Subject subject) {
        user.getTeachingSubjects().remove(subject);
        refresh_subjectlist();
        load_spinner();
        setRequiredToSave(true);
        ((TextView) getView().findViewById(R.id.teachinginfo_txtv_techingsubject_2)).setText(user.getTeachingSubjects().size() + "/" + user.getMax_subjects());

    }

    private void select_workday(View v) {
        DayOfWeek day = DayOfWeek.valueOf((String) v.getTag());
        if (user.getWorkdays().contains(day)) {
            user.getWorkdays().remove(day);
            v.setBackgroundResource(android.R.color.transparent);
        } else {
            user.getWorkdays().add(day);
            v.setBackgroundResource(R.drawable.ic_txt_backgrount);
        }
        ((TextView) getView().findViewById(R.id.teachinginfo_txtv_nodays)).setText(user.getWorkdays().size() + "");
        setRequiredToSave(true);
    }

    private void add_subject() {
        View v = getView();
        Subject subject = (Subject) ((Spinner) v.findViewById(R.id.teachinginfo_spinner)).getSelectedItem();
        if (!subject.toString().equals("Select Subject")) {
            if (user.getTeachingSubjects().size() < user.getMax_subjects()) {
                MemberSubject memberSubject = new MemberSubject();
                memberSubject.setSubject(subject);
                try {
                    memberSubject.setNumberOfSections(Integer.parseInt(((EditText) v.findViewById(R.id.teachinginfo_txte_sections)).getText().toString()));
                    memberSubject.setType((MemberSubjectType) ((Spinner)v.findViewById(R.id.teachinginfo_spinner_type)).getSelectedItem());
                    user.getTeachingSubjects().add(memberSubject);
                    refresh_subjectlist();
                    load_spinner();
                    setRequiredToSave(true);
                    ((TextView) getView().findViewById(R.id.teachinginfo_txtv_techingsubject_2)).setText(user.getTeachingSubjects().size() + "/" + user.getMax_subjects());

                }catch (Exception e){
                    show_message("Invaled Number of Sections",R.drawable.ic_robot_unready);
                }
            } else {
                show_message("Teaching Subjects Limit Exceeds", R.drawable.ic_robot_unready);
            }
        }

    }
}
