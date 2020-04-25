package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

public class InputDialog extends AlertDialog {

    DataBase db;

    public InputDialog(Context context, Category cg) {
        super(context);
        db = DataBase.getInstance(context);
        OpenDialog(context, cg);
    }


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
    }

    private void CloseDialog(AlertDialog dialog) {
        dialog.cancel();
    }

    private void AcceptWaste(AlertDialog dialog, EditText waste, Category cg) {
        if (waste.getText().toString().equals("")) {
            waste.setHint("Indtast spild i Kilo");
            return;
        }
        String foodwaste;
        if (db.GetEnumToString().equals("Mad Affald")) {
            cg.AddFS(Float.parseFloat(waste.getText().toString()));
            db.CreateInput(cg.GetName(), (Float.parseFloat(waste.getText().toString())), true);
            foodwaste=" Mad Affald ";
        } else {
            cg.AddFW(Float.parseFloat(waste.getText().toString()));
            db.CreateInput(cg.GetName(), (Float.parseFloat(waste.getText().toString())), false);
            foodwaste=" Mad Spild ";
        }

        Toast.makeText(getContext(), "Indtastede " + waste.getText().toString() + " Kg " + foodwaste + " i " + cg.GetName(), Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
}
