package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import java.util.Date;
import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();

    List<Room> getRooms();

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    boolean isReserved(Date debut, Date fin, Room room);
    /*List<String> checkIfEmailIsOK(String str);*/

}
