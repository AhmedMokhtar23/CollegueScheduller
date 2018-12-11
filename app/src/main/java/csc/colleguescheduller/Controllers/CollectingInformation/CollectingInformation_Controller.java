package csc.colleguescheduller.Controllers.CollectingInformation;


/*
Majed Seif El Nasr 70%
Ahmed Mokhtar Hassanin 30%
 */

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Globals.SystemSettings;
import csc.colleguescheduller.Models.Globals.WaitingTime;
import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Schedule.Schema.Schema;
import csc.colleguescheduller.Models.StaffMembers.HiringDegree;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.PartTimeMember;
import csc.colleguescheduller.Models.StaffMembers.Permission;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Models.Subjects.Subject;
import csc.colleguescheduller.Views.CollectingInformation.CollectingInfomation_interface;

public class CollectingInformation_Controller extends Controller {

    CollectingInfomation_interface UI;

    private DocumentReference Ref = firestore.collection("System").document("Settings");

    public CollectingInformation_Controller(CollectingInfomation_interface UI) {
        super();
        this.UI = UI;
    }

    public void checkCollectingInformation() {
        checkUserPermission();
    }

    //Majed Seif El Nasr
    public void checkUserPermission() {
        Permission permission = Globals.session.user.getPermission();
        if (permission == Permission.Admin){
            get_SystemSettings();
        } else {
            checkSystem();
        }
    }

    //Majed Seif El Nasr
    public void checkSystem() {
        Ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Boolean ready = (Boolean) documentSnapshot.get("Is Ready");
                if (ready){
                    checkTime(documentSnapshot);
                } else {
                    UI.not_Ready();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.getMessage());
            }
        });
    }

    //Majed Seif El Nasr
    public void checkTime(DocumentSnapshot document) {
        final StaffMember user = Globals.session.user;
        WaitingTime time =  Globals.session.systemSettings.getWaiting_time().get(user.getHiringType().toString() + "," + user.getScientificDegree().toString());
        Calendar start_date = Calendar.getInstance();
        start_date.setTimeInMillis(time.getStart_date().getTime());
        Calendar end_date = Calendar.getInstance();
        end_date.setTimeInMillis(time.getEnd_date().getTime());
        Calendar calendarCurrent = Calendar.getInstance();

        if(calendarCurrent.before(start_date)){
            Calendar remainig_time = Calendar.getInstance();
            remainig_time.setTimeInMillis(start_date.getTime().getTime() - calendarCurrent.getTime().getTime());
            UI.time_Waiting(remainig_time);
        }else if(calendarCurrent.after(end_date)){
            UI.on_time_expired();
        }else{
            get_SystemSettings();
        }
    }

    private void get_SystemSettings(){
        Ref.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Globals.session.systemSettings = new SystemSettings(task.getResult());
                            get_subjects();
                        } else UI.on_database_error(task.getException().toString());
                    }
                });
    }

    //Ahmed Mokhtar Hassanin
    private void set_user_subjects(){

        firestore.collection("Staff Members").document(Globals.session.user.getUID()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Globals.session.user.setTeachingSubjects_((ArrayList<HashMap<String,Object>>)documentSnapshot.get("Teaching Subjects"));
                        get_Rooms();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.getMessage());
            }
        });
    }

    //Ahmed Mokhtar Hassanin
    private void get_subjects(){
        firestore.collection("Subjects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Globals.session.subjects = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Subject newSubject = new Subject(document);
                        Globals.session.subjects.add(newSubject);
                    }
                    set_user_subjects();
                } else UI.on_database_error(task.getException().toString());
            }
        });

    }

    private void get_Rooms() {

        firestore.collection("Rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Room> rooms = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Room newRoom = new Room(document);
                        rooms.add(newRoom);
                    }
                    Globals.session.rooms = rooms;
                    get_staffmembers();
                } else UI.on_database_error(task.getException().toString());
            }
        });

    }

    public void get_staffmembers() {
        final ArrayList<StaffMember> staffMembers = new ArrayList<>();

        firestore.collection("Staff Members").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        StaffMember member;
                        if (document.get("Hiring Type").equals("Full_Time")) {
                            member = new StaffMember(document);
                        } else {
                            member = new PartTimeMember(document);
                        }
                        staffMembers.add(member);
                    }
                    Globals.session.staffMembers = new HashMap<>();
                    for(HiringType hiringType : HiringType.values()){
                        for(HiringDegree hiringDegree : HiringDegree.values()){
                            Globals.session.staffMembers.put(hiringType.toString() + "," + hiringDegree.toString(),new ArrayList<StaffMember>());
                        }
                    }
                    HashMap<String ,ArrayList<StaffMember>> tmp = Globals.session.staffMembers;
                    for(StaffMember member : staffMembers){
                        String key = member.getHiringType().toString() + "," + member.getHiringDegree().toString();
                        Globals.session.staffMembers.get(key).add(member);
                    }
                    get_schemas();

                } else UI.on_database_error(task.getException().toString());
            }
        });

    }

    public void get_schemas() {
        firestore.collection("Schemas").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Globals.session.schemas = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Globals.session.schemas.add(new Schema(documentSnapshot));
                        }
                        UI.ready();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.getMessage());
            }
        });
    }
}
