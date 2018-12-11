package csc.colleguescheduller.Models.Schedule;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Schedule.MemberSchedule.MemberCell;
import csc.colleguescheduller.Models.Schedule.MemberSchedule.MemberSchedule;
import csc.colleguescheduller.Models.Schedule.RoomSchedule.RoomCell;
import csc.colleguescheduller.Models.Schedule.RoomSchedule.RoomSchedule;
import csc.colleguescheduller.Models.Schedule.SchemaSchedule.SchemaCell;
import csc.colleguescheduller.Models.Schedule.SchemaSchedule.SchemaSchedule;
import csc.colleguescheduller.Models.Schedule.Schema.Schema;
import csc.colleguescheduller.Models.StaffMembers.HiringDegree;
import csc.colleguescheduller.Models.StaffMembers.HiringType;
import csc.colleguescheduller.Models.StaffMembers.MemberSubject;
import csc.colleguescheduller.Models.StaffMembers.MemberSubjectType;
import csc.colleguescheduller.Models.StaffMembers.SciDegree;
import csc.colleguescheduller.Models.StaffMembers.StaffMember;
import csc.colleguescheduller.Models.Subjects.Subject;


public class ScheduleGenerator {

    private HashMap<Schema, SchemaSchedule> schemaSchedules;
    private HashMap<StaffMember, MemberSchedule> memberSchedules;
    private HashMap<Room, RoomSchedule> roomSchedules;

    private SchemaSchedule schemaSchedule;
    private MemberSchedule memberSchedule;
    private RoomSchedule roomSchedule;

    private HashMap<DayOfWeek, ArrayList<SchemaCell>> schemaScheduleRows;
    private HashMap<DayOfWeek, ArrayList<MemberCell>> memberScheduleRows;
    private HashMap<DayOfWeek, ArrayList<RoomCell>> roomScheduleRows;

    private AvailableSubjectCounter availableSubjectCounter;
    private HashMap<Section, Integer> subjectTheoreticalCounter;
    private HashMap<Section, Integer> subjectAppliedCounter;
    private HashMap<Section, Integer> subjectPracticalCounter;

    //private String logMessage;

    public ScheduleGenerator(){
    }

    public HashMap<Schema, SchemaSchedule> getSchemaSchedules() {
        return this.schemaSchedules;
    }

    public HashMap<StaffMember, MemberSchedule> getMemberSchedules() {
        return this.memberSchedules;
    }

    public HashMap<Room, RoomSchedule> getRoomSchedules() {
        return this.roomSchedules;
    }

    /*
    public String getLog() {
        return this.logMessage;
    }
    */
    
    public void generate(
        int workDays, 
        DayOfWeek startDay,
        ArrayList<Schema> schemas, 
        HashMap<Room, Room> rooms
    ) {
        // Create empty schedules for all schemas, staff members, and rooms
        schemaSchedules = new HashMap<>();
        memberSchedules = new HashMap<>();
        roomSchedules = new HashMap<>();
        // End

        // Start generating schedules for every schema
        for (Schema schema: schemas) {

            // Log message
            // logMessage += String.join(" ", "Start generating schedule for"
            //        , schema.getSchemaID(), "schema\n");
            
            // Create an empty schedule for current schema
            schemaSchedule = (schemaSchedules.get(schema) == null) ? 
                    new SchemaSchedule(workDays, startDay) : schemaSchedules.get(schema);
            // Get the current schema schedule rows
            schemaScheduleRows = schemaSchedule.getRows();

            // Sorte current schema teaching members
            // Log messgae
            // logMessage += String.join(" ", "Generating a list of sorted teaching members of"
            //        , schema.getSchemaID(), "schema\n");
            SortedTeachingMembers sortedTeachingMembers = sortedTeachingMembers(schema);

            // Check available teaching units for every schema teaching subjects 
            HashMap<Subject, AvailableSubjectCounter> availableSubjectCounters = new HashMap<>();

            for(Subject subject : schema.getSubjects()){

                // Log message
                // logMessage += String.join(" ", "Generating a list of the available"
                //         , "subject teaching units of" , subject.getName(), "\n");

                availableSubjectCounters.put(subject, availableSubjectCounter(subject,schema));

            }
            // End
            
            // Start generating schedules for every teaching member in the current schema
            for (StaffMember member : sortedTeachingMembers.getLecturers()) {

                // Log message
                // logMessage += String.join(" ", "Start generating schedule of"
                //         , member.getName(), "member\n");

                // Get the current teaching member schedule
                memberSchedule = (memberSchedules.get(member) == null) ? 
                        new MemberSchedule(workDays, startDay) : memberSchedules.get(member);
                // Get the current teaching member schedule rows
                memberScheduleRows = memberSchedule.getRows();

                // Check teaching subjects for current teaching member
                ArrayList<MemberSubject> memberTeachingSubjects = new ArrayList<>();

                for (MemberSubject memberSubject : member.getTeachingSubjects()) 
                    if (availableSubjectCounters.containsKey(memberSubject.getSubject()))
                        memberTeachingSubjects.add(memberSubject);
                // End
                
                // Get work days
                ArrayList<DayOfWeek> memberWorkDays = member.getWorkdays();

                // Fill day checker list with not set status[0]
                int[] dayCheckerList = new int[memberWorkDays.size()];
                for (int i = 0; i < memberWorkDays.size(); i++) dayCheckerList[i] = 0;
                
                // Start adding teaching member subjects to an empty cell if available
                for (MemberSubject subject : memberTeachingSubjects) {

                    // Log message
                    // logMessage += String.join(" ", "Start generating schedule of"
                    //         , subject.getSubject().getName(), "subject\n");

                    // Get current subject objetc
                    Subject currentSubject = subject.getSubject();
                    // Get current subject type (Theoretical, Applied, or Practical)
                    MemberSubjectType subjectType = subject.getType();
                    // Get number of teaching sections for current teaching member subject
                    // Number of sections to be teaching by current teaching member for the current subject
                    int numberOfSections = subject.getNumberOfSections();

                    // Get lists of current subject different fields (Theoretical, Applied, or Practical)
                    // number of teaching units for every section
                    availableSubjectCounter = availableSubjectCounters.get(subject);
                    subjectTheoreticalCounter = availableSubjectCounter.getTheoretical();
                    subjectAppliedCounter = availableSubjectCounter.getApplied();
                    subjectPracticalCounter = availableSubjectCounter.getPractical();


                    reDay:
                        for (DayOfWeek day : memberWorkDays) {

                            // Check if the current day in the set status[1]
                            if (dayCheckerList[memberWorkDays.indexOf(day)] == 1) {
                                
                                // Set the current day to not set status[0]
                                dayCheckerList[memberWorkDays.indexOf(day)] = 0;
                                
                                // Reset day looper
                                if (memberWorkDays.indexOf(day) == (memberWorkDays.size() - 1))
                                    continue reDay;     // Restart day looper
                                else continue;          // Ignore the current day
                            }
                            
                            // Get a list of filled cells of the current teaching member for 
                            // the current day
                            ArrayList<MemberCell> memberScheduleCells = memberScheduleRows.get(day);
                            // Get a list of filled cells of the current schema for the current day
                            ArrayList<SchemaCell> schemaScheduleCells = schemaSchedule.getRows().get(day);
                            
                            // Initial section
                            Section section = Section.A;
                            Boolean caseStatus = false;

                            reSubject:
                                if (subjectType.equals(MemberSubjectType.Theoretical)){
                                    
                                    // Set section to All
                                    section = Section.All;

                                    // Check if the current subject has teaching units or not
                                    // Log message
                                    // logMessage += String.join(" ", "Check if", currentSubject.getName()
                                    //         , "subject has theoretical teaching units\n");
                                    caseStatus = checkSubjectAvailability(subjectType, currentSubject
                                            , subjectTheoreticalCounter);
                                    // If not, ignore the current subject 
                                    // if (caseStatus) logMessage += "Pass check succesfuly\n";
                                    // else {
                                    //     logMessage += String.join(" ", "Subject" , currentSubject.getName() 
                                    //             , "dosent have available theoretical teaching units.", "\n");
                                    //     continue;
                                    // }
                                    
                                    if (!caseStatus) continue;


                                    // Add cell
                                    // Log message
                                    // logMessage += "Start adding cell\n";
                                    Boolean addCell = addCell(schema, member, currentSubject, subjectType, 
                                            section, schemaScheduleCells, memberScheduleCells, rooms, 
                                            availableSubjectCounter,day);

                                    // if (addCell) logMessage += "Cell added succesfuly\n";
                                    // else logMessage += "Error in adding cell\n";

                                } else {

                                    HashMap<Section, Integer> map;
                                    int numberOfUnits;

                                    if (subjectType.equals(MemberSubjectType.Applied)){

                                        map = subjectAppliedCounter;
                                        numberOfUnits = currentSubject.getNumberOfSectionsApplied();

                                        // Check if the current subject has teaching units or not
                                        // Log message
                                        // logMessage += String.join(" ", "Check if", currentSubject.getName()
                                        //         , "subject has applied teaching units\n");
                                        caseStatus = checkSubjectAvailability(subjectType, currentSubject
                                                , subjectAppliedCounter);
                                        // If not, ignore the current subject 
                                        // if (caseStatus) logMessage += "Pass check succesfuly\n";
                                        // else {
                                        //     logMessage += String.join(" ", "Subject" , currentSubject.getName() 
                                        //             , "dosent have available applied teaching units.", "\n");
                                        //     continue;
                                        // }
                            
                                        if (!caseStatus) continue;

                                    } else {
                                        
                                        map = subjectPracticalCounter;
                                        numberOfUnits = currentSubject.getNumberOfSectionsPractical();

                                        // Check if the current subject has teaching units or not
                                        // Log message
                                        // logMessage += String.join(" ", "Check if", currentSubject.getName()
                                        //         , "subject has practical teaching units\n");
                                        caseStatus = checkSubjectAvailability(subjectType, currentSubject
                                                , subjectPracticalCounter);
                                        // If not, ignore the current subject 
                                        // if (caseStatus) logMessage += "Pass check succesfuly\n";
                                        // else {
                                        //     logMessage += String.join(" ", "Subject" , currentSubject.getName() 
                                        //             , "dosent have available practical teaching units.", "\n");
                                        //     continue;
                                        // }
                            
                                        if (!caseStatus) continue;

                                    }

                                    for (Entry<Section, Integer> entry : map.entrySet()){

                                        if (entry.getValue().intValue() < numberOfUnits) continue;
                                        else {
                                            section = entry.getKey();
                                            break;
                                        }

                                    }

                                    // Add cell
                                    // Log message
                                    // logMessage += "Start adding cell\n";
                                    Boolean addCell = addCell(schema, member, currentSubject, subjectType, 
                                            section, schemaScheduleCells, memberScheduleCells, rooms, 
                                            availableSubjectCounter,day);

                                    // if (addCell) logMessage += "Cell added succesfuly\n";
                                    // else logMessage += "Error in adding cell\n";

                                    numberOfSections--;
                                    if (numberOfSections > 0)  continue reDay;
                                    
                                }

                        }
                    
                }

                memberSchedule.setRows(memberScheduleRows);

            }

            schemaSchedule.setRows(schemaScheduleRows);

        }
        
    }

    private AvailableSubjectCounter availableSubjectCounter(Subject subject,Schema schema) {

        HashMap<Section, Integer> theoretical = new HashMap<>();
        HashMap<Section, Integer> applied = new HashMap<>();
        HashMap<Section, Integer> practical = new HashMap<>();

        theoretical.put(Section.All , subject.getNumberOfSectionsTheoretical());

        for(int i = 0; i < schema.getNumberOfSections(); i++){

            applied.put(Section.values()[i+1] , subject.getNumberOfSectionsApplied());
            practical.put(Section.values()[i+1] , subject.getNumberOfSectionsPractical());

        }

        AvailableSubjectCounter subjectCounter = 
                new AvailableSubjectCounter(theoretical, applied, practical);
        
        // Log messgae
        // logMessage += "Passsuccessfully\n";
        return subjectCounter;

    }

    private SortedTeachingMembers sortedTeachingMembers(Schema schema) {

        ArrayList<StaffMember> unSplitedMembers = schema.getTeachingMembers();
        SortedTeachingMembers splitedMembers = new SortedTeachingMembers();

        ArrayList<StaffMember> lecturers = new ArrayList<StaffMember>();
        ArrayList<StaffMember> assistants = new ArrayList<StaffMember>();

        for (StaffMember unSplitedMember : unSplitedMembers) {

            if(unSplitedMember.getHiringDegree() == HiringDegree.Lecturer)
                lecturers.add(unSplitedMember);
            else assistants.add(unSplitedMember);
            
        }

        splitedMembers.setLecturers(sortArrayList(lecturers));
        splitedMembers.setAssistants(sortArrayList(assistants));

        // Log messgae
        // logMessage += "Passsuccessfully\n";
        return splitedMembers;

    }

    private ArrayList<StaffMember> sortArrayList(ArrayList<StaffMember> list) {

        ArrayList<StaffMember> sortedList = new ArrayList<StaffMember>();
        ArrayList<StaffMember> prepareList = new ArrayList<StaffMember>();
        //==============
        ArrayList<SciDegree> sciDegrees = new ArrayList<>();

        for(StaffMember member : list) 
            if(prepareList.isEmpty()) prepareList.add(member);
            else for(StaffMember sortedMember : prepareList) 
                    if(sciDegrees.indexOf(member.getScientificDegree()) > 
                            sciDegrees.indexOf(sortedMember.getScientificDegree())){
                        prepareList.add(prepareList.indexOf(sortedMember), member);
                        break;
                    }
                    else if(prepareList.indexOf(sortedMember) == (prepareList.size()-1))
                        prepareList.add(member);
                    
        for(StaffMember member : prepareList) 
            if(member.getHiringType().equals(HiringType.Part_Time))
                sortedList.add(member);

        for(StaffMember member : prepareList) 
            if(member.getHiringType().equals(HiringType.Full_Time))
                sortedList.add(member);
          
        return sortedList;

    }

    private boolean checkSubjectAvailability(
        MemberSubjectType subjectType,
        Subject currentSubject,
        HashMap<Section, Integer> counter
    ) {
        
        boolean caseStatus = false;
        int numberOfUnits = 0;

        if (subjectType.equals(MemberSubjectType.Theoretical)){

            numberOfUnits = currentSubject.getNumberOfSectionsTheoretical();
            Integer x = counter.get(Section.All);
            if (x.intValue() >= numberOfUnits) caseStatus = true;

        } else if (subjectType.equals(MemberSubjectType.Applied)){

            numberOfUnits = currentSubject.getNumberOfSectionsApplied();
            for (Integer x : counter.values()) 
                if (x.intValue() >= numberOfUnits) caseStatus = true;

        } else if (subjectType.equals(MemberSubjectType.Practical)){

            numberOfUnits = currentSubject.getNumberOfSectionsPractical();
            for (Integer x : counter.values()) 
                if (x.intValue() >= numberOfUnits) caseStatus = true;
        }

        return caseStatus;
        
    }
    
    private ArrayList<Integer> checkAvailableCells(
        ArrayList<SchemaCell> schemaScheduleCells, 
        ArrayList<MemberCell> memberScheduleCells, 
        ArrayList<RoomCell> roomScheduleCells
    ){
        ArrayList<Integer> availableCells = new ArrayList<>();
        for (int i = 1; i <= 8; i++)  availableCells.add(Integer.valueOf(i));

        ArrayList<Integer> availableMemberScheduleCells = availableCells;
        ArrayList<Integer> availableSchemaScheduleCells = availableCells;
        ArrayList<Integer> availableRoomScheduleCells = availableCells;
        
        if(!memberScheduleCells.isEmpty() || memberScheduleCells != null)
            for (MemberCell cell : memberScheduleCells) 
                for (int i = cell.getStart(); i < (cell.getStart() + cell.getPeriod()); i++) 
                    availableMemberScheduleCells.remove(Integer.valueOf(i));
        
        if(!schemaScheduleCells.isEmpty() || schemaScheduleCells != null)
            for (SchemaCell cell : schemaScheduleCells) 
                for (int i = cell.getStart(); i < (cell.getStart() + cell.getPeriod()); i++) 
                    availableSchemaScheduleCells.remove(Integer.valueOf(i));

        if(!roomScheduleCells.isEmpty() || roomScheduleCells != null)
            for (RoomCell cell : roomScheduleCells) 
                for (int i = cell.getStart(); i < (cell.getStart() + cell.getPeriod()); i++) 
                    availableRoomScheduleCells.remove(Integer.valueOf(i));
        
        for (int i = 1; i <= availableCells.size(); i++) 
            if(!availableMemberScheduleCells.contains(Integer.valueOf(i)) || 
                !availableSchemaScheduleCells.contains(Integer.valueOf(i)) ||
                !availableRoomScheduleCells.contains(Integer.valueOf(i))
            ) availableCells.remove(Integer.valueOf(i));
        
        return availableCells;
    }

    private Integer checkCellAvailability(ArrayList<Integer> availableCells, Integer required){

        Integer start = null;

        if (availableCells.size() < required.intValue())
            return start;
        
        start = availableCells.get(0);
        Integer master = start;

        for (int i = 1; i < availableCells.size(); i++) {

            int reMAster = i;

            for (int j = master.intValue() + 1; j < (master.intValue() + required.intValue()); j++) {

                if (!availableCells.get(reMAster).equals(Integer.valueOf(j))) {

                    master = availableCells.get(reMAster);                    
                    start = ((required.intValue() > (availableCells.size() - i)) ? null : master);
                    break;

                }

                reMAster++;
            }

            if (start == null) break;
        }

        return start;
    }

    private Boolean addCell(
        Schema schema,
        StaffMember member,
        Subject currentSubject,
        MemberSubjectType subjectType,
        Section section,
        ArrayList<SchemaCell> schemaScheduleCells,
        ArrayList<MemberCell> memberScheduleCells,
        HashMap<Room, Room> rooms,
        AvailableSubjectCounter availableSubjectCounter,
        DayOfWeek day
    ){

        // Processing required data
        Boolean status = false;
        Boolean operationStatus = true;
        ArrayList<RoomCell> roomScheduleCells = null;
        MemberCell memberCell = null;
        SchemaCell schemaCell = null;
        RoomCell roomCell = null;
        ArrayList<Integer> availableCells;
        int numberOfUnits = 0;
        Integer required = 0;
        Integer start = 0;

        // Cell data
        int cellStart = 0;
        int cellPeriod = 0;
        Year cellYear = schema.getYear();
        Specialization cellGroup = schema.getSpecialization();
        Subject cellSubject = currentSubject;
        Section cellSection = section;
        MemberSubjectType cellType = subjectType;
        StaffMember cellMember = member;
        Room cellRoom = null;

        // Subject counter data
        subjectTheoreticalCounter = availableSubjectCounter.getTheoretical();
        subjectAppliedCounter = availableSubjectCounter.getApplied();
        subjectPracticalCounter = availableSubjectCounter.getPractical();

        // Check if the current subject is a theoretical subject
        Room room = null;
        if (subjectType.equals(MemberSubjectType.Theoretical)){
            
            // Get main room fo the current schema (main hall)
            room = schema.getHall();
            // Get a list of filled cells of the current room for the current day
            roomSchedule = roomSchedules.get(room);
            // Get the current teaching member schedule rows
            roomScheduleRows = roomSchedule.getRows();
            roomScheduleCells = roomScheduleRows.get(day);

            // Get shared cells that be available in the current schema, member, and room schedule
            availableCells = checkAvailableCells(
                schemaScheduleCells, memberScheduleCells, roomScheduleCells);
        
            // Get number of teaching units (hours) for the current subject
            numberOfUnits = currentSubject.getNumberOfSectionsTheoretical();

            // Get start cell that will work as a start pointer to add teaching units
            start = checkCellAvailability(availableCells, Integer.valueOf(numberOfUnits));

            // Check if the start pointer not equal null
            if (start != null){

                cellStart = start.intValue();
                cellPeriod = numberOfUnits;
                cellRoom = room;

                status = true;
            }

        // Check if the current subject is a applied or practical subject
        } else if (subjectType.equals(MemberSubjectType.Applied) || 
                    subjectType.equals(MemberSubjectType.Practical)){
                            
            // Check every available room to add the current cell
            for (Room room1  : rooms.values()) {
                room = room1;
                // Get a list of filled cells of the current room for the current day
                roomSchedule = roomSchedules.get(room1);
                // Get the current teaching member schedule rows
                roomScheduleRows = roomSchedule.getRows();
                roomScheduleCells = roomScheduleRows.get(day);

                // Get shared cells that be available in the current schema, member, and room schedule
                availableCells = checkAvailableCells(
                    schemaScheduleCells, memberScheduleCells, roomScheduleCells);
                
                // Get number of teaching units (hours) for the current subject
                numberOfUnits = (subjectType.equals(MemberSubjectType.Applied)) ? 
                        currentSubject.getNumberOfSectionsApplied() : 
                        currentSubject.getNumberOfSectionsPractical();
                
                // Get start cell that will work as a start pointer to add teaching units
                start = checkCellAvailability(availableCells, Integer.valueOf(numberOfUnits));

                // Check if the start pointer not equal null
                if (start != null){

                    cellStart = start.intValue();
                    cellPeriod = numberOfUnits;
                    cellRoom = room1;
    
                    status = true;
                }

            }

        }

        // Start adding the cell to the schedules to which it belongs
        if (status){
            
            memberCell = new MemberCell(cellStart, cellPeriod, 
                    cellYear, cellGroup, cellSection, cellSubject, cellType, cellRoom);
        
            schemaCell = new SchemaCell(cellStart, cellPeriod, 
                    cellSection, cellSubject, cellType, cellMember, cellRoom);
            
            roomCell = new RoomCell(cellStart, cellPeriod, 
                    cellYear, cellGroup, cellSection, cellSubject, cellType, cellMember);
            
            memberScheduleCells.add(memberCell);
            schemaScheduleCells.add(schemaCell);
            roomScheduleCells.add(roomCell);

            roomScheduleRows.put(day, roomScheduleCells);
            memberScheduleRows.put(day, memberScheduleCells);
            schemaScheduleRows.put(day, schemaScheduleCells);

            roomSchedule.setRows(roomScheduleRows);
            roomSchedules.put(room, roomSchedule);

            Integer value;
            if (subjectType.equals(MemberSubjectType.Theoretical)){
                value = Integer.valueOf(subjectTheoreticalCounter.get(section).intValue() - numberOfUnits);
                subjectTheoreticalCounter.put(section, value);
            }
            else if (subjectType.equals(MemberSubjectType.Applied)){
                value = Integer.valueOf(subjectAppliedCounter.get(section).intValue() - numberOfUnits);
                subjectAppliedCounter.put(section, value);
            }
            else if (subjectType.equals(MemberSubjectType.Practical)){
                value = Integer.valueOf(subjectPracticalCounter.get(section).intValue() - numberOfUnits);
                subjectPracticalCounter.put(section, value);
            }

        } else operationStatus = false;

        return operationStatus;
    }
}