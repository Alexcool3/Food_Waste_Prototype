package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class BackgroundTask extends AsyncTask<String, Void, String> {

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

        final String urlRegistration = "https://lortesiden.000webhostapp.com/register.php";
        final String urlLogin = "https://lortesiden.000webhostapp.com/login.php";
        final String urlInputs = "https://lortesiden.000webhostapp.com/inputs.php";
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
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+ URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_password","UTF-8")+"="+URLEncoder.encode(regPassword,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                Log.d("Works!", "Works!");
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
                URL url = new URL(urlLogin); // s
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//s
                httpURLConnection.setRequestMethod("POST");//
                httpURLConnection.setDoOutput(true);//s
                httpURLConnection.setDoInput(true);

                //send the email and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();//s
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");//s
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(loginUsername,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_password","UTF-8")+"="+URLEncoder.encode(loginPassword,"UTF-8");//s
                bufferedWriter.write(myData);//s
                bufferedWriter.flush();//s
                bufferedWriter.close();//s
                outputStream.close();

                //get response from the database
                InputStream inputStream = httpURLConnection.getInputStream();//s
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dataResponse = "";
                String inputLine = "";
                while((inputLine = bufferedReader.readLine()) != null){
                    dataResponse += inputLine;
                }
                bufferedReader.close();
                inputStream.close();//s
                httpURLConnection.disconnect();

                System.out.println(dataResponse);

                editor.putString("flag","login");//s
                editor.commit();//s
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
            Intent intent = new Intent(context, InputActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        if(flag.equals("login")){
            String test = "false";
            String username = "";
            String password = "";
            String[] serverResponse = s.split(",");
            Log.d("serverResponse", serverResponse[0]);
            test = serverResponse[0];
            username = serverResponse[1];
            password = serverResponse[2];

            if(test.equals("true")){
                editor.putString("username",username);
                editor.commit();
                editor.putString("password",password);
                editor.commit();
                Intent intent = new Intent(context, InputActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else{
                Toast.makeText(context, "Brugernavnet eller kodeordet findes ikke", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "login failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}