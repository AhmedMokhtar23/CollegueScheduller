package csc.colleguescheduller.Models.Schedule.Schema;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Schedule.Semester;
import csc.colleguescheduller.Models.Schedule.Specialization;
import csc.colleguescheduller.Models.Schedule.Year;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Models.Subjects.Subject;

public class Schema{

    private Year year = Year.values()[0];
    private Specialization specialization = Specialization.values()[0];
    private Semester semester = Semester.values()[0];
    private ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<StaffMember> teachingMembers = new ArrayList<>();
    private int numberOfStudents = 0;
    private int numberOfSections = 0;
    private Room hall = new Room();

    public Schema(){

    }

    public Schema(DocumentSnapshot document){
        setYear(Year.valueOf(document.getString("Year")));
        setSpecialization(Specialization.valueOf(document.getString("Specialization")));
        setSemester(Semester.valueOf(document.getString("Semester")));
        setTeachingMembers_((ArrayList<String>) document.get("Teaching Members"));
        setSubjects_((ArrayList<String>) document.get("Subjects"));
        setNumberOfStudents(document.getDouble("Number Of Students").intValue());
        setNumberOfSections(document.getDouble("Number Of Sections").intValue());
        setHall(document.getString("Hall"));
    }

    public Schema(
        Year year,
        Specialization specialization,
        Semester semester,
        ArrayList<Subject> subjects,
        ArrayList<StaffMember> teachingMembers,
        int numberOfStudents,
        int numberOfSections,
        Room hall
        ) {
            this.year = year;
            this.specialization = specialization;
            this.semester = semester;
            this.subjects = subjects;
            this.teachingMembers = teachingMembers;
            this.numberOfStudents = numberOfStudents;
            this.numberOfSections = numberOfSections;
            this.hall = hall;
    }

    public String getSchemaID() {
        return (Integer.toString(year.ordinal()) + Integer.toString(specialization.ordinal()) +
                Integer.toString(semester.ordinal()));
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
    public void setSubjects_(ArrayList<String> subjects) {
        this.subjects = new ArrayList<>();
        for(String id : subjects){
            for(Subject subject : Globals.session.subjects){
                if(subject.getSubjectId().equals(id)){
                    this.subjects.add(subject);
                }
            }
        }
    }


    public ArrayList<StaffMember> getTeachingMembers() {
        return teachingMembers;
    }

    public void setTeachingMembers(ArrayList<StaffMember> teachingMembers) {
        this.teachingMembers = teachingMembers;
    }
    public void setTeachingMembers_(ArrayList<String> teachingMembers) {
        this.teachingMembers = new ArrayList<>();
        for(String id : teachingMembers){
            for(int i = 0;i< Globals.session.staffMembers.size();i++){
                ArrayList<StaffMember> staffMembers = Globals.session.staffMembers.get((String)Globals.session.staffMembers.keySet().toArray()[i]);
                for(StaffMember staffMember : staffMembers){
                    if(staffMember.getUID().equals(id)){
                        this.teachingMembers.add(staffMember);
                    }
                }
            }

        }
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getNumberOfSections() {
        return numberOfSections;
    }

    public void setNumberOfSections(int numberOfSections) {
        this.numberOfSections = numberOfSections;
    }

    public Room getHall() {
        return hall;
    }

    public void setHall(Room hall) {
        this.hall = hall;
    }
    public void setHall(String hall) {
        for(Room room : Globals.session.rooms){
            if(room.getRoomid().equals(hall)){
                this.hall = room;
            }
        }
    }

    public Schema copy(){
        return new Schema(year,specialization,semester,(ArrayList<Subject>) subjects.clone(),(ArrayList<StaffMember>) teachingMembers.clone(),numberOfStudents,numberOfSections,hall);
    }

    @Override
    public int hashCode() {
        final int prime = 24;
        int result = 3;
        int ascii = 0;

        for (char ch: getSchemaID().toCharArray())
            ascii += (int) ch;

        return prime * result + ascii;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Schema other = (Schema) obj;
        if (!getSchemaID().equals(other.getSchemaID()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return getYear().toString() + " - " + getSpecialization().toString();
    }

    public HashMap<String,Object> to_hashmap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("Year",year.toString());
        result.put("Specialization",specialization.toString());
        result.put("Semester",semester.toString());
        result.put("Number Of Students",numberOfStudents);
        result.put("Number Of Sections",numberOfSections);
        result.put("Teaching Members",get_members_ids());
        result.put("Subjects",get_subjects_ids());

        return result;
    }

    private ArrayList<String> get_members_ids(){
        ArrayList<String> result = new ArrayList<>();
        for(StaffMember member : teachingMembers){
            result.add(member.getUID());
        }
        return result;
    }

    private ArrayList<String> get_subjects_ids(){
        ArrayList<String> result = new ArrayList<>();
        for(Subject subject : subjects){
            result.add(subject.getSubjectId());
        }
        return result;
    }

}
		
		