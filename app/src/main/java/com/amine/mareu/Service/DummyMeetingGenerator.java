package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(1, "16:00", "Réunion A", "Android", "amine91480@hotmail.fr"),
            new Meeting(2,"14:00", "Réunion B", "JAVA", "flegard91480@hotmail.fr"),
            new Meeting(3, "12:00", "Réunion C", "Kotlin", "valentin91, tuto45@hotmail.fr"),
            new Meeting(4, "10:00", "Réunion A", "Front", "Caroline@hotmail.fr"),
            new Meeting(5, "09:00", "Réunion B", "API", "jack@hotmail.fr"),
            new Meeting(6, "08:00", "Réunion C", "RH", "Fred@hotmail.fr")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }
}
