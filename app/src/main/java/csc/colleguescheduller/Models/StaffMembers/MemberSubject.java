package csc.colleguescheduller.Models.StaffMembers;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;

import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Subjects.Subject;

public class MemberSubject extends Subject {

    private int numberOfSections;
    private MemberSubjectType type;

    public MemberSubject(){
        super();
        this.numberOfSections = 1;
        this.type = MemberSubjectType.Theoretical;
    }

    public MemberSubject(HashMap<String,Object> document){
        setSubject((String) document.get("Subject"));
        setNumberOfSections(((Long)document.get("Number of Sections")).intValue());
        setType(MemberSubjectType.valueOf((String) document.get("Type")));
    }

    public MemberSubject(Subject subject, int numberOfSections, MemberSubjectType type){
        setSubject(subject);
        this.numberOfSections = (type.equals(MemberSubjectType.Theoretical)) ? 1 : numberOfSections;
        this.type = type;
    }

    public Subject getSubject() {
        return new Subject(getSubjectId(),getName(),getNameInArabic(),getSubjectType(),getNumberOfSectionsPractical(),getNumberOfSectionsApplied(),getNumberOfSectionsTheoretical());
    }
    private void setSubject(String subjectid){
        for(Subject subject : Globals.session.subjects){
            if(subject.getSubjectId().equals(subjectid)){
                setSubject(subject);
            }
        }
    }
    public void setSubject(Subject subject) {
        setSubjectId(subject.getSubjectId());
        setName(subject.getName());
        setNameInArabic(subject.getNameInArabic());
        setSubjectType(subject.getSubjectType());
        setNumberOfSectionsApplied(subject.getNumberOfSectionsApplied());
        setNumberOfSectionsPractical(subject.getNumberOfSectionsPractical());
        setNumberOfSectionsTheoretical(subject.getNumberOfSectionsTheoretical());
    }

    public int getNumberOfSections() {
        return numberOfSections;
    }

    public void setNumberOfSections(int numberOfSections) {
        this.numberOfSections = numberOfSections;
    }

	public MemberSubjectType getType() {
		return type;
	}

	public void setType(MemberSubjectType type) {
		this.type = type;
	}

	public HashMap<String,Object> to_hashmap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("Subject",getSubjectId());
        result.put("Number of Sections",numberOfSections);
        result.put("Type",type.toString());
        return result;
    }

    @Override
    public String toString() {
        return getName();
    }
}