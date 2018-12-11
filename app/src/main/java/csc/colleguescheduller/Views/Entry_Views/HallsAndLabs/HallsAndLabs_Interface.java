package csc.colleguescheduller.Views.Entry_Views.HallsAndLabs;

import java.util.ArrayList;

import csc.colleguescheduller.Models.Rooms.Room;
import csc.colleguescheduller.Views.MyInterface;

public interface HallsAndLabs_Interface extends MyInterface {
    void on_rooms_loaded(ArrayList<Room> rooms);
    void on_room_removed();
    void on_save_success();
}
