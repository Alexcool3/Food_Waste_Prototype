package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

import  java.time.Duration;


@RequiresApi(api = Build.VERSION_CODES.O)
public class InputDialog extends AlertDialog {

    DataBase db;
    LocalDateTime from = LocalDateTime.now();

    public InputDialog(Context context, Category cg) {
        super(context);
        db = DataBase.getInstance(context);
        OpenDialog(context, cg);
    }

    public InputDialog(final Context context, final DataBase.Input input, final HistoryDialog hd) {
        super(context);
        db = DataBase.getInstance(context);
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.dialog_input, null);
        newView.bringToFront();
        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();

        final TextView title = newView.findViewById(R.id.dialog_title);
        title.setText(("Rediger " + input.getName()));

        final EditText amountInput = dialog.findViewById(R.id.waste_input);
        amountInput.setHint(String.valueOf(input.getamount()));
        Button cancel_button = newView.findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialog(dialog);
            }
        });

        Button confirm_button = newView.findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                EditInput(dialog, amountInput, input, hd);
            }
        });


        final TextView toggleText = newView.findViewById(R.id.toggleText2);
        toggleText.setText(db.GetEnumToString());

        final ImageButton toggle = newView.findViewById(R.id.button_toggle2);

        if (db.GetEnumToString().equals("Mad Affald")) {
            toggle.setImageResource(R.drawable.button_toggle_reverse);
        } else {
            toggle.setImageResource(R.drawable.button_toggle);
        }
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.flipEnum();
                toggleText.setText(db.GetEnumToString());
                if (db.GetEnumToString().equals("Mad Affald")) {
                    toggle.setImageResource(R.drawable.button_toggle_reverse);
                } else {
                    toggle.setImageResource(R.drawable.button_toggle);
                }

            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void OpenDialog(Context context, final Category cg) {
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.dialog_input, null);
        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();

        TextView title = newView.findViewById(R.id.dialog_title);
        title.setText((cg.GetName()));

        final EditText amountInput = dialog.findViewById(R.id.waste_input);

        Button cancel_button = newView.findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialog(dialog);
            }
        });

        Button confirm_button = newView.findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                AcceptWaste(dialog, amountInput, cg);
            }
        });


        final TextView toggleText = newView.findViewById(R.id.toggleText2);
        toggleText.setText(db.GetEnumToString());

        final ImageButton toggle = newView.findViewById(R.id.button_toggle2);
        if (db.GetEnumToString().equals("Mad Affald")) {
            toggle.setImageResource(R.drawable.button_toggle_reverse);
        } else {
            toggle.setImageResource(R.drawable.button_toggle);
        }
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.flipEnum();
                toggleText.setText(db.GetEnumToString());
                if (db.GetEnumToString().equals("Mad Affald")) {
                    toggle.setImageResource(R.drawable.button_toggle_reverse);

                } else {
                    toggle.setImageResource(R.drawable.button_toggle);

                }

            }
        });

        ImageButton info = newView.findViewById(R.id.button_information3);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HelpDialog(getContext(), "Mad Spild er defineret som mad der kunne have været spist, men ikke er blevet spist. Eksempelvis gammel mælk. \n \n  Mad Affald er mad, der ikke er spiseligt af mennesker. For eksempel æggeskaller eller roden af et løg. ");

            }
        });

    }

    private void CloseDialog(AlertDialog dialog) {
        dialog.cancel();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void AcceptWaste(AlertDialog dialog, EditText waste, Category cg) {
        DataBase.Input input;
        if (waste.getText().toString().equals("")) {
            waste.setHint("Indtast spild i Kilo");
            return;
        }
        String foodwaste;
        if (db.GetEnumToString().equals("Mad Affald")) {
            cg.AddFS(Float.parseFloat(waste.getText().toString()));
            input = db.CreateInput(cg.GetName(), (Float.parseFloat(waste.getText().toString())), true);
            foodwaste = " Mad Affald ";
        } else {
            cg.AddFW(Float.parseFloat(waste.getText().toString()));
            input = db.CreateInput(cg.GetName(), (Float.parseFloat(waste.getText().toString())), false);
            foodwaste = " Mad Spild ";
        }
        new CustomToast("Indtastede " + waste.getText().toString() + " Kg " + foodwaste + " i " + cg.GetName(), getContext());
        dialog.dismiss();
        LocalDateTime too = LocalDateTime.now();
        Duration duration = Duration.between(from, too);
        Log.d("time","Seconds for input: "+ (duration.getSeconds())); // amount of seconds it took to input
        BackgroundTask backgroundTask = new BackgroundTask(getContext());
        Log.d("Date", "Date: " + too);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        DataBase.instance.username = sharedPreferences.getString("username", "");
        backgroundTask.execute("input", DataBase.instance.username, String.valueOf(input.getamount()), cg.GetName(), String.valueOf(input.GetFoodType()), String.valueOf((float)duration.getSeconds()), input.getTime().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void EditInput(AlertDialog dialog, EditText waste, DataBase.Input input, final HistoryDialog hd) {

        int type;
        if (input.getfoodScraps()) {
            db.GetCategory(input.getName()).AddFS(-input.getamount());
            type = 0;
        } else {
            db.GetCategory(input.getName()).AddFW(-input.getamount());
            type = 1;
        }

        if (waste.getText().toString().equals("")) {
            if (db.GetEnumToString().equals("Mad Affald")) {
                input.SetfoodScraps(true);
                db.GetCategory(input.getName()).AddFS(input.getamount());
                //db.GetCategory(input.getName()).AddFS(Float.parseFloat(waste.getText().toString()));

            } else {
                input.SetfoodScraps(false);
                db.GetCategory(input.getName()).AddFW(input.getamount());
            }
            hd.Populate(getContext(), db);
            dialog.dismiss();
            return;
        }

        if (db.GetEnumToString().equals("Mad Affald")) {
            input.SetfoodScraps(true);
            input.SetAmount(Float.parseFloat(waste.getText().toString()));
            db.GetCategory(input.getName()).AddFS(Float.parseFloat(waste.getText().toString()));
        } else {
            input.SetfoodScraps(false);
            input.SetAmount(Float.parseFloat(waste.getText().toString()));
            db.GetCategory(input.getName()).AddFW(Float.parseFloat(waste.getText().toString()));
        }

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);

        DataBase.instance.username = sharedPreferences.getString("username", "");

        Log.d("EditInput", "username: " + DataBase.instance.username + " Input ID: " + input.GetID());

        BackgroundTask backgroundTask = new BackgroundTask(getContext());
        backgroundTask.execute("editInput", DataBase.instance.username, waste.getText().toString(), String.valueOf(input.GetID()), String.valueOf(type));
        hd.Populate(getContext(), db);
        dialog.dismiss();
        return;
    }
}
