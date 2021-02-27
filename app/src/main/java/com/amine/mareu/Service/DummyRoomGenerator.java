package com.amine.mareu.Service;

import com.amine.mareu.Model.Room;
import com.amine.mareu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyRoomGenerator {

    public static List<Room> DUMMY_ROOM = Arrays.asList(
            new Room("Room A","#2F4F4F"),
            new Room("Room B","#FFF8DC"),
            new Room("Room C","#A52A2A"),
            new Room("Room D","#7CFC00")
    );

    static List<Room> generateRoom() {
        return new ArrayList<>(DUMMY_ROOM);
    }
}
