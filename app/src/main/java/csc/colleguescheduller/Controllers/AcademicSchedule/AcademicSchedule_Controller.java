package csc.colleguescheduller.Controllers.AcademicSchedule;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Schedule.MemberSchedule.MemberSchedule;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Views.AchademicSchedule.AcandemicSchedule_Interface;

public class AcademicSchedule_Controller extends Controller {

    private CollectionReference ref = firestore.collection("Schedules");
    private MemberSchedule memberSchedule;

    AcandemicSchedule_Interface UI;

    public AcademicSchedule_Controller(AcandemicSchedule_Interface UI) {
        this.UI = UI;
    }

    public void get_schedule(){

        // where is interface to call
        // Interface UI

        final StaffMember member = Globals.session.user;

        ref.document("Members Schedules").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        HashMap<String, Object> data = (HashMap<String, Object>) document.get(member.getUID());
                        if(data != null){
                            memberSchedule = new MemberSchedule(data, member);
                            UI.on_schedule_loaded(memberSchedule);
                        }else{
                            UI.on_database_error("No generated Schedules To View");
                        }

                    } else {
                        UI.on_database_error("No generated Schedules To View");
                    }
                } else {
                    UI.on_database_error(task.getException().toString());
                }
            }
        });

    }
}
