package com.example.food_waste_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class InputActivity extends AppCompatActivity{

    Button addButton;
    // Initial Food category class
    public class Food{
        private float weight;
        private float price;
        private float fwn; // Food Waste Number
        String name;

        // Constructor for setting name
        Food(String name){
            this.name = name;
        }

        public void SetWeight(float weight){
            this.weight = weight;
        }

        public float GetWeight(){
            return weight;
        }

        public void SetPrice(float price){
            this.price = price;
        }

        public float GetPrice(){
            return price;
        }

        public void SetFoodWastenumber(float foodWastenumber){
            this.fwn = foodWastenumber;
        }

        public float GetFoodWasteNumber(){
            return fwn;
        }

        Button foodButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }



}
