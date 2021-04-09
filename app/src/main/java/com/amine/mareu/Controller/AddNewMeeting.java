package com.amine.mareu.Controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;

public class AddNewMeeting extends AppCompatActivity {

    private ActivityAddNewReunionBinding binding; // BindingView to return the XML and Element View
    private DatePickerDialog mDateSetListener;
    private TimePickerDialog mTimePickerDialog;

    private MeetingApiService mApiService; // Service to import the method/logic

    // Model Object to created a NewInstance or Modify
    private List<Meeting> mMeetingList; // Pas utilie pour l'instant
    private List<Room> mRoomList;
    private List<String> mParticipantList; // Contain all Email insert on the InputText

    // Element to Init the New Meeting//
    private Room mRoom; // Room choose by the User
    private LocalDateTime mDateBegin, mDateFinish; // Date/Time choose by the User
    private String mParticipants; // All Email insert by the User split by 's'

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewReunionBinding.inflate(getLayoutInflater()); //ViewBinding
        View view = binding.getRoot();
        setContentView(view); // Return the View by BindingView

        setSupportActionBar(binding.toolbarNew); // Add the Support Tollbar -> I don't now if it's work

        mApiService = DI.getMeetingApiService(); // Give the Service to use Method

        // TODO -> Ici on va récupérer le getExtra envoyer par Main et le comparé à la List dans le Service pour voir si il sont identique
        mMeetingList = mApiService.getMeetings();
        // On Récupère la List envoyer par Main ->
        List<Meeting> mMeetingListToMain = getIntent().getParcelableArrayListExtra("meetingList");

        if (mMeetingList.size() != mMeetingListToMain.size()) { //Si elle est identique c'est cool c'est qu'il n'y a pas eu de rotation de l'écran et tout :D
            // Ici on va supprimer les Meeting dans le Service puis ajouter ceux qui on était envoyer par Main
            // Clear la liste présente dans le Service
            mApiService.setClearListMeeting();
            // Ajouter les Meeting envoyer par Main a cette AActivité dans le Service
            mMeetingList.forEach(meeting -> mApiService.createMeeting(meeting));
        }
        // <----------------------------

        mRoomList = DummyRoomGenerator.DUMMY_ROOM; // ListOfRoom
        receiveData(); //Logic Work
    }

    private void receiveData() {
        binding.addMeeting.setEnabled(false);
        chooseYourRoom(); // Spinner for choose the Room of the Meeting -> OK
        chooseYourDate(); // Open Dialogue Alert to choose a Date -> OK but the Time not Working :/
        chooseYourParticipant();
        binding.toolbarNew.setNavigationIcon(R.drawable.ic_baseline_arrow_back); // Insert the Drawable on the Toolbar
        // Insert the method Previous Button on the Drawable
        binding.toolbarNew.setNavigationOnClickListener(v -> onBackPressed());
        createNewMeeting();
    }

    public void chooseYourRoom() { // Spinner for choose the Room of the Meeting -> OK
        mRoom = mRoomList.get(0);// Securité pour que la salle soit toujours séléctionner sur le première Item
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mApiService.getListNameRooms());
        binding.autoCompleteRoom.setAdapter(spinnerArrayAdapter);

        binding.autoCompleteRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRoom = mRoomList.get(position); // Take the Room Choose in the Spinner with the position
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mRoom = mRoomList.get(0); // Choose the First Room Object if the User d'ont choose a Room iun a Spinner
            }
        });
    }

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
        return v -> {
            mDateSetListener = new DatePickerDialog(AddNewMeeting.this, onDateSet(mHour, mMinute), mYear, mMonth, mDay);
            mDateSetListener.show();
        };
    }

    private DatePickerDialog.OnDateSetListener onDateSet(int mHour, int mMinute) {
        return (view, year, month, dayOfMonth) -> {
            mTimePickerDialog = new TimePickerDialog(AddNewMeeting.this, (view1, hourOfDay, minute) -> {
                DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
                mDateBegin = LocalDateTime.of(year, month, dayOfMonth, hourOfDay, minute);
                mDateFinish = mDateBegin.plusHours(1);
                binding.date.setText(mDateBegin.format(formatterDate));
                binding.time.setText(mDateBegin.format(formatTime));
                checkTheReservation(mDateBegin, mDateFinish, mRoom);
            }, mHour, mMinute, true);
            mTimePickerDialog.show();
        };
    }

    // Take the String insert in the EditText after click in the Drawable and must check if is a good email to insert the email entry in the string
    @SuppressLint({"ClickableViewAccessibility", "NewApi"})
    private void chooseYourParticipant() {
        mParticipantList = new ArrayList<>();
        Objects.requireNonNull(binding.participant).setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            String email;
            //Préparation a effectuer une vérification des String participants via une expression régulière pour les mails !
            String patrn = "^([\\w-.]+){1,64}@([\\w&&[^_]]+){2,255}(.[a-z]{2,3})+$|^$";

            email = Objects.requireNonNull(binding.participant.getText()).toString(); // Require not null
            //String finalEmail = email;

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
                        mParticipants = String.join("", mParticipantList); // A verifier !
                        //for (String s : mParticipantList) {
                        //    mParticipants += s + ", ";
                        //}
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
        } else {
            binding.time.setBackgroundColor(getResources().getColor(R.color.blueblue));
            Toast.makeText(getApplicationContext(), "libre", LENGTH_SHORT).show();
            binding.addMeeting.setEnabled(true);  //Bouton activé
        }
    }

    private void createNewMeeting() {
        binding.addMeeting.setOnClickListener(v -> {
            // -> Take the last Id of Meeting and add 1 for the new Meeting
            int id;
            if (mApiService.getMeetings().isEmpty()) { // A tester si sa veux dire que la list est vide
                id = 0;
            } else {
                id = mApiService.getMeetings().size() + 1;
            }
            // -> Take the subject of the Meeting, can't be null assignement
            String subject = Objects.requireNonNull(binding.subject.getText()).toString();

            // TODO -> Ici On va vérifier si les attributs de Meeting sont bien présent puis si c'est le cas on envoie le nouveau Meeting avec ces attributs a Main via setResult
            if (new Meeting(id, mDateBegin, mDateFinish, mRoom, subject, mParticipants).isCompleted()) {
                Intent intent = new Intent();
                intent.putExtra("newMeeting", new Meeting(id, mDateBegin, mDateFinish, mRoom, subject, mParticipants));
                setResult(1, intent);
                finish();
            } else {
                // TODO Trouver une gestion des cas si tout n'est pas correctement si il manque des attribut !!
                System.out.println("Toz");
            }
        });
    }

}
