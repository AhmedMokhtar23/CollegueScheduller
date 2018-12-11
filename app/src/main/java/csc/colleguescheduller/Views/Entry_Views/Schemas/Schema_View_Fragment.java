package csc.colleguescheduller.Views.Entry_Views.Schemas;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.Schemas.Schema_Controller;
import csc.colleguescheduller.Models.Schedule.Schema.Schema;
import csc.colleguescheduller.Models.Subjects.Subject;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.SubjectToListView_Interface;
import csc.colleguescheduller.Views.Entry_Views.Subject_listview_Adapter;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class Schema_View_Fragment extends Entry_Fragment implements Schema_Interface,SubjectToListView_Interface {

    private Schema schema;
    private Schema_Controller controller;

    public Schema_View_Fragment() {

    }

    public Schema_View_Fragment(ActivitytoFragment_interface activity, Schema schema, MyFragment previous) {
        super(activity, Main_Activity.SCHEMAS_VIEW_TITLE, previous);
        this.schema = schema;
        controller = new Schema_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.schema_view_layout,container,false);
        ((TextView)v.findViewById(R.id.schema_view_txtv_year)).setText(schema.getYear().toString());
        ((TextView)v.findViewById(R.id.schema_view_txtv_semester)).setText(schema.getSemester().toString());
        ((TextView)v.findViewById(R.id.schema_view_txtv_specialization)).setText(schema.getSpecialization().toString());
        ((TextView)v.findViewById(R.id.schema_view_txtv_hall)).setText(schema.getHall().getRoomid());
        ((TextView)v.findViewById(R.id.schema_view_number_of_txtv_students)).setText(schema.getNumberOfStudents() + "");
        ((TextView)v.findViewById(R.id.schema_view_number_of_txtv_sections)).setText(schema.getNumberOfSections() + "");
        ((ListView)v.findViewById(R.id.schema_view_list)).setAdapter(new Subject_listview_Adapter(this,(Context) activity, schema.getSubjects()));
        return v;
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

    private void delete(){
        activity.on_connecting();
        controller.remove_schema(schema);
    }

    private void edit(){
        activity.move_to_fragment(new Schema_Edit_Fragment(activity,schema.copy(),previous));
    }

    @Override
    public void remove_subject(Subject subject) {

    }

    @Override
    public void on_schemas_loaded(ArrayList<Schema> schemas) {

    }

    @Override
    public void on_schema_removed() {
        activity.abort_connection(null);
        activity.onBackPressed();
    }
}
