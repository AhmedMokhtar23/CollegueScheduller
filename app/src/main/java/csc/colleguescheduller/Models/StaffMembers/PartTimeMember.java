package csc.colleguescheduller.Models.StaffMembers;

/*
Mahmoud Maher
 */

import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import csc.colleguescheduller.Models.Subjects.Subject;

public class PartTimeMember extends StaffMember {

    private String college = "";
    private String university = "";

    public PartTimeMember() {
        super();
    }

/*
    public PartTimeMember(QueryDocumentSnapshot document) {
        super(document);
        this.college = document.getString("College");
        this.university = document.getString("University");
    }
*/

    public PartTimeMember(QueryDocumentSnapshot document) {
        super(document);
        this.college = document.getString("College");
        this.university = document.getString("University");
    }

    public PartTimeMember(
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
            String access_code,
            String college,
            String university) {

        super(
                name,scientificDegree,scientificDepartment,workHours,workdays,
                hiringDate,hiringType,hiringDegree,birthDate,teachingSubjects,permission,uID,access_code);

        this.college = college;
        this.university = university;
    }

    public void setcollege(String college) {
        this.college = college;
    }

    public String getcollege() {
        return this.college;
    }

    public void setuniversity(String university) {
        this.university = university;
    }

    public String getuniversity() {
        return this.university;
    }

    @Override
    public HashMap<String, Object> to_HashMap() {
        HashMap<String,Object> result = super.to_HashMap();
        result.put("College", getcollege());
        result.put("University", getuniversity());
        return result;
    }

    public PartTimeMember copy(){
        return new PartTimeMember(getName(),getScientificDegree(),getScientificDepartment(),getWorkHours(),getWorkdays(),getHiringDate(),
                getHiringType(),getHiringDegree(),getBirthDate(),getTeachingSubjects(),getPermission(),getUID(),getAccessCode(),getcollege(),getuniversity());
    }
}
