package com.chaloa.android.app;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public MyNewIntentService() {
        super("NewIntentService");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String heading = intent.getStringExtra("heading");
        String body = intent.getStringExtra("text");

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(heading);
        builder.setContentText(body);
        builder.setSmallIcon(R.drawable.ic_marker_maps_24dp_img);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                2,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager;

            NotificationChannel channel = new NotificationChannel("a", "channel_name", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("channel_description");

            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId("a");
        }
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);

    }
}
