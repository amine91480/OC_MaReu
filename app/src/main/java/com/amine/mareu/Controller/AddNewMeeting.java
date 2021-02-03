package com.amine.mareu.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddNewMeeting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityAddNewReunionBinding binding;
    private MeetingApiService mApiService;
    private String spinner;

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
        spinnerOption();

        binding.addMeeting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addMeeting();
                }
            }
        );
    }

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
        Toast.makeText(AddNewMeeting.this, "Value choose" + selectedItem, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void addMeeting() {
        Integer size;
        size = mApiService.getMeetings().size();
        String participant, subject;


        participant = binding.participant.getText().toString();
        subject = binding.subject.getText().toString();
        Date date = new Date();
        Meeting meeting = new Meeting(size, date.toString(), spinner, subject, participant );
        mApiService.createMeeting(meeting);
        finish();
    }

}
