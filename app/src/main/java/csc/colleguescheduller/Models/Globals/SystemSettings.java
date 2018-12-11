package csc.colleguescheduller.Models.Globals;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;

import java.util.Date;
import java.util.HashMap;

import csc.colleguescheduller.Models.StaffMembers.HiringDegree;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.SciDegree;

public class SystemSettings {
    private Boolean Is_Ready;
    private HashMap<String, WaitingTime> waiting_time;

    public SystemSettings(DocumentSnapshot document) {
        this(document.getBoolean("Is Ready"), new HashMap<String, WaitingTime>());
        for (HiringType hiringType : HiringType.values()) {
            for (SciDegree sciDegree : SciDegree.values()) {
                FieldPath path = FieldPath.of("Waiting Time",hiringType.toString(),sciDegree.toString());
                waiting_time.put(hiringType.toString() + "," + sciDegree.toString(),new WaitingTime((HashMap<String, Object>) document.get(path)));
            }
        }
    }

    public SystemSettings(Boolean is_Ready, HashMap<String, WaitingTime> waiting_time) {
        Is_Ready = is_Ready;
        this.waiting_time = waiting_time;
    }

    public Boolean getIs_Ready() {
        return Is_Ready;
    }

    public void setIs_Ready(Boolean is_Ready) {
        Is_Ready = is_Ready;
    }

    public HashMap<String, WaitingTime> getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(HashMap<String, WaitingTime> waiting_time) {
        this.waiting_time = waiting_time;
    }
}
