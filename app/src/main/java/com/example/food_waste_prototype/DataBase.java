package com.example.food_waste_prototype;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

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
import java.time.LocalDate;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DataBase {
    public static String username = "";
    public static String password = "";
    public static int userID = 0;
    int loggedIN = 0;
    boolean loggedIn = false;

    public static DataBase instance;
    public static WhichWaste waste = WhichWaste.foodwaste;
    private ArrayList<Category> categories = new ArrayList<>(); // contains a list of all the current categories and how much waste has been input into them
    private ArrayList<Input> inputs = new ArrayList<>(); // contains a list of all the past inputs
    private ArrayList<User> users = new ArrayList<>(); // contains a list of all users and the current user
    public float purchases = 0;

    private DataBase(Context context) {

    }

    public enum WhichWaste {
        foodwaste,
        foodscraps
    }

    public void flipEnum() {
        if (waste.equals(WhichWaste.foodscraps)) {
            waste = WhichWaste.foodwaste;
        } else {
            waste = WhichWaste.foodscraps;
        }
    }

    public String GetEnumToString() {
        if (waste.equals(WhichWaste.foodscraps)) {
            return "Mad Affald";
        } else {
            return "Mad Spild";
        }
    }

    public static DataBase getInstance(Context context) {


        if (instance == null) {
            instance = new DataBase(context);
        }
        return instance;
    }

    public void clearData() {
        categories.clear();
        inputs.clear();
    }

    public void wipeinator() {
        users.clear();
        categories.clear();
        inputs.clear();
    }


    //region Methods to do with users

    public void NewUser(String userName, String password, String email, int days, boolean remember, boolean currentUser) {
        // the idea is to call this when a new is created
        User user = new User(userName, password, email, days, remember, currentUser);
        users.add(user);
    }

    public boolean ValidateUser(String username, String password) {
        // the idea is to call this when a user tries to login
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (username.equals(user.GetUserName()) && password.equals(user.GetPassword())) {
                user.isCurrentUser = true;
                return true;
            }
        }
        return false;
    }

    public DataBase.User GetCurrentUser() {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.isCurrentUser) {
                return user;
            }
        }
        return null;
    }

    public void DeleteCurrentUser() {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.isCurrentUser) {
                users.remove(user);
            }
        }
    }

    //endregion

    //region Methods to do with categories

    public Category CreateCategory(String name, float pricePerUnit, Context context) {
        Category cg = new Category(context, name, pricePerUnit);
        categories.add((cg));
        return cg;

    }

    public Category EditCategory(Category cg, String newName, float pricePerUnit) {
        // the idea is to call this function upon pressing the edit category button
        if (!(cg.GetName().equals(newName))) {
            cg.SetName(newName);
        }
        if (!(cg.GetPricePerUnit() == pricePerUnit)) {
            cg.SetPricePerUnit(pricePerUnit);
        }
        return cg;
        // how should this function interact with the list?
    }

    public void DeleteCategory(String name) {
        Category cg = GetCategory(name);
        categories.remove(cg);
        DeleteInputs(name);

    }

    public void AddFoodWaste(String categoryName, float amount, boolean foodScraps) {
        Category cg = GetCategory(categoryName);
        if (!(cg == null)) {


            if (foodScraps) {
                cg.AddFS(amount);
            } else {
                cg.AddFW(amount);
            }
        }
    }

    public ArrayList<Category> GetAllCategories() {
        return categories;
    }

    public Category GetCategory(String target) {
        for (int i = 0; i < categories.size(); i++) {
            Category cg = categories.get(i);
            if (target.equals(cg.GetName())) {
                return cg;
            }
        }
        Log.d(TAG, "GetCategory: could not find target category");
        return null;
    }
    //endregion

    //region  Methods to do with inputs

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Input CreateInput(String name, float amount, boolean foodScraps){
        //TODO: Does it work on Nikos old ass phone?
        LocalDate date = LocalDate.now();
        Input ip = new Input(date, name, amount, foodScraps);
        // Log.d("hej", "input time at start:"+ ip.TimetoCalendar().getTime().toString() );
        inputs.add(ip);
        return ip;
    }

    public void DeleteInput(Input input) {
        //delete a single input based on the number in the list
        Category cg = GetCategory(input.getName());
        if (input.getfoodScraps()) {
            cg.AddFS(-input.getamount());
        } else {
            cg.AddFW(-input.getamount());
        }
        inputs.remove(input);

    }

    public void DeleteInputs(String nameOfCategory) {
        // goes trough the list of inputs and removes them if they match the name
        // the idea is to remove the related inputs if a category is deleted
        for (int i = 0; i < inputs.size(); i++) {
            Input ip = inputs.get(i);
            if (nameOfCategory.equals(ip.getName())) {
                inputs.remove(ip);
            }
        }
    }

    public ArrayList<Input> GetInputs() {
        return inputs;
    }
    //endregion

    //region Methods to do with saving, reading and deleting data
    // NOTE: I did not test if these functions work so fuck it

    public void SaveAllDataToFile(Context context) {
        // I did not test if it works :)
        JSONArray cat = new JSONArray(categories);
        cat.put(CreateCategory("OST", 33, context));
        JSONArray in = new JSONArray(inputs);
        JSONArray use = new JSONArray(users);

        save(cat, context, "Categories");
        save(in, context, "Inputs");
        save(use, context, "Users");
    }


    public void ReadAllDate(Context context) {
        try {
            read("Categories", categories, context);
        } catch (NullPointerException e) {
            Log.d("load", " Failed to load categories");
        }
        try {
            read("Inputs", inputs, context);
        } catch (NullPointerException e) {
            Log.d("load", " Failed to load inputs");
        }

        try {
            read("Users", users, context);
        } catch (NullPointerException e) {
            Log.d("load", " Failed to load users");
        }


    }

    private <T> void read(String filename, ArrayList<T> arraylist, Context context) {
        String data = ReadDataFromFile(context, filename);
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

    private void save(JSONArray jArray, Context context, String fileName) {
        String json = jArray.toString();
        boolean isFilePresent = isFilePresent(context, json);
        if (isFilePresent) {
            String jsonString = ReadDataFromFile(context, json);
            // TODO:delete the file? or parse it and add the new stuff
        } else {
            boolean isFileCreated = CreateFile(context, fileName);
            if (isFileCreated) {
                Log.d(TAG, "save: filed saved succesfully at ");
            } else {
                Log.d(TAG, "save: failed saving file");
            }
        }
    }

    private boolean CreateFile(Context context, String fileName) {
        String jsonString = "{}";
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
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

    public void DeleteAllData() {
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


    public class User {
        // Should represent a user
        private String userName;
        private String password;
        private String email;
        private int days;
        private boolean rememberLogin;
        private boolean isCurrentUser;

        User(String userName, String password, String email, int days, boolean remember, boolean isCurrentUser) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.days = days;
            this.rememberLogin = remember;
            this.isCurrentUser = isCurrentUser;
        }

        public void setUserName(String name) {
            this.userName = name;
        }

        public void setPassword(String passCode) {
            this.password = passCode;
        }

        public String GetUserName() {
            return userName;
        }

        public String GetPassword() {
            return password;
        }

        private boolean GetSsCurrentUser() {
            return isCurrentUser;
        }

        private boolean GetRememberLogin() {
            return rememberLogin;
        }
    }

    public class Input {
        // The idea is this class should be used to in combination with the history/edit popups
        private LocalDate time;
        private String name;
        private float amount;
        private boolean foodScraps;
        private int id = 0;
        private int foodType = 0; // 0 = foodscraps, 1 = foodwaste.

        Input(LocalDate time, String name, float amount, boolean foodScraps) {
            this.time = time;
            this.name = name;
            this.amount = amount;
            this.foodScraps = foodScraps;
        }

        public String getName() {
            return name;
        }

        public LocalDate getTime() {
            return time;
        }

        public float getamount() {
            return amount;
        }

        public boolean getfoodScraps() {
            return foodScraps;
        }

        public void SetfoodScraps(boolean scraps) {
            foodScraps = scraps;
        }

        public void SetAmount(float amountings) {
            amount = amountings;
        }

        public void SetName(String name) {
            this.name = name;
        }

        public String GetWasteString() {
            if (foodScraps) {
                return "Mad Affald";
            } else {
                return "Mad Spild";
            }
        }

        public void SetID(int id){
            this.id = id;
            Log.d("Input instance", "ID:" + id);
        }

        public int GetID(){
            return id;
        }

       /* public Calendar TimetoCalendar(){
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            return cal;
        }*/
    }
    //endregion

    public DataBase.Input GetLastInputInstance(){
        return inputs.get(inputs.size()-1);
    }

    public Category GetLastCategoryInstance(){
        return categories.get(categories.size()-1);
    }

}
