package csc.colleguescheduller.Controllers.StaffMembers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.StaffMembers.HiringDegree;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.PartTimeMember;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Views.Entry_Views.StaffMemebers.StaffMembers_Interface;

public class Staffmemebers_Controller extends Controller {

    private StaffMembers_Interface UI;
    private CollectionReference ref = firestore.collection("Staff Members");
    private StaffMember member_before;

    public Staffmemebers_Controller(StaffMembers_Interface UI) {
        super();
        this.UI = UI;
    }

    public Staffmemebers_Controller(StaffMembers_Interface UI, StaffMember member_before) {
        super();
        this.UI = UI;
        this.member_before = member_before;
    }

    // Maged Seif El Nasr
    public void get_staffmembers(final HiringType hiringType, final HiringDegree hiringDegree) {
        final ArrayList<StaffMember> staffMembers = new ArrayList<>();

        ref.whereEqualTo("Hiring Type", hiringType.toString())
                .whereEqualTo("Hiring Degree", hiringDegree.toString())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        StaffMember member;
                        if (document.get("Hiring Type").equals("Full_Time")) {
                            member = new StaffMember(document);
                        } else {
                            member = new PartTimeMember(document);
                        }
                        staffMembers.add(member);
                    }
                    Globals.session.staffMembers.put(hiringType.toString() + "," + hiringDegree.toString(),staffMembers);
                    UI.on_members_loaded(staffMembers);

                } else UI.on_database_error(task.getException().toString());
            }
        });

    }

    // Ahmed Mokhtar Hassanin
    public void add_staffMember(final StaffMember staffMember) {
        if (check_id(staffMember.getUID())) {
            create_authentication(staffMember);
        } else {
            UI.on_authentication_error("Failed To Create Authentication");
        }
    }
    // Maged Seif El Nasr
    public void remove_staffmember(final StaffMember member) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(member.getUID(), member.getAccessCode())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        delete_authentication(member);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_authentication_error("Failed To Remove Authentication :- \n" + e.getMessage());
            }
        });
    }

    // Ahmed Mokhtar Hassanin
    public void persist_staffMember(StaffMember staffMember) {
        if (!staffMember.getAccessCode().equals(member_before.getAccessCode())) {
            update_authentication(staffMember);
        } else {
            persist_staffMember_(staffMember, false);
        }

    }

    // Ahmed Mokhtar Hassanin
    private Boolean check_id(String id) {
        for(int i = 0;i< Globals.session.staffMembers.size();i++){
            ArrayList<StaffMember> staffMembers = Globals.session.staffMembers.get((String)Globals.session.staffMembers.keySet().toArray()[i]);
            for(StaffMember staffMember : staffMembers){
                if(staffMember.getUID().equals(id)){
                    return false;
                }
            }
        }
        return true;
    }
    // Ahmed Mokhtar Hassanin
    private void create_authentication(final StaffMember staffMember) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(staffMember.getUID(), staffMember.getAccessCode())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        persist_staffMember_(staffMember, true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_authentication_error("Failed To Create Authentication :-\n" + e.getMessage());
            }
        });
    }

    // Maged Seif El Nasr
    private void persist_staffMember_(final StaffMember staffMember, final Boolean resign) {
        ref.document(staffMember.getUID())
                .set(staffMember.to_HashMap(), SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (resign) {
                            if (staffMember.getUID().equals(Globals.session.user.getUID())) {
                                Globals.session.user = staffMember;
                                Globals.session.userAuth = FirebaseAuth.getInstance().getCurrentUser();
                                UI.on_save_success();
                            } else {
                                re_signin();
                            }
                        } else {
                            UI.on_save_success();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.toString());
            }
        });
    }
    // Ahmed Mokhtar Hassanin
    private void re_signin() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(Globals.session.user.getUID(), Globals.session.user.getAccessCode())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Globals.session.userAuth = FirebaseAuth.getInstance().getCurrentUser();
                        UI.on_save_success();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_authentication_error("Failed To Re Sign In :-\n" + e.getMessage());
            }
        });
    }
    // Ahmed Mokhtar Hassanin
    private void sign_for_update( final Runnable onSuccess) {
        if (member_before.getUID().equals(Globals.session.user.getUID())) {
            onSuccess.run();
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(member_before.getUID(), member_before.getAccessCode())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            onSuccess.run();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    UI.on_authentication_error("Failed To Update Authentication :- \n" + e.getMessage());
                }
            });
        }
    }
    // Ahmed Mokhtar Hassanin
    private void update_authentication(final StaffMember staffMember) {

        sign_for_update(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(staffMember.getAccessCode())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                persist_staffMember_(staffMember, true);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        UI.on_authentication_error("Failed To Update Authentication :- \n" + e.getMessage());
                    }
                });
            }
        });

    }

    // Ahmed Mokhtar Hassanin
    private void delete_authentication(final StaffMember member) {
        FirebaseAuth.getInstance().getCurrentUser().delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        remove_staffmember_(member);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_authentication_error("Failed To Remove Authentication :- \n" + e.getMessage());
            }
        });
    }
    // Maged Seif El Nasr
    private void remove_staffmember_(StaffMember member) {
        ref.document(member.getUID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UI.on_member_removed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UI.on_database_error(e.toString());
            }
        });
    }

}
