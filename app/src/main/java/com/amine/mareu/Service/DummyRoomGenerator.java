package com.amine.mareu.Service;

import com.amine.mareu.Model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyRoomGenerator {

  public static final List<Room> DUMMY_ROOM = Arrays.asList(
      new Room("Room A", "#7BDFF2"),
      new Room("Room B", "#B2F7EF"),
      new Room("Room C", "#EFF7F6"),
      new Room("Room D", "#F7D6E0"),
      new Room("Room E", "#F2B5D4"),
      new Room("Room F", "#171738"),
      new Room("Room G", "#593C8F"),
      new Room("Room H", "#FFD9CE"),
      new Room("Room I", "#DB5461"),
      new Room("Room J", "#413F54"),
      new Room("Room K", "#355691")
  );

  static List<Room> generateRoom() {
    return new ArrayList<>(DUMMY_ROOM);
  }
}
