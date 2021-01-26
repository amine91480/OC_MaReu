package com.amine.mareu;

import android.content.Intent;
import android.os.Bundle;

import com.amine.mareu.Controller.AddNewMeeting;
import com.amine.mareu.Controller.MyListMeetingAdapter;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyListMeetingAdapter mAdapter;
    private List<Meeting> mMeeting;
    private MeetingApiService mApiService;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        FloatingActionButton addMeeting = (FloatingActionButton) findViewById(R.id.fab);
        addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddNewMeeting.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("OK", "Fragment.onCreateOptionsMenu///////////////////////////////////");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("OK", "OK///////////////////////////////////");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "It's Action setting", Toast.LENGTH_LONG).show();
                System.out.println("It's Action setting");
                return true;

            case R.id.filter_date:
                Toast.makeText(this, "It's Date setting", Toast.LENGTH_LONG).show();
                System.out.println("It's Date setting");
                return true;

            case R.id.filter_location:
                Toast.makeText(this, "It's Loca setting", Toast.LENGTH_LONG).show();
                return true;

            case R.id.filter_location_and_date:
                Toast.makeText(this, "It's the booth setting", Toast.LENGTH_LONG).show();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}

