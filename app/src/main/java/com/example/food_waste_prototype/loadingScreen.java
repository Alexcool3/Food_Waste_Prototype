package com.example.food_waste_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class loadingScreen extends AppCompatActivity {
DataBase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        DB = DataBase.getInstance(loadingScreen.this);

        DB.CreateCategory("Grøntsager", 5, loadingScreen.this);
        DB.CreateCategory("Kød", 50, loadingScreen.this);
        DB.CreateCategory("Brød", 5, loadingScreen.this);
        DB.CreateCategory("Mælk", 5, loadingScreen.this);
        DB.CreateCategory("Fisk", 5, loadingScreen.this);
        ImageView image = findViewById(R.id.imageView);

        image.startAnimation(AnimationUtils.loadAnimation(loadingScreen.this, R.anim.rotation));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!DB.loggedIn) {
                    Intent intent = new Intent(loadingScreen.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(loadingScreen.this, InputActivity.class);
                    startActivity(intent);
                }
            }
        }, 1000);   //5 seconds


    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(loadingScreen.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
