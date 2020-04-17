package com.example.food_waste_prototype;


import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText brugernavn;
    private EditText password;
    private Button login;
    private Button registrer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        brugernavn = (EditText)findViewById(R.id.etBrugernavn);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button)findViewById(R.id.buttonLogin);
        registrer = (Button)findViewById(R.id.buttonRegistrer);


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                validate(brugernavn.getText().toString(), password.getText().toString());
            }
        });


    }

    private void validate(String inputBrugernavn, String inputPassword) {
        if((inputBrugernavn == "Nikolaj") && (inputPassword == "ersej")){
            Intent intent = new Intent(LoginActivity.this, InputActivity.class);
            startActivity(intent);
        }
    }

}
