package com.amine.mareu;

import android.content.Intent;
import android.os.Bundle;

import com.amine.mareu.Controller.AddNewMeeting;
import com.amine.mareu.Controller.FilterDialogue;

import com.amine.mareu.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //ViewBindind
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view.getRootView());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
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
                String setting = "It's Action setting";
                return true;

            case R.id.filter_date:
                Toast.makeText(MainActivity.this, "It's Date setting", Toast.LENGTH_LONG).show();
                String date = "Filter Meeting with Date";

                FilterDialogue filterDialogue = new FilterDialogue(MainActivity.this);
                filterDialogue.setTitle(date);
                filterDialogue.build();
                return true;

            case R.id.filter_location:
                String location = "It's Loca setting";
                dialog(location);
                return true;

            case R.id.filter_location_and_date:
                String nul = "PTDR té qui ?";
                dialog(nul);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialog(String string) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(MainActivity.this);
        myAlert.setTitle(string);
        myAlert.show();
    }

}

