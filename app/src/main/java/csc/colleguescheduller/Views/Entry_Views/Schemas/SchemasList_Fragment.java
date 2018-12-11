package csc.colleguescheduller.Views.Entry_Views.Schemas;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.Schemas.Schema_Controller;
import csc.colleguescheduller.Models.Schedule.Schema.Schema;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Entry_Views.Entry_Fragment;
import csc.colleguescheduller.Views.Entry_Views.ListItem_Adapter;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class SchemasList_Fragment extends Entry_Fragment implements Schema_Interface {

    private Schema_Controller controller;
    ArrayList<Schema> schemas = new ArrayList<>();

    public SchemasList_Fragment() {
    }

    public SchemasList_Fragment(ActivitytoFragment_interface activity, MyFragment previous) {
        super(activity, Main_Activity.SCHEMAS_LIST_TITLE,previous);
        controller = new Schema_Controller(this);
    }

    @Override
    protected void apply_model() {
        ListView lst = (ListView) getView();
        lst.setAdapter(new ListItem_Adapter<>(activity,schemas));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ListView lst = new ListView((Context) activity);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                open_schema((Schema) parent.getItemAtPosition(position));
            }
        });
        return lst;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.on_connecting();
        controller.get_schemas();
    }

    @Override
    protected void set_buttons() {
        ImageButton btn1 = new ImageButton((Context) activity);
        btn1.setImageResource(R.drawable.ic_add);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_schema();
            }
        });
        activity.set_buttons(null,btn1);
    }


    private void open_schema(Schema schema){
        activity.move_to_fragment(new Schema_View_Fragment(activity,schema,this));
    }


    private void add_schema(){
        activity.move_to_fragment(new Schema_Edit_Fragment(activity,this));
    }

    @Override
    public void on_schemas_loaded(ArrayList<Schema> schemas) {
        activity.abort_connection(null);
        this.schemas = schemas;
        apply_model();
    }

    @Override
    public void on_schema_removed() {

    }
}
