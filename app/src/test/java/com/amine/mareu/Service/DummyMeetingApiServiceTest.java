package com.amine.mareu.Service;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
  }

  @After
  public void resetmApiService() {
    mApiService.setClearListMeeting(); // RETURN MEETING LIST TO EMPTY
  }
   /* @Before
    public void setupTestMeeting() {
        Meeting meeting1 = new Meeting(0, LocalDateTime.now(), LocalDateTime.now().plusHours(1) , mRooms.get(0), "testMeeting1", "test91480@hotmail.fr");
        Meeting meeting2 = new Meeting(0, LocalDateTime.now(), LocalDateTime.now().plusHours(1) , mRooms.get(0), "testMeeting2", "test91480@hotmail.fr");
        Meeting meeting3 = new Meeting(0, LocalDateTime.now(), LocalDateTime.now().plusHours(1) , mRooms.get(0), "testMeeting3", "test91480@hotmail.fr");
        Meeting meeting4 = new Meeting(0, LocalDateTime.now(), LocalDateTime.now().plusHours(1) , mRooms.get(0), "testMeeting4", "test91480@hotmail.fr");
        mApiService.createMeeting(meeting1);
        mApiService.createMeeting(meeting2);
        mApiService.createMeeting(meeting3);
        mApiService.createMeeting(meeting4);
        System.out.println( mApiService.getMeetings().size());
    }*/

  public void generatedRandomMeeting() { // GENERATE 5 MEETING
    LocalDateTime time = LocalDateTime.of(2021, 4, 20, 9, 0, 0);
    Meeting meeting1 = new Meeting(0, time, time.plusHours(1), mRooms.get(0), "testMeeting1", "test91480@hotmail.fr");
    Meeting meeting2 = new Meeting(0, time.plusHours(2), time.plusHours(3), mRooms.get(0), "testMeeting2", "test91480@hotmail.fr");
    Meeting meeting3 = new Meeting(0, time.minusDays(1), time.minusDays(1).plusHours(1), mRooms.get(0), "testMeeting3", "test91480@hotmail.fr");
    Meeting meeting4 = new Meeting(0, time.minusDays(1).plusHours(2), time.minusDays(1).plusHours(3), mRooms.get(0), "testMeeting4", "test91480@hotmail.fr");
    mApiService.createMeeting(meeting1); mApiService.createMeeting(meeting2);
    mApiService.createMeeting(meeting3); mApiService.createMeeting(meeting4);
  }

  // TEST LIST
  @Test
  public void getMeetings() { // THIS APP IS A POC -> LIST OF MEETING IS EMPTY -> TEST
    mMeetings = mApiService.getMeetings(); System.out.println(mMeetings.size()); //
    System.out.println(mApiService.getMeetings().size()); //
    assertEquals(mMeetings.size(), DI.getMeetingApiService().getMeetings().size());
  }

  @Test
  public void getRooms() { // RETURN THE LIST OF ROOM
    for ( Room room : mRooms ) {
      System.out.println(room.getName()); //
    } assertEquals(mRooms, DummyRoomGenerator.generateRoom());
  }

  @Test
  public void getListNameRooms() { // RETURN THE LIST OF THE NAME OF THE ROOM
    List<String> mRoomName = new ArrayList<>(); for ( Room room : mRooms ) {
      System.out.println(room.getName()); //
      mRoomName.add(room.getName());
    } assertEquals(mRoomName, DI.getMeetingApiService().getListNameRooms());
  }
  // TEST LIST

  // TEST MEETING
  @Test
  public void createMeeting() { // CREAT THE MEETING
    Meeting testCreated = new Meeting(0, LocalDateTime.now(), LocalDateTime.now().plusHours(1), mRooms.get(0), "Test Creating", "test91480@hotmail.fr");
    mApiService.createMeeting(testCreated); mMeetings = mApiService.getMeetings();
    System.out.println(testCreated.isCompleted()); assertTrue(mMeetings.contains(testCreated));
  }

  @Test
  public void deleteMeeting() { // DELETE THE MEETING
   generatedRandomMeeting(); // CREAT 4 MEETING
    int size = mApiService.getMeetings().size() - 1;
    System.out.println(size); //
    Meeting meetingToDelete = mApiService.getMeetings().get(size); // Take the last Meeting
    System.out.println(mApiService.getMeetings().size()); //
    mApiService.deleteMeeting(meetingToDelete); // Delete the Last Meeting
    System.out.println(mApiService.getMeetings().size()); //
    assertEquals(3, mApiService.getMeetings().size());

  }

  @Test
  public void setClearListMeeting() { // DELETE THE ALL LIST OF MEETING
    generatedRandomMeeting(); // CREAT 4 MEETING
    System.out.println(mApiService.getMeetings().size()); //
    mApiService.setClearListMeeting();
    System.out.println(mApiService.getMeetings().size()); //
    assertEquals(0, mApiService.getMeetings().size());
  }

  @Test
  public void checkTheFuturReservation() { // CHECK IF WE CAN RESERVED A MEETING IF IT DIFFERENT OF A MEETING ALREADY CREATED
    generatedRandomMeeting(); // CREAT 4 MEETING
    mApiService.getMeetings().forEach(meeting -> System.out.println(meeting.getDateBegin()+" "+meeting.getDateAfter()));
    // FIRST MEETING YEARS ->2021 , MONTH->4 , DAYS->20 , HOURS->9 , MINUTE->0, SECONDS->0
    // THIRD MEETING YEARS ->2021 , MONTH->4 , DAYS->19 , HOURS->9 , MINUTE->0, SECONDS->0
    // USE METHODE TO CHECK IF WE CAN ADD A MEETING AT THE SAME DATE OF THE FIRST AND THIRD MEETING LIST
    Room roomTest = mRooms.get(0);
    LocalDateTime timeBeginTest = LocalDateTime.of(2021, 4, 19, 11, 0, 0);
    LocalDateTime timeFinshTest = timeBeginTest.plusHours(1);
    // METHOD RETURN TRUE IF THE ROOM AND THE LOCALDATE IS ALEREADY TAKE BY A MEETING
    assertTrue(mApiService.checkTheFuturReservation(timeBeginTest,timeFinshTest,roomTest));
  }
  // TEST MEETING

  // TEST FILTRED

  @Test // Test method -> to check if the method filter Room return the good list of Room
  public void testRoomChooseToFilter() {
    generatedRandomMeeting();
    // meetingFiltredByRoom taken all the Meeting with the first Room object
    roomToTest = mRooms.get(0); // Contain the first object Room, use for test
    List<Meeting> meetingFiltredByRoom = mApiService.roomChooseToFilter(roomToTest);
    List<Meeting> test = new ArrayList<>();
    for ( Meeting meeting : mApiService.getMeetings() ) {
      if ( roomToTest.equals(meeting.getRoom()) ) {
        test.add(meeting);
      }
    } assertEquals(meetingFiltredByRoom, test); // Valid if two list is Equal
  }


  @Test
  public void testDateChooseToFilter() { // Test to check if the method filter Date return the good list of Date
    generatedRandomMeeting();
    // meetingFiltredByDate taken all the Meeting with the same Date
    dateToTest = mApiService.getMeetings().get(0).getDateBegin().toLocalDate(); // Contain the LocalDate of the first Meeting, use for testing
    List<Meeting> meetingFiltredByDate = mApiService.dateChooseToFilter(dateToTest);
    List<Meeting> test = new ArrayList<>();
    for ( Meeting meeting : mApiService.getMeetings()) {
      if ( dateToTest.equals(meeting.getDateBegin().toLocalDate()) ) {
        test.add(meeting);
      }
    } assertEquals(meetingFiltredByDate, test); // Valid if two list is Equal
  }

  @Test
  public void getFilteredMeeting() {
    generatedRandomMeeting();
    roomToTest = mRooms.get(0); // Contain the first object Room, use for test
    dateToTest = mApiService.getMeetings().get(0).getDateBegin().toLocalDate(); // Contain the LocalDate of the first Meeting
    List<Meeting> meetingsFiltredByRoomTest = mApiService.roomChooseToFilter(roomToTest);
    List<Meeting> meetingsFiltredByDateTest = new ArrayList<>();
    for (Meeting meeting : meetingsFiltredByRoomTest ) {
      if ( dateToTest.equals(meeting.getDateBegin().toLocalDate()) ) {
        meetingsFiltredByDateTest.add(meeting);
      }
    }
    List<Meeting> getFiltredMeeting = mApiService.getFilteredMeeting(roomToTest, dateToTest);

    assertEquals(getFiltredMeeting, meetingsFiltredByDateTest);
  }
}