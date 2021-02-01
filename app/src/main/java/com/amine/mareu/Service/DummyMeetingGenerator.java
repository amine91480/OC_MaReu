package com.amine.mareu.Service;

import com.amine.mareu.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(1, new Date().toString(), "Réunion A", "Android", "amine91480@hotmail.fr"),
            new Meeting(2, new Date().toString(), "Réunion B", "JAVA", "flegard91480@hotmail.fr"),
            new Meeting(3, new Date().toString(), "Réunion C", "Kotlin", "valentin91, tuto45@hotmail.fr"),
            new Meeting(4, new Date().toString(), "Réunion A", "Front", "Caroline@hotmail.fr"),
            new Meeting(5, new Date().toString(), "Réunion B", "API", "jack@hotmail.fr"),
            new Meeting(6, new Date().toString(), "Réunion C", "RH", "Fred@hotmail.fr"),
            new Meeting(7, new Date().toString(), "Marseille", "Alterance", "Karen@hotmail.fr")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }
}
