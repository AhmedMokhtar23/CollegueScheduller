package csc.colleguescheduller.Views.Entry_Views.Subjects;

/*
Ahmed Mokhtar Hassanin
 */

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

import csc.colleguescheduller.Controllers.Subject.Subject_Controller;
import csc.colleguescheduller.Models.Subjects.Subject;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.ListItem_Adapter;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;


public class Subject_list_Fragment extends Entry_Fragment implements Subjects_Interface {

    Subject_Controller controller;
    private ArrayList<Subject> subjects;

    public Subject_list_Fragment() {
        super();
    }

    public Subject_list_Fragment(ActivitytoFragment_interface activity, MyFragment previous) {
        super(activity, Main_Activity.SUBJECTS_LIST_TITLE, previous);
        controller = new Subject_Controller(this);
        subjects = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final ListView list = new ListView((Context)activity);;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                open_subject((Subject) list.getItemAtPosition(i));
            }
        });
        return list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            activity.on_connecting();
            controller.get_subjects();
    }

    @Override
    protected void apply_model() {
        ((ListView)getView()).setAdapter(new ListItem_Adapter(activity,subjects));
    }

    private void open_subject(Subject subject){
        activity.move_to_fragment(new Subject_View_Fragment(activity,subject.copy(),this));
    }

    @Override
    protected void set_buttons() {
        ImageButton btn2 = new ImageButton((Context) activity);
        btn2.setImageResource(R.drawable.ic_add);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        activity.set_buttons(null,btn2);
    }

    private void add(){
        activity.move_to_fragment(new Subject_Edit_Fragment(activity,this));
    }

    @Override
    public void on_subjects_loaded(ArrayList<Subject> subjects) {
        this.subjects = subjects;
        if(!isDetached()){
            activity.abort_connection(null);
            apply_model();
        }
        Toast.makeText((Context) activity,"Subjects List Refreshed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void on_subject_removed() {

    }
}
