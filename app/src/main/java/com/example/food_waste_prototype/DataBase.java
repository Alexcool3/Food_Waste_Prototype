package com.example.food_waste_prototype;

import android.widget.Button;

import java.sql.Struct;

public class DataBase {

    public static final DataBase instance = new DataBase();


    public class Food{
        private float weight;
        private float price;
        private float pricePerUnit;
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

        public void SetFoodWasteNumber(float foodWastenumber){
            this.fwn = foodWastenumber;
        }

        public float GetFoodWasteNumber(){
            return fwn;
        }

        public void SetPricePerUnit(float pricePerUnit){
            this.pricePerUnit = pricePerUnit;
        }

        public float GetPricePerUnit(){
            return pricePerUnit;
        }

        public void SetName(String name){
            this.name = name;
        }

        public String GetName(){
            return name;
        }

        Button foodButton;
    }
}
