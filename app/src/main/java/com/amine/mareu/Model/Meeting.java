package com.amine.mareu.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;

public class Meeting implements Parcelable {

  private Integer id;
  private final LocalDateTime dateBegin;
  private final LocalDateTime dateFinish;
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

  protected Meeting(Parcel in) {
    if (in.readByte() == 0) {
      id = null;
    } else {
      id = in.readInt();
    }
    dateBegin = (LocalDateTime) in.readSerializable();
    dateFinish = (LocalDateTime) in.readSerializable();
    room = in.readParcelable(Room.class.getClassLoader());
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

  public boolean isCompleted() {
    return id != null && room != null && dateBegin != null && dateFinish != null && subject != null && participants != null;
  } // TODO this method is good for check if all attribut is received -> Search where to use it


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

  public LocalDateTime getDateAfter() {
    return dateFinish;
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
    dest.writeParcelable(room, flags);
    dest.writeString(subject);
    dest.writeString(participants);
  }
}
