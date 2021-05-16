package com.amine.mareu.Service;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Tag("TestService")
@DisplayName("This is the Test of Service")

public class DummyMeetingApiServiceTest {

  private MeetingApiService mApiService;
  private List<Meeting> mMeetings;
  private List<Room> mRooms;

  @BeforeEach
  @DisplayName("TAKE A INSTANCE OF SERVICE AND THE ROOM GENERATOR TO SETUP THE TEST -> OK")
  public void setupTest() {
    mApiService = DI.getMeetingApiService(); // Contain all method write in Service/ApiService share with DI
    mRooms = DummyRoomGenerator.generateRoom(); // Contain the List of object Room
  }

  @AfterEach
  @DisplayName("DELETE THE INSTANCE OF SERVICE AFTER EACH -> OK")
  public void resetTheApiService() {
    mApiService.setClearListMeeting(); // RETURN MEETING LIST TO EMPTY
  }

  @DisplayName(" CREATED 4 MEETING USE FOR TEST -> OK")
  public void generatedRandomMeeting() { // GENERATE 4 MEETING
    LocalDateTime time = LocalDateTime.of(2021, 4, 20, 9, 0, 0);
    Meeting meeting1 = new Meeting(0, time, time.plusHours(1), mRooms.get(0), "testMeeting1", "test91480@hotmail.fr"); // 2021-04-20T09:00 2021-04-20T10:00
    Meeting meeting2 = new Meeting(0, time.plusHours(2), time.plusHours(3), mRooms.get(0), "testMeeting2", "test91480@hotmail.fr"); // 2021-04-20T11:00 2021-04-20T12:00
    Meeting meeting3 = new Meeting(0, time.minusDays(1), time.minusDays(1).plusHours(1), mRooms.get(0), "testMeeting3", "test91480@hotmail.fr"); // 2021-04-19T09:00 2021-04-19T10:00
    Meeting meeting4 = new Meeting(0, time.minusDays(1).plusHours(2), time.minusDays(1).plusHours(3), mRooms.get(0), "testMeeting4", "test91480@hotmail.fr"); // 2021-04-19T11:00 2021-04-19T12:00
    mApiService.createMeeting(meeting1);
    mApiService.createMeeting(meeting2);
    mApiService.createMeeting(meeting3);
    mApiService.createMeeting(meeting4);
  }

  @Tag("ListOfDataTest")
  @Test
  @DisplayName("1 CHECK IF THE FIRST CALL OF SERVICE IS A EMPTY INSTANCE -> OK")
  public void getMeetingsTestBeginWithMeetingIsEmpty() { // THIS APP IS A POC -> LIST OF MEETING IS EMPTY -> TEST
    mMeetings = mApiService.getMeetings();
    System.out.println(mMeetings.size()); //
    System.out.println(mApiService.getMeetings().size()); //
    assertEquals(mMeetings.size(), DI.getMeetingApiService().getMeetings().size());
  }

  @Test
  @DisplayName("2 CHECK IF THE METHOD RETURN THE LIST OF ROOM -> OK")
  public void getTheRoomsTest() {
    for ( Room room : mRooms ) {
      System.out.println(room.getName()); //
    }
    assertEquals(mRooms, DummyRoomGenerator.generateRoom());
  }

  @Test
  @DisplayName("3 CHECK IF THE METHOD RETURN THE LIST OF THE ROOM NAME -> OK")
  public void getTheRoomsListNameTest() {
    List<String> mRoomName = new ArrayList<>();
    for ( Room room : mRooms ) {
      System.out.println(room.getName()); //
      mRoomName.add(room.getName());
    }
    assertEquals(mRoomName, DI.getMeetingApiService().getListNameRooms());
  }
  // TEST LIST

  // TEST MEETING
  @Test
  @DisplayName("4 CREATE A NEW MEETING TEST -> OK")
  public void createMeetingTest() {
    Meeting testCreated = new Meeting(0, LocalDateTime.now(), LocalDateTime.now().plusHours(1), mRooms.get(0), "Test Creating", "test91480@hotmail.fr");
    mApiService.createMeeting(testCreated);
    mMeetings = mApiService.getMeetings(); // CREAT THE MEETING "testCreated"
    System.out.println(testCreated.isCompleted());
    assertTrue(mMeetings.contains(testCreated));
  }

  @Test
  @DisplayName("5 CREATE 4 MEETING AND DELETE ONE MEETING -> OK")
  public void deleteMeetingTest() {
    generatedRandomMeeting(); // CREAT 4 MEETING
    int size = mApiService.getMeetings().size() - 1; // GIVE THE SIZE OF THE LIST AND SUB 1 ON IT
    Meeting meetingToDelete = mApiService.getMeetings().get(size); // meetingToDelete TAKE THE LAST OF THE LIST MEETING
    mApiService.deleteMeeting(meetingToDelete); // DELETE THE LAST MEETING
    System.out.println("NUMBER OF MEETING -> 4 BEFORE -> " + mApiService.getMeetings().size() + " BEFORE");
    assertEquals(3, mApiService.getMeetings().size());
  }

  @Test
  @DisplayName("6 CLEAR THE LIST OF MEETING -> OK")
  public void setClearListMeetingTest() {
    generatedRandomMeeting(); // CREAT 4 MEETING
    mApiService.setClearListMeeting(); // REMOVE ALL USER
    assertEquals(0, mApiService.getMeetings().size());
  }

  @Test
  @DisplayName("7  CHECK IF WE CAN RESERVED A MEETING IF IT DIFFERENT OF A MEETING ALREADY CREATED -> OK")
  public void checkTheFuturReservationTest() {
    generatedRandomMeeting(); // CREAT 4 MEETING
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
  @DisplayName("8 CHECK IF METHOD FILTER ROOM AND RETURN THE GOOD LIST OF ROOM -> OK")
  public void testRoomChooseToFilter() {
    generatedRandomMeeting();// CREAT 4 MEETING
    // meetingFiltredByRoom taken all the Meeting with the first Room object
    Room roomToTest = mRooms.get(0); // GET SALLE A
    List<Meeting> meetingFiltredByRoom = mApiService.roomChooseToFilter(roomToTest); // GIVE THE LIST OF DATE
    List<Meeting> test = new ArrayList<>();
    for ( Meeting meeting : mApiService.getMeetings() ) {
      if ( roomToTest.equals(meeting.getRoom()) ) {
        test.add(meeting);
      }
    }
    assertEquals(meetingFiltredByRoom, test); // Valid if two list is Equal
  }


  @Test
  @DisplayName("9 CHECK IF METHOD FILTER DATE RETURN THE GOOD LIST OF DATE -> OK")
  public void testDateChooseToFilter() {
    generatedRandomMeeting();// CREAT 4 MEETING
    LocalDate dateToTest = mApiService.getMeetings().get(0).getDateBegin().toLocalDate(); // GET 2021-04-20
    List<Meeting> meetingFiltredByDate = mApiService.dateChooseToFilter(dateToTest); // GIVE THE LIST OF DATE
    List<Meeting> test = new ArrayList<>();
    for ( Meeting meeting : mApiService.getMeetings() ) {
      if ( dateToTest.equals(meeting.getDateBegin().toLocalDate()) ) {
        test.add(meeting);
      }
    }
    assertEquals(meetingFiltredByDate, test); // Valid if two list is Equal
  }

  @Test
  @DisplayName("10 CHECK IF METHOD FILTER ROOM & DATE RETURN THE GOOD LIST OF BOTH -> OK")
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
    }
    List<Meeting> getFiltredMeeting = mApiService.getFilteredMeeting(roomToTest, dateToTest);
    assertEquals(getFiltredMeeting, meetingsFiltredByDateTest);
  }
}