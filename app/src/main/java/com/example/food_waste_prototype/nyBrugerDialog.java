package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import static android.view.View.GONE;

public class nyBrugerDialog extends AlertDialog {
    private EditText editTextBrugernavn;
    private EditText editTextKodeord;
    private Context context;


    protected nyBrugerDialog(Context context, DataBase db) {
        super(context);
        OpenDialog(context, db);
    }

    protected nyBrugerDialog(final Context context, final DataBase db, String settings) {
        // modifies the dialog to become edit user dialog
        super(context);
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        View newView = getLayoutInflater().inflate(R.layout.nybrugerdialog, null);
        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();
        SetupButtons(newView, dialog, db);
        this.context = context;

        final DataBase.User user = db.GetCurrentUser();
        editTextBrugernavn = newView.findViewById(R.id.edit_brugernavn);
        editTextBrugernavn.setHint("Køkken navn: " + user.GetUserName());

        editTextKodeord = newView.findViewById(R.id.edit_kodeord);
        editTextKodeord.setHint("Kode: " + user.GetPassword());

        final EditText email = newView.findViewById(R.id.edit_brugernavn2);
        email.setVisibility(GONE);

        final Spinner dropdown = newView.findViewById(R.id.dropdown2);
        Button accept = newView.findViewById(R.id.button);
        Button cancel = newView.findViewById(R.id.button2);
        ImageButton warning = newView.findViewById(R.id.button_information4);
        ImageButton info = newView.findViewById(R.id.button_information2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AcceptEditInput(editTextBrugernavn, editTextKodeord, dialog, user);

            }
        });

        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new HelpDialog(context, "Brug ikke dit rigtige kodeord, da det ikke er gemt sikkert.");

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HelpDialog(getContext(), "Du skal vælge hvor mange dage om ugen dit køkken opererer. Dette bliver brugt til at udregne statistikker for dit ugentlige mad spild i køkkenet.");
            }
        });

        String[] items = new String[]{"5", "7"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), R.layout.spinner_text, items);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        dropdown.setAdapter(adapter);



    }

    public void OpenDialog(Context context, DataBase db) {
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        View newView = getLayoutInflater().inflate(R.layout.nybrugerdialog, null);
        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();
        SetupButtons(newView, dialog, db);
        this.context = context;

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
        ImageButton info = newView.findViewById(R.id.button_information2);
        ImageButton warning = newView.findViewById(R.id.button_information4);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase.instance.username = editTextBrugernavn.getText().toString();
                AcceptInput(email, editTextBrugernavn, editTextKodeord, dropdown, db, dialog);

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HelpDialog(getContext(), "Du skal vælge hvor mange dage om ugen dit køkken opererer. Dette bliver brugt til at udregne statistikker for dit ugentlige mad spild i køkkenet.");
            }
        });

        String[] items = new String[]{"5", "7"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), R.layout.spinner_text, items);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        dropdown.setAdapter(adapter);

        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new HelpDialog(context, "Brug ikke dit rigtige kodeord, da det ikke er gemt sikkert.");

            }
        });

    }

    private void AcceptInput(EditText email, EditText username, EditText password, Spinner dropdown, DataBase db, AlertDialog dialog) {
        if (email.getText().toString().equals("")) {
            email.setHint("Manglende email");
            return;
        }
        if (username.getText().toString().equals("")) {
            email.setHint("Manglende køkkennavn");
            return;
        }
        if (password.getText().toString().equals("")) {
            email.setHint("Manglende kodeord");
            return;
        }
        int days = Integer.parseInt(dropdown.getSelectedItem().toString());
        db.NewUser(username.getText().toString(), password.getText().toString(), email.getText().toString(), days, true, true);
        Log.d("Register: ", "Username: " + username.getText().toString() + " Password: " + password.getText().toString());
        new CustomToast("Ny bruger registeret", getContext());


        dialog.cancel();
        BackgroundTask backgroundTask = new BackgroundTask(context);
        backgroundTask.execute("register", username.getText().toString(), password.getText().toString());
        DataBase.instance.username = username.getText().toString();
        DataBase.instance.password = password.getText().toString();
    }


    private void AcceptEditInput(EditText username, EditText password, AlertDialog dialog, DataBase.User user) {

        if (!username.getText().toString().equals("")) {
            user.setUserName(username.getText().toString());
        }
        if (!(password.getText().toString().equals(""))) {
            user.setPassword(password.getText().toString());

        }
        //  int days = Integer.parseInt(dropdown.getSelectedItem().toString());

        new CustomToast("Du har redigeret dine bruger oplysninger", context);
        dialog.cancel();

        // TODO: how does the server handle editting a user?
        //BackgroundTask backgroundTask = new BackgroundTask(context);
        //backgroundTask.execute("register", username.getText().toString(), password.getText().toString());
    }
}
