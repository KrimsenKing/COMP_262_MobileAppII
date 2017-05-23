package com.example.scherr3143.lab5chapter3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String CANCEL_NOTIFICATION_ACTION = "cancel_notification";
    int notificationID = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //sends notification to cancel the built notification
                notificationManager.cancel(notificationID);
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(CANCEL_NOTIFICATION_ACTION);
        this.registerReceiver(receiver,filter);
    }

    public void setNotification (View view){
        //creates new intent
        Intent cancelIntent = new Intent(CANCEL_NOTIFICATION_ACTION);
        //creates pending intent using intent
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this,100,cancelIntent,0);

        //builds notification using pending intent
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Stop Press")
                .setContentText("Everyone gets extra vacation week!")
                .setSmallIcon(R.drawable.star)
                .setAutoCancel(true)
                .addAction(android.R.drawable.btn_dialog,"Dismiss",cancelPendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //sends notification to the notification manager, notifying with built notification
        notificationManager.notify(notificationID, notification);

    }

    public void clearNotification(View view){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //sends notification to cancel the built notification
        notificationManager.cancel(notificationID);
    }
}
