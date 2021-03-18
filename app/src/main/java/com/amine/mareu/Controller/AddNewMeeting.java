package com.amine.mareu.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.*;

public class AddNewMeeting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityAddNewReunionBinding binding; // BindingView to return the XML
    private MeetingApiService mApiService; // Service to import the method/logic

    // Element Model to created a NewInstance or Modify
    private List<Meeting> mMeetingList;
    private List<Room> mRoomList;
    private List<String> mRoomListName;
    private List<String> mParticipantList;

    // Element to Init the New Meeting//
    private Meeting mMeeting;
    private Room mRoom;
    private LocalDateTime mDateBegin, mDateFinish;
    private String mParticipants;

    // Element View
    private DatePickerDialog mDateSetListener;
    private TimePickerDialog mTimePickerDialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewReunionBinding.inflate(getLayoutInflater()); //ViewBinding
        View view = binding.getRoot();
        setContentView(view); // Return the View by BindingView

        setSupportActionBar(binding.toolbarNew); // Add the Support Tollbar -> I don't now if it's work

        mApiService = DI.getMeetingApiService(); // Give the Service to use Method
        mMeetingList = mApiService.getMeetings(); // ListOfMeeting
        mRoomList = DummyRoomGenerator.DUMMY_ROOM; // ListOfRoom
        mRoomListName = mApiService.getListNameRooms();
        mParticipantList = new ArrayList<>();
        receipData(); //Logic Work
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void receipData() {
        binding.addMeeting.setEnabled(false);
        chooseYourRoom(); // Spinner for choose the Room of the Meeting -> OK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chooseYourDate(); // Open Dialogue Alert to choose a Date -> OK but the Time not Working :/
        }
        chooseYourParticipant();
        binding.toolbarNew.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back)); // Insert the Drawable on the Toolbar
        binding.toolbarNew.setNavigationOnClickListener(new View.OnClickListener() { // Insert the method Previous Button on the Drawable
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        createNewMeeting();
    }

    //// ->  A decouvrir
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { // N'ai pas apeller
        mRoom = mRoomList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { // N'ai pas apeller
        mRoom = mRoomList.get(0);
    }
    //// <--  A decouvrir

    public void chooseYourRoom() { // Spinner for choose the Room of the Meeting -> OK
        mRoom = mRoomList.get(0);// Securité pour que la salle soit toujours séléctionner sur le première Item
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mRoomListName);
        binding.autoCompleteRoom.setAdapter(spinnerArrayAdapter);
        binding.autoCompleteRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRoom = mRoomList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
//// A decouvrir

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void chooseYourDate() {
        mDateBegin = LocalDateTime.now();
        int mYear = mDateBegin.getYear();
        int mMonth = mDateBegin.getMonthValue();
        int mDay = mDateBegin.getDayOfMonth();
        int mHour = mDateBegin.getHour();
        int mMinute = mDateBegin.getMinute();
        binding.info.setOnClickListener(onInfoClick(mYear, mMonth, mDay, mHour, mMinute));
    }

    private View.OnClickListener onInfoClick(int mYear, int mMonth, int mDay, int mHour, int mMinute) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                mDateSetListener = new DatePickerDialog(AddNewMeeting.this, onDateSet(mHour, mMinute), mYear, mMonth, mDay);
                mDateSetListener.show();
            }
        };
    }

    private DatePickerDialog.OnDateSetListener onDateSet(int mHour, int mMinute) {
        return new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mTimePickerDialog = new TimePickerDialog(AddNewMeeting.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("YYYY/MM/DD");
                        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
                        mDateBegin = LocalDateTime.of(year, month, dayOfMonth, hourOfDay, minute);
                        mDateFinish = mDateBegin.plusHours(1);
                        binding.date.setText(mDateBegin.format(formatterDate));
                        binding.time.setText(mDateBegin.format(formatTime));
                        checkTheReservation(mDateBegin, mDateFinish, mRoom);
                    }
                }, mHour, mMinute, true);
                mTimePickerDialog.show();
            }
        };
    }

    // Take the String insert in the EditText after click in the Drawable and must check if is a good email to insert the email entry in the string
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    private void chooseYourParticipant() {
        mParticipantList = new ArrayList<>();
        binding.participant.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            String email = "";
            //Préparation a effectuer une vérification des String participants via une expression régulière pour les mails !
            String patrn = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}(.[a-z]{2,3})+$|^$";

            email = binding.participant.getText().toString();
            String finalEmail = email;

            // Ici on prépare la Vus suivant l'enttréz de l'utilisateur via le bindind et MAtérial Design
      /*      binding.participant.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.labelParticipant.setError("L'adresse mail n'est pas valide...");
                    binding.labelParticipant.setErrorEnabled(true);
                    if (finalEmail.matches(patrn)) {
                        binding.labelParticipant.setErrorEnabled(false);
                    }
                }
            });*/

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (binding.participant.getRight() - binding.participant.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (email.matches(patrn)) {
                        mParticipantList.add(email);
                        binding.participant.setText("");
                        // Sert a afficher les adresse mail renseigner mais c'est pas très jolie, a améliorer !!
                        //binding.email.setVisibility(View.VISIBLE);
                        for (String s : mParticipantList) {
                            mParticipants += s + ", ";
                        }
                        //binding.email.setText(mParticipants);
                    }
                }
            }
            return false;
        });
    }

    public void checkTheReservation(LocalDateTime mDateBegin, LocalDateTime mDateFinish, Room mRoom) {
        if (mApiService.checkTheFuturReservation(mDateBegin, mDateFinish, mRoom)) {
            binding.time.setBackgroundColor(getResources().getColor(R.color.red));
            binding.addMeeting.setEnabled(false);  //Bouton désactiver
        }
        if (!mApiService.checkTheFuturReservation(mDateBegin, mDateFinish, mRoom)) {
            binding.time.setBackgroundColor(getResources().getColor(R.color.blueblue));
            Toast.makeText(getApplicationContext(), "libre", LENGTH_SHORT).show();
            binding.addMeeting.setEnabled(true);  //Bouton activé
        }
    }

    private void createNewMeeting() {
        binding.addMeeting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer id = mApiService.getMeetings().size() + 1; /* Génére un Id, prend la liste des Meeting et ajoute un */
                String subject = binding.subject.getText().toString(); /* -> Initilisation du Params */


                mMeeting = new Meeting(id, mDateBegin, mDateFinish, mRoom, subject, mParticipants); /* -> Création d'une nouvelle Instance de Meeting avec les Params récolter */
                Log.d("Toz", mMeeting.getId() + " " + mMeeting.getDateAfter() + " " + mMeeting.getDateAfter() + " " + mMeeting.getRoom() + " " + mMeeting.getParticipants());
                checkTheReservation(mDateBegin, mDateFinish, mRoom);

                mApiService.createMeeting(mMeeting);  //-> API qui va vérifier si c'est réserver et crée le Meeting
                if (mMeetingList.contains(mMeeting)) { //Si le nouveau Meeting et dans la liste des Meeting -> Redirige vers la RecyclerView
                    finish();
                } else {  //Sinon Affiche un Toast message comme quoi la salle et l'heure et déjà pris :D
                    Toast.makeText(getApplicationContext(), "This Place is reserved a this time, Can't you change the date please ?", LENGTH_SHORT).show();
                }
            }
        });
    }

}
