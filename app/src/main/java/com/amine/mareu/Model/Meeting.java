package com.amine.mareu.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Meeting {

    private Integer id;
    private Date dateBegin;
    private Date dateFinish;
    private Room room;
    private String subject;
    private String participants;

    public Meeting(Integer id, Date dateBegin, Date dateFinish, Room room, String subject, String partcipants) {
        this.id = id;
        this.dateBegin = dateBegin;
        this.dateFinish = dateFinish;
        this.room = room;
        this.subject = subject;
        this.participants = partcipants;
    }

    public boolean isCompleted() {
        if (id != null && room != null && dateBegin != null && dateFinish != null && subject != null && participants != null) {
            return true;
        } else {
            return false;
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
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

}
