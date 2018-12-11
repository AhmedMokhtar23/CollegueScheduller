package csc.colleguescheduller.Views.Entry_Views.Schemas;

import java.util.ArrayList;

import csc.colleguescheduller.Models.Schedule.Schema.Schema;
import csc.colleguescheduller.Views.MyInterface;

public interface Schema_Interface extends MyInterface {
    void on_schemas_loaded(ArrayList<Schema> schemas);
    void on_schema_removed();
    void on_save_success();
}
