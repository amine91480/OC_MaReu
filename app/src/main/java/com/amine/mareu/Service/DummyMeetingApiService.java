package com.amine.mareu.Service;

import android.util.Log;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import java.util.Date;
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
    public void createMeeting(Meeting meeting) {
        if (isReserved(meeting.getDateBegin(), meeting.getDateAfter(), meeting.getRoom()) == false) {
            meetings.add(meeting);
        }
    }

    @Override
    public boolean isReserved(Date dateBegin, Date dateFinish, Room room) {
        boolean isReserved = false;
        for (Meeting meet : meetings) {
            if (room.toString().equals(meet.getRoom().toString())) { // Vérifie si la salle est identique
                Log.d("isReserve/API", meet.getId().toString() + " " + meet.getRoom().toString() + " " + meet.getDateBegin());

                // Vérifier si date de début de meeting n'est pas entre le date de début du meet et la date de fin de meet
                if ((dateBegin.after(meet.getDateBegin())) && (dateBegin.before(meet.getDateAfter()))) {
                    Log.d("isReserve/API", "Date de debut et entre l'intervalle");
                    isReserved = true;
                    return isReserved;
                }
                // Vérifier si la date de fin de meeting n'est pas entre la date de début du meet et la date de fin du meet
                if ((dateFinish.after(meet.getDateBegin())) && (dateFinish.before(meet.getDateAfter()))) {
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
        return isReserved;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

}
