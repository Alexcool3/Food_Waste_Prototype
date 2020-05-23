package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        final CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        registrer = (Button) findViewById(R.id.buttonRegistrer);
        final SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validate(brugernavn.getText().toString(), password.getText().toString());
                if (!brugernavn.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    validate(brugernavn.getText().toString(), password.getText().toString());
                    if(checkbox.isChecked()){
                        editor.putInt("loggedIN", 1);
                        editor.commit();
                    } else {
                        editor.putInt("loggedIN", 0);
                        editor.commit();
                    }
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
                AllowAccess();
                //DataBase.username = brugernavn.getText().toString();
            }
        });


        registrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(DB);
            }
        });





    }

    public void openDialog(DataBase db) {

        nyBrugerDialog nyBrugerDialog = new nyBrugerDialog(LoginActivity.this, db);
    }

    private void AllowAccess() {
        /*
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null &&
                activeNetworkInfo.isConnectedOrConnecting();
        if (isConnected){ // Check for cellular connectivity.
            BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
            backgroundTask.execute("login", brugernavn.getText().toString(), password.getText().toString());
            //DataBase.username = brugernavn.getText().toString();
            //DataBase.password = password.getText().toString();

        }else{
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }
        */
        Intent intent = new Intent(LoginActivity.this, InputActivity.class);
        startActivity(intent);
    }

    private void validate(String inputBrugernavn, String inputPassword) {

        DB.NewUser(inputBrugernavn, inputPassword, "Fisk", 7, true, true);
        // else {
        //   Toast.makeText(getApplicationContext(), "Brugernavnet eller kodeordet findes ikke", Toast.LENGTH_SHORT).show();
        //}
    }


}

