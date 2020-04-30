package com.example.food_waste_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        brugernavn = (EditText)findViewById(R.id.etBrugernavn);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button)findViewById(R.id.buttonLogin);
        registrer = (Button)findViewById(R.id.buttonRegistrer);


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //validate(brugernavn.getText().toString(), password.getText().toString());
                if (!brugernavn.getText().toString().equals("") && !password.getText().toString().equals("")){
                    AllowAccess();
                } else {
                    if (brugernavn.getText().toString().equals("")){
                        brugernavn.setHint("Indtast brugernavn");
                        //Toast.makeText(getApplicationContext(), "Brugernavn ikke indtastet", Toast.LENGTH_SHORT);
                    }
                    if (password.getText().toString().equals("")){
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

    }

    public void openDialog(DataBase db) {
    
        nyBrugerDialog nyBrugerDialog = new nyBrugerDialog(LoginActivity.this, db);
    }

    private void AllowAccess(){
        BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
        backgroundTask.execute("login", brugernavn.getText().toString(), password.getText().toString());
    }

    private void validate(String inputBrugernavn, String inputPassword) {

        if(DB.ValidateUser(inputBrugernavn, inputPassword)){
            DB.loggedIn = true;
            Intent intent = new Intent(LoginActivity.this, InputActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Brugernavnet eller kodeordet findes ikke", Toast.LENGTH_SHORT).show();
        }
    }


}

