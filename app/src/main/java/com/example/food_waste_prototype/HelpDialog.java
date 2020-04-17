package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpDialog  extends  AlertDialog{

    protected HelpDialog(Context context, String text) {
        super(context);
        OpenDialog(context, text);
    }

    public void OpenDialog(Context context, String text) {
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        View newView = getLayoutInflater().inflate(R.layout.dialog_help, null);
        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();

        TextView explainer = newView.findViewById(R.id.dialog_info);
        explainer.setText((text));

        Button okButton = newView.findViewById(R.id.dialog_cancel);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialog(dialog);            }
        });
    }

    public void CloseDialog(AlertDialog dialog){
        dialog.cancel();
    }
}
