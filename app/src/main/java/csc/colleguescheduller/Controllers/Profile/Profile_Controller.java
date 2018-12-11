package csc.colleguescheduller.Controllers.Profile;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Views.Entry_Views.Profile.Profile_Interface;

public class Profile_Controller extends Controller {

    Profile_Interface UI;

    public Profile_Controller(Profile_Interface UI) {
        super();
        this.UI = UI;
    }

    public void send_updates(StaffMember user) {
        firestore.collection("Staff Members").document(user.getUID()).set(user.to_HashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UI.on_success();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.getMessage());
            }
        });
    }

}
