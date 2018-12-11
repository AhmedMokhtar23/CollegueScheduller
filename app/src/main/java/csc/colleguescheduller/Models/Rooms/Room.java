package csc.colleguescheduller.Models.Rooms;

/*
Ahmed Abd El Aziz
 */

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;

public class Room {
    private String Roomid = "";
    private RoomType Type = RoomType.values()[0];
    private int Capacity = 0;
    private RoomStatus Status = RoomStatus.values()[0];

    public Room() {}

    public Room(QueryDocumentSnapshot document){
        this(
            document.getString("ID"),
            RoomType.valueOf(document.getString("Type")),
            document.getDouble("Capacity").intValue(),
            RoomStatus.valueOf(document.getString("Status"))
        );
    }

    public Room(String roomid, RoomType type, int capacity, RoomStatus status) {
        Roomid = roomid;
        Type = type;
        Capacity = capacity;
        Status = status;
    }


    public String getRoomid() {
        return Roomid;
    }

    public void setRoomid(String roomid) {
        Roomid = roomid;
    }

    public RoomType getType() {
        return Type;
    }

    public void setType(RoomType type) {
        Type = type;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public RoomStatus getStatus() {
        return Status;
    }

    public void setStatus(RoomStatus status) {
        Status = status;
    }

    public Room copy(){
        return new Room(Roomid,Type,Capacity,Status);
    }

    @Override
    public String toString() {
        return Type.toString() + " - " + Roomid;
    }

    public HashMap<String,Object> to_HashMap(){
        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("ID", getRoomid());
        result.put("Capacity", getCapacity());
        result.put("Type", getType().toString());
        result.put("Status", getStatus().toString());

        return result;
    }


    @Override
    public int hashCode() {
        final int prime = 24;
        int result = 3;
        int ascii = 0;

        for (char ch: Roomid.toCharArray())
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
        Room other = (Room) obj;
        if (!Roomid.equals(other.getRoomid()))
            return false;
        return true;
    }
}
