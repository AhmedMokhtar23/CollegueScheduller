package csc.colleguescheduller.Controllers;

/*
Ahmed Mokhtar Hassanin
 */

import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Controller{

    protected FirebaseFirestore firestore;

    public Controller() {
        if(Globals.session != null){
            this.firestore = Globals.session.firestore;
        }
    }

    public void Logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public void Abort_Thread(){

    }

    public StaffMember get_user() {
        return Globals.session.user.copy();

    }

}
