package csc.colleguescheduller.Controllers.AdminPanel;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Views.AdminPanel.AdminPanel_Interface;

public class AdminPanel_Controller extends Controller {
    AdminPanel_Interface UI;

    public AdminPanel_Controller(AdminPanel_Interface UI) {
        super();
        this.UI = UI;
    }


    // Amr Samir Afifi
    public void Toggle_System_Enability() {
        firestore.collection("System").document("Settings").update("Is Ready",!Globals.session.systemSettings.getIs_Ready())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Globals.session.systemSettings.setIs_Ready(!Globals.session.systemSettings.getIs_Ready());
                        UI.on_system_toggled();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if(e.getMessage().equals("A network error (such as timeout, interrupted connection or unreachable host) has occurred.")){
                    UI.on_connection_error();
                }else{
                    UI.on_database_error(e.getMessage());
                }
            }
        });
    }

    public void Reset_System(String password){
        if(password.equals(Globals.session.user.getAccessCode())){

        }else{
            UI.on_authentication_error("Wrong Password");
        }
    }
}
