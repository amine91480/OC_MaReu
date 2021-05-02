package com.amine.mareu.Service;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DummyMeetingApiServiceTest {

  private MeetingApiService mApiService;
  private List<Meeting> mMeetings;
  private List<Room> mRooms;

  @Before
  public void setupTest() { // Contain all variables used to passed Test
    mApiService = DI.getMeetingApiService(); // Contain all method write in Service/ApiService share with DI
    mRooms = DummyRoomGenerator.generateRoom(); // Contain the List of object Room
  }

  @After
  public void resetTheApiService() {
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

  public void generatedRandomMeeting() { // GENERATE 4 MEETING
    LocalDateTime time = LocalDateTime.of(2021, 4, 20, 9, 0, 0);
    Meeting meeting1 = new Meeting(0, time, time.plusHours(1), mRooms.get(0), "testMeeting1", "test91480@hotmail.fr"); // 2021-04-20T09:00 2021-04-20T10:00
    Meeting meeting2 = new Meeting(0, time.plusHours(2), time.plusHours(3), mRooms.get(0), "testMeeting2", "test91480@hotmail.fr"); // 2021-04-20T11:00 2021-04-20T12:00
    Meeting meeting3 = new Meeting(0, time.minusDays(1), time.minusDays(1).plusHours(1), mRooms.get(0), "testMeeting3", "test91480@hotmail.fr"); // 2021-04-19T09:00 2021-04-19T10:00
    Meeting meeting4 = new Meeting(0, time.minusDays(1).plusHours(2), time.minusDays(1).plusHours(3), mRooms.get(0), "testMeeting4", "test91480@hotmail.fr"); // 2021-04-19T11:00 2021-04-19T12:00
    mApiService.createMeeting(meeting1); mApiService.createMeeting(meeting2);
    mApiService.createMeeting(meeting3); mApiService.createMeeting(meeting4);
  }

  // TEST LIST
  @Test
  public void getMeetingsTestBeginWithMeetingIsEmpty() { // THIS APP IS A POC -> LIST OF MEETING IS EMPTY -> TEST
    mMeetings = mApiService.getMeetings(); System.out.println(mMeetings.size()); //
    System.out.println(mApiService.getMeetings().size()); //
    assertEquals(mMeetings.size(), DI.getMeetingApiService().getMeetings().size());
  }

  @Test
  public void getTheRoomsTest() { // RETURN THE LIST OF ROOM
    for ( Room room : mRooms ) {
      System.out.println(room.getName()); //
    } assertEquals(mRooms, DummyRoomGenerator.generateRoom());
  }

  @Test
  public void getTheRoomsListNameTest() { // RETURN THE LIST OF THE NAME OF THE ROOM
    List<String> mRoomName = new ArrayList<>(); for ( Room room : mRooms ) {
      System.out.println(room.getName()); //
      mRoomName.add(room.getName());
    } assertEquals(mRoomName, DI.getMeetingApiService().getListNameRooms());
  }
  // TEST LIST

  // TEST MEETING
  @Test
  public void createMeetingTest() { // CREAT THE MEETING
    Meeting testCreated = new Meeting(0, LocalDateTime.now(), LocalDateTime.now().plusHours(1), mRooms.get(0), "Test Creating", "test91480@hotmail.fr");
    mApiService.createMeeting(testCreated);
    mMeetings = mApiService.getMeetings(); // CREAT THE MEETING "testCreated"
    System.out.println(testCreated.isCompleted()); assertTrue(mMeetings.contains(testCreated));
  }

  @Test
  public void deleteMeetingTest() { // DELETE THE MEETING
    generatedRandomMeeting(); // CREAT 4 MEETING
    int size = mApiService.getMeetings().size() - 1; // GIVE THE SIZE OF THE LIST AND SUB 1 ON IT
    System.out.println(size); //
    Meeting meetingToDelete = mApiService.getMeetings().get(size); // meetingToDelete TAKE THE LAST OF THE LIST MEETING
    System.out.println(mApiService.getMeetings().size()); //
    mApiService.deleteMeeting(meetingToDelete); // DELETE THE LAST MEETING
    System.out.println(mApiService.getMeetings().size()); //
    assertEquals(3, mApiService.getMeetings().size());

  }

  @Test
  public void setClearListMeetingTest() { // DELETE THE ALL LIST OF MEETING
    generatedRandomMeeting(); // CREAT 4 MEETING
    System.out.println(mApiService.getMeetings().size()); //
    mApiService.setClearListMeeting(); // REMOVE ALL USER
    System.out.println(mApiService.getMeetings().size()); //
    assertEquals(0, mApiService.getMeetings().size());
  }

  @Test
  public void checkTheFuturReservationTest() { // CHECK IF WE CAN RESERVED A MEETING IF IT DIFFERENT OF A MEETING ALREADY CREATED
    generatedRandomMeeting(); // CREAT 4 MEETING
    // FOR TEST mApiService.getMeetings().forEach(meeting -> System.out.println(meeting.getDateBegin()+" "+meeting.getDateAfter()));
    Room roomTest = mRooms.get(0); // GET SALLE A
    LocalDateTime timeBeginTest = LocalDateTime.of(2021, 4, 19, 9, 0, 0);
    LocalDateTime timeFinishTest = timeBeginTest.plusHours(1); // timeFinishTest = 2021 , MONTH->4 , DAYS->20 , HOURS->10 , MINUTE->0, SECONDS->0
    // FIRST MEETING YEARS ->2021 , MONTH->4 , DAYS->20 , HOURS->9 , MINUTE->0, SECONDS->0
    // USE METHOD TO CHECK IF WE CAN ADD A MEETING AT THE SAME DATE OF OTHER MEETING // RETURN TRUE IF THE ROOM AND THE LOCAL DATE IS ALREADY TAKEN
    assertTrue(mApiService.checkTheFuturReservation(timeBeginTest, timeFinishTest, roomTest));
  }
  // TEST MEETING

  // TEST FILTRED
  @Test
  public void testRoomChooseToFilter() { // TEST -> CHECK IF METHOD FILTER ROOM RETURN THE GOOD LIST OF ROOM
    generatedRandomMeeting();// CREAT 4 MEETING
    // meetingFiltredByRoom taken all the Meeting with the first Room object
    Room roomToTest = mRooms.get(0); // GET SALLE A
    List<Meeting> meetingFiltredByRoom = mApiService.roomChooseToFilter(roomToTest); // GIVE THE LIST OF DATE
    List<Meeting> test = new ArrayList<>(); for ( Meeting meeting : mApiService.getMeetings() ) {
      if ( roomToTest.equals(meeting.getRoom()) ) {
        test.add(meeting);
      }
    } assertEquals(meetingFiltredByRoom, test); // Valid if two list is Equal
  }


  @Test
  public void testDateChooseToFilter() {  // TEST -> CHECK IF METHOD FILTER DATE RETURN THE GOOD LIST OF DATE
    generatedRandomMeeting();// CREAT 4 MEETING
    LocalDate dateToTest = mApiService.getMeetings().get(0).getDateBegin().toLocalDate(); // GET 2021-04-20
    List<Meeting> meetingFiltredByDate = mApiService.dateChooseToFilter(dateToTest); // GIVE THE LIST OF DATE
    List<Meeting> test = new ArrayList<>(); for ( Meeting meeting : mApiService.getMeetings() ) {
      if ( dateToTest.equals(meeting.getDateBegin().toLocalDate()) ) {
        test.add(meeting);
      }
    } assertEquals(meetingFiltredByDate, test); // Valid if two list is Equal
  }

  @Test
  public void getFilteredMeeting() {  // TEST -> CHECK IF METHOD FILTER ROOM & DATE RETURN THE GOOD LIST OF BOTH
    generatedRandomMeeting();// CREAT 4 MEETING
    Room roomToTest = mRooms.get(0); // GET SALLE A
    LocalDate dateToTest = mApiService.getMeetings().get(0).getDateBegin().toLocalDate(); // GET 2021-04-20
    List<Meeting> meetingsFiltredByRoomTest = mApiService.roomChooseToFilter(roomToTest);
    List<Meeting> meetingsFiltredByDateTest = new ArrayList<>();
    for ( Meeting meeting : meetingsFiltredByRoomTest ) {
      if ( dateToTest.equals(meeting.getDateBegin().toLocalDate()) ) {
        meetingsFiltredByDateTest.add(meeting);
      }
    } List<Meeting> getFiltredMeeting = mApiService.getFilteredMeeting(roomToTest, dateToTest);
    assertEquals(getFiltredMeeting, meetingsFiltredByDateTest);
  }
}