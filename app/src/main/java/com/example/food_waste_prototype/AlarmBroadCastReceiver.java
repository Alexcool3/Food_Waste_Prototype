package com.example.food_waste_prototype;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

public class AlarmBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            showNotification(context);

        }




        void showNotification(Context context) {
            String CHANNEL_ID = "Mad_Spild_App";// The id of the channel.
            CharSequence name = context.getResources().getString(R.string.app_name);// The user-visible name of the channel.
            NotificationCompat.Builder mBuilder;
           // Intent notificationIntent = new Intent(context, InputActivity.class);
            Intent notificationIntent = new Intent( Intent.ACTION_VIEW , Uri.parse( "https://stackoverflow.com/questions/2762861/android-goto-http-url-on-button-click" ) );
            Bundle bundle = new Bundle();
            notificationIntent.putExtras(bundle);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= 26) {
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
                assert mNotificationManager != null;
                mNotificationManager.createNotificationChannel(mChannel);
                mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.meaticon_round)
                        .setLights(Color.RED, 300, 300)
                        .setChannelId(CHANNEL_ID)
                        .setContentTitle("Mad Spild");
            } else {
                mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.meaticon_round)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentTitle("Mad Spild");
            }

            mBuilder.setContentIntent(contentIntent);
            mBuilder.setContentText("Husk at udfylde dagens spørgeskema!");
            mBuilder.setAutoCancel(true);
            assert mNotificationManager != null;
            mNotificationManager.notify(1, mBuilder.build());
        }
}
