package csc.colleguescheduller.Models.Schedule.SchemaSchedule;

import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Schedule.ScheduleCell;
import csc.colleguescheduller.Models.Schedule.Section;
import csc.colleguescheduller.Models.StaffMembers.MemberSubjectType;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Models.Subjects.Subject;


public class SchemaCell extends ScheduleCell {

    private StaffMember member;
    private Room room;

    public SchemaCell(
        int start,
        int period,
        Section section,
        Subject course,
        MemberSubjectType type,
        StaffMember member,
        Room room
        ) {
            super(start, period, section, course, type);
            this.member = member;
            this.room = room;
        }

    public void setStaffMember(StaffMember member) {
        this.member = member;
    }

    public StaffMember getStaffMember() {
        return this.member;        
    }
     
    public void setRoom(Room room) {
        this.room = room;
    }
    public Room getRoom() {
        return this.room;        
    }
    
}
