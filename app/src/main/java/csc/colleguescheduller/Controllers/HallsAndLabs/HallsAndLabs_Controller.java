package csc.colleguescheduller.Controllers.HallsAndLabs;

/*
Maged Seif El Nasr 90%
Ahmed Mokhtar hassanin 10%
 */

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Views.Entry_Views.HallsAndLabs.HallsAndLabs_Interface;

public class HallsAndLabs_Controller extends Controller {

    private HallsAndLabs_Interface UI;
    private CollectionReference roomsCollectionRef = firestore.collection("Rooms");

    public HallsAndLabs_Controller(HallsAndLabs_Interface UI) {
        this.UI = UI;
    }

    public void get_Rooms() {
        
        roomsCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Room> rooms = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Room newRoom = new Room(document);
                        rooms.add(newRoom);
                    }
                    Globals.session.rooms = rooms;
                    UI.on_rooms_loaded(rooms);
                } else UI.on_database_error(task.getException().toString());
            }
        });

    }

    public void add_room(final Room room){
        firestore.collection("Rooms").document(room.getRoomid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(!documentSnapshot.exists()){
                    persist_room(room);
                }else{
                    UI.on_database_error("There is A Room With The Same ID Already Exist");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.getMessage());
            }
        });
    }

    public void persist_room(final Room room){
        roomsCollectionRef.document(room.getRoomid())
                .set(room.to_HashMap(), SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UI.on_save_success();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.toString());
            }
        });
    }

    public void remove(Room room){
        roomsCollectionRef.document(room.getRoomid())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UI.on_room_removed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.toString());
            }
        });
    }

}
