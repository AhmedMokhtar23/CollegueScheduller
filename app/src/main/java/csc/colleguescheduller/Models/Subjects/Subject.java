package csc.colleguescheduller.Models.Subjects;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;


public class Subject {

    private String subjectId = "";
    private String name = "";
    private String nameInArabic = "";
    private SubjectType subjectType = SubjectType.values()[0];
    private int numberOfSectionsPractical = 0;
    private int numberOfSectionsApplied = 0;
    private int numberOfSectionsTheoretical = 0;

    public Subject() {
    }

    public Subject(
            String subjectId, 
            String name, 
            String nameInArabic, 
            SubjectType type,
            int numberOfSectionsPractical, 
            int numberOfSectionsApplied,
            int numberOfSectionsTheoretical
            ) {
        this.subjectId = subjectId;
        this.name = name;
        this.nameInArabic = nameInArabic;
        this.subjectType = type;
        this.numberOfSectionsPractical = numberOfSectionsPractical;
        this.numberOfSectionsApplied = numberOfSectionsApplied;
        this.numberOfSectionsTheoretical = numberOfSectionsTheoretical;
    }

    public Subject(QueryDocumentSnapshot document){
        this(
            document.getString("ID"),
            document.getString("Name"),
            document.getString("Name in Arabic"),
            SubjectType.valueOf(document.getString("Type")),
            document.getDouble("Number of Sections.Practical").intValue(),
            document.getDouble("Number of Sections.Applied").intValue(),
            document.getDouble("Number of Sections.Theoretical").intValue()
        );
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameInArabic() {
        return this.nameInArabic;
    }

    public void setNameInArabic(String nameInArabic) {
        this.nameInArabic = nameInArabic;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public int getNumberOfSectionsPractical() {
        return this.numberOfSectionsPractical;
    }

    public void setNumberOfSectionsPractical(int numberOfSectionsPractical) {
        this.numberOfSectionsPractical = numberOfSectionsPractical;
    }

    public int getNumberOfSectionsApplied() {
        return this.numberOfSectionsApplied;
    }

    public void setNumberOfSectionsApplied(int numberOfSectionsApplied) {
        this.numberOfSectionsApplied = numberOfSectionsApplied;
    }

    public int getNumberOfSectionsTheoretical() {
        return this.numberOfSectionsTheoretical;
    }

    public void setNumberOfSectionsTheoretical(int numberOfSectionsTheoretical) {
        this.numberOfSectionsTheoretical = numberOfSectionsTheoretical;
    }

    public Subject copy(){
        return new Subject(subjectId,name,nameInArabic,subjectType,numberOfSectionsPractical,
                numberOfSectionsApplied,numberOfSectionsTheoretical);
    }

    @Override
    public String toString() {
        return getName();
    }

    public HashMap<String,Object> to_HashMap(){
        HashMap<String, Object> result = new HashMap<>();

        HashMap<String, Integer> sections = new HashMap<>();

        sections.put("Practical", getNumberOfSectionsPractical());
        sections.put("Applied", getNumberOfSectionsApplied());
        sections.put("Theoretical", getNumberOfSectionsTheoretical());

        result.put("ID", getSubjectId());
        result.put("Name", getName());
        result.put("Name in Arabic", getNameInArabic());
        result.put("Type", getSubjectType().toString());
        result.put("Number of Sections", sections);

        return result;
    }


    @Override
    public int hashCode() {
        final int prime = 24;
        int result = 3;
        int ascii = 0;

        for (char ch: subjectId.toCharArray())
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
        Subject other = (Subject) obj;
        if (!subjectId.equals(other.getSubjectId()))
            return false;
        return true;
    }
}
