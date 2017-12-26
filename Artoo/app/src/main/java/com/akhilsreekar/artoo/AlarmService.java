package com.akhilsreekar.artoo;

/**
 * Created by akhil on 17-12-2017.
 */


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.akhilsreekar.artoo.Models.MeetingDetail;

import org.greenrobot.eventbus.EventBus;

import static com.akhilsreekar.artoo.AlarmReceiver.MEETING;

public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        MeetingDetail meetingDetail = (MeetingDetail) intent.getSerializableExtra(MEETING);
        if(meetingDetail==null){
            return;
        }
        sendNotification(meetingDetail);
    }

    private void sendNotification(MeetingDetail meetingDetail) {
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Meet "+meetingDetail.getWhomToMeet()).setSmallIcon(R.drawable.ic_alarm_black_24px)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Description: " + meetingDetail.getDescription()+ "Address: "+meetingDetail.getAddress()))
                .setContentText(meetingDetail.getDescription());

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();

        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        EventBus.getDefault().post(meetingDetail);
    }
}