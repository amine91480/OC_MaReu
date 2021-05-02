package com.amine.mareu.Controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;
import com.amine.mareu.R;
import com.amine.mareu.Service.DummyRoomGenerator;
import com.amine.mareu.databinding.ActivityAddReunionBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AddNewMeeting extends AppCompatActivity {

  private ActivityAddReunionBinding binding; // BindingView to return the XML and Element View
  private DatePickerDialog mDateSetListener;
  private TimePickerDialog mTimePickerDialog;


  // Model Object to created a NewInstance or Modify
  private List<Room> mRoomList;
  private List<String> mParticipantList; // Contain all Email insert on the InputText

  // Element to Init the New Meeting//
  private Room mRoom; // Room choose by the User
  private LocalDateTime mDateBegin, mDateFinish; // Date/Time choose by the User
  private String mParticipants; // All Email insert by the User split by 's'
  private String mSubject;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAddReunionBinding.inflate(getLayoutInflater()); //ViewBinding
    View view = binding.getRoot(); setContentView(view); // Return the View by BindingView

    mRoomList = DummyRoomGenerator.DUMMY_ROOM; // ListOfRoom
    receiveData(); //Logic Work
  }

  private void receiveData() {
    chooseYourDate(); // Open Dialogue Alert to choose a Date -> OK but the Time not Working :/
    chooseYourRoom(); // Spinner for choose the Room of the Meeting -> OK
    chooseYourParticipant(); //
    binding.toolbarNew.setNavigationIcon(R.drawable.ic_arrow_back); // Insert the Drawable on the Toolbar
    binding.toolbarNew.setNavigationOnClickListener(v -> onBackPressed()); // Insert the Drawable icone on the Toolbar and lambda to setAction Previous Button
    createNewMeeting(); // Method to send setResult to Main for the creation of Meeting
  }

  public List<String> getListNameRooms() { // Render the List of Room Name -> Use to AutoCompleteView
    List<String> mRoomName = new ArrayList<>(); for ( Room room : mRoomList ) {
      mRoomName.add(room.getName());
    } return mRoomName;
  }

  public void chooseYourRoom() { // Spinner for choose the Room of the Meeting -> OK
    mRoom = mRoomList.get(0);// Securité pour que la salle soit toujours séléctionner sur le première Item
    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getListNameRooms());
    binding.autoCompleteRoom.setText(mRoomList.get(0).getName()); // Insert the first Room of the list default value view
    binding.autoCompleteRoom.setAdapter(spinnerArrayAdapter);
    binding.autoCompleteRoom.setOnItemClickListener((parent, view, position, id) -> mRoom = mRoomList.get(position));
  }

  private void chooseYourDate() {
    mDateBegin = LocalDateTime.now();
    int mYear = mDateBegin.getYear();
    int mMonth = mDateBegin.getMonthValue() - 1; // PB -> DataPicker Month begin to 0 not 1, sub 1 to the month
    int mDay = mDateBegin.getDayOfMonth(); int mHour = mDateBegin.getHour();
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
    return (DatePicker view, int year, int month, int dayOfMonth) -> {
      mTimePickerDialog = new TimePickerDialog(AddNewMeeting.this, (view1, hourOfDay, minute) -> {
        // We Take add 1 to the month because DataPicker begin to 0 and LocalDate to 1
        mDateBegin = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute);
        mDateFinish = mDateBegin.plusHours(1);
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final String theReturnDate = mDateBegin.format(formatterDate).toString()+" "+mDateBegin.toLocalTime().toString();
        binding.info.setText(theReturnDate);
      }, mHour, mMinute, true); mTimePickerDialog.show();
    };
  }

  // Take the String insert in the EditText after click in the Drawable and must check if is a good email to insert the email entry in the string
  @SuppressLint({ "ClickableViewAccessibility", "NewApi", "ResourceAsColor", "WrongConstant" })
  private void chooseYourParticipant() {
    mParticipantList = new ArrayList<>();
    Objects.requireNonNull(binding.participant).setOnTouchListener((v, event) -> {
      final int DRAWABLE_RIGHT = 2;

      String email;
      //Préparation a effectuer une vérification des String participants via une expression régulière pour les mails !
      String patrn = "^([\\w-.]+){1,64}@([\\w&&[^_]]+){2,255}(.[a-z]{2,3})+$|^$";

      email = Objects.requireNonNull(binding.participant.getText()).toString(); // Require not null

      binding.labelParticipant.addOnEditTextAttachedListener(textInputLayout -> {
        if ( (!email.matches(patrn)) || (email.isEmpty()) ) {
          binding.labelParticipant.setError("The email address is invalid...");
          //binding.labelParticipant.setEndIconDrawable(R.drawable.ic_check);
          binding.labelParticipant.setErrorEnabled(true);
        } else {
          binding.labelParticipant.setErrorEnabled(false);
          binding.labelParticipant.setEndIconDrawable(R.drawable.ic_check_circle);
        }
      });

      if ( event.getAction() == MotionEvent.ACTION_UP ) {
        if ( event.getRawX() >= (binding.participant.getRight() - binding.participant.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) ) {
          if ( email.matches(patrn) ) {
            mParticipantList.add(email);
            binding.participant.setText("");
            mParticipants = String.join(" ; ", mParticipantList);
          }
        }
      } return false;
    });
  }

  private void createNewMeeting() {
    binding.addMeeting.setOnClickListener(v -> {
      // -> We take all data send by the user to send this to MainActivity for the Creation and closed the Activity
      mSubject = binding.subject.getText().toString();
      Intent intent = new Intent();
      intent.putExtra("newMeeting", new Meeting(0, mDateBegin, mDateFinish, mRoom, mSubject, mParticipants));
      setResult(1, intent); finish();
    });
  }

}
