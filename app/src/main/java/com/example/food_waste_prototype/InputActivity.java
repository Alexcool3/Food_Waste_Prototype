package com.example.food_waste_prototype;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {

    DataBase db;
    int currenrowindex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        final Context context = InputActivity.this;


        db = DataBase.getInstance(context);
        db.clearArays();
        db.CreateCategory("ost", 500, context);

        TableLayout tb = findViewById(R.id.tableLayout);
        tb.removeAllViews();
        populate(context, tb);
        SetupButtons();
        final TaskBarView taskbar = findViewById(R.id.taskBarView);
        taskbar.taskings(context);


    }

    private void SetupButtons() {
        final ImageButton settingsButton = findViewById(R.id.button_options2);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InputActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populate(Context context, TableLayout tb) {

        removeParents(tb);
        tb.removeAllViews();
        ArrayList<Category> cats = db.GetAllCategories();
        TableRow row = new TableRow(context);
        row.removeAllViews();
        for (int number = 0; number < cats.size(); number++) {
            if (tb.getChildAt(currenrowindex) != null) {
                row = new TableRow(context);
            }
            if (row.getChildCount() == 3 || row.getChildCount() > 3) {
                tb.addView(row);
                currenrowindex++;
                row = new TableRow(context);
            }
            row.setLayoutParams((new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)));
            // row.setGravity(Gravity.LEFT);
            //row.setPadding(100, 0, 200, 100);
            Category cg = cats.get(number);
            cg.MakeLayout(context);
            //    Category  cg= new  Category(context);
            Log.d("ost", "Created category " + cg.GetName());
            TableRow tr = (TableRow) cg.getParent();
            if (!(tr == null)) {
                tr.removeView(cg);
            }
            row.addView(cg);
        }
        if (row.getChildCount() > 2) {
            tb.addView(row);
            row = new TableRow(context);
            addTheAddButton(row, tb, context);
        } else {
            addTheAddButton(row, tb, context);
        }
    }

    private void removeParents(TableLayout tl) {
        for (int number = 0; number < tl.getChildCount(); number++) {
            TableRow tr = (TableRow) tl.getChildAt(number);
            tr.removeAllViews();
        }
    }

    private void addTheAddButton(TableRow row, final TableLayout tb, final Context context) {

        ImageButton ib = new ImageButton(context);
        ib.setImageResource(R.drawable.ic_add_box_black_24dp);
        ib.setBackground(null);
        row.addView(ib);
        tb.addView(row);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCategory(context, tb);
            }
        });
    }


    private void newCategory(final Context context, final TableLayout tb) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.new_category_dialog);
        final EditText nameInput = dialog.findViewById(R.id.name_input);
        final EditText priceInput = dialog.findViewById(R.id.price_input);
        Button confrimButton = dialog.findViewById(R.id.confirm_button);
        Button cancelButton = dialog.findViewById(R.id.cancel_button);
        dialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                populate(context, tb);
            }
        });

        confrimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameInput.getText().toString().equals("") || priceInput.getText().toString().equals("")) {

                    if (nameInput.getText().toString().equals("")) {
                        Log.d("nameInput", "is Called");
                        nameInput.setText("");
                        nameInput.setHint("Indtast navn");
                    }

                    if (priceInput.getText().toString().equals("")) {
                        Log.d("priceInput", "is Called");
                       // priceInput.setText("");
                        priceInput.setHint("Indtast pris per kilo");
                    }
                    return;
                }

                db.CreateCategory(nameInput.getText().toString(), Float.valueOf(priceInput.getText().toString()), context);
                populate(context, tb);
                dialog.dismiss();

            }
        });

    }
};


