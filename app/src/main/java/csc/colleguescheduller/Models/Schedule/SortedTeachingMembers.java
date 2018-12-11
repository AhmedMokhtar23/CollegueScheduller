package csc.colleguescheduller.Models.Schedule;

import java.util.ArrayList;

import csc.colleguescheduller.Models.StaffMembers.StaffMember;

public class SortedTeachingMembers {

    private ArrayList<StaffMember> lecturers;
    private ArrayList<StaffMember> assistants;

    public SortedTeachingMembers() {
        lecturers = new ArrayList<StaffMember>();
        assistants = new ArrayList<StaffMember>();
    }

    public SortedTeachingMembers(
        ArrayList<StaffMember> lecturers,
        ArrayList<StaffMember> assistants
    ) {
        this.lecturers = lecturers;
        this.assistants = assistants;
    }

    public ArrayList<StaffMember> getLecturers() {
        return lecturers;
    }

    public void setLecturers(ArrayList<StaffMember> lecturers) {
        this.lecturers = lecturers;
    }

    public ArrayList<StaffMember> getAssistants() {
        return assistants;
    }

    public void setAssistants(ArrayList<StaffMember> assistants) {
        this.assistants = assistants;
    }

}