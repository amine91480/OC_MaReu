package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.amine.mareu.Service.DummyRoomGenerator.*;

public class DummyMeetingGenerator {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static ArrayList<Date> getRandomDate() {
        ArrayList<Date> randDate = new ArrayList<Date>();
        randDate.add(new Date(2021, 00, 01, getRandomNumber(07, 19), 00, 00));
        randDate.add(new Date(2021, 00, 01, getRandomNumber(07, 19), 00, 00));
        randDate.add(new Date(2021, 00, 01, getRandomNumber(07, 19), 00, 00));
        randDate.add(new Date(2021, 00, 01, getRandomNumber(07, 19), 00, 00));
        return randDate;
    }

    public static List<Meeting> getDummyMeeting() {
        Integer i = 0;
        List<Meeting> test = new ArrayList<Meeting>();

        for (i = 0; i < getRandomDate().size(); i++) {
            Date theDate = getRandomDate().get(i);
            long theDateFinish = theDate.getTime() + TimeUnit.HOURS.toMillis(1);
            test.add(new Meeting(1, theDate, new Date(theDateFinish), generateRoom().get(getRandomNumber(0, generateRoom().size())), "Android", "flegard91480@hotmail.fr"));
            System.out.println(test.get(i).getDateBegin() + "YESSSSSSSSS" + test.get(i).getDateAfter());
        }
        return test;
    }
}