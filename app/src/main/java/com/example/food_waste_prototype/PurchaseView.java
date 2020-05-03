package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class PurchaseView extends ConstraintLayout {
    DataBase db;

    public PurchaseView(Context context) {
        super(context);
        View newView = inflate(context, R.layout.input_purchasebutton, this);
        db = DataBase.getInstance(context);

    }


    public void OpenDialog(Context context) {
        // Log.d("fred", String.valueOf(context));
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.dialog_input, null);
        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();

        TextView title = newView.findViewById(R.id.dialog_title);
        title.setText("Pris af Indkøb");

        final EditText priceinput = dialog.findViewById(R.id.waste_input);
        priceinput.setHint("DKK");

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
                AcceptWaste(dialog, priceinput);
            }
        });


        final TextView toggleText = newView.findViewById(R.id.toggleText2);
        toggleText.setVisibility(GONE);

        final ImageButton toggle = newView.findViewById(R.id.button_toggle2);
        toggle.setVisibility(GONE);
        ImageButton info = newView.findViewById(R.id.button_information3);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HelpDialog(getContext(), "På denne side kan du indtaste prisen af dit samlede madindkøb. Dette bliver brugt til at udregne Food Waste Number, som du kan læse mere om på statistik siden.");

            }
        });


    }

    private void CloseDialog(AlertDialog dialog) {
        dialog.cancel();
    }

    private void AcceptWaste(AlertDialog dialog, EditText price) {
        if (price.getText().toString().equals("")) {
            price.setHint("Pris af indkøb i DKK");
            return;
        }
        String pricestring;

        db.purchases += Float.parseFloat(price.getText().toString());
        new CustomToast("Indtastede " + price.getText().toString() + " Dkk", getContext());

        dialog.dismiss();
    }


}
