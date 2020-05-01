package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.jar.Attributes;

import static android.content.ContentValues.TAG;

public class HistoryDialog extends AlertDialog {

    View thisview = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HistoryDialog(Context context, DataBase db) {
        super(context);

        db.AddFoodWaste("ost", 500, false);
        // db.AddFoodWaste("ked",500,false);
        //  db.CreateCategory("freds tårer", 500);
        //  db.AddFoodWaste("freds tårer",500,false);
        OpenDialog(context, db);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void OpenDialog(Context context, DataBase db) {
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        thisview = getLayoutInflater().inflate(R.layout.dialog_history2, null);

        ImageButton back = thisview.findViewById(R.id.back_button);
        AlertDialog.setView(thisview);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();
        Populate(context, db);

        // Button okButton = newView.findViewById(R.id.dialog_cancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialog(dialog);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Populate(Context context, DataBase db) {
        TableLayout tb = thisview.findViewById(R.id.tableLayout);
        tb.removeAllViews();
        ArrayList<DataBase.Input> inputs = db.GetInputs();

        if (inputs.size() == 0) {
            TableRow row = new TableRow(context);
            TextView words = new TextView(context);
            words.setTextSize(20);
            words.setText("Der er ingen indtastninger");
            row.addView(words);
            tb.addView(row);
            return;
        }

        for (int i = db.GetInputs().size() - 1; i > -1; i--) {
            DataBase.Input s = db.GetInputs().get(i);

            Log.d("ost", s.getName());
            TableRow row = new TableRow(context);
            row.setLayoutParams((new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)));
            row.setGravity(Gravity.LEFT);
            row.setPadding(0, 10, 0, 5);
            HistoryView hv = new HistoryView(context, s, HistoryDialog.this);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            LocalDate dateTime = s.getTime();
            String formattedDateTime = dateTime.format(formatter);

            hv.SetText(formattedDateTime + " " + (int) (s.getamount()) + " Kg " + s.getName() + " " + s.GetWasteString());
            row.addView(hv);
            tb.addView(row);
            // row.addView(hv);
            //hv.SetText("ost");
            //tb.addView(row);
        }
    }

    public void CloseDialog(AlertDialog dialog) {
        dialog.cancel();
    }
}


//}