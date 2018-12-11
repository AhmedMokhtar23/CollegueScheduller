package csc.colleguescheduller.Controllers.StaffMemebersSettings;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldPath;

import java.util.Date;
import java.util.HashMap;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.SciDegree;
import csc.colleguescheduller.Models.StaffMembers.SciDepartment;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Views.StaffMemebrsSettings.StaffMemebersSettings_Interface;

public class StaffMemebrsSettings_Controller extends Controller {

    StaffMemebersSettings_Interface UI;

    public StaffMemebrsSettings_Controller(StaffMemebersSettings_Interface UI) {
        super();
        this.UI = UI;
    }

    public void Save(HiringType category, SciDegree scidegree,Date startdate,Date enddate){
        firestore.collection("System").document("Settings").update(
                FieldPath.of("Waiting Time",category.toString(),scidegree.toString(),"Start Date"),startdate,
                FieldPath.of("Waiting Time",category.toString(),scidegree.toString(),"End Date"),enddate)
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
}
