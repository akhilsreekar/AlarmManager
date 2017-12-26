package com.akhilsreekar.artoo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.akhilsreekar.artoo.Models.MeetingDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 24-12-2017.
 */

public class AppPreferences {
    private static final String MEETINGS = "MEETINGS";

    public static void addMeetingDetails(Context context, MeetingDetail meetingDetail) {
        Gson gson = new Gson();
        String existingMeetings = getSharedPreferenceInstance(context).getString(MEETINGS,"");
        Type type = new TypeToken<ArrayList<MeetingDetail>>(){}.getType();
        List<MeetingDetail> existingMeetingsList = gson.fromJson(existingMeetings, type);
        if(existingMeetingsList==null){
            existingMeetingsList = new ArrayList<>();
        }
        existingMeetingsList.add(meetingDetail);
        setString(context,MEETINGS,gson.toJson(existingMeetingsList,type));
    }

    public static List<MeetingDetail> getMeetingDetails(Context context){
        String meetingsString = getString(context,MEETINGS,"");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MeetingDetail>>(){}.getType();
        List<MeetingDetail> meetingDetails = gson.fromJson(meetingsString, type);
        if(meetingDetails==null){
            meetingDetails = new ArrayList<>();
        }
        return meetingDetails;
    }

    private static SharedPreferences getSharedPreferenceInstance(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static void clearString(Context context,String key){
        SharedPreferences.Editor editor = getSharedPreferenceInstance(context).edit();
        editor.remove(key).commit();
    }


    private static void setString(Context context,String key,String val) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(key, val);
        e.commit();
    }

    private static String getString(Context context, String key, String def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String string = prefs.getString(key, def);
        return string;
    }

}
