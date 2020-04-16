package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    private boolean foodwastetoggle = false;


    public StatsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        final Context context = StatsActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // make the app fullscreen
        TaskBarButtons(); // activate the taskbar buttons
    }


    private void TaskBarButtons() {


        ImageButton inputtaskbarbutton = findViewById(R.id.button_input);


        inputtaskbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity();
            }
        });
    }

    private void SwitchActivity() {


    Intent intent = new Intent(this, InputActivity.class);

    startActivity(intent);
    }
}
