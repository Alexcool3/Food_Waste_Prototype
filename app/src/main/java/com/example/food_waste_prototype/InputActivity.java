package com.example.food_waste_prototype;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class InputActivity extends AppCompatActivity{

    // Initial Food category class
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

    // List containing all food category instances
    ArrayList<Food> foodCategories = new ArrayList<Food>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        final Context context = InputActivity.this;

        // Screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Button addButton = findViewById(R.id.addButton);

        // Add new category button + dialog
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // Set the title of the dialog
                final Context context = alertDialogBuilder.getContext();
                final LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                alertDialogBuilder.setTitle("Ny Kategori");
                alertDialogBuilder.setMessage("Tryk på bekræft for at oprette kategorien");
                alertDialogBuilder.setCancelable(false);

                final EditText foodCategoryName = new EditText(InputActivity.this);
                foodCategoryName.setHint("Navn");
                foodCategoryName.setInputType(InputType.TYPE_CLASS_TEXT);
                alertDialogBuilder.setView(foodCategoryName);
                linearLayout.addView(foodCategoryName);

                final EditText price = new EditText(InputActivity.this);
                price.setHint("Pris per kilo");
                price.setInputType(InputType.TYPE_CLASS_NUMBER);
                alertDialogBuilder.setView(price);
                linearLayout.addView(price);

                alertDialogBuilder.setView(linearLayout);

                alertDialogBuilder.setPositiveButton("Bekræft", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Food food = new Food(foodCategoryName.getText().toString());
                        food.SetPricePerUnit(price.getText().toString().matches("") ? 0.0f : Float.valueOf(price.getText().toString()));
                        Log.d("New Category Test", "name: " + food.GetName() + "price per unit: " + food.GetPricePerUnit());
                        food.foodButton = new Button(InputActivity.this);
                        /*
                        android:layout_width="60dp"
                        android:layout_height="76dp"
                        android:background="#FFFFFF"
                        android:drawableTop="@drawable/ic_add_box_black_24dp"
                        android:text="Ny Kategori"
                        android:textColor="#000000"
                        android:textSize="8sp"
                        app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintHorizontal_bias="0.304"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toTopOf="parent"
                        app:layout_constraintVertical_bias="0.024"
                         */
                        food.foodButton.setWidth(60);
                        food.foodButton.setWidth(76);
                        food.foodButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                final Context context = alertDialogBuilder.getContext();
                                final LinearLayout linearLayout = new LinearLayout(context);
                                linearLayout.setOrientation(LinearLayout.VERTICAL);
                                alertDialogBuilder.setTitle(food.GetName());

                            }
                        });
                        ConstraintLayout cl = findViewById(R.id.input_layout);
                        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(food.foodButton.getWidth(), food.foodButton.getHeight());
                        food.foodButton.setText(food.GetName());
                        food.foodButton.setTextSize(8);
                        food.foodButton.setTextColor(Color.BLACK);

                        //cl.addView(food.foodButton, lp);

                        foodCategories.add(food);
                    }
                });
                alertDialogBuilder.setNegativeButton("Fortryd", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });



                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });
    }


}
