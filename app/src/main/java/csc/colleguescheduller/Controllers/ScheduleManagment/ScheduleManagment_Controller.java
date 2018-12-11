package csc.colleguescheduller.Controllers.ScheduleManagment;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.WriteBatch;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Schedule.MemberSchedule.MemberSchedule;
import csc.colleguescheduller.Models.Schedule.RoomSchedule.RoomSchedule;
import csc.colleguescheduller.Models.Schedule.ScheduleGenerator;
import csc.colleguescheduller.Models.Schedule.Schema.Schema;
import csc.colleguescheduller.Models.Schedule.SchemaSchedule.SchemaSchedule;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Views.ScheduleManagment.ScheduleManagment_Interface;

public class ScheduleManagment_Controller extends Controller {

    private CollectionReference ref = firestore.collection("Schedules");

    ScheduleManagment_Interface UI;

    public ScheduleManagment_Controller(ScheduleManagment_Interface UI) {
        super();
        this.UI = UI;
    }


    public void generate_schedule(String password){

        String accessCode = Globals.session.user.getAccessCode();

        if (accessCode.equals(password)){
        
            int workDays = 5;
            DayOfWeek startDay = DayOfWeek.SUNDAY;
            ArrayList<Schema> schemas = Globals.session.schemas;
            HashMap<Room,Room> rooms = new HashMap<>();

            for(Room room : Globals.session.rooms){
                rooms.put(room,room);
            }
    
            ScheduleGenerator generate = new ScheduleGenerator();
            generate.generate(workDays, startDay, schemas, rooms);
    
            HashMap<Schema, SchemaSchedule> schemaSchedules = generate.getSchemaSchedules();
            HashMap<StaffMember, MemberSchedule> memberSchedules = generate.getMemberSchedules();
            HashMap<Room, RoomSchedule> roomSchedules = generate.getRoomSchedules();
    
            HashMap<String, Object> newSchemaSchedules = new HashMap<>();
            HashMap<String, Object> newMemberSchedules = new HashMap<>();
            HashMap<String, Object> newRoomSchedules = new HashMap<>();
    
            for (Map.Entry<Schema, SchemaSchedule> entry : schemaSchedules.entrySet())
                newSchemaSchedules.put(entry.getKey().getSchemaID(), entry.getValue().to_HashMap());
            
            for (Map.Entry<StaffMember, MemberSchedule> entry : memberSchedules.entrySet())
                newMemberSchedules.put(entry.getKey().getUID(), entry.getValue().to_HashMap());
            
            for (Map.Entry<Room, RoomSchedule> entry : roomSchedules.entrySet())
                newRoomSchedules.put(entry.getKey().getRoomid(), entry.getValue().to_HashMap());
            
    
            WriteBatch batch = firestore.batch();
    
            DocumentReference memberSchedulesRef = ref.document("Members Schedules");
            batch.set(memberSchedulesRef, newMemberSchedules);
    
            DocumentReference schemaSchedulesRef = ref.document("Schemas Schedules");
            batch.set(schemaSchedulesRef, newSchemaSchedules);
    
            DocumentReference roomSchedulesRef = ref.document("Rooms Schedules");
            batch.set(roomSchedulesRef, newRoomSchedules);
    
            // Commit the batch
            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        UI.on_generate_success();
                    } else {
                        UI.on_generate_failed(task.getException().toString());
                    }
                }
            });

        } else {
            UI.on_authentication_error("Wrong Password");
        }
    }
}
