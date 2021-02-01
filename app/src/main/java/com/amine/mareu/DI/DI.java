package com.amine.mareu.DI;

import com.amine.mareu.Service.DummyMeetingApiService;
import com.amine.mareu.Service.MeetingApiService;

public class DI {

    private static MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

}
