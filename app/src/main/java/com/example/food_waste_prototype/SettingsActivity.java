package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        final TaskBarView taskbar = findViewById(R.id.taskBarView);
        taskbar.taskings(context);
    }


    private void TaskBarButtons() {


        ImageButton backbutton = findViewById(R.id.backbutton); //back button
        Button logoff = findViewById(R.id.logout);  //log off button
        Button indstillinger = findViewById(R.id.change); //change button
        Button slet = findViewById(R.id.erase); //nulstil button
        Button nulstil = findViewById(R.id.delete); //slet button
        Button skema = findViewById(R.id.erase2); //spørgeskema button


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("back");
            }
        });
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog("login");
                // SwitchActivity("login");
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
                openDialog("warning");
            }
        });
        slet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog("delete");
            }
        });
        skema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { SwitchActivity("skema");
            }
        });
    }

    public void openDialog(final String action) {
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(this); // Context, this, etc.
        View newView = getLayoutInflater().inflate(R.layout.dialog_activity, null);
        AlertDialog.setView(newView);

        Button bt = newView.findViewById(R.id.dialog_ok);
        Button btno = newView.findViewById(R.id.dialog_cancel);

        final AlertDialog dialog = AlertDialog.create();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                SwitchActivity(action);
            }
        });
        btno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        if (action.equals("login")) {


            TextView tv = newView.findViewById(R.id.dialog_info);
            tv.setText("Er du sikker på du vil logge af?");
        }

        dialog.show();
    }

    public void openDialoglog() {

        new nyBrugerDialog(SettingsActivity.this, DataBase.getInstance(SettingsActivity.this), "settingsactivity");

    }

    private void SwitchActivity(String string) {

        if (string == "back") {
            finish();
        }
        if (string == "login") {
            new CustomToast("Du er blevet logget af", SettingsActivity.this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        if (string == "warning") {
            DataBase.getInstance(SettingsActivity.this).clearData();
            SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
            DataBase.instance.username = sharedPreferences.getString("username", "");
            BackgroundTask backgroundTask = new BackgroundTask(SettingsActivity.this);
            backgroundTask.execute("resetUser", DataBase.instance.username);
            new CustomToast("Din data er blevet nulstillet", SettingsActivity.this);

            // Intent intent = new Intent(this, SettingsActivity.class);

            //startActivity(intent);
        }
        if (string == "delete") {
            DataBase.getInstance(SettingsActivity.this).wipeinator();
            SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
            DataBase.instance.username = sharedPreferences.getString("username", "");
            BackgroundTask backgroundTask = new BackgroundTask(SettingsActivity.this);
            backgroundTask.execute("deleteUser", DataBase.instance.username);
            new CustomToast("Din bruger og data er blevet slettet", SettingsActivity.this);

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        if (string == "same") {
            openDialoglog();
            //Intent intent = new Intent(this, SettingsActivity.class);
            //startActivity(intent);
        }

        if (string == "skema") {
            Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("https://forms.gle/T3sKJfoWc4wU4VsJ7") );

            startActivity( browse );
        }
    }
}