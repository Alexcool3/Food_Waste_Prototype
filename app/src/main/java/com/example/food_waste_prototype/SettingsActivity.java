package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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


        ImageButton inputtaskbarbutton = findViewById(R.id.button_input5); //input button
        ImageButton statsbutton = findViewById(R.id.button_stats5); //stats button
        ImageButton backbutton = findViewById(R.id.backbutton); //back button
        Button logoff = findViewById(R.id.logout);  //log off button
        Button indstillinger = findViewById(R.id.change); //change button
        Button slet = findViewById(R.id.erase); //nulstil button
        Button nulstil = findViewById(R.id.delete); //slet button

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
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("login");
            }
        });
        indstillinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("same");
            }
        });
        nulstil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("warning");
            }
        });
        slet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("delete");
            }
        });

    }
    public void openDialog() {
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(this); // Context, this, etc.
        View newView = getLayoutInflater().inflate(R.layout.dialog_activity, null);
        //String dialog_title = "Bekræft";

       // dialog.setTitle(dialog_title);
        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();
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
            finish();
        }
        if (string == "login") {
            openDialog();
            Toast.makeText(SettingsActivity.this,
                    "Du af blevet logget af", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
        }
        if (string == "warning") {
            Toast.makeText(SettingsActivity.this,
                    "Failed", Toast.LENGTH_LONG).show();

           // Intent intent = new Intent(this, SettingsActivity.class);

            //startActivity(intent);
        }
        if (string == "delete") {
            openDialog();
            Toast.makeText(SettingsActivity.this,
                    "Din bruger er sletet", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
        }
        if (string == "same") {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
    }
}