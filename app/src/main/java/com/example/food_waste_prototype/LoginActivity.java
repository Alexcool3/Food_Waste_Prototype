package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


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
                BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
                backgroundTask.execute("login", brugernavn.getText().toString(), password.getText().toString());
                validate(brugernavn.getText().toString(), password.getText().toString());
            }
        });


        registrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(DB);
                BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
                backgroundTask.execute("register", brugernavn.getText().toString(), password.getText().toString());
            }
        });

    }

    public void openDialog(DataBase db) {
    
        nyBrugerDialog nyBrugerDialog = new nyBrugerDialog(LoginActivity.this, db);
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

    class BackgroundTask extends AsyncTask<String, Void, String>{

        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        private Context context;

        public BackgroundTask(Context context){
            this.context = context;
        }


        @Override
        protected String doInBackground(String... params) {

            sharedPreferences = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("flag", "0");
            editor.commit();

            String urlRegistration = "https://foodwaste.scm.gear.host/registration.php";
            String urlLogin = "https://foodwaste.scm.gear.host/login.php";
            String task = params[0];

            if (task.equals("register")){
                String regName = params[1];
                String regPassword = params[2];

                try {
                    URL url = new URL(urlRegistration);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String myData = URLEncoder.encode("identifier_name","UTF-8")+"="+ URLEncoder.encode(regName,"UTF-8")+"&"
                            +URLEncoder.encode("identifier_password","UTF-8")+"="+URLEncoder.encode(regPassword,"UTF-8");
                    bufferedWriter.write(myData);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStream.close();
                    editor.putString("flag","register");
                    editor.commit();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if(task.equals("login")){
                String loginUsername = params[1];
                String loginPassword = params[2];
                try {
                    URL url = new URL(urlLogin);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    //send the email and password to the database
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(loginUsername,"UTF-8")+"&"
                            +URLEncoder.encode("identifier_loginPassword","UTF-8")+"="+URLEncoder.encode(loginPassword,"UTF-8");
                    bufferedWriter.write(myData);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    //get response from the database
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String dataResponse = "";
                    String inputLine = "";
                    while((inputLine = bufferedReader.readLine()) != null){
                        dataResponse += inputLine;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(dataResponse);

                    editor.putString("flag","login");
                    editor.commit();
                    return  dataResponse;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            String flag = sharedPreferences.getString("flag","0");

            if(flag.equals("register")) {
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
            }
            if(flag.equals("login")){
                String test = "false";
                String name = "";
                String email = "";
                String[] serverResponse = s.split("[,]");
                test = serverResponse[0];
                name = serverResponse[1];
                email = serverResponse[2];

                if(test.equals("true")){
                    editor.putString("name",name);
                    editor.commit();
                    editor.putString("email",email);
                    editor.commit();
                    Intent intent = new Intent(context,InputActivity.class);
                    context.startActivity(intent);
                }else{
                    //display("Login Failed...", "That email and password do not match our records :(.");
                    Log.d("login failed", "login failed");
                }
            }else{
                //display("Login Failed...","Something weird happened :(.");
                Log.d("login failed", "login failed");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        public void display(String title, String message){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.show();
        }



    }
}

