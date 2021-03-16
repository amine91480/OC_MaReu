package com.amine.mareu.Service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.amine.mareu.Model.Meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.amine.mareu.Service.DummyRoomGenerator.*;

public class DummyMeetingGenerator {

    public static List<String> getRandomEmail() {
        ArrayList<String> randEmail = new ArrayList<String>();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<LocalDateTime> getRandomDate() {
        ArrayList<LocalDateTime> randDate = new ArrayList<LocalDateTime>();
        for (int i = 0; i < 5; i++) {
            randDate.add(LocalDateTime.of(2021, 03, 15, getRandomNumber(07, 16), 00, 00));
        }
        return randDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<Meeting> getDummyMeeting() {
        Integer i = 0;
        List<Meeting> newMeetingTest = new ArrayList<Meeting>();

        for (i = 0; i < getRandomDate().size(); i++) {
            newMeetingTest.add(new Meeting(i, getRandomDate().get(i), getRandomDate().get(i).plusHours(1), generateRoom().get(getRandomNumber(0, generateRoom().size())), "Android", getRandomEmail().get(getRandomNumber(0, getRandomEmail().size()))));
        }
        return newMeetingTest;
    }
}