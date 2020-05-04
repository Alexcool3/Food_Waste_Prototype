package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    DataBase DB;
    private EditText brugernavn;
    private EditText password;
    private Button login;
    private Button registrer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = DataBase.getInstance(LoginActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        brugernavn = (EditText) findViewById(R.id.etBrugernavn);
        CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        registrer = (Button) findViewById(R.id.buttonRegistrer);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validate(brugernavn.getText().toString(), password.getText().toString());
                if (!brugernavn.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    validate(brugernavn.getText().toString(), password.getText().toString());
                    AllowAccess();
                } else {
                    if (brugernavn.getText().toString().equals("")) {
                        brugernavn.setHint("Indtast brugernavn");
                        //Toast.makeText(getApplicationContext(), "Brugernavn ikke indtastet", Toast.LENGTH_SHORT);
                    }
                    if (password.getText().toString().equals("")) {
                        password.setHint("Indtast kodeord");
                        //Toast.makeText(getApplicationContext(), "Kodeord ikke indtastet", Toast.LENGTH_SHORT);
                    }
                }
                DataBase.username = brugernavn.getText().toString();
            }
        });


        registrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(DB);
            }
        });


        if( checkbox.isChecked()){
            // do something
        };


    }

    public void openDialog(DataBase db) {

        nyBrugerDialog nyBrugerDialog = new nyBrugerDialog(LoginActivity.this, db);
    }

    private void AllowAccess() {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null &&
                activeNetworkInfo.isConnectedOrConnecting();
        if (isConnected){ // Check for cellular connectivity.
            BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
            backgroundTask.execute("login", brugernavn.getText().toString(), password.getText().toString());
            DataBase.username = brugernavn.getText().toString();
        }else{
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }

    }

    private void validate(String inputBrugernavn, String inputPassword) {

        DB.NewUser(inputBrugernavn, inputPassword, "Fisk", 7, true, true);
        // else {
        //   Toast.makeText(getApplicationContext(), "Brugernavnet eller kodeordet findes ikke", Toast.LENGTH_SHORT).show();
        //}
    }


}

