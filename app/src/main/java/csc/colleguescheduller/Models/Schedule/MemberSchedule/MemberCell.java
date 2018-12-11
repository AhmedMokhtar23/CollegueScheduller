package csc.colleguescheduller.Models.Schedule.MemberSchedule;


import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Schedule.ScheduleCell;
import csc.colleguescheduller.Models.Schedule.Section;
import csc.colleguescheduller.Models.Schedule.Specialization;
import csc.colleguescheduller.Models.Schedule.Year;
import csc.colleguescheduller.Models.StaffMembers.MemberSubjectType;
import csc.colleguescheduller.Models.Subjects.Subject;


public class MemberCell extends ScheduleCell {

    private Year year;
    private Specialization group;
    private Room room;

    public MemberCell(
        int start,
        int period,
        Year year,
        Specialization group,
        Section section,
        Subject course,
        MemberSubjectType type,
        Room room
        ) {
            super(start, period, section, course, type);
            this.year = year;
            this.group = group;
            this.room = room;
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
     
    public void setRoom(Room room) {
        this.room = room;
    }
    public Room getRoom() {
        return this.room;        
    }
    
}

	
	