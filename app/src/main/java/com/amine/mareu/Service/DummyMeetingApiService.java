package com.amine.mareu.Service;

import android.util.Log;

import com.amine.mareu.Model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {return meetings;}

    @Override
    public void createMeeting(Meeting meeting) {
        Boolean isReserved;
        isReserved = false;
        for (Meeting meet : meetings) {
            if (meeting.getLocation().equals(meet.getLocation())) {
                if ( (meeting.getDateBegin().after(meet.getDateBegin()) ) && ( meeting.getDateBegin().before(meet.getDateAfter()) )
                    || ( meeting.getDateAfter().after(meet.getDateBegin()) ) && ( meeting.getDateAfter().before(meet.getDateAfter()) ) ) {
                        isReserved = true;
                        Log.d("isReserve/API", "Réservation impossible....");
                        break;
                }
            }
        }
        if (!isReserved) meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {meetings.remove(meeting);}

}
