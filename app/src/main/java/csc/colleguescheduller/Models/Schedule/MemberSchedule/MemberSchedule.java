package csc.colleguescheduller.Models.Schedule.MemberSchedule;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;

import csc.colleguescheduller.Models.Schedule.Schedule;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;

public class MemberSchedule extends Schedule {

    private StaffMember member;
    private HashMap<DayOfWeek, ArrayList<MemberCell>> rows;

    public MemberSchedule() {
        super();
        this.rows = new HashMap<>();
    }
    
    public MemberSchedule(int workDays, DayOfWeek startDay) {
        super(workDays, startDay);
        this.rows = new HashMap<>();
    }
           
    public MemberSchedule(HashMap<String, Object> data, StaffMember member) {
        super(5, DayOfWeek.SUNDAY);
        this.member = member;
        this.rows = (HashMap<DayOfWeek, ArrayList<MemberCell>>) data.get("Rows");
    }
    
    public void setMember(StaffMember member) {
        this.member = member;
    }

    public StaffMember getMember() {
        return this.member;
    }

	public HashMap<DayOfWeek, ArrayList<MemberCell>> getRows() {
		return this.rows;
	}

	public void setRows(HashMap<DayOfWeek, ArrayList<MemberCell>> rows) {
		this.rows = rows;
	}
 
    public HashMap<String ,Object> to_HashMap(){
        HashMap<String, Object> result = new HashMap<String, Object>();
        
        result.put("Work Days", this.getWorkDays());
        result.put("Start Day", this.getStartDay());
        result.put("Staff Member", this.member.getUID());
        result.put("Rows", this.rows);

        return result;
    }

}
