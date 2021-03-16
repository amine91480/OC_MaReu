package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings(); // Render the List of Meeting

    List<Room> getRooms(); // Render the List of Room

    List<String> getListNameRooms(); // Render the List of Room Name(Use to AutoCompleteView)

    ArrayList<Meeting> roomChoseToFilter(Room room); // Take a Room to return all Metting in this Room

    void createMeeting(Meeting meeting); // Create a New Meeting to add on the List Meeting

    void deleteMeeting(Meeting meeting); // Deleate the Meeting to the List

    boolean checkTheFuturReservation(LocalDateTime debut, LocalDateTime fin, Room room); // Take Time and Room to check and return True or False

    /*List<String> checkIfEmailIsOK(String str); // Check if the String is a Email valide to Add on a List of Meeting */
}
