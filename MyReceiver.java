package com.example.abgomsale.iot;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

/**
 * Created by abgomsale on 10/18/15.
 */
public class MyReceiver extends BroadcastReceiver {

    static String TAG = "MyReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        String ACTION = intent.getAction();
        if(ACTION.equalsIgnoreCase("my_action")) {
            Bundle extras = intent.getExtras();

            if (extras != null) {



                String temp = extras.getString("RES");
                String old_blind = extras.getString("OLD_BLIND");
                String new_blind = extras.getString("NEW_BLIND");



                String msg = "Blind changed from " + old_blind + " to "+ new_blind;
                // Set Notification Title
                String strtitle = "Change in Blind state Recorded!";
                // Open NotificationView Class on Notification Click
                Intent i = new Intent(context, MainActivity.class);
                // Send data to NotificationView Class
                i.putExtra("title", strtitle);
                i.putExtra("text", msg);
                // Open NotificationView.java Activity
                PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                // Create Notification using NotificationCompat.Builder
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        context)
                        // Set Icon
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                                // Set Ticker Message
                        .setTicker(msg)
                                // Set Title
                        .setContentTitle(strtitle)
                                // Set Text
                        .setContentText(msg)
                                // Set PendingIntent into Notification
                        .setContentIntent(pIntent)
                                // Dismiss Notification
                        .setAutoCancel(true);

                // Create Notification Manager
                NotificationManager notificationmanager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                // Build Notification with Notification Manager
                notificationmanager.notify(0, builder.build());
            }
        }

    }
}
