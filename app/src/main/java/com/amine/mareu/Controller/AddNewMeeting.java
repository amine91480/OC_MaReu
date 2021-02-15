package com.amine.mareu.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.R;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.ActivityAddNewReunionBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.*;


public class AddNewMeeting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityAddNewReunionBinding binding;
    private MeetingApiService mApiService;

    // Element View
    private String spinner;
    private DatePickerDialog mDateSetListener;
    private TimePickerDialog mTimePickerDialog;
    private Calendar mDateBegin, mDateFinish;
    private List<Meeting> meetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewBinding
        binding = ActivityAddNewReunionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mApiService = DI.getMeetingApiService();
        receipData();
    }

    private void receipData() {
        // Spinner for choose the Room of the Meeting -> OK
        spinnerOption();
        // Open Dialogue Alert to choose a Date -> OK but the Time not Working :/
        dialogDate();
        // Give data for Add a new Meeting -> OK
        addMeeting();
    }

    // Spinner for choose the Room of the Meeting -> OK /*/
    public void spinnerOption() {
        List<String> salle = new ArrayList<String>();
        salle.add(" Réunion A ");
        salle.add(" Réunion B ");
        salle.add(" Réunion C ");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, salle);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.salle.setAdapter(adapter);
        binding.salle.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = (String) parent.getItemAtPosition(position);
        spinner = selectedItem;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /**/ //

    private void dialogDate() {
        mDateBegin = Calendar.getInstance();
        mDateFinish = Calendar.getInstance();
        int mDay = mDateBegin.get(Calendar.DAY_OF_MONTH);
        int mMonth = mDateBegin.get(Calendar.MONTH);
        int mYear = mDateBegin.get(Calendar.YEAR);
        int mHour = mDateBegin.get(Calendar.HOUR);
        int mMinute = mDateBegin.get(Calendar.MINUTE);

        binding.info.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            public void onClick(View v) {
                mDateSetListener = new DatePickerDialog(AddNewMeeting.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mTimePickerDialog = new TimePickerDialog(AddNewMeeting.this, new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mDateBegin.set(year, month, dayOfMonth, hourOfDay, minute, 00);
                                mDateFinish.set(year, month, dayOfMonth, hourOfDay + 1, minute, 00);
                                binding.date.setText(DateFormat.getDateFormat(getApplicationContext()).format(mDateBegin.getTime()));
                                binding.time.setText(DateFormat.getTimeFormat(getApplicationContext()).format(mDateBegin.getTime()));
                                Log.d("TimePicker/verifiData", "Lunch method to find if the Meeting have reserved a the same time");
                            }
                        }, mHour, mMinute, false);
                        mTimePickerDialog.show();
                    }
                }, mYear, mMonth, mDay);
                mDateSetListener.show();
            }
        });
    }

    private void addMeeting() {
        binding.addMeeting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer size;
                size = mApiService.getMeetings().size();
                String participant, subject;

                SimpleDateFormat createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                Log.d("isReserved", createDate.format(mDateBegin.getTime()) + "// //" + createDate.format(mDateFinish.getTime()));
                Log.d("isReserved", mDateBegin.getTime() + "// //" + mDateFinish.getTime());

                participant = binding.participant.getText().toString();
                subject = binding.subject.getText().toString();

                Meeting meeting = new Meeting(size, mDateBegin.getTime(), mDateFinish.getTime(), spinner, subject, participant);
                mApiService.createMeeting(meeting);
                finish();
            }
        });
    }

}
