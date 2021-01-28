package com.amine.mareu.Controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amine.mareu.R;

import java.util.Date;

public class FilterDialogue extends Dialog {

    private String mTitle;
    private TextView mFilter;
    private EditText mEditDate;
    private EditText mEditTime;
    private Button mValide;

    @SuppressLint("WrongViewCast")
    public FilterDialogue(Activity activity) {
        super(activity, R.style.Theme_AppCompat);
        setContentView(R.layout.dialogue_pop_up);
        this.mTitle = "Its the Title ?!";
        this.mFilter = (TextView) findViewById(R.id.filter);
        this.mValide = (Button) findViewById(R.id.valide);
        this.mEditDate = (EditText) findViewById(R.id.editDate);
        this.mEditTime = (EditText) findViewById(R.id.editTime);
    }

    public void setTitle() {
        this.mTitle = mTitle;
    }

    public Button getYesButton() {
        return mValide;
    }

    public void build(){
        show();
        setTitle();
        getYesButton();
    }

}
