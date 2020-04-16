package com.example.food_waste_prototype;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> amounts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        final Context context = SettingsActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // make the app fullscreen
        TaskBarButtons(); // activate the taskbar buttons
    }


    private void TaskBarButtons() {


        ImageButton inputtaskbarbutton = findViewById(R.id.button_input5);
        ImageButton statsbutton = findViewById(R.id.button_stats5);
        ImageButton backbutton = findViewById(R.id.backbutton);


        inputtaskbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("input");
            }
        });
        statsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("stats");
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("back");
            }
        });
    }
    private void SwitchActivity(String string) {
        if (string == "input") {
            Intent intent = new Intent(this, InputActivity.class);

            startActivity(intent);
        }
        if (string == "stats") {
            Intent intent = new Intent(this, StatsActivity.class);

            startActivity(intent);
        }
        if (string == "back") {
            Intent intent = new Intent(this, InputActivity.class);

            startActivity(intent);
        }
    }
}