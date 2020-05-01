package com.example.food_waste_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.IntentCompat;

public class loadingScreen extends AppCompatActivity {
    DataBase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        DB = DataBase.getInstance(loadingScreen.this);
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

   /* @Override
    protected void onResume() {
        super.onResume();

    }*/
}
