package com.example.food_waste_prototype;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.food_waste_prototype.DataBase.*;


public class InputActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        final Context context = InputActivity.this;

        Button addButton = findViewById(R.id.addButton);
        // Add new category button + dialog
        final DataBase db;
        db = DataBase.getInstance(context);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.new_category_dialog);

                final EditText nameInput = dialog.findViewById(R.id.name_input);
                final EditText priceInput = dialog.findViewById(R.id.price_input);
                Button confrimButton = dialog.findViewById(R.id.confirm_button);
                Button cancelButton = dialog.findViewById(R.id.cancel_button);
                // Cancel Button

                dialog.show();

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                confrimButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (nameInput.getText().toString().matches("") || Float.valueOf(priceInput.getText().toString()) == 0.0f){

                            if (nameInput.getText().toString().matches("")){
                                Log.d("nameInput", "is Called");
                                nameInput.setText("");
                                nameInput.setHint("Indtast navn");
                            }

                            if (priceInput.getText().toString().matches("")){
                                Log.d("priceInput", "is Called");
                                priceInput.setText("");
                                priceInput.setHint("Indtast pris per kilo");
                            }
                            return;
                        }

                        db.CreateCategory(nameInput.getText().toString(), Float.valueOf(priceInput.getText().toString()));

                        dialog.dismiss();

                    }
                });

            }
        });
    }

}
