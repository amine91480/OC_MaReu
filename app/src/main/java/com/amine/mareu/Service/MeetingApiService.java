package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;

import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    boolean isReserved(Meeting meeting);
}
