package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyMeetingGenerator {


    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(1, new Date(), new Date(), "Réunion A", "Android", "amine91480@hotmail.fr"),
            new Meeting(2, new Date(), new Date(), "Réunion B", "JAVA", "flegard91480@hotmail.fr"),
            new Meeting(3, new Date(), new Date(), "Réunion C", "Kotlin", "valentin91, tuto45@hotmail.fr"),
            new Meeting(4, new Date(), new Date(), "Réunion A", "Front", "Caroline@hotmail.fr")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }
}
