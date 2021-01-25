package com.amine.mareu.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalTime;

public class Meeting implements Parcelable {

    private Integer id;
    private String date;
    private String location;
    private String subject;
    private String participants;

    public Meeting(Integer id, String date, String location, String subject, String partcipants){
        this.id = id;
        this.date = date;
        this.location = location;
        this.subject = subject;
        this.participants = partcipants;
    }

    protected Meeting(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        date = in.readString();
        location = in.readString();
        subject = in.readString();
        participants = in.readString();
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(date);
        dest.writeString(location);
        dest.writeString(subject);
        dest.writeString(participants);
    }
}
