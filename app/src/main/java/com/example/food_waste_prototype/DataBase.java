package com.example.food_waste_prototype;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static android.content.ContentValues.TAG;

public class DataBase {

    public static  DataBase instance;

    private ArrayList<Category> categories = new ArrayList<>(); // contains a list of all the current categories and how much waste has been input into them
    private ArrayList<Input> inputs = new ArrayList<>(); // contains a list of all the past inputs
    private ArrayList<User> users = new ArrayList<>(); // contains a list of all users and the current user

    private DataBase(Context context){

    }

    public static DataBase getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DataBase(context);
        }
        return instance;
    }


    //region Methods to do with users

    public void NewUser(String userName, String password, boolean remember, boolean currentUser){
        // the idea is to call this when a new is created
        User user = new User(userName,password,remember, currentUser);
        users.add(user);
    }

    public boolean ValidateUser(String username, String password){
        // the idea is to call this when a user tries to login
        for(int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if(username.equals(user.GetUserName()) && password.equals(user.GetPassword())) {
                user.isCurrentUser=true;
                return true;
            }
        }
        return false;
    }

    public void DeleteCurrentUser(){
        for(int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if(user.isCurrentUser){
                users.remove(user);
            }
        }
    }

    //endregion

    //region Methods to do with categories

    public Category CreateCategory(String name, float pricePerUnit){
        Category cg = new Category(name, pricePerUnit);
        categories.add((cg));
        return cg;
    }

    public Category EditCategory(Category cg, String newName, float pricePerUnit){
        // the idea is to call this function upon pressing the edit category button
        if (!(cg.GetName().equals(newName))){
            cg.SetName(newName);
        }
        if(!(cg.GetPricePerUnit()==pricePerUnit)){
            cg.SetPricePerUnit(pricePerUnit);
        }
        return cg;
        // how should this function interact with the list?
    }

    public void DeleteCategory(String name){
        Category cg = GetCategory(name);
        categories.remove(cg);
        DeleteInputs(name);
    }

    public void AddFoodWaste(String categoryName, float amount, boolean foodScraps){
        Category cg= GetCategory(categoryName);
        if(!(cg ==null)) {


            if (foodScraps) {
                cg.AddFS(amount);
            } else {
                cg.AddFW(amount);
            }
            inputs.add(CreateInput(categoryName, amount, foodScraps));
        }
    }

    public ArrayList<Category> GetAllCategories(){
        return categories;
    }

    public Category GetCategory(String target){
        for(int i = 0; i < categories.size(); i++) {
            Category cg = categories.get(i);
            if(target.equals(cg.GetName())) {
                return cg;
            }
        }
        Log.d(TAG, "GetCategory: could not find target category");
        return null;
    }
    //endregion

    //region  Methods to do with inputs

    private Input CreateInput(String name, float amount, boolean foodScraps){
        Calendar time = Calendar.getInstance();
        Input ip = new Input(time.getTime(), name, amount, foodScraps);
        return  ip;
    }

    public void DeleteInput(int index){
        //delete a single input based on the number in the list
        inputs.remove(index);
    }

    public void DeleteInputs(String nameOfCategory){
        // goes trough the list of inputs and removes them if they match the name
        // the idea is to remove the related inputs if a category is deleted
        for(int i = 0; i < inputs.size(); i++) {
            Input ip = inputs.get(i);
            if(nameOfCategory.equals(ip.getName())) {
                inputs.remove(ip);
            }
        }
    }
    public ArrayList<Input> GetInputs(){
        return inputs;
    }
    //endregion

    //region Methods to do with saving, reading and deleting data
    // NOTE: I did not test if these functions work so fuck it

    public void SaveAllDataToFile(Context context){
        // I did not test if it works :)
        JSONArray cat = new JSONArray(categories);
        JSONArray in = new JSONArray(inputs);
        JSONArray use = new JSONArray(users);

        save(cat, context, "Categories");
        save(in, context, "Inputs");
        save(use, context, "Users");
    }


    private void ReadAllDate(Context context){
        read("Categories", categories, context);
        read("Inputs", inputs, context);
        read("Users", users, context);
    }

    private <T> void read(String filename, ArrayList<T> arraylist, Context context) {
        String data = ReadDataFromFile(context,filename);
        try {
            JSONArray jsonArr = new JSONArray(data);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                arraylist.add((T) jsonObj); // im amazed if this works

            }
        } catch (JSONException e) {
            Log.d(TAG, "read: Failed to read file");
        }
    }

    private void save(JSONArray jArray,Context context, String fileName){
        String json = jArray.toString();
        boolean isFilePresent = isFilePresent(context, json);
        if(isFilePresent) {
            String jsonString = ReadDataFromFile(context, json);
            // TODO:delete the file? or parse it and add the new stuff
        } else {
            boolean isFileCreated = CreateFile(context, fileName);
            if(isFileCreated) {
                Log.d(TAG, "save: filed saved succesfully");
            } else {
                Log.d(TAG, "save: failed saving file");
            }
        }
    }

    private boolean CreateFile(Context context, String fileName){
        String jsonString="{}";
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            fos.write(jsonString.getBytes());
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
    }

    private boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    public void DeleteAllData(){
        users.clear();
        categories.clear();
        users.clear();
        //TODO: delete the saved files
    }


    private String ReadDataFromFile(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }
    //endregion

    //region The inner classes

    public class Category{
        // this class should represent a category.
        // it should be read from upon startup to create the categories

        private String name;
        private float pricePerUnit;
        private float amountFW;
        private float amountFS;


        // Constructor
        Category(String name, float pricePerUnit){
            this.name = name;
            this.amountFW=0;
            this.amountFS=0;
        }

        public String GetName(){
            return name;
        }

        public float GetPricePerUnit(){
            return pricePerUnit;
        }

        public float GetAmountFW(){
            return amountFW;
        }

        public float GetAmountFS(){
            return amountFS;
        }

        private void SetName(String name) {
            this.name = name;
        }

        private void SetPricePerUnit(float amount) {
            this.pricePerUnit = amount;
        }

        private void AddFW(float amount) {
            amountFW+=amount;
        }

        private void AddFS(float amount) {
            amountFS+=amount;
        }



        Button foodButton;
    }

    private class User{
        // Should represent a user
        private String userName;
        private String password;
        private boolean rememberLogin;
        private boolean isCurrentUser;

        User(String userName, String password, boolean remember, boolean isCurrentUser){
            this.userName=userName;
            this.password=password;
            this.rememberLogin=remember;
            this.isCurrentUser = isCurrentUser;
        }

        private void setUserName(String name){
            this.userName=name;
        }

        private void setPassword(String passCode){
            this.password=passCode;
        }

        private String GetUserName(){
            return userName;
        }

        private String GetPassword(){
            return password;
        }

        private boolean GetSsCurrentUser(){
            return isCurrentUser;
        }

        private boolean GetRememberLogin(){
            return rememberLogin;
        }
    }

    public class Input{
        // The idea is this class should be used to in combination with the history/edit popups
        private Date time;
        private String name;
        private float amount;
        private boolean foodScraps;

        Input(Date  time, String name, float amount, boolean foodScraps){
            this.time= time;
            this.name=name;
            this.amount=amount;
            this.foodScraps=foodScraps;
        }

        public String getName() {
            return name;
        }

        public Date getTime(){
            return time;
        }

        public float amount(){
            return amount;
        }

        public boolean getfoodScraps(){
            return foodScraps;
        }
    }
    //endregion
}
