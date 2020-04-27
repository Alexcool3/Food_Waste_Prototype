package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

public class nyBrugerDialog extends AlertDialog {
    private EditText editTextBrugernavn;
    private EditText editTextKodeord;
    private Button button;

    protected nyBrugerDialog(Context context, DataBase db) {
        super(context);
        OpenDialog(context, db);
    }

    public void OpenDialog(Context context, DataBase db) {
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        View newView = getLayoutInflater().inflate(R.layout.nybrugerdialog, null);
        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();
        SetupButtons(newView, dialog, db);


    }

    public void CloseDialog(AlertDialog dialog) {
        dialog.cancel();
    }


    private void SetupButtons(View newView, final AlertDialog dialog, final DataBase db) {
        editTextBrugernavn = newView.findViewById(R.id.edit_brugernavn);
        editTextKodeord = newView.findViewById(R.id.edit_kodeord);
        final EditText email = newView.findViewById(R.id.edit_brugernavn2);
        final Spinner dropdown = newView.findViewById(R.id.dropdown2);
        Button accept = newView.findViewById(R.id.button);
        Button cancel = newView.findViewById(R.id.button2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptInput(email, editTextBrugernavn, editTextKodeord, dropdown, db, dialog);
            }
        });

        String[] items = new String[]{"5", "7"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), R.layout.spinner_text, items);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        dropdown.setAdapter(adapter);

    }
    private void AcceptInput(EditText email, EditText username, EditText password, Spinner dropdown, DataBase db, AlertDialog dialog) {
        if(email.getText().toString().equals("")){
            email.setHint("Manglende email");
            return;
        }
        if(username.getText().toString().equals("")){
            email.setHint("Manglende køkkennavn");
            return;
        }
        if(password.getText().toString().equals("")){
            email.setHint("Manglende kodeord");
            return;
        }
        int days = Integer.parseInt(dropdown.getSelectedItem().toString());
        db.NewUser(username.getText().toString(),password.getText().toString(),email.getText().toString(), days, true, true);
        Toast.makeText(getContext(), "Ny bruger registeret", Toast.LENGTH_LONG).show();
        dialog.cancel();
        Intent intent = new Intent(getContext(), InputActivity.class);
        getContext().startActivity(intent);
    }
}
