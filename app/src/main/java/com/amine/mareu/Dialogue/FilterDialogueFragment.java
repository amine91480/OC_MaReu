package com.amine.mareu.Dialogue;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.FilterDialogueBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FilterDialogueFragment extends AppCompatDialogFragment {

  private FilterDialogueBinding mBinding; // Return the XML view

  private MeetingApiService mApiService; // Use to share the API method

  // Model -> Class Room attribute
  private List<Room> mRooms;
  private List<String> mRoomsName;
  private Room mRoom;

  // Date
  private LocalDate mDate;
  private DatePickerDialog mDateSetListener;

  private AlertDialogueListener mListener;

  private List<Meeting> megaFiltre;


  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    mApiService = DI.getMeetingApiService(); // Method API can use her
    mRoomsName = mApiService.getListNameRooms(); mRooms = mApiService.getRooms();

    mBinding = FilterDialogueBinding.inflate(getLayoutInflater()); // Use BindingView to render the view XML

    // We use builder.setView for creta and paramètre the Bouton cancel and Ok ->
    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity()); // Use Builder to create the Dialogue
    builder.setView(mBinding.getRoot()); builder.setTitle("Filter Dialogue");
    builder.setNegativeButton("Cancel", (DialogInterface, witch) -> {
      // Create the button Cancel
    });
    // Create the button OK to Valide
    builder.setPositiveButton("OK", (dialog, which) -> {
      megaFiltre = mApiService.getFilteredMeeting(mRoom, mDate); mListener.returnData(megaFiltre);
    }); return builder.create();
    // Method her to take the Attribut of the Room & Date & Time if it's possible to render in the MainActivity to render the result filter - InProgress
  }

  @Override
  public void onResume() {
    super.onResume(); takeRoomToFilter(); // -> OK
    initDatePicker(); //-> OK
  }

  private void takeRoomToFilter() { // Work -> Initialise mRoom
    mRoom = mRooms.get(0); //Choose the First Room if the user don't choose
    // It's a AutoCompleteTextView to see the List of View -> OK but don't take the value of the item taken
    ArrayAdapter<String> autoCompleteTextViewRoom = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mRoomsName);
    mBinding.autoCompleteFilterRoom.setAdapter(autoCompleteTextViewRoom);
    mBinding.autoCompleteFilterRoom.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long id) -> {
      mRoom = mRooms.get(position); mBinding.textRoom.setText(mRoom.getName());
    });
  }

  private void initDatePicker() {
    mDate = LocalDate.now(); int mYear = mDate.getYear(); int mMonth = mDate.getMonthValue() - 1;
    int mDay = mDate.getDayOfMonth();
    mBinding.buttonToDate.setOnClickListener(onInfoClick(mYear, mMonth, mDay));
  }

  private View.OnClickListener onInfoClick(int mYear, int mMonth, int mDay) {
    return (View v) -> {
      mDateSetListener = new DatePickerDialog(getContext(), onDateSet(mYear, mMonth, mDay), mYear, mMonth, mDay);
      mDateSetListener.show();
    };
  }

  private DatePickerDialog.OnDateSetListener onDateSet(int mYear, int mMonth, int mDay) {
    return (DatePicker view, int year, int month, int dayOfMonth) -> {
      DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      String date = mYear + " " + mMonth + " " + mDay;
      //DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
      mDate = LocalDate.of(year, month, dayOfMonth);
      mBinding.textDate.setText(mDate.format(formatterDate));
      Log.d("OK", date + " " + mDate + " " + mDateSetListener.getDatePicker().getDayOfMonth());
    };
  }

  public interface AlertDialogueListener {
    void returnData(List<Meeting> meetings);
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context); try {
      mListener = ( AlertDialogueListener ) context;
    } catch ( ClassCastException e ) {
      throw new ClassCastException(context.toString() + "Je ne sais pas...");
    }
  }
}
