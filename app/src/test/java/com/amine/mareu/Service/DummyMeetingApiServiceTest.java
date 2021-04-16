package com.amine.mareu.Service;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.amine.mareu.Service.DummyRoomGenerator.generateRoom;
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
        //mMeetings = mApiService.getMeetings(); // Contain the List of object Meeting
        mRooms = mApiService.getRooms(); // Contain the List of object Room
        //roomToTest = mRooms.get(0); // Contain the first object Room, use for test
        //dateToTest = mMeetings.get(0).getDateBegin().toLocalDate(); // Contain the LocalDate of the first Meeting, use for testing
        mApiService.createMeeting(new Meeting(0, LocalDateTime.now(), LocalDateTime.now().plusHours(1) , mRooms.get(0), "TEST", "test91480@hotmail.fr"));
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
            if (dateToTest.equals(meeting.getDateBegin().toLocalDate())) {
                System.out.println();
                test.add(meeting);
            }
        }
        assertEquals(meetingFiltredByDate, test); // Valid if two list is Equal
    }

    @Test
    public void getMeetings() {
        mMeetings = mApiService.getMeetings();
        assertEquals( mMeetings.size() , mApiService.getMeetings().size() );
    }

    @Test
    public void getFilteredMeeting() {
        // TODO ->
    }

    @Test
    public void getRooms() {
        for (Room room : mRooms ) {
            System.out.println(room.getName());
        }
        assertEquals( mRooms , mApiService.getRooms() );
    }

    @Test
    public void getListNameRooms() {
        List<String> mRoomName = new ArrayList<>();
        for (Room room : mRooms ) {
            mRoomName.add(room.getName());
        }
        assertEquals( mRoomName , mApiService.getListNameRooms() );
    }

    @Test
    public void setClearListMeeting() {
        // TODO ->
    }

    @Test
    public void createMeeting() {
        // TODO ->
    }

    @Test
    public void deleteMeeting() {
        // TODO ->
        mApiService.deleteMeeting(mMeetings.get(0));
    }

    @Test
    public void roomChooseToFilter() {
        // TODO ->
    }

    @Test
    public void dateChooseToFilter() {
        // TODO ->
    }

    @Test
    public void checkTheFuturReservation() {
        // TODO ->
    }
}