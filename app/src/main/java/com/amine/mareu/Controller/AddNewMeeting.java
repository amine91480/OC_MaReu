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
    private String room;
    private DatePickerDialog mDateSetListener;
    private TimePickerDialog mTimePickerDialog;
    private Calendar mDateBegin, mDateFinish;
    private List<Meeting> mMeetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewBinding
        binding = ActivityAddNewReunionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mApiService = DI.getMeetingApiService();
        mMeetings = mApiService.getMeetings();
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
        room = selectedItem;
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
                                /* Vérifie si la HEURE DATE et Salle sont déjà réserver et bloque le bouton Crée*/
                                isReserved(mDateBegin, mDateFinish);
                            }
                        }, mHour, mMinute, true);
                        mTimePickerDialog.show();
                    }
                }, mYear, mMonth, mDay);
                mDateSetListener.show();
            }
        });
    }
    /*Methode lancer quand l'utilisateur séléctionne une Date et Heure -> elle bloque le bouton Crée quand la salle et la Date/Heure est déjà réserver !*/
    private void isReserved(Calendar debut, Calendar fin) {
        Boolean isReserved = false;

        binding.addMeeting.setEnabled(false); /* Block the Button Create Meeting*/

        for (Meeting meet : mMeetings) { /* Loop sur tout les Meeting existant */
            if (room.equals(meet.getRoom())) { /* Vérifie si la salle et la même */
                /*Log.d("isReserve/Check", "Même salle !" + meet.getDateBegin() + " " + debut.getTime() + "// " + meet.getDateAfter() + " " + fin.getTime());*/
                if ((debut.getTime().after(meet.getDateBegin())) && (debut.getTime().before(meet.getDateAfter()))
                        || (fin.getTime().after(meet.getDateBegin())) && (fin.getTime().before(meet.getDateAfter()))) { /* Vérifie si l'heure entre dans l'intervalle */
                    isReserved = true;
                    /*Log.d("isReserve/Check", "Impossible");*/
                    Toast.makeText(getApplicationContext(), "Dejà réserver", LENGTH_SHORT).show();
                    binding.addMeeting.setEnabled(true); /* Bouton devient clicable*/
                    break;
                }
            }
        }
    }

    /*Method to Create a New Meeting with the Data(Room,Date/Time,Subject,Participant) */
    private void addMeeting() {
        binding.addMeeting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer size = mApiService.getMeetings().size(); /* Génére un Id */
                String participant, subject; /* -> Params utile à la création d'une réunion */

                participant = binding.participant.getText().toString();  /* -> Initilisation du Params */
                subject = binding.subject.getText().toString(); /* -> Initilisation du Params */

                SimpleDateFormat createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); /* Sert à rien pour l'instant */

                Meeting meeting = new Meeting(size, mDateBegin.getTime(), mDateFinish.getTime(), room, subject, participant); /* -> Création d'une nouvelle Instance de Meeting avec les Params récolter */

                mApiService.createMeeting(meeting); /* -> API qui va vérifier si c'est réserver et crée le Meeting*/

                if (mMeetings.contains(meeting)) { /*Si le nouveau Meeting et dans la liste des Meeting -> Redirige vers la RecyclerView*/
                    finish();
                } else { /* Sinon Affiche un Toast message comme quoi la salle et l'heure et déjà pris :D*/
                    Toast.makeText(getApplicationContext(), "This Place is reserved a this time, Can't you change the date please ?", LENGTH_SHORT).show();
                }
            }
        });
    }
}
