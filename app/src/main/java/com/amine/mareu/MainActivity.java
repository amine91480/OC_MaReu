package com.amine.mareu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.amine.mareu.Controller.AddNewMeeting;

import com.amine.mareu.Controller.MyListMeetingAdapter;
import com.amine.mareu.DI.DI;
import com.amine.mareu.Dialogue.FilterDialogueFragment;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.ActivityMainBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FilterDialogueFragment.AlertDialogueListener {

  private ActivityMainBinding binding; //ViewBindind

  private List<Meeting> mMeetingList;

  private final MeetingApiService mApiService = DI.getMeetingApiService();

  private MyListMeetingAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    View view = binding.getRoot();
    setContentView(view);
    setSupportActionBar(binding.toolbar);
    binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
    binding.myRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    mMeetingList = mApiService.getMeetings();
    mAdapter = new MyListMeetingAdapter(mMeetingList);
    binding.myRecyclerView.setAdapter(mAdapter);

    binding.fab.setColorFilter(Color.WHITE);
    binding.fab.setOnClickListener(view1 -> {
      Intent intent = new Intent(view1.getContext(), AddNewMeeting.class);
      startActivityForResult(intent, 1);
    });
  }

  @Override
  public void onResume() {
    super.onResume();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if ( item.getItemId() == R.id.button_menu ) {
      openDialogue(); // Call method to init and Lunch openDialog
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void openDialogue() { // Instancie notre FilterDialogueFragment et l'apelle
    FilterDialogueFragment filterDialogueFragment = new FilterDialogueFragment();
    filterDialogueFragment.show(getSupportFragmentManager(), "Go");
  }


  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {
    // TODO  Search -> it's used for what ?
  }

  @Override
  public void returnData(LocalDate date, Room room) {
    List<Meeting> mFilterListMeeting;
    mFilterListMeeting = mApiService.getFilteredMeeting(room, date);
    mAdapter = new MyListMeetingAdapter(mFilterListMeeting);
    binding.myRecyclerView.setAdapter(mAdapter);

    binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    binding.toolbar.setNavigationOnClickListener(e -> {
      mAdapter = new MyListMeetingAdapter(mMeetingList);
      binding.myRecyclerView.setAdapter(mAdapter);
      binding.toolbar.setNavigationIcon(null);
    });

    // Call ReturnData on AlertFiltered to receip the list of Meeting Filtred and return this on the Adapter
    //mAdapter = new MyListMeetingAdapter(meetings); binding.myRecyclerView.setAdapter(mAdapter);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    // TODO -> Effectuer une vérification sur requestCode et ResultCode pour être sur que le retour et conforme
    if ( data != null ) {// Récupère la data si elle n'est pas vide
      Meeting addNewMeeting = data.getExtras().getParcelable("newMeeting");
      if ( addNewMeeting.isCompleted() && (! mApiService.checkTheFuturReservation(addNewMeeting.getDateBegin(), addNewMeeting.getDateAfter(), addNewMeeting.getRoom())) ) {
        addNewMeeting.setId(mMeetingList.size() + 1);
        mApiService.createMeeting(addNewMeeting);
        mMeetingList = mApiService.getMeetings();
      } else {
        Toast.makeText(getApplicationContext(), "Your Reservation cannot be made", Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(getApplicationContext(), "Error System", Toast.LENGTH_SHORT).show();
      System.out.println("Error System");
    }
    mAdapter = new MyListMeetingAdapter(mMeetingList);
    binding.myRecyclerView.setAdapter(mAdapter);
  }

}