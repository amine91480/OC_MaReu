package com.amine.mareu.DI;

import com.amine.mareu.Service.DummyMeetingApiService;
import com.amine.mareu.Service.MeetingApiService;

public class DI {


  public static MeetingApiService getMeetingApiService() {
    return new DummyMeetingApiService();
  }
  // Render a new Instance of Meeting at the MainActivity, it's the only One instance of Service.

}
