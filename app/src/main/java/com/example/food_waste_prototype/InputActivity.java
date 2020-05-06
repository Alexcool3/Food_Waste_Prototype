package com.example.food_waste_prototype;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {
    public static InputActivity instance;
    DataBase db;
    TableLayout tb;
    int currenrowindex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        final Context context = InputActivity.this;
        instance = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // make the app fullscreen
        db = DataBase.getInstance(context);
        //   db.ReadAllDate(context);
        tb = findViewById(R.id.tableLayout);
        tb.removeAllViews();
        populate(context);
        SetupButtons(context);
        final TaskBarView taskbar = findViewById(R.id.taskBarView);
        taskbar.taskings(context);


    }

    @Override
    protected void onPause() {


        db.SaveData(InputActivity.this);
        Toast.makeText(getApplicationContext(), "onPause called", Toast.LENGTH_LONG).show();
        super.onPause();


    }

    public static InputActivity getInstance() {
        return instance;
    }


    @Override
    public void onBackPressed() {
    }

    private void SetupButtons(final Context context) {
        final ImageButton settingsButton = findViewById(R.id.button_options2);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InputActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        settingsButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("ost", "hej");
                Toast.makeText(InputActivity.this, "ost", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        final ImageButton dotsbutton = findViewById(R.id.button_3dots);
        final ConstraintLayout cl = findViewById(R.id.input_layout);
        final DotsMenuView dots = new DotsMenuView(context);
        dotsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, String.valueOf(dots.isopen), Toast.LENGTH_LONG).show();
                if (!(dots.isopen)) {
                    dots.MakeView(context, cl, dotsbutton);

                } else {
                    dots.RemoveView(cl);


                }
            }
        });


    }

    public void populate(Context context) {
        ConstraintLayout cl = findViewById(R.id.input_layout);
        removeParents(tb);
        tb.removeAllViews();
        ArrayList<Category> cats = db.GetAllCategories();
        TableRow row = new TableRow(context);
        row.removeAllViews();
        addTheCartButton(row, tb, context);


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
            cg.MakeLayout(context, cl);
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


        //ConstraintLayout scv = (ConstraintLayout) ((Activity) context).findViewById(R.id.input_layout);
        AddCategoryView add = new AddCategoryView(context);
        ImageButton ib = add.findViewById(R.id.image);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCategory(context);
            }
        });

        row.addView(add);
        tb.addView(row);
    }


    private void addTheCartButton(TableRow row, final TableLayout tb, final Context context) {


        //ConstraintLayout scv = (ConstraintLayout) ((Activity) context).findViewById(R.id.input_layout);
        final PurchaseView add = new PurchaseView(context);
        ImageButton ib = add.findViewById(R.id.image);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.OpenDialog(context);
            }
        });

        row.addView(add);

    }


    public void newCategory(final Context context) {
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
                populate(context);
            }
        });

        confrimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<Category> cats = db.GetAllCategories();
                for (int number = 0; number < cats.size(); number++) {
                    Category cg = cats.get(number);
                    if (nameInput.getText().toString().toLowerCase().equals(cg.GetName().toLowerCase())) {
                        Toast.makeText(context, "Kategorien eksisterer allerede", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if (nameInput.getText().toString().equals("")) {
                    Log.d("nameInput", "is Called");
                    nameInput.setText("");
                    nameInput.setHint("Indtast navn");
                }


                if (priceInput.getText().toString().equals("")) {
                    Log.d("priceInput", "is Called");
                    // priceInput.setText("");
                    priceInput.setHint("Indtast pris per kilo");
                    return;

                }


                if (nameInput.getText().toString().length() > 10) {
                    new CustomToast("Navnet er for langt. Maks 11 bogstaver.", context);
                    return;
                }

                db.CreateCategory(nameInput.getText().toString(), Float.parseFloat(priceInput.getText().toString()), context);

                populate(context);

                SharedPreferences sharedPreferences = InputActivity.this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);

                DataBase.instance.username = sharedPreferences.getString("username", "");

                BackgroundTask backgroundTask = new BackgroundTask(InputActivity.this);
                Log.d("Category Database Login", "username: " + DataBase.instance.username + " password: "+ DataBase.instance.password);
                backgroundTask.execute("categories", DataBase.instance.username, nameInput.getText().toString(), priceInput.getText().toString());

                dialog.dismiss();

            }
        });
    }
}




