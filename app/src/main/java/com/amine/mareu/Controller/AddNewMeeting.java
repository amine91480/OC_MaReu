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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.widget.Toast.*;


public class AddNewMeeting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityAddNewReunionBinding binding;
    private MeetingApiService mApiService;

    // Element View
    private String spinner;
    private DatePickerDialog mDateSetListener;
    private TimePickerDialog mTimePickerDialog;
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
        Calendar date = Calendar.getInstance();
        int mDay = date.get(Calendar.DAY_OF_MONTH);
        int mMonth = date.get(Calendar.MONTH);
        int mYear = date.get(Calendar.YEAR);
        int mHour = date.get(Calendar.HOUR);
        int mMinute = date.get(Calendar.MINUTE);

        binding.info.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            public void onClick(View v) {
                mDateSetListener = new DatePickerDialog(AddNewMeeting.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mTimePickerDialog = new TimePickerDialog(AddNewMeeting.this, new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                date.set(Calendar.MINUTE, minute);
                                date.set(year, month, dayOfMonth);
                                binding.date.setText(DateFormat.getDateFormat(getApplicationContext()).format(date.getTime()));
                                binding.time.setText(DateFormat.getTimeFormat(getApplicationContext()).format(date.getTime()));
                                /*verifyData();*/
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
                String participant, subject, date;

                participant = binding.participant.getText().toString();
                subject = binding.subject.getText().toString();
                date = binding.time.getText().toString();

                Meeting meeting = new Meeting(size, date, spinner, subject, participant);
                makeText(getApplicationContext(), "La date et heure des autres meeting sont different", LENGTH_SHORT).show();
                mApiService.createMeeting(meeting);
                finish();
            }
        });
    }

    private boolean verifyData() {
        boolean isOK = false;
        Integer id = 0;
        Log.d("isReserved/verifiData", spinner.length() + " " + binding.time.getText().length());
        meetings = mApiService.getMeetings();
        for (id = 0; id < meetings.size(); id++) {
            if (meetings.get(id).getDate().equals(binding.time.getText()) || meetings.get(id).getLocation().equals(spinner)) {
                Toast.makeText(this, "This Date and Time is reserved", LENGTH_SHORT).show();
                Log.d("isReserved/verifiData", "Trouver !!" + meetings.get(id).getId() + " BD: " + meetings.get(id).getDate() + " New: " + binding.time.getText());
                isOK = true;
            } else {
                Log.d("isReserved/verifiData", "Non sa pue  ID " + meetings.get(id).getId() + " BD: " + meetings.get(id).getDate() + " New: " + binding.time.getText());
            }
        }
        return isOK;
    }

}
