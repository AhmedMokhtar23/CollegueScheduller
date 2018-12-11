package csc.colleguescheduller.Controllers.SignIn;

/*
Maged Seif El Nasr %
Ahmed Mokhtar Hassanin %
 */


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.PartTimeMember;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Views.SignIn.SignIn_Interface;


public class SignIn_Controller extends Controller {

    private SignIn_Interface UI;

    public SignIn_Controller(SignIn_Interface UI) {
        super();
        this.UI = UI;
    }


    public void SignIn(String username, String password) {

        // check if the user or password are empty
        if(username.equals("")){
            UI.on_authentication_error("UserName Can't be Empty");
        }else if(password.equals("")){
            UI.on_authentication_error("Password Can't be Empty");
        }else{

            FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Globals.session.userAuth = FirebaseAuth.getInstance().getCurrentUser();
                            getUserData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
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
    }

    //Maged Seif El Nasr
    private void getUserData(){
        firestore.collection("Staff Members")
                .whereEqualTo("UID", Globals.session.userAuth.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    StaffMember member = null;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (HiringType.valueOf(document.getString("Hiring Type")) == HiringType.Full_Time) {
                            member = new StaffMember(document);
                        } else {
                            member = new PartTimeMember(document);
                        }
                    }
                    if (member != null){
                        Globals.session.user = member;
                        UI.on_success();
                    }
                    else{
                        UI.on_authentication_error("There is no user data for current user");
                    }
                } else UI.on_authentication_error(task.getException().getMessage());
            }

        });
    }

    //Maged Seif El Nasr
    public void check_user(){
        Globals.session.userAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (Globals.session.userAuth != null){
            getUserData();
        }
        else {
            UI.on_signin();
        }
    }
}
