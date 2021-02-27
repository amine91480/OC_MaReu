package com.amine.mareu.Model;

public class Room {

    private String room;
    private String color;

    public Room(String room, String color) {
        this.room = room;
        this.color = color;
    }
    @Override
    public String toString() {
        return getRoom(); // You can add anything else like maybe getDrinkType()
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
