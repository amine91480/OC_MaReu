package com.amine.mareu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.amine.mareu.Controller.AddNewMeeting;

import com.amine.mareu.Controller.MyListMeetingAdapter;
import com.amine.mareu.DI.DI;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Model.Room;
import com.amine.mareu.Service.MeetingApiService;
import com.amine.mareu.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    private String selectedRoom;

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
        mMeetingListCopy = new ArrayList<>();
        mMeetingListCopy.addAll(mMeetingList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Erreur -> Rend la bonne liste mais affiche les anciens éléments
                mAdapter.getFilter().filter(newText);
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_menu:
                Toast.makeText(getApplicationContext(), "Watch this super New Item", Toast.LENGTH_SHORT);
                binding.newFilter.setVisibility(View.VISIBLE);
                return true;
            case R.id.action_search:
                binding.newFilter.setVisibility(View.GONE);
                selectedRoom = null;
                mAdapter = new MyListMeetingAdapter(mMeetingList, this);
                binding.myRecyclerView.setAdapter(mAdapter);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void filtredRoom(String selected) {
        Log.d("Room", "Filtre Test !: ");
        selectedRoom = selected;

        ArrayList<Meeting> filtredRoom = new ArrayList<Meeting>();

        if (selectedRoom == null || selectedRoom.length() == 00) {
            Log.d("Room", "Render the List Begin ");
            mAdapter = new MyListMeetingAdapter(mMeetingList, mContext);
            binding.myRecyclerView.setAdapter(mAdapter);
        }
        for (Meeting meeting : mMeetingList) {
            if (meeting.getRoom().toString().toLowerCase().contains(selectedRoom.toLowerCase())) {
                filtredRoom.add(meeting);
            }
            mAdapter = new MyListMeetingAdapter(filtredRoom, mContext);
            binding.myRecyclerView.setAdapter(mAdapter);
        }
    }

    public void RoomAFilterChoose(View view) {
        Log.d("Room", "Touched: " + mRoomList.get(0).toString());
        filtredRoom(mRoomList.get(0).toString());
    }

    public void RoomBFilterChoose(View view) {
        Log.d("Room", "Touched: " + mRoomList.get(1));
        filtredRoom(mRoomList.get(1).toString());
    }

    public void RoomCFilterChoose(View view) {
        Log.d("Room", "Touched: " + mRoomList.get(2));
        filtredRoom(mRoomList.get(2).toString());
    }

    public void RoomDFilterChoose(View view) {
        Log.d("Room", "Touched: ");
        filtredRoom(mRoomList.get(3).toString());
    }
}