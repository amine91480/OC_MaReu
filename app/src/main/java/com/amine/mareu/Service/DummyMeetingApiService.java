package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {return meetings;}

    @Override
    public void createMeeting(Meeting meeting) {meetings.add(meeting);}

    @Override
    public void deleteMeeting(Meeting meeting) {meetings.remove(meeting);}

}
