package com.example.myapplication.Classes;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.MainActivity;

/**
 * Created by starapps on 1/24/2017.
 */
@Deprecated
public class NotificationReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentIntent(pendingIntent)
                .setContentText("this is my notification")
                .setContentTitle("my notificaton")
                .setSound(alarmSound)
                .setAutoCancel(true);
        notificationManager.notify(100, builder.build());

    }
}
// https://www.tutorialspoint.com/android/android_notifications.htm