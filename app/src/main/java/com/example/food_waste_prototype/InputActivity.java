package com.example.food_waste_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class InputActivity extends AppCompatActivity{

    final Context context = this;

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

        public void SetFoodWasteNumber(float foodWastenumber){
            this.fwn = foodWastenumber;
        }

        public float GetFoodWasteNumber(){
            return fwn;
        }

        ImageButton foodButton;
    }

    // List containing all food category instances
    List<Food> foodCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        ImageButton addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // Set the title of the dialog
                alertDialogBuilder.setTitle("Ny Kategori");
                alertDialogBuilder.setMessage("Tryk på bekræft for at oprette kategorien");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Bekræft", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InputActivity.this.finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Fortryd", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            }
        });
    }


}
