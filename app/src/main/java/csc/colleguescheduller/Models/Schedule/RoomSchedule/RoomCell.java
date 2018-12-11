package csc.colleguescheduller.Models.Schedule.RoomSchedule;

import csc.colleguescheduller.Models.Schedule.ScheduleCell;
import csc.colleguescheduller.Models.Schedule.Section;
import csc.colleguescheduller.Models.Schedule.Specialization;
import csc.colleguescheduller.Models.Schedule.Year;
import csc.colleguescheduller.Models.StaffMembers.MemberSubjectType;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Models.Subjects.Subject;


public class RoomCell extends ScheduleCell {

    private Year year;
    private Specialization group;
    private StaffMember member;

    public RoomCell(
        int start,
        int period,
        Year year,
        Specialization group,
        Section section,
        Subject course,
        MemberSubjectType type,
        StaffMember member
        ) {
            super(start, period, section, course, type);
            this.year = year;
            this.group = group;
            this.member = member;
        }
        
    public void setYear(Year year) {
        this.year = year;
    }

    public Year getYear() {
        return this.year;        
    }
    
    public void setGroup(Specialization group) {
        this.group = group;
    }

    public Specialization getGroup() {
        return this.group;        
    }
         
    public void setStaffMember(StaffMember member) {
        this.member = member;
    }
    public StaffMember getStaffMember() {
        return this.member;        
    }
    
}

	
	