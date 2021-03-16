package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();

    List<Room> getRooms();

    ArrayList<Meeting> chooseYourRoom(Room room);

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    boolean isReserved(LocalDateTime debut, LocalDateTime fin, Room room);
    /*List<String> checkIfEmailIsOK(String str);*/

}
