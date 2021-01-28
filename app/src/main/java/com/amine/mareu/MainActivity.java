package com.amine.mareu;

import android.app.Application;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.amine.mareu.Controller.AddNewMeeting;
import com.amine.mareu.Controller.MyListMeetingAdapter;
import com.amine.mareu.Model.Meeting;
import com.amine.mareu.Service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "It's Action setting", Toast.LENGTH_LONG).show();
                String setting ="It's Action setting";
                dialog(setting);
                return true;

            case R.id.filter_date:
                Toast.makeText(MainActivity.this, "It's Date setting", Toast.LENGTH_LONG).show();
                String date ="It's Date setting";
                dialog(date);
                return true;

            case R.id.filter_location:
                String location ="It's Loca setting";
                dialog(location);
                return true;

            case R.id.filter_location_and_date:
                String nul ="PTDR té qui ?";
                dialog(nul);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
    private void dialog(String string){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(MainActivity.this);
        myAlert.setTitle(string);
        myAlert.show();
    }


}

