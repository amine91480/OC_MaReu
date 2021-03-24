package com.amine.mareu.Model;

import java.time.LocalDateTime;

public class Meeting {

    private Integer id;
    private final LocalDateTime dateBegin;
    private LocalDateTime dateFinish;
    private Room room;
    private String subject;
    private String participants;

    public Meeting(Integer id, LocalDateTime dateBegin, LocalDateTime dateFinish, Room room, String subject, String partcipants) {
        this.id = id;
        this.dateBegin = dateBegin;
        this.dateFinish = dateFinish;
        this.room = room;
        this.subject = subject;
        this.participants = partcipants;
    }

//    public boolean isCompleted() {
//        return id != null && room != null && dateBegin != null && dateFinish != null && subject != null && participants != null;
//    } // TODO this method is good for check if all attribut is received -> Search where to use it


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

    public LocalDateTime getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin() {
    }

    public LocalDateTime getDateAfter() {
        return dateFinish;
    }

    public void setDateAfter() {
        this.dateFinish = dateFinish;
    }

}
