package csc.colleguescheduller.Models.Globals;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.HashMap;

public class WaitingTime {
    private Date start_date;
    private Date end_date;

    public WaitingTime(Date start_date, Date end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public WaitingTime(HashMap<String, Object> document) {
        this(
                (Date) document.get("Start Date"),
                (Date) document.get("End Date")
        );
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}