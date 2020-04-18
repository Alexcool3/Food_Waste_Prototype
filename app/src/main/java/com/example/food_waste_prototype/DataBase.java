package com.example.food_waste_prototype;

import android.util.Log;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static android.content.ContentValues.TAG;

public class DataBase {

    public static final DataBase instance = new DataBase();
    private ArrayList<Category> categories = new ArrayList<>(); // contains a list of all the current categories
    private ArrayList<Input> inputs = new ArrayList<>(); // contains a list of all the past inputs
    private ArrayList<User> users = new ArrayList<>(); // contains a list of all current users

    DataBase(){
       // ReadDataFromFile();
    }


    //region Methods to do with users

    public void NewUser(String userName, String password, boolean remember){
        // the idea is to call this when a new is created
        User user = new User(userName,password,remember);
        users.add(user);
    }

    public boolean ValidateUser(String username, String password){
        // the idea is to call this when a user tries to login
        for(int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if(username.equals(user.GetUserName()) && password.equals(user.GetPassword())) {
                return true;
            }
        }

        return false;
    }
    //endregion

    //region Methods to do with categories

    public Category CreateCategory(String name, float pricePerUnit){
        Category cg = new Category(name, pricePerUnit);
        categories.add((cg));
        return cg;
    }

    public Category EditCategory(Category cg, String newName, float pricePerunit){
        // the idea is to call this function upon pressing the edit category button
        if (!(cg.GetName().equals(newName))){
            cg.SetName(newName);
        }
        if(!(cg.GetPricePerUnit()==pricePerunit)){
            cg.SetPricePerUnit(pricePerunit);
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
        if (foodScraps){
            cg.AddFS(amount);
        } else{
            cg.AddFW(amount);
        }
        inputs.add( CreateInput(categoryName, amount, foodScraps));
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
    //region  Methods to do with the input class

    private Input CreateInput(String name, Float amount, boolean foodScraps){
        Input ip = new Input(Calendar.getInstance().getTime(), name, amount, foodScraps);
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

    //region Methods to do with saving and reading data

    public void SaveDataToFile(){
        //TODO: save the category list to a file
    }

    public void ReadDataFromFile(){
        //TODO: Read the data upon startup;
    }
    //endregion

    //region The inner classes

    private class Category{
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

        public float GetAmountFW(float price){
            return amountFW;
        }

        public void SetName(String name) {
            this.name = name;
        }

        public void SetPricePerUnit(float amount) {
            this.pricePerUnit = amount;
        }

        public void AddFW(float amount) {
            amountFW+=amount;
        }

        public void AddFS(float amount) {
            amountFS+=amount;
        }

        Button foodButton;
    }

    private class User{
        // Should represent a user
        private String userName;
        private String password;
        private boolean rememberLogin;

        User(String userName, String password, boolean remember){
            this.userName=userName;
            this.password=password;
            this.rememberLogin=remember;
        }

        private void setUserName(String name){
            this.userName=name;
        }

        private void setPassword(String word){
            this.password=word;
        }

        private String GetUserName(){
            return userName;
        }

        private String GetPassword(){
            return password;
        }
    }

    private class Input{
        // The idea is this class should be used to in combination with the history/edit popups
        private Date  time;
        private String name;
        private Float amount;
        private boolean foodScraps;

        Input(Date  time, String name, Float amount, boolean foodScraps){
            this.time= time;
            this.name=name;
            this.amount=amount;
            this.foodScraps=foodScraps;
        }

        public String getName() {
            return name;
        }
    }
    //endregion
}
