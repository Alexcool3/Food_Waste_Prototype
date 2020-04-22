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

    protected nyBrugerDialog(Context context, String text) {
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

/*public class nyBrugerDialog extends AppCompatDialogFragment {
    private EditText editTextBrugernavn;
    private EditText editTextKodeord;
    private Button button;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = new inflater.inflate(R.layout.nybrugerdialog, null);

        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        editTextBrugernavn = view.findViewById(R.id.edit_brugernavn);
        editTextKodeord = view.findViewById(R.id.edit_kodeord);

        return builder.create();

    }
}
*/

/*
public class nyBrugerDialog extends AppCompatActivity {
    private TextView tv_brugernavn;
    private TextView tv_kodeord;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nybrugerdialog);

        tv_brugernavn = (TextView) findViewById(R.id.tv_brugernavn);
        tv_kodeord = (TextView) findViewById(R.id.tv_kodeord);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    public void openDialog() {
        nyBrugerDialog exampleDialog = new nyBrugerDialog();
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
    }
}
*/