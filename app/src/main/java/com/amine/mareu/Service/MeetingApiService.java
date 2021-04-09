package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings(); // Render the List of Meeting

    // Render the List of Meeting filtered by LocalDate and Room
    List<Meeting> getFilteredMeeting(Room room, LocalDate date);

    List<Room> getRooms(); // Render the List of Room

    List<String> getListNameRooms(); // Render the List of Room Name(Use to AutoCompleteView)

    List<Meeting> setClearListMeeting();

    void createMeeting(Meeting meeting); // Create a New Meeting to add on the List Meeting

    void deleteMeeting(Meeting meeting); // Deleate the Meeting to the List

    List<Meeting> roomChooseToFilter(Room room); // Take a Room to return all Metting in this Room

    // Take a LocalDate(Year,Month,Day) to return all Meeting in this Date
    List<Meeting> dateChooseToFilter(LocalDate date);

    // Take Time and Room to check and return True or False
    boolean checkTheFuturReservation(LocalDateTime debut, LocalDateTime fin, Room room);

    /*List<String> checkIfEmailIsOK(String str); // Check if the String is a Email valide to Add on a List of Meeting */
}
