package com.amine.mareu.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.R;
import com.amine.mareu.Service.MeetingApiService;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;


public class AddNewMeeting extends AppCompatActivity {

    private MeetingApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_reunion);
        mApiService = DI.getMeetingApiService();
        receipData();
    }

    private void receipData() {
        Button addButton = (Button) findViewById(R.id.addMeeting);
        Button backButton = (Button) findViewById(R.id.backButton);
        DatePicker laDate = (DatePicker) findViewById(R.id.laDate);
        laDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursButton();
                dateButton();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMeeting();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addMeeting() {
        Integer size = mApiService.getMeetings().size() + 1;
        EditText mLocalisation = (EditText) findViewById(R.id.addLocalisation);
        EditText mSubject = (EditText) findViewById(R.id.addSubject);
        EditText mParticipant = (EditText) findViewById(R.id.addParticipant);
        DatePicker laDate = (DatePicker) findViewById(R.id.laDate);
        TimePicker lheure = (TimePicker) findViewById(R.id.heure);
        Date heure = (Time) new Time(lheure.getHour());
        Date date1 = (Date) new Date(laDate.getYear(),
                laDate.getDayOfMonth(),
                laDate.getMonth());

        System.out.println(heure + "////////////////////////////////////////////////////////////");
        System.out.println(date1 + "////////////////////////////////////////////////////////////");

        Meeting meeting = new Meeting(
                size,
                heure.toString(),
                mLocalisation.getText().toString(),
                mSubject.getText().toString(),
                mParticipant.getText().toString()
        );
        mApiService.createMeeting(meeting);
        finish();
    }

    private void dateButton() {
        Calendar calendar = Calendar.getInstance();
        Integer YEAR = calendar.get(Calendar.YEAR);
        Integer MONTH = calendar.get(Calendar.MONTH);
        Integer DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                Calendar calendarDate = Calendar.getInstance();
                calendarDate.set(Calendar.YEAR, YEAR);
                calendarDate.set(Calendar.MONTH, MONTH);
                calendarDate.set(Calendar.YEAR, YEAR);

                String dateString = (String) DateFormat.format("dd-MM-yyyy", calendarDate);
                System.out.println(dateString + "////////////////////////////////////////////////////////////");
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.show();
    }

    private void hoursButton() {
        Calendar calendar = Calendar.getInstance();
        Integer HOUR = calendar.get(Calendar.HOUR);
        Integer MINUTE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String timeString = (String) DateFormat.format("hh:mm", calendar);
                System.out.println(timeString + "////////////////////////////////////////////////////////////");
            }
        }, HOUR, MINUTE, true);
        timePickerDialog.show();
    }
}
