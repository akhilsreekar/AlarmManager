package com.akhilsreekar.artoo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akhilsreekar.artoo.Adapters.MeetingsAdapter;
import com.akhilsreekar.artoo.AppPreferences;
import com.akhilsreekar.artoo.Models.MeetingDetail;
import com.akhilsreekar.artoo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ViewMeetingsFragment extends Fragment {

    Context context;
    RecyclerView listOfMeetings;
    TextView noMeetinsPresent;
    MeetingsAdapter meetingsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_meetings,container,false);
        listOfMeetings = (RecyclerView) view.findViewById(R.id.list_of_meetings);
        noMeetinsPresent = (TextView) view.findViewById(R.id.no_meetings_present);
        refreshMeetings();
        return view;
    }

    public void refreshMeetings(){
        List<MeetingDetail> meetingDetailsList = AppPreferences.getMeetingDetails(context);
        if(meetingDetailsList==null || meetingDetailsList.size()==0){
            meetingsAbsent();
        }else {
            meetingsPresent();
            listOfMeetings.setLayoutManager(new LinearLayoutManager(context));
            meetingsAdapter = new MeetingsAdapter(meetingDetailsList, context);
            listOfMeetings.setAdapter(meetingsAdapter);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            refreshMeetings();
        }
    }

    public void meetingsPresent(){
        if(listOfMeetings!=null && noMeetinsPresent!=null) {
            listOfMeetings.setVisibility(View.VISIBLE);
            noMeetinsPresent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MeetingDetail event) {
        refreshMeetings();
    }

    public void meetingsAbsent(){
        if(listOfMeetings!=null && noMeetinsPresent!=null) {
            listOfMeetings.setVisibility(View.GONE);
            noMeetinsPresent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
