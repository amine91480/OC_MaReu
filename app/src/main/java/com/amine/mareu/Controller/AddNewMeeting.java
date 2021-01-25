package com.amine.mareu.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Build;
import android.os.Bundle;
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

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.*;

public class AddNewMeeting extends AppCompatActivity {

    private MeetingApiService mApiService;
    DateFormat DFormat = DateFormat.getDateInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_reunion);
        mApiService = DI.getMeetingApiService();
        receipData();

    }

    private void receipData(){
        Button mButton = (Button)findViewById(R.id.button);
        Button mBack = (Button)findViewById(R.id.backButton);

        TextView laDate = (TextView) findViewById(R.id.laDate);
        laDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursButton();
                dateButton();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                addMeeting();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addMeeting(){
        Integer size = mApiService.getMeetings().size() + 1;
        EditText mLocalisation = (EditText)findViewById(R.id.addLocalisation);
        EditText mSubject = (EditText)findViewById(R.id.addSubject);
        EditText mParticipant = (EditText)findViewById(R.id.addParticipant);
        TextView laDate = (TextView) findViewById(R.id.laDate);

        Meeting meeting = new Meeting(
                size,
                laDate.getText().toString(),
                mLocalisation.getText().toString(),
                mSubject.getText().toString(),
                mParticipant.getText().toString()
        );
        Toast.makeText(getApplicationContext(),meeting.toString(), LENGTH_LONG).show();
        systemeOut(meeting);
        Meeting meeting1 = new Meeting(
                size + 1, LocalTime.now().toString(),"Android","OpenClasserooms","Quelqu'un");
        mApiService.createMeeting(meeting);
        mApiService.createMeeting(meeting1);
    }

    private void dateButton(){
        Calendar calendar = Calendar.getInstance();
        Integer YEAR = calendar.get(Calendar.YEAR);
        Integer MONTH = calendar.get(Calendar.MONTH);
        Integer DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                TextView dateTextView = (TextView)findViewById(R.id.laDate);

                Calendar calendarDate = Calendar.getInstance();
                calendarDate.set(Calendar.YEAR,YEAR);
                calendarDate.set(Calendar.MONTH,MONTH);
                calendarDate.set(Calendar.YEAR,YEAR);

                CharSequence charSequence = android.text.format.DateFormat.format("dd-MM-yyyy",calendarDate);
                dateTextView.setText(charSequence);
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.show();
    }

    private  void hoursButton() {
        Calendar calendar = Calendar.getInstance();
        Integer HOUR = calendar.get(Calendar.HOUR);
        Integer MINUTE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                TextView timeTextView = (TextView)findViewById(R.id.heure);
                String timeString = hour+":"+minute;
                timeTextView.setText(timeString);
            }
        },HOUR,MINUTE, true);
        timePickerDialog.show();
    }
    private void systemeOut(Meeting meeting){
        System.out.println(meeting.getId());
        System.out.println(meeting.getDate());
        System.out.println(meeting.getLocation());
        System.out.println(meeting.getSubject());
        System.out.println(meeting.getParticipants());
        System.out.println(mApiService.getMeetings().size());

    }
}
