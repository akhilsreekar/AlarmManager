package com.akhilsreekar.artoo.Fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.akhilsreekar.artoo.AlarmReceiver;
import com.akhilsreekar.artoo.AppPreferences;
import com.akhilsreekar.artoo.MainActivity;
import com.akhilsreekar.artoo.Models.MeetingDetail;
import com.akhilsreekar.artoo.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static com.akhilsreekar.artoo.AlarmReceiver.MEETING;
import static com.akhilsreekar.artoo.MainActivity.MINUTE;

public class AddMeetingsFragment extends Fragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    Context context;
    EditText whomToMeet;
    EditText description;
    EditText address;
    EditText date;
    EditText time;
    Button submit;
    AddMeetingInterface addMeetingInterface;
    AlarmManager alarmManager;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_meetings,container,false);
        whomToMeet = view.findViewById(R.id.whom_to_meet);
        description = view.findViewById(R.id.description);
        address = view.findViewById(R.id.address);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        submit = view.findViewById(R.id.submit);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        initializeClickListeners();
        return view;
    }

    public void initializeClickListeners(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEntryValid(whomToMeet) ||  !isEntryValid(description) || !isEntryValid(address) || !isEntryValid(date) || !isEntryValid(time)){
                    return;
                }

                if(myCalendar.getTimeInMillis()<System.currentTimeMillis()){
                    date.setError(getString(R.string.old_time_cant_be_set));
                    time.setError(getString(R.string.old_time_cant_be_set));
                    Toast.makeText(context, R.string.old_time_cant_be_set,Toast.LENGTH_SHORT).show();
                    return;
                }

                List<MeetingDetail> meetingDetailList = AppPreferences.getMeetingDetails(context);
                if(meetingDetailList!=null) {
                    for (MeetingDetail meetingDetail : meetingDetailList) {
                        if (Math.abs(meetingDetail.getMeetingTime() - myCalendar.getTimeInMillis()) < 10 * MINUTE) {
                            Toast.makeText(context, getString(R.string.meeting_already_present)+" at "+MainActivity.displayableTimeFormat.format(meetingDetail.getMeetingTime()), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                addNewMeeting();

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(context,AddMeetingsFragment.this,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker = new TimePickerDialog(context, AddMeetingsFragment.this,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), false);
                mTimePicker.show();
            }
        });
    }

    public void addNewMeeting(){
        MeetingDetail newMeeting = new MeetingDetail( whomToMeet.getText().toString(),
                description.getText().toString(),
                address.getText().toString(),
                myCalendar.getTimeInMillis());
        AppPreferences.addMeetingDetails(context, newMeeting);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(MEETING,newMeeting);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        addMeetingInterface.addMeeting(newMeeting);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), 10000, pendingIntent);
        clearViews();
        Toast.makeText(context, R.string.meeting_added_successfully,Toast.LENGTH_LONG).show();
    }

    public void clearViews(){
        whomToMeet.getText().clear();
        description.getText().clear();
        address.getText().clear();
        date.getText().clear();
        time.getText().clear();
    }

    public boolean isEntryValid(TextView view){
        if(view.getText().toString().isEmpty()){
            view.setError(getString(R.string.field_mandatory));
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        addMeetingInterface = (MainActivity)context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR,year);
        myCalendar.set(Calendar.MONTH,month);
        myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        date.setText(MainActivity.displayableDateFormat.format(myCalendar.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        myCalendar.set(Calendar.MINUTE,minute);
        myCalendar.set(Calendar.SECOND,0);
        time.setText(MainActivity.displayableTimeFormat.format(myCalendar.getTime()));
    }

    public interface AddMeetingInterface{
        void addMeeting(MeetingDetail meetingDetail);
    }
}
