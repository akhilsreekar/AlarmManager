package com.akhilsreekar.artoo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.akhilsreekar.artoo.Adapters.TabsAdapter;
import com.akhilsreekar.artoo.Fragments.AddMeetingsFragment;
import com.akhilsreekar.artoo.Fragments.ViewMeetingsFragment;
import com.akhilsreekar.artoo.Models.MeetingDetail;

import java.text.SimpleDateFormat;

public class MainActivity extends FragmentActivity implements AddMeetingsFragment.AddMeetingInterface{

    AddMeetingsFragment addMeetingsFragment;
    ViewMeetingsFragment viewMeetingsFragment;

    public static SimpleDateFormat displayableDateTimeFormat = new SimpleDateFormat("HH:mm:ss a dd-MMM-yy");
    public static SimpleDateFormat displayableTimeFormat = new SimpleDateFormat("HH:mm:ss a");
    public static SimpleDateFormat displayableDateFormat = new SimpleDateFormat("dd-MMM-yy");
    public static int MINUTE = 1*60*1000;
    public static int TABS = 2;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager.setOffscreenPageLimit(TABS);
        setUpViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setUpViewPager(){
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        addMeetingsFragment =new AddMeetingsFragment();
        viewMeetingsFragment = new ViewMeetingsFragment();

        adapter.addFragment(addMeetingsFragment,"Add Meetings");
        adapter.addFragment(viewMeetingsFragment,"View Meetings");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void addMeeting(MeetingDetail meetingDetail) {
        viewPager.setCurrentItem(1);
    }
}
