package csc.colleguescheduller.Views.Entry_Views.Schemas;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.Schemas.Schema_Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Schedule.Schema.Schema;
import csc.colleguescheduller.Models.Schedule.Semester;
import csc.colleguescheduller.Models.Schedule.Specialization;
import csc.colleguescheduller.Models.Schedule.Year;
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

public class Schema_Edit_Fragment extends Entry_Fragment implements SubjectToListView_Interface,Schema_Interface {

    Schema schema;
    Schema_Controller controller;

    public Schema_Edit_Fragment(ActivitytoFragment_interface activity, MyFragment previous) {
        super(activity, Main_Activity.SCHEMAS_EDIT_TITLE, previous);
        controller = new Schema_Controller(this);
    }

    public Schema_Edit_Fragment(ActivitytoFragment_interface activity, Schema schema, MyFragment previous) {
        super(activity, Main_Activity.SCHEMAS_EDIT_TITLE, previous);
        this.schema = schema;
        controller = new Schema_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.schema_add_layout, container, false);

        Spinner spinner = v.findViewById(R.id.schema_add_year_spinner);
        spinner.setAdapter(new Enum_Adapter<>(activity, Year.values()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schema.setYear((Year) parent.getItemAtPosition(position));
                setRequiredToSave(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(schema != null) spinner.setEnabled(false);
        spinner = v.findViewById(R.id.schema_add_specialization_spinner);
        spinner.setAdapter(new Enum_Adapter<>(activity, Specialization.values()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schema.setSpecialization((Specialization) parent.getItemAtPosition(position));
                setRequiredToSave(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(schema != null) spinner.setEnabled(false);
        spinner = v.findViewById(R.id.schema_add_semester_spinner);
        spinner.setAdapter(new Enum_Adapter<>(activity, Semester.values()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schema.setSemester((Semester) parent.getItemAtPosition(position));
                setRequiredToSave(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(schema != null) spinner.setEnabled(false);

        if(schema == null){
            schema = new Schema();
            ((TextView)v.findViewById(R.id.schema_add_btn_save)).setText("ADD");
        }

        load_subject_spinner(v);

        ((TextView) v.findViewById(R.id.schema_add_number_of_sections_editx)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    try {
                        schema.setNumberOfSections(Integer.parseInt(s.toString()));
                        setRequiredToSave(true);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((TextView) v.findViewById(R.id.schema_add_number_of_students_editxt)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    try {
                        schema.setNumberOfStudents(Integer.parseInt(s.toString()));
                        setRequiredToSave(true);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        v.findViewById(R.id.schema_add_btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_subject();
            }
        });

        v.findViewById(R.id.schema_add_btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

        return v;
    }

    @Override
    protected void apply_model() {
        View v = getView();
        ((TextView) v.findViewById(R.id.schema_add_number_of_students_editxt)).setText(schema.getNumberOfStudents() + "");
        ((TextView) v.findViewById(R.id.schema_add_number_of_sections_editx)).setText(schema.getNumberOfSections() + "");
        ((Spinner) v.findViewById(R.id.schema_add_year_spinner)).setSelection(schema.getYear().ordinal());
        ((Spinner) v.findViewById(R.id.schema_add_specialization_spinner)).setSelection(schema.getSpecialization().ordinal());
        ((Spinner) v.findViewById(R.id.schema_add_semester_spinner)).setSelection(schema.getSemester().ordinal());
        ((Spinner) v.findViewById(R.id.schema_add_hall_spinner)).setAdapter(new HallsAndRooms_Adapter(activity, Globals.session.rooms));
        refresh_subjectlist();
        select_room();
    }

    private void select_room() {
        Room room = schema.getHall();
        Spinner spinner = getView().findViewById(R.id.schema_add_hall_spinner);
        HallsAndRooms_Adapter adapter = (HallsAndRooms_Adapter) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).getRoomid().equals(room.getRoomid())) {
                spinner.setSelection(i);
                i = adapter.getCount();
            }
        }
    }

    @Override
    public void remove_subject(Subject subject) {
        for (int i = 0; i < schema.getSubjects().size(); i++) {
            if(subject.getSubjectId().equals(schema.getSubjects().get(i).getSubjectId())){
                schema.getSubjects().remove(i);
                i = schema.getSubjects().size();
            }
        }
        load_subject_spinner(getView());
        refresh_subjectlist();
    }

    private void refresh_subjectlist() {
        ((ListView) getView().findViewById(R.id.schema_add_lstv_subjects)).setAdapter(new Subject_listview_Adapter<>(this, (Context) activity, schema.getSubjects()));
    }

    private void load_subject_spinner(View v) {
        ArrayList<Subject> subjects = getSubjects();
        ((Spinner) v.findViewById(R.id.schema_add_select_subject_spinner)).setAdapter(new Subject_Spinner_Adapter((Context)activity, subjects));

    }

    private ArrayList<Subject> getSubjects() {
        ArrayList<Subject> subjects = (ArrayList<Subject>) Globals.session.subjects.clone();

        for (int i = 0; i < schema.getSubjects().size(); i++) {
            Subject s = schema.getSubjects().get(i);
            for (int j = 0; j < subjects.size(); j++) {
                if (s.getSubjectId().equals(subjects.get(j).getSubjectId())) {
                    subjects.remove(j);
                    j = subjects.size();
                }
            }
        }
        return subjects;
    }

    private void add_subject() {
        View v = getView();
        Subject subject = (Subject) ((Spinner) v.findViewById(R.id.schema_add_select_subject_spinner)).getSelectedItem();
        if (!subject.toString().equals("Select Subject")) {
            schema.getSubjects().add(subject);
            refresh_subjectlist();
            load_subject_spinner(getView());
            setRequiredToSave(true);
        }

    }

    @Override
    public void on_schemas_loaded(ArrayList<Schema> schemas) {

    }

    @Override
    public void on_schema_removed() {

    }

    @Override
    public void Save() {
        String validation = Validate();
        if(validation.equals("")){
            activity.on_connecting();
            if(((TextView)getView().findViewById(R.id.schema_add_btn_save)).getText().toString().equals("ADD")){
                controller.add_schema(schema);
            }else{
                controller.persist_schema(schema);
            }
        }else {
            show_message(validation,R.drawable.ic_robot_unready);
        }
    }

    private String Validate(){
        View v = getView();
        if(((TextView)v.findViewById(R.id.schema_add_number_of_students_editxt)).getText().toString().equals("")){
            return "Number Of Students Can't be Empty";
        }else if(((TextView)v.findViewById(R.id.schema_add_number_of_sections_editx)).getText().toString().equals("")){
            return "Number Of Sections Can't be Empty";
        }else{
            return "";
        }
    }
}
