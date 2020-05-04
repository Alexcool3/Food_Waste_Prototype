package com.example.food_waste_prototype;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
        SharedPreferences sharedPreferences = loadingScreen.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        DB.loggedIN = sharedPreferences.getInt("loggedIN", 0);
        Log.d("loggedIn", "loggedIn: " + DB.loggedIn);
        image.startAnimation(AnimationUtils.loadAnimation(loadingScreen.this, R.anim.rotation));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (DB.loggedIN != 1) {
                    Intent intent = new Intent(loadingScreen.this, LoginActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    ActivityCompat.finishAffinity(loadingScreen.this);

                    startActivity(intent);
                } else {
                    SharedPreferences sharedPreferences = loadingScreen.this.getPreferences(Context.MODE_PRIVATE);
                    DataBase.instance.username = sharedPreferences.getString("username", "");
                    DataBase.instance.password = sharedPreferences.getString("password", "");
                    Log.d("Saved user login", "username: " + sharedPreferences.getString("username", "") + " password: " + sharedPreferences.getString("password", ""));
                    BackgroundTask backgroundTask = new BackgroundTask(loadingScreen.this);
                    backgroundTask.execute("login", DataBase.instance.username, DataBase.instance.password);
                    /*
                    Intent intent = new Intent(loadingScreen.this, InputActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    ActivityCompat.finishAffinity(loadingScreen.this);

                    startActivity(intent);
                     */
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
