package csc.colleguescheduller.Controllers.ChangePassword;

// Amr Samir Afifi

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Views.ChangePassword.Change_Password_Interface;

public class Change_Password_Controller extends Controller {
    Change_Password_Interface UI;

    public Change_Password_Controller(Change_Password_Interface UI) {
        this.UI = UI;
    }

    public void changePassword(String old_password, final String new_password) {
        Globals.session.userAuth.updatePassword(new_password)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pass_change_database(new_password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(e.getMessage().equals("A network error (such as timeout, interrupted connection or unreachable host) has occurred.")){
                            UI.on_connection_error();
                        }else{
                            UI.on_authentication_error(e.getMessage());
                        }

                    }
                });
    }

    private void pass_change_database(final String new_password) {
        firestore.collection("Staff Members").document(Globals.session.user.getUID()).update("Access Code",new_password)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UI.onSuccess();
                        Globals.session.user.setAccessCode(new_password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
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
}
