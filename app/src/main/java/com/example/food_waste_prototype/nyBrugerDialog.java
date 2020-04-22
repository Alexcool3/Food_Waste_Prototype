package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

public class nyBrugerDialog  extends  AlertDialog{
    private EditText editTextBrugernavn;
    private EditText editTextKodeord;
    private Button button;

    protected nyBrugerDialog(Context context) {
        super(context);
        OpenDialog(context);
    }

    public void OpenDialog(Context context) {
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        View newView = getLayoutInflater().inflate(R.layout.nybrugerdialog, null);
        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();

        editTextBrugernavn = newView.findViewById(R.id.edit_brugernavn);
        editTextKodeord = newView.findViewById(R.id.edit_kodeord);

        Button button = newView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialog(dialog);            }
        });
    }

    public void CloseDialog(AlertDialog dialog){
        dialog.cancel();
    }
}
