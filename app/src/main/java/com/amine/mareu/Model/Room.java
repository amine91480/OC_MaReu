package com.amine.mareu.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Room {

    private String room;
    private Integer color;

    public Room(String room, int color) {
        this.room = room;
        this.color = color;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
