package csc.colleguescheduller.Controllers.Subject;

    /*
    Bahaa Oyoon Ahmed %
    Maged Seif El Nasr %
    Ahmed Mokhtar Hassanin %
     */

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Subjects.Subject;
import csc.colleguescheduller.Views.Entry_Views.Subjects.Subjects_Interface;

public class Subject_Controller extends Controller {

    private Subjects_Interface UI;
    private CollectionReference ref = firestore.collection("Subjects");

    public Subject_Controller(Subjects_Interface UI) {
        this.UI = UI;
    }

    public void get_subjects(){
	    final ArrayList<Subject> subjects = new ArrayList<>(); 
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Subject newSubject = new Subject(document);
                        subjects.add(newSubject);
                    }
                    Globals.session.subjects = subjects;
                    UI.on_subjects_loaded(subjects);

                } else UI.on_database_error(task.getException().toString());
            }
        });

    }

    public void add_subject(final Subject subject){
        ref.document(subject.getSubjectId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(!documentSnapshot.exists()){
                            persist(subject);
                        }else{
                            UI.on_database_error("There is A Subject With The Same ID Already Exist");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.getMessage());
            }
        });
    }

    public void persist(Subject subject){

        ref.document(subject.getSubjectId())
                .set(subject.to_HashMap(), SetOptions.merge())
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

    public void remove_subject(Subject subject){
        ref.document(subject.getSubjectId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UI.on_subject_removed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.toString());
            }
        });

    }

}
