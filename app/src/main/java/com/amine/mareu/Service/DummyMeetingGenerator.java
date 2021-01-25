package com.amine.mareu.Service;

import android.os.Build;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.amine.mareu.Model.Meeting;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
        new Meeting(1, LocalTime.now().toString(),"Tatouine","Android","amine91480@hotmail.fr"),
        new Meeting(2, LocalTime.now().toString(),"Nova","JAVA","flegard91480@hotmail.fr"),
        new Meeting(3, LocalTime.now().toString(),"Vegeta","Kotlin","valentin91, tuto45@hotmail.fr"),
        new Meeting(4, LocalTime.now().toString(),"Kuroro-Montaine","Front","Caroline@hotmail.fr"),
        new Meeting(5, LocalTime.now().toString(),"Kiri","API","jack@hotmail.fr"),
        new Meeting(6, LocalTime.now().toString(),"Barbacha","RH","Fred@hotmail.fr"),
        new Meeting(7, LocalTime.now().toString(),"Marseille","Alterance","Karen@hotmail.fr")
    );

    static List<Meeting> generateMeetings() {return new ArrayList<>(DUMMY_MEETING); }
}
