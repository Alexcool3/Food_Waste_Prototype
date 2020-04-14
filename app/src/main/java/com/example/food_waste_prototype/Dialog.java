package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment {
    public AlertDialog onCreateBundle(Bundle savedInstanceState){
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Ny Kategori")
        .setPositiveButton(R.string.app_name, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        })
        .setNegativeButton(R.string.app_name, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });

        return builder.create();
    }
}
