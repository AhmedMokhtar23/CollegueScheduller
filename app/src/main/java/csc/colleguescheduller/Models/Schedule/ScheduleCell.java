package csc.colleguescheduller.Models.Schedule;

import csc.colleguescheduller.Models.StaffMembers.MemberSubjectType;
import csc.colleguescheduller.Models.Subjects.Subject;


public class ScheduleCell {

    private int start, period;
    private Section section;
    private Subject course;
    private MemberSubjectType type;

    public ScheduleCell(
        int start,
        int period,
        Section section,
        Subject course,
        MemberSubjectType type
        ) {
            this.start = start;
            this.period = period;
            this.section = section;
            this.course = course;
            this.type = type;
        }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Subject getCourse() {
        return course;
    }

    public void setCourse(Subject course) {
        this.course = course;
    }

    public MemberSubjectType getType() {
        return type;
    }

    public void setType(MemberSubjectType type) {
        this.type = type;
    }
}

	
	