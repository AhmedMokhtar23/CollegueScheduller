package csc.colleguescheduller.Controllers.Schemas;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Rooms.RoomStatus;
import csc.colleguescheduller.Models.Rooms.RoomType;
import csc.colleguescheduller.Models.Schedule.Schema.Schema;
import csc.colleguescheduller.Models.Schedule.Semester;
import csc.colleguescheduller.Models.Schedule.Specialization;
import csc.colleguescheduller.Models.Schedule.Year;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Models.Subjects.Subject;
import csc.colleguescheduller.Views.Entry_Views.Schemas.Schema_Interface;

public class Schema_Controller extends Controller {

    Schema_Interface UI;

    public Schema_Controller(Schema_Interface UI) {
        this.UI = UI;
    }

    public void get_schemas() {
        firestore.collection("Schemas").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Schema> schemas = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            schemas.add(new Schema(documentSnapshot));
                        }
                        Globals.session.schemas = schemas;
                        UI.on_schemas_loaded(schemas);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.getMessage());
            }
        });
    }

    public void remove_schema(Schema schema) {
        firestore.collection("Schemas").document(schema.getSchemaID()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UI.on_schema_removed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.getMessage());
            }
        });
    }

    public void persist_schema(Schema schema) {
        firestore.collection("Schemas").document(schema.getSchemaID()).set(schema.to_hashmap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UI.on_save_success();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.getMessage());
            }
        });
    }

    private Boolean check_id(String id){
        for(Schema schema : Globals.session.schemas){
            if(schema.getSchemaID().equals(id)){
                return false;
            }
        }
        return true;
    }

    public void add_schema(Schema schema) {
        if(check_id(schema.getSchemaID())){
            persist_schema(schema);
        }
    }
}
