package com.amine.mareu.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Meeting implements Parcelable {

    private Integer id;
    private Date dateBegin;
    private Date dateFinish;
    private String room;
    private String subject;
    private String participants;

    public Meeting(Integer id, Date dateBegin, Date dateFinish, String room, String subject, String partcipants) {
        this.id = id;
        this.dateBegin = dateBegin;
        this.dateFinish = dateFinish;
        this.room = room;
        this.subject = subject;
        this.participants = partcipants;
    }

    public boolean isCompleted() {
        if(id != null && room != null && dateBegin != null && dateFinish != null && subject != null && participants != null){
            return true;
        }
        else {
            return false;
        }
    }

    protected Meeting(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        dateBegin = (Date) in.readSerializable();
        dateFinish = (Date) in.readSerializable();
        room = in.readString();
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

    public String getRoom() {
        return room;
    }


    public void setRoom(String room) {
        this.room = room;
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

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin() {
        this.dateBegin = dateBegin;
    }

    public Date getDateAfter() {
        return dateFinish;
    }

    public void setDateAfter() {
        this.dateFinish = dateFinish;
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
        dest.writeSerializable(dateBegin);
        dest.writeSerializable(dateFinish);
        dest.writeString(room);
        dest.writeString(subject);
        dest.writeString(participants);
    }
}
