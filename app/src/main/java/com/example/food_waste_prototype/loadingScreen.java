package com.example.food_waste_prototype;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.IntentCompat;

import java.util.Calendar;

public class loadingScreen extends AppCompatActivity {
    DataBase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        DB = DataBase.getInstance(loadingScreen.this);
        startAlarmBroadcastReceiver(loadingScreen.this);
        ImageView image = findViewById(R.id.imageView);

        image.startAnimation(AnimationUtils.loadAnimation(loadingScreen.this, R.anim.rotation));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!DB.loggedIn) {
                    Intent intent = new Intent(loadingScreen.this, LoginActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    ActivityCompat.finishAffinity(loadingScreen.this);

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(loadingScreen.this, InputActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    ActivityCompat.finishAffinity(loadingScreen.this);

                    startActivity(intent);
                }
            }
        }, 1000);   //5 seconds


    }

    public static void startAlarmBroadcastReceiver(Context context) {
        Intent _intent = new Intent(context, AlarmBroadCastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, _intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        if(pendingIntent!=null){
            alarmManager.cancel(pendingIntent);
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 37);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
