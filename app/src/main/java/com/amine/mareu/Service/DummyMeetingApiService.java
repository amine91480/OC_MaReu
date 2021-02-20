package com.amine.mareu.Service;

import android.util.Log;

import com.amine.mareu.Model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        if (isReserved(meeting) == false) {
            meetings.add(meeting);
        }
    }

    @Override
    public boolean isReserved(Meeting meeting) {
        boolean isReserved = false;
        for (Meeting meet : meetings) {
            if (meeting.getRoom().equals(meet.getRoom())) { // Vérifie si la salle est identique

                // Vérifier si date de début de meeting n'est pas entre le date de début du meet et la date de fin de meet
                if ((meeting.getDateBegin().after(meet.getDateBegin())) && (meeting.getDateBegin().before(meet.getDateAfter()))) {
                    Log.d("isReserve/API", "Date de debut et entre l'intervalle");
                    isReserved = true;
                    break;
                }
                // Vérifier si la date de fin de meeting n'est pas entre la date de début du meet et la date de fin du meet
                if ((meeting.getDateAfter().after(meet.getDateBegin())) && (meeting.getDateAfter().before(meet.getDateAfter()))) {
                    Log.d("isReserve/API", "Date de fin et entre l'intervalle");
                    isReserved = true;
                    break;
                }
                // Vérifier si la date du début de meeting n'est pas égal au meet
                if (meeting.getDateBegin().equals(meet.getDateBegin())) {
                    Log.d("isReserve/API", "date identiaque");
                    isReserved = true;
                    break;
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
