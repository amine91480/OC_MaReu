package com.amine.mareu.Service;

import android.util.Log;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.getDummyMeeting();
    private List<Room> rooms = DummyRoomGenerator.generateRoom();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public List<String> getListNameRooms() { // Render the List of Room Name -> Use to AutoCompleteView
        List<String> mRoomName = new ArrayList<>();
        for (Room room : getRooms()) {
            mRoomName.add(room.getName());
        }
        return mRoomName;
    }

    @Override
    public void createMeeting(Meeting meeting) { // Create a New Meeting(check if the reservation is correct)
        if (!checkTheFuturReservation(meeting.getDateBegin(), meeting.getDateAfter(), meeting.getRoom())) {
            meetings.add(meeting);
        }
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public ArrayList<Meeting> roomChoseToFilter(Room room) { // Take a Room and return a List of every Meeting in this Room
        ArrayList<Meeting> filtredRoom = new ArrayList<Meeting>();
        for (Meeting meeting : meetings) {
            if (meeting.getRoom().getName().contains(room.getName())) {
                filtredRoom.add(meeting);
            }
        }
        return filtredRoom;
    }

    @Override
    public boolean checkTheFuturReservation(LocalDateTime dateBegin, LocalDateTime dateFinish, Room room) {
        // Check if the reservation is correct with Date and Room and return True or False
        boolean isReserved = false;
        for (Meeting meet : meetings) {
            if (room.toString().equals(meet.getRoom().toString())) { // Vérifie si la salle est identique
                Log.d("isReserve/API", meet.getId().toString() + " " + meet.getRoom().toString() + " " + meet.getDateBegin());

                // Vérifier si date de début de meeting n'est pas entre le date de début du meet et la date de fin de meet
                if ((dateBegin.isAfter(meet.getDateBegin())) && (dateBegin.isBefore(meet.getDateAfter()))) {
                    Log.d("isReserve/API", "Date de debut et entre l'intervalle");
                    isReserved = true;
                    return isReserved;
                }
                // Vérifier si la date de fin de meeting n'est pas entre la date de début du meet et la date de fin du meet
                if ((dateFinish.isAfter(meet.getDateBegin())) && (dateFinish.isBefore(meet.getDateAfter()))) {
                    Log.d("isReserve/API", "Date de fin et entre l'intervalle");
                    isReserved = true;
                    return isReserved;
                }
                // Vérifier si la date du début de meeting n'est pas égal au meet
                if (dateBegin.equals(meet.getDateBegin())) {
                    Log.d("isReserve/API", "date identiaque");
                    isReserved = true;
                    return isReserved;
                }
            }
        }
        return isReserved; // Return Boolean
    }
}
