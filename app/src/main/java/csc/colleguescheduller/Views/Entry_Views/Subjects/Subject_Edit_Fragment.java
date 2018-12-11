package csc.colleguescheduller.Views.Entry_Views.Subjects;

/*
Ahmed Mokhtar Hassanin
 */

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

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.Subject.Subject_Controller;
import csc.colleguescheduller.Models.Subjects.Subject;
import csc.colleguescheduller.Models.Subjects.SubjectType;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.Enum_Adapter;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class Subject_Edit_Fragment extends Entry_Fragment implements Subjects_Interface {

    Subject subject;
    Subject_Controller controller;

    public Subject_Edit_Fragment() {
        super();
    }

    public Subject_Edit_Fragment(ActivitytoFragment_interface activity, Subject subject, MyFragment previous) {
        this(activity, previous);
        this.subject = subject;
        controller = new Subject_Controller(this);
    }

    public Subject_Edit_Fragment(ActivitytoFragment_interface activity, MyFragment previous) {
        super(activity, Main_Activity.SUBJECT_EDIT_TITLE, previous);
        controller = new Subject_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.editsubject_fragment, container, false);
        EditText txt = v.findViewById(R.id.edit_subject_txe_id);
        if (subject == null) {
            subject = new Subject();
            ((Button) v.findViewById(R.id.subject_edit_btn)).setText("ADD");
            txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    subject.setSubjectId(charSequence.toString());
                    setRequiredToSave(true);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } else {
            txt.setEnabled(false);
        }
        v.findViewById(R.id.subject_edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
            }
        });
        txt = v.findViewById(R.id.edit_subject_txe_name);
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subject.setName(charSequence.toString());
                setRequiredToSave(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt = v.findViewById(R.id.edit_subject_txe_nameinarabic);
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subject.setNameInArabic(charSequence.toString());
                setRequiredToSave(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt = v.findViewById(R.id.edit_subject_txe_nosectionpractical);
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    subject.setNumberOfSectionsPractical(Integer.parseInt(charSequence.toString()));
                    setRequiredToSave(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt = v.findViewById(R.id.edit_subject_txe_noofsectionstraining);
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    subject.setNumberOfSectionsApplied(Integer.parseInt(charSequence.toString()));
                    setRequiredToSave(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((Spinner) v.findViewById(R.id.addsubject_sort_by_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subject.setSubjectType((SubjectType) adapterView.getItemAtPosition(i));
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
        ((EditText) v.findViewById(R.id.edit_subject_txe_id)).setText(subject.getSubjectId());
        ((EditText) v.findViewById(R.id.edit_subject_txe_name)).setText(subject.getName());
        ((EditText) v.findViewById(R.id.edit_subject_txe_nameinarabic)).setText(subject.getNameInArabic());
        Spinner spinner = v.findViewById(R.id.addsubject_sort_by_spinner);
        spinner.setAdapter(new Enum_Adapter<>(activity, SubjectType.values()));
        spinner.setSelection(subject.getSubjectType().ordinal());
        ((EditText) v.findViewById(R.id.edit_subject_txe_nosectionpractical)).setText(subject.getNumberOfSectionsPractical() + "");
        ((EditText) v.findViewById(R.id.edit_subject_txe_noofsectionstraining)).setText(subject.getNumberOfSectionsApplied() + "");
    }

    @Override
    public void Save() {
        String validation = Validate();
        if (validation.equals("")) {
            activity.on_connecting();
            if(((TextView)getView().findViewById(R.id.subject_edit_btn)).getText().toString().equals("ADD")){
                controller.add_subject(subject);
            }else{
                controller.persist(subject);
            }
        } else {
            show_message(validation, R.drawable.ic_robot_unready);
        }

    }

    private String Validate() {
        if (subject.getSubjectId().equals("")) {
            return "Subject's Id Can't Be Empty";
        } else if (subject.getName().equals("")) {
            return "Subject's Name Can't Be Empty";
        } else if (subject.getNameInArabic().equals("")) {
            return "Subject's Name In Arabic Can't Be Empty";
        } else if (((EditText) getView().findViewById(R.id.edit_subject_txe_noofsectionstraining)).getText().equals("")) {
            return "Number of Section training Can't Be Empty";
        } else if (((EditText) getView().findViewById(R.id.edit_subject_txe_nosectionpractical)).getText().equals("")) {
            return "Number of Section Practical Can't Be Empty";
        } else {
            return "";
        }
    }

    @Override
    public void on_subjects_loaded(ArrayList<Subject> subjects) {

    }

    @Override
    public void on_subject_removed() {

    }
}
