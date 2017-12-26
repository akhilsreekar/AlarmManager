package com.akhilsreekar.artoo.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by akhil on 21-12-2017.
 */

public class MeetingDetail implements Serializable {

    private String whomToMeet;
    private String description;
    private String address;
    long meetingTime;

    public MeetingDetail(){

    }

    public MeetingDetail(String whomToMeet, String description, String address, long meetingTime) {
        this.whomToMeet = whomToMeet;
        this.description = description;
        this.address = address;
        this.meetingTime = meetingTime;
    }

    public String getWhomToMeet() {
        return whomToMeet;
    }

    public void setWhomToMeet(String whomToMeet) {
        this.whomToMeet = whomToMeet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(long meetingTime) {
        this.meetingTime = meetingTime;
    }
}
