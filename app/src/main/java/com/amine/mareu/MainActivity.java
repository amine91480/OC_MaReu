package com.amine.mareu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.amine.mareu.Controller.AddNewMeeting;

import com.amine.mareu.Controller.MyListMeetingAdapter;
import com.amine.mareu.DI.DI;
import com.amine.mareu.Dialogue.FilterDialogueFragment;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;


public class MainActivity extends AppCompatActivity implements FilterDialogueFragment.AlertDialogueListener {

    private ActivityMainBinding binding; //ViewBindind

    private List<Meeting> mMeetingList;

    private MyListMeetingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        setupData();

        binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        binding.myRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new MyListMeetingAdapter(mMeetingList);
        binding.myRecyclerView.setAdapter(mAdapter);

        binding.fab.setColorFilter(Color.WHITE);
        binding.fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(view1.getContext(), AddNewMeeting.class);
            view1.getContext().startActivity(intent);
        });
    }

    private void setupData() {
        // On the top -> Field can be converted to a local variable
        MeetingApiService apiService = DI.getMeetingApiService();
        mMeetingList = apiService.getMeetings();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new MyListMeetingAdapter(mMeetingList);
        binding.myRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.button_menu) {
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
    public void returnData(List<Meeting> meetings) {
        // Call ReturnData on AlertFiltered to receip the list of Meeting Filtred and return this on the Adapter
        //Log.d("OK", String.valueOf(meetings.size()));
        mAdapter = new MyListMeetingAdapter(meetings);
        binding.myRecyclerView.setAdapter(mAdapter);
    }
}