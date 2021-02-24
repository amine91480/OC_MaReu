package com.amine.mareu.Service;

import com.amine.mareu.Model.Room;
import com.amine.mareu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyRoomGenerator {

    public static List<Room> DUMMY_ROOM = Arrays.asList(
            new Room("Room A", R.color.purple_500),
            new Room("Room B",R.color.blueblue),
            new Room("Room C",R.color.teal_200),
            new Room("Room D",R.color.teal_700),
            new Room("Room A",R.color.purple_200)
    );

    static List<Room> generateRoom() {
        return new ArrayList<>(DUMMY_ROOM);
    }
}
