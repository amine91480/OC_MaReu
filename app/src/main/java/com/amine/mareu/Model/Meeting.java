package com.amine.mareu.Model;

import java.time.LocalTime;

public class Meeting {

    private Integer id;
    private LocalTime date;
    private String location;
    private String subject;
    private String participants;

    public Meeting(Integer id, LocalTime date,String location,String subject,String partcipants){
        this.id = id;
        this.date = date;
        this.location = location;
        this.subject = subject;
        this.participants = partcipants;
    }

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

    public LocalTime getDate() {
        return date;
    }

    public void setDate(LocalTime date) {
        this.date = date;
    }
}
