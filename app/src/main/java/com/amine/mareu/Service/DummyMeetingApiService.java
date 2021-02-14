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
        boolean isReserve = false;
        for (Meeting m : meetings) {
            if (m.getLocation() == meeting.getLocation() && m.getDate() == meeting.getDate()) {
                isReserve = true;
                System.out.println(m.getDate()+"////"+meeting.getDate()+"/////"+isReserve);
                break;
            }
        }
        if (isReserve == false) {
            System.out.println("////" + meeting.getDate());
            meetings.add(meeting);
        }
    }

    @Override
    public void deleteMeeting(Meeting meeting) {meetings.remove(meeting);}

}
