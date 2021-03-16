package com.amine.mareu;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //ViewBindind
    private ActivityMainBinding binding;
    private Context mContext;

    private MeetingApiService mApiService;
    private List<Meeting> mMeetingList;
    private List<Meeting> mMeetingListCopy;
    private List<Room> mRoomList;

    private MyListMeetingAdapter mAdapter;

    private Room selectedRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mContext = getApplicationContext();

        setSupportActionBar(binding.toolbar);
        setupData();

        binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.myRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new MyListMeetingAdapter(mMeetingList, mContext);
        binding.myRecyclerView.setAdapter(mAdapter);

        binding.fab.setColorFilter(Color.WHITE);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddNewMeeting.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void setupData() {
        mApiService = DI.getMeetingApiService();
        mMeetingList = mApiService.getMeetings();
        mRoomList = mApiService.getRooms();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new MyListMeetingAdapter(mMeetingList, mContext);
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
        switch (item.getItemId()) {
            case R.id.button_menu:
             /*   FilterDialogueFragment filterDialogue = new FilterDialogueFragment(MainActivity.this);
                filterDialogue.startFilterDialogue();*/
                openDialogue();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialogue() {
        FilterDialogueFragment filterDialogueFragment = new FilterDialogueFragment();
        filterDialogueFragment.show(getSupportFragmentManager(), "Go");
    }


}