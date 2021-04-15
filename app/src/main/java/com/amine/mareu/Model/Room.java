package com.amine.mareu.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {

  private String name;
  private String color;

  public Room(String name, String color) {
    this.name = name;
    this.color = color;
  }

  protected Room(Parcel in) {
    name = in.readString();
    color = in.readString();
  }

  public static final Creator<Room> CREATOR = new Creator<Room>() {
    @Override
    public Room createFromParcel(Parcel in) {
      return new Room(in);
    }

    @Override
    public Room[] newArray(int size) {
      return new Room[size];
    }
  };

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(color);
  }
}
