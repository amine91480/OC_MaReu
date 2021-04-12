package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.amine.mareu.Service.DummyRoomGenerator.*;

public class DummyMeetingGenerator {

  public static List<String> getRandomEmail() {
    List<String> randEmail = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      randEmail.add("amine91480@hotmail.fr");
      randEmail.add("flegard91@hotmail.fr");
      randEmail.add("valentin@gmail.com");
      randEmail.add("test@test.com");
    }
    return randEmail;
  }

  public static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  public static ArrayList<LocalDateTime> getRandomDate() {
    ArrayList<LocalDateTime> randomDate = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      randomDate.add(LocalDateTime.of(2021, 2, 16, getRandomNumber(7, 16), 0, 0));
    }
    for (int i = 0; i < 3; i++) {
      randomDate.add(LocalDateTime.of(2021, 2, 17, getRandomNumber(7, 16), 0, 0));
    }
    return randomDate;
  }

  public static List<Meeting> getDummyMeeting() {
    List<Meeting> listMeetingTest = new ArrayList<>();
    LocalDateTime date, datePlusOne;
    for (int i = 0; i < getRandomDate().size(); i++) {
      date = getRandomDate().get(i);
      datePlusOne = date.plusHours(1);
      listMeetingTest.add(new Meeting(i, date, datePlusOne, generateRoom().get(getRandomNumber(0, generateRoom().size())), "Android", getRandomEmail().get(getRandomNumber(0, getRandomEmail().size()))));
    }
    return listMeetingTest;
  }
}