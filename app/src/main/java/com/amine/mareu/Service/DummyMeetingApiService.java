package com.amine.mareu.Service;

import android.util.Log;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = new ArrayList<>(); // POC Don't use the Generator Meeting
    private final List<Room> rooms = DummyRoomGenerator.generateRoom();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Meeting> getFilteredMeeting(Room room, LocalDate date) {
        List<Meeting> filteredMeetingByRoom = roomChooseToFilter(room);
        List<Meeting> filteredMeetingByDate = new ArrayList<>();
        for (Meeting meeting : filteredMeetingByRoom) {
            if (date.isEqual(meeting.getDateBegin().toLocalDate())) {
                filteredMeetingByDate.add(meeting);
            }
        }
        return filteredMeetingByDate;

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
    public List<Meeting> setClearListMeeting() {
        meetings.clear();
        return new ArrayList<>();
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
    public List<Meeting> roomChooseToFilter(Room room) { // Take a Room and return a List of every Meeting in this Room
        List<Meeting> filteredMeetingByRoom = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (meeting.getRoom().getName().contains(room.getName())) {
                filteredMeetingByRoom.add(meeting);
            }
        }
        return filteredMeetingByRoom;
    }

    @Override
    public List<Meeting> dateChooseToFilter(LocalDate date) {
        List<Meeting> filteredMeetingByDate = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (date.isEqual(meeting.getDateBegin().toLocalDate())) {
                filteredMeetingByDate.add(meeting);
            }
        }
        return filteredMeetingByDate;
    }

    @Override
    public boolean checkTheFuturReservation(LocalDateTime dateBegin, LocalDateTime dateFinish, Room room) {
        // Check if the reservation is correct with Date and Room and return True or False
        if (meetings.size() == 0) {
            return false;
        }
        for (Meeting meet : meetings) {

            if (room.getName().equals(meet.getRoom().getName())) { // Vérifie si la salle est identique

                // Vérifier si date de début de meeting n'est pas entre le date de début du meet et la date de fin de meet
                if ((dateBegin.isAfter(meet.getDateBegin())) && (dateBegin.isBefore(meet.getDateAfter()))) {
                    Log.d("isReserve/API", "Date de debut et entre l'intervalle");
                    return true;
                }
                // Vérifier si la date de fin de meeting n'est pas entre la date de début du meet et la date de fin du meet
                if ((dateFinish.isAfter(meet.getDateBegin())) && (dateFinish.isBefore(meet.getDateAfter()))) {
                    Log.d("isReserve/API", "Date de fin et entre l'intervalle");
                    return true;
                }
                // Vérifier si la date du début de meeting n'est pas égal au meet
                if (dateBegin.equals(meet.getDateBegin())) {
                    Log.d("isReserve/API", "date identique");
                    return true;
                }
            }
        }
        return false;
    }
}
