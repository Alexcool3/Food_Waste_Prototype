package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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
    private static int currentID = 0;



    private Context context;
    public BackgroundTask(Context context){
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {

        sharedPreferences = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
        editor.putString("flag", "0");
        //editor.putBoolean("loggedIn", true);
        editor.commit();


        final String urlRegistration = "https://lortesiden.000webhostapp.com/register_user.php";
        final String urlLogin = "https://lortesiden.000webhostapp.com/login.php";
        final String urlCategories = "https://lortesiden.000webhostapp.com/newCategory.php";
        final String urlDeleteCategory = "https://lortesiden.000webhostapp.com/deleteCategory.php";
        final String urlInput = "https://lortesiden.000webhostapp.com/inputs.php";
        final String urlDeleteInput = "https://lortesiden.000webhostapp.com/deleteInput.php";
        final String urlEditInput = "https://lortesiden.000webhostapp.com/editInput.php";
        final String urlEditCategory = "https://lortesiden.000webhostapp.com/editCategory.php";
        final String urlResetUser = "https://lortesiden.000webhostapp.com/resetUser.php";
        final String urlDeleteUser = "https://lortesiden.000webhostapp.com/deleteUser.php";

        String task = params[0];

        if (task.equals("register")){
            String regName = params[1];
            String regPassword = params[2];
            try {
                URL url = new URL(urlRegistration);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//s
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //send the username and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();//s
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_password","UTF-8")+"="+URLEncoder.encode(regPassword,"UTF-8");//s
                bufferedWriter.write(myData);//s
                bufferedWriter.flush();//s
                bufferedWriter.close();//s
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
                inputStream.close();//s
                httpURLConnection.disconnect();

                System.out.println(dataResponse);

                editor.putString("flag","register");//s
                editor.commit();//s
                return  dataResponse;

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

        if (task.equals("categories")){
            String regName = params[1];
            String regCategory = params[2];
            String regPrice = params[3];
            try {
                URL url = new URL(urlCategories);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//s
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //send the username and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();//s
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_category","UTF-8")+"="+URLEncoder.encode(regCategory,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_price","UTF-8")+"="+URLEncoder.encode(regPrice,"UTF-8");//s
                bufferedWriter.write(myData);//s
                bufferedWriter.flush();//s
                bufferedWriter.close();//s
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
                inputStream.close();//s
                httpURLConnection.disconnect();

                System.out.println(dataResponse);

                editor.putString("flag","categories");//s
                editor.commit();//s
                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (task.equals("deleteCategory")){
            String regName = params[1];
            String regCategory = params[2];

            try {
                URL url = new URL(urlDeleteCategory);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+ URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_category","UTF-8")+"="+URLEncoder.encode(regCategory,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                Log.d("Delete!", "Deleted category!");
                editor.putString("flag","inputs");
                editor.commit();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (task.equals("input")){
            String regName = params[1];
            String regWeight = params[2];
            String regCategory = params[3];
            String regType = params[4];
            String regTime = params[5];
            String regDate = params[6];
            try {
                URL url = new URL(urlInput);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //send the username and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();//s
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_weight","UTF-8")+"="+URLEncoder.encode(regWeight,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_category","UTF-8")+"="+URLEncoder.encode(regCategory,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_type","UTF-8")+"="+URLEncoder.encode(regType,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_time","UTF-8")+"="+URLEncoder.encode(regTime,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_date","UTF-8")+"="+URLEncoder.encode(regDate,"UTF-8");
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
                inputStream.close();//s
                httpURLConnection.disconnect();

                System.out.println(dataResponse);

                editor.putString("flag","input");
                editor.commit();//s
                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (task.equals("deleteInput")){
            String regName = params[1];
            String regId = params[2];
            try {
                URL url = new URL(urlDeleteInput); // s
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//s
                httpURLConnection.setRequestMethod("POST");//
                httpURLConnection.setDoOutput(true);//s
                httpURLConnection.setDoInput(true);

                //send the email and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();//s
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");//s
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_id","UTF-8")+"="+URLEncoder.encode(regId,"UTF-8");//s
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

                editor.putString("flag","deleteInput");//s
                editor.commit();//s
                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        if (task.equals("editInput")){
            String regName = params[1];
            String regWeight = params[2];
            String regId = params[3];
            String regType = params[4];
            try {
                URL url = new URL(urlEditInput); // s
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//s
                httpURLConnection.setRequestMethod("POST");//
                httpURLConnection.setDoOutput(true);//s
                httpURLConnection.setDoInput(true);

                //send the email and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();//s
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");//s
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_weight","UTF-8")+"="+URLEncoder.encode(regWeight,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_id","UTF-8")+"="+URLEncoder.encode(regId,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_type","UTF-8")+"="+URLEncoder.encode(regType,"UTF-8");//s
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

                editor.putString("flag","editInput");
                editor.commit();//s
                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        if (task.equals("editCategory")){
            String regName = params[1];
            String regId = params[2];
            String regCategory = params[3];
            String regPrice = params[4];

            try {
                URL url = new URL(urlEditCategory);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//s
                httpURLConnection.setRequestMethod("POST");//
                httpURLConnection.setDoOutput(true);//s
                httpURLConnection.setDoInput(true);

                //send the email and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();//s
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");//s
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_id","UTF-8")+"="+URLEncoder.encode(regId,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_category","UTF-8")+"="+URLEncoder.encode(regCategory,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_price","UTF-8")+"="+URLEncoder.encode(regPrice,"UTF-8");//s
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

                editor.putString("flag","editCategory");
                editor.commit();//s
                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        if (task.equals("resetUser")){
            String regName = params[1];
            try {
                URL url = new URL(urlResetUser);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//s
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //send the email and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");//s
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8");
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

                editor.putString("flag","resetUser");
                editor.commit();
                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (task.equals("deleteUser")){
            String regName = params[1];
            try {
                URL url = new URL(urlDeleteUser);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_username","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8");
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
                inputStream.close();//s
                httpURLConnection.disconnect();

                System.out.println(dataResponse);

                editor.putString("flag","deleteUser");
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
            String test = "false";
            String id = "";
            String[] serverResponse = s.split(",");
            test = serverResponse[0];
            //Log.d("serverResponse", serverResponse[0]);
            if (test.equals("true")){
                id = serverResponse[1];
                DataBase.userID = Integer.parseInt(id);
                Log.d("user id", "id: " + DataBase.userID);
                editor.putString("flag","deleteInput");//s
                editor.commit();//s
                editor.putString("id", id);
                editor.commit();
                editor.putString("username", DataBase.instance.username);
                editor.commit();
                editor.putString("password", DataBase.instance.password);
                editor.commit();
                Intent intent = new Intent(context, InputActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
        if(flag.equals("login")){
            String test = "false";
            String username = "";
            String password = "";
            String id = "";
            String[] serverResponse = s.split(",");
            test = serverResponse[0];

            if(test.equals("true")){
                username = serverResponse[1];
                password = serverResponse[2];
                id = serverResponse[3];
                DataBase.userID = Integer.parseInt(id);
                editor.putString("username",username);
                editor.commit();
                editor.putString("password",password);
                editor.commit();
                //DataBase.instance.username = sharedPreferences.getString("username", "");
                //DataBase.instance.password = sharedPreferences.getString("password", "");
                //Log.d("Check", "username: " + DataBase.instance.username + " password: " + DataBase.instance.password);
                Intent intent = new Intent(context, InputActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
            }else{
                new CustomToast("Brugernavnet eller kodeordet findes ikke", context);
                Intent intent = new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);

            }
        }else{
            //new CustomToast("Login fejlede", context);

        }

        if (flag.equals("categories")){
            String test = "false";
            String id = "";
            String[] serverResponse = s.split(",");
            test = serverResponse[0];
            Log.d("serverResponse", serverResponse[0]);
            if (test.equals("true")){
                id = serverResponse[1];
                Category latestCategory = DataBase.instance.GetLastCategoryInstance();
                latestCategory.SetID(Integer.parseInt(id));
                Log.d("Database Category", "ID: " + DataBase.instance.GetLastCategoryInstance().GetID());
            }
        }

        if (flag.equals("input")){
            String test = "false";
            String id = "";
            String[] serverResponse = s.split(",");
            test = serverResponse[0];
            //Log.d("serverResponse", serverResponse[0]);
            if (test.equals("true")){
                id = serverResponse[1];

                DataBase.instance.GetLastInputInstance().SetID(Integer.parseInt(id));
                editor.putInt("Input"+DataBase.instance.GetLastInputInstance().GetID(), DataBase.instance.GetLastInputInstance().GetID());
                editor.commit();
                Log.d("DatabaseInput", "ID: " + DataBase.instance.GetLastInputInstance().GetID());
            }
        }

        if (flag.equals("deleteInput")){

        }

        if (flag.equals("editInput")){

        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}