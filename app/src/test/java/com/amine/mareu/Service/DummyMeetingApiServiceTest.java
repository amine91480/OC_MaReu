package com.amine.mareu.Service;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DummyMeetingApiServiceTest {

    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
    private List<Room> mRooms;
    private Room roomToTest;
    private LocalDate dateToTest;

    @Before
    public void setupTest() { // Contain all variables used to passed Test
        mApiService = DI.getMeetingApiService(); // Contain all method write in Service/ApiService share with DI
        mMeetings = mApiService.getMeetings(); // Contain the List of object Meeting
        mRooms = mApiService.getRooms(); // Contain the List of object Room
        roomToTest = mRooms.get(0); // Contain the first object Room, use for test
        dateToTest = mMeetings.get(0).getDateBegin().toLocalDate(); // Contain the LocalDate of the first Meeting, use for testing
    }

    @Test // Test method -> to check if the method filter Room return the good list of Room
    public void testRoomChooseToFilter() {
        // meetingFiltredByRoom taken all the Meeting with the first Room object
        roomToTest = mRooms.get(0); // Contain the first object Room, use for test
        List<Meeting> meetingFiltredByRoom = mApiService.roomChooseToFilter(roomToTest);
        List<Meeting> test = new ArrayList<>();
        for (Meeting meeting : mMeetings) {
            if (roomToTest.equals(meeting.getRoom())) {
                test.add(meeting);
            }
        }
        assertEquals(meetingFiltredByRoom, test); // Valid if two list is Equal
    }

    @Test // Test to check if the method filter Date return the good list of Date
    public void testDateChooseToFilter() {
        // meetingFiltredByDate taken all the Meeting with the same Date
        dateToTest = mMeetings.get(0).getDateBegin().toLocalDate(); // Contain the LocalDate of the first Meeting, use for testing
        List<Meeting> meetingFiltredByDate = mApiService.dateChooseToFilter(dateToTest);
        List<Meeting> test = new ArrayList<>();
        for (Meeting meeting : mMeetings) {
            if (dateToTest.isEqual(meeting.getDateBegin().toLocalDate())) {
                test.add(meeting);
            }
        }
        assertEquals(meetingFiltredByDate, test); // Valid if two list is Equal
    }

}