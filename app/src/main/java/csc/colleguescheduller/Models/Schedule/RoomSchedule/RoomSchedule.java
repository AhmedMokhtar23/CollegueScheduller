package csc.colleguescheduller.Models.Schedule.RoomSchedule;


import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;

import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Models.Schedule.MemberSchedule.MemberCell;
import csc.colleguescheduller.Models.Schedule.Schedule;

public class RoomSchedule extends Schedule {

    private Room room;
    private HashMap<DayOfWeek, ArrayList<RoomCell>> rows;

    public RoomSchedule() {
        super();
        this.rows = new HashMap<>();
    }
    
    public RoomSchedule(int workDays, DayOfWeek startDay) {
        super(workDays, startDay);
        this.rows = new HashMap<>();
    }
                  
    public RoomSchedule(HashMap<String, Object> data, Room room) {
        super(5, DayOfWeek.SUNDAY);
        this.room = room;
        this.rows = (HashMap<DayOfWeek, ArrayList<RoomCell>>) data.get("Rows");
    }
     
	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public HashMap<DayOfWeek, ArrayList<RoomCell>> getRows() {
		return this.rows;
	}

	public void setRows(HashMap<DayOfWeek, ArrayList<RoomCell>> rows) {
		this.rows = rows;
	}
 
    public HashMap<String ,Object> to_HashMap(){
        HashMap<String, Object> result = new HashMap<String, Object>();
        
        result.put("Work Days", this.getWorkDays());
        result.put("Start Day", this.getStartDay());
        result.put("Room", this.room.getRoomid());
        result.put("Rows", this.rows);

        return result;
    }

}
