package csc.colleguescheduller.Models.Globals;

/*
Ahmed Mokhtar Hassanin %
Maged Seif El Nasr %
 */

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Schedule.Schema.Schema;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Models.Subjects.Subject;

public class Session {


    public FirebaseFirestore firestore;
    private FirebaseAuth auth;
    public FirebaseUser userAuth;

    public StaffMember user = null;
    static private int id;
    public Boolean Exiting;
    public Boolean LogingOut;
    public ArrayList<Subject> subjects = new ArrayList<>();
    public HashMap<String,ArrayList<StaffMember>> staffMembers;
    public ArrayList<Room> rooms;
    public ArrayList<Schema> schemas;
    public SystemSettings systemSettings;

    public Session(){
        this.firestore = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
        userAuth = null;
        id = 0;
        Exiting = false;
        LogingOut = false;
    }

}
