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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.jar.Attributes;

import static android.content.ContentValues.TAG;

public class HistoryDialog extends AlertDialog {
    
    public HistoryDialog(Context context, DataBase db) {
        super(context);

        db.AddFoodWaste("ost",500,false);
       // db.AddFoodWaste("ked",500,false);
      //  db.CreateCategory("freds tårer", 500);
      //  db.AddFoodWaste("freds tårer",500,false);
        OpenDialog(context, db);

    }

    public void OpenDialog(Context context, DataBase db) {
        final AlertDialog.Builder AlertDialog = new AlertDialog.Builder(context); // Context, this, etc.
        View newView = getLayoutInflater().inflate(R.layout.dialog_history2, null);

        AlertDialog.setView(newView);
        final AlertDialog dialog = AlertDialog.create();
        dialog.show();
        Populate(newView, context, db);

       // Button okButton = newView.findViewById(R.id.dialog_cancel);
       /*okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialog(dialog);            }
        });*/
    }

    private void Populate(View view, Context context, DataBase db) {
        TableLayout tb = view.findViewById(R.id.tableLayout);
        ArrayList<DataBase.Input> inputs = db.GetInputs();
        for (DataBase.Input s : inputs) {
            Log.d(TAG, "hej");
            TableRow row = new TableRow(context);
            row.setLayoutParams((new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)));
            row.setGravity(Gravity.LEFT);
            row.setPadding(0,10,0,5);
            HistoryView hv = new HistoryView(context);
            hv.SetText(s.getName());
            row.addView(hv);


            tb.addView(row);
            // row.addView(hv);
            //hv.SetText("ost");
            //tb.addView(row);
        }
    }
}


   //}