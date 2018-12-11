package csc.colleguescheduller.Models.StaffMembers;

/*
Mahmoud Maher
 */

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class StaffMember {

    private String name = "";
    private SciDegree scientificDegree = SciDegree.values()[0];
    private SciDepartment scientificDepartment = SciDepartment.values()[0];
    private int workHours = 0;
    private ArrayList<org.threeten.bp.DayOfWeek> workdays = new ArrayList<>();
    private Date hiringDate = new Date();
    private HiringType hiringType = HiringType.values()[0];
    private HiringDegree hiringDegree = HiringDegree.values()[0];
    private Date birthDate = new Date();
    private ArrayList<MemberSubject> teachingSubjects = new ArrayList<>();
    private Permission permission = Permission.values()[0];
    private String uID = "";
    private String accessCode = "";

    // dummy variables

    private int max_subjects = 4;

    public StaffMember(){}



    public StaffMember(QueryDocumentSnapshot document){
        this(
                document.getString("Name"),
                SciDegree.valueOf(document.getString("Scientific Degree")),
                SciDepartment.valueOf(document.getString("Scientific Department")),
                document.getDouble("Work Hours").intValue(),
                new ArrayList<DayOfWeek>(),
                document.getDate("Hiring Date"),
                HiringType.valueOf(document.getString("Hiring Type")),
                HiringDegree.valueOf(document.getString("Hiring Degree")),
                document.getDate("Birthdate"),
                new ArrayList<MemberSubject>(),
                Permission.valueOf(document.getString("Permission")),
                document.getString("UID"),
                document.getString("Access Code")
        );
        setWorkdays(create_workdays(document));
        setTeachingSubjects_((ArrayList<HashMap<String,Object>>) document.get("Teaching Subjects"));
    }

    public StaffMember(
                String name, 
                SciDegree scientificDegree, 
                SciDepartment scientificDepartment, 
                int workHours, 
                ArrayList<DayOfWeek> workdays, 
                Date hiringDate, 
                HiringType hiringType, 
                HiringDegree hiringDegree, 
                Date birthDate, 
                ArrayList<MemberSubject> teachingSubjects,
                Permission permission, 
                String uID,
                String accessCode) {
        this.name = name;
        this.scientificDegree = scientificDegree;
        this.scientificDepartment = scientificDepartment;
        this.workHours = workHours;
        this.workdays = workdays;
        this.hiringDate = hiringDate;
        this.hiringType = hiringType;
        this.hiringDegree = hiringDegree;
        this.birthDate = birthDate;
        this.teachingSubjects = teachingSubjects;
        this.permission = permission;
        this.uID = uID;
        this.accessCode = accessCode;
    }

    private ArrayList<DayOfWeek> create_workdays(QueryDocumentSnapshot document){
        ArrayList<String> workDaysRef = (ArrayList<String>) document.get("Work Days");
        ArrayList<DayOfWeek> workDays = new ArrayList<DayOfWeek>();

        for (int i = 0; i < workDaysRef.size(); i++){
            workDays.add(DayOfWeek.valueOf(workDaysRef.get(i)));
        }
        return workDays;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setScientificDegree(SciDegree scientificDegree) {
        this.scientificDegree = scientificDegree;
    }

    public SciDegree getScientificDegree() {
        return this.scientificDegree;
    }

    public void setScientificDepartment(SciDepartment scientificDepartment) {
        this.scientificDepartment = scientificDepartment;
    }

    public SciDepartment getScientificDepartment() {
        return this.scientificDepartment;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public int getWorkHours() {
        return this.workHours;
    }

    public void setWorkdays(ArrayList<DayOfWeek> workdays) {
        this.workdays = workdays;
    }

    public ArrayList<DayOfWeek> getWorkdays() {
        return this.workdays;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    public Date getHiringDate() {
        return this.hiringDate;
    }

    public void setHiringType(HiringType hiringType) {
        this.hiringType = hiringType;
    }

    public HiringType getHiringType() {
        return this.hiringType;
    }

    public void setHiringDegree(HiringDegree hiringDegree) {
        this.hiringDegree = hiringDegree;
    }

    public HiringDegree getHiringDegree() {
        return this.hiringDegree;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setTeachingSubjects(ArrayList<MemberSubject> teachingSubjects) {
        this.teachingSubjects = teachingSubjects;
    }
    public void setTeachingSubjects_(ArrayList<HashMap<String,Object>> teachingSubjects) {
        this.teachingSubjects = new ArrayList<>();
        for(HashMap<String,Object> document : teachingSubjects){
            this.teachingSubjects.add(new MemberSubject(document));
        }
    }

    public ArrayList<MemberSubject> getTeachingSubjects() {
        return this.teachingSubjects;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return this.permission;
    }

    public void setUId(String uID) {
        this.uID = uID;
    }

    public String getUID() {
        return this.uID;
    }
    
    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getAccessCode() {
        return this.accessCode;
    }

    public StaffMember copy(){
        return new StaffMember(
                name,scientificDegree,scientificDepartment,workHours,(ArrayList<DayOfWeek>) workdays.clone(),hiringDate,hiringType,hiringDegree,
                birthDate,(ArrayList<MemberSubject>) teachingSubjects.clone(),permission,uID,accessCode);
    }

    // dummy code

    public int getMax_subjects() {
        return max_subjects;
    }

    public void setMax_subjects(int max_subjects) {
        this.max_subjects = max_subjects;
    }

    public HashMap<String ,Object> to_HashMap(){
        HashMap<String, Object> result = new HashMap<String, Object>();
        ArrayList<DayOfWeek> days = getWorkdays();
        ArrayList<String> newDays = new ArrayList<>();

        for (int i = 0; i < days.size(); i++)
            newDays.add(days.get(i).toString());

        ArrayList<MemberSubject> subjects = getTeachingSubjects();
        ArrayList<HashMap<String ,Object>> newSubjects = new ArrayList<>();

        for (int i = 0; i < subjects.size(); i++)
            newSubjects.add(subjects.get(i).to_hashmap());

        result.put("Name", getName());
        result.put("Scientific Degree", getScientificDegree().toString());
        result.put("Scientific Department", getScientificDepartment().toString());
        result.put("Work Hours", getWorkHours());
        result.put("Work Days", newDays);
        result.put("Hiring Date", getHiringDate());
        result.put("Hiring Type", getHiringType().toString());
        result.put("Hiring Degree", getHiringDegree().toString());
        result.put("BirthDate", getBirthDate());
        result.put("Teaching Subjects", newSubjects);
        result.put("Permission", getPermission().toString());
        result.put("UID", getUID());
        result.put("Access Code", getAccessCode());

        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 24;
        int result = 3;
        int ascii = 0;

        for (char ch: uID.toCharArray())
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
        StaffMember other = (StaffMember) obj;
        if (!uID.equals(other.getUID()))
            return false;
        return true;
    }

}
