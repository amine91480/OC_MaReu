package com.amine.mareu.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;
import com.amine.mareu.R;
import com.amine.mareu.Service.DummyRoomGenerator;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.ActivityAddNewReunionBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.*;


public class AddNewMeeting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityAddNewReunionBinding binding; // BindingView to return the XML
    private MeetingApiService mApiService; // Service to import the method/logic

    // Element Model to created a NewInstance or Modify
    private List<Meeting> mMeetingList;
    private List<Room> mRoomList;
    private Meeting mMeeting;
    private Room mRoom;

    // Element View
    private DatePickerDialog mDateSetListener;
    private TimePickerDialog mTimePickerDialog;
    private Calendar mDateBegin, mDateFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewReunionBinding.inflate(getLayoutInflater()); //ViewBinding
        View view = binding.getRoot();
        setContentView(view); // Return the View by BindingView

        setSupportActionBar(binding.toolbarNew); // Add the Support Tollbar -> I don't now if it's work

        mApiService = DI.getMeetingApiService();
        mMeetingList = mApiService.getMeetings();
        mRoomList = DummyRoomGenerator.DUMMY_ROOM;
        receipData(); //Logic Work
    }


    private void receipData() {
        spinnerOption(); // Spinner for choose the Room of the Meeting -> OK
        dialogDate(); // Open Dialogue Alert to choose a Date -> OK but the Time not Working :/
        binding.toolbarNew.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24_add)); // Insert the Drawable on the Toolbar
        binding.toolbarNew.setNavigationOnClickListener(new View.OnClickListener() { // Insert the method Previous Button on the Drawable
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void spinnerOption() { // Spinner for choose the Room of the Meeting -> OK /*/
        ArrayAdapter<Room> adapter = new ArrayAdapter<Room>(this, android.R.layout.simple_spinner_item, mRoomList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.salle.setAdapter(adapter);
        binding.salle.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*String str = (String) parent.getItemAtPosition(position);*/
        /*Toast.makeText(getApplicationContext(), str+"//"+position, Toast.LENGTH_LONG).show();*/
        mRoom = mRoomList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Trouver un truck a mettre ici
    }

    private void dialogDate() {
        binding.addMeeting.setEnabled(false);
        mDateBegin = Calendar.getInstance();
        mDateFinish = Calendar.getInstance();
        int mDay = mDateBegin.get(Calendar.DAY_OF_MONTH);
        int mMonth = mDateBegin.get(Calendar.MONTH);
        int mYear = mDateBegin.get(Calendar.YEAR);
        int mHour = mDateBegin.get(Calendar.HOUR);
        int mMinute = mDateBegin.get(Calendar.MINUTE);
        binding.info.setOnClickListener(new View.OnClickListener() {
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
                                newMeetingData(); // Rend les éléments propre pour crée une instance de l'objet Meeting
                            }
                        }, mHour, mMinute, true);
                        mTimePickerDialog.show();
                    }
                }, mYear, mMonth, mDay);
                mDateSetListener.show();
            }
        });
    }

    private void newMeetingData() {
        Integer id = mApiService.getMeetings().size(); /* Génére un Id */
        String participant, subject; /* -> Params utile à la création d'une réunion */

        participant = binding.participant.getText().toString();  /* -> Initilisation du Params */
        subject = binding.subject.getText().toString(); /* -> Initilisation du Params */

        SimpleDateFormat createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); /* Sert à rien pour l'instant */

        mMeeting = new Meeting(id, mDateBegin.getTime(), mDateFinish.getTime(), mRoom, subject, participant); /* -> Création d'une nouvelle Instance de Meeting avec les Params récolter */
        checkReservation(mMeeting);

        addMeeting();
        //String patrn = "^([a-zA-Z0-9]+(([\.\-\_]?[a-zA-Z0-9]+)+)?)\@(([a-zA-Z0-9]+[\.\-\_])+[a-zA-Z]{2,4})$"; Préparation a effectuer une vérification des String participants via une expression régulière pour les mails !
    }

    public void checkReservation(Meeting meeting) {
        if (mApiService.isReserved(meeting) == true) {
            binding.addMeeting.setEnabled(false); /* Bouton désactiver*/
        }
        if (mApiService.isReserved(meeting) == false) {
            Toast.makeText(getApplicationContext(), "libre", LENGTH_SHORT).show();
            binding.addMeeting.setEnabled(true); /* Bouton activé*/
        }
    }

    private void addMeeting() { /*Method to Create a New Meeting with the Data(Room,Date/Time,Subject,Participant) */
        binding.addMeeting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mApiService.createMeeting(mMeeting); /* -> API qui va vérifier si c'est réserver et crée le Meeting*/

                if (mMeetingList.contains(mMeeting)) { /*Si le nouveau Meeting et dans la liste des Meeting -> Redirige vers la RecyclerView*/
                    finish();
                } else { /* Sinon Affiche un Toast message comme quoi la salle et l'heure et déjà pris :D*/
                    Toast.makeText(getApplicationContext(), "This Place is reserved a this time, Can't you change the date please ?", LENGTH_SHORT).show();
                }
            }
        });
    }
}
