package com.example.food_waste_prototype;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class DotsMenuView extends RelativeLayout {

    private DataBase db;
    public boolean isopen = false;
    private boolean hasbeeninflated = false;
    private View dummy;
    View dots;

    public DotsMenuView(Context context) {
        super(context);
        db = DataBase.getInstance(context);
    }

    public View MakeView(Context context, final ConstraintLayout cl, ImageButton button) {
        if (hasbeeninflated) {
            dummy.setVisibility(VISIBLE);
            dots.setVisibility(VISIBLE);

            return dots;
        }
        hasbeeninflated = true;
        isopen = true;
        dots = inflate(context, R.layout.input_dotsmenu, this);
        int[] location = new int[2];  // create new int array to store position of window
        button.getLocationOnScreen(location); // gets pos of the button on the category view
        setPadding(location[0] - 375, location[1] + 170, 0, 0); // sets the padding of the small buttons view to be slighty above the category


        dummy = new View(context); // create a new empty view. The view is invisible and exists behind the small button menu. Its purpose is to register a click outside the menu and then close the menu
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT); // make the new view fills the entire screen
        dummy.setLayoutParams(lp2);

        dummy.setOnTouchListener(new View.OnTouchListener() { // When someone clicks outside the small menu buttons
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) { //not sure its matters what enum
                    RemoveView(cl);
                    return true; // returns false so that the click continues on to the parent below the dummy
                }
                return false;
            }
        });

        cl.addView(dummy);
        dots.bringToFront();
        cl.addView(dots);
        SetupButtons(context, cl);
        return dots;
    }

    public void RemoveView(final ConstraintLayout cl) {

        dummy.setVisibility(GONE);
        dots.setVisibility(GONE);
        isopen = false;
    }

    public void SetupButtons(final Context context, final ConstraintLayout cl) {
        ImageButton add = findViewById(R.id.addbutton);
        ImageButton delete = findViewById(R.id.deletebutton);
        ImageButton edit = findViewById(R.id.editbutton);
        ImageButton history = findViewById(R.id.historybutton);
        TextView addtext = findViewById(R.id.addtext);
        TextView deletetext = findViewById(R.id.deletetext);
        TextView historytext = findViewById(R.id.historyText);
        TextView edittext = findViewById(R.id.edittext);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputActivity.getInstance().newCategory(context);
                RemoveView(cl);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveView(cl);
                StopAction(context, cl);
                ArrayList<Category> cats = db.GetAllCategories();
                if(cats.size()==0){
                    new CustomToast("Ingen kategorier at slette",context);

                    return;
                }
                for (int number = 0; number < cats.size(); number++) {
                    Category cg = cats.get(number);
                    cg.StartShaking(context, "delete");
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveView(cl);
                StopAction(context, cl);
                ArrayList<Category> cats = db.GetAllCategories();
                if(cats.size()==0){
                    new CustomToast("Ingen kategorier at redigere", context);

                    return;
                }
                for (int number = 0; number < cats.size(); number++) {
                    Category cg = cats.get(number);
                    cg.StartShaking(context, "edit");
                }
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HistoryDialog hd = new HistoryDialog(context, db);
                RemoveView(cl);
            }
        });

        addtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputActivity.getInstance().newCategory(context);
                RemoveView(cl);
            }
        });

        deletetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveView(cl);
                StopAction(context, cl);
                ArrayList<Category> cats = db.GetAllCategories();
                if(cats.size()==0){
                    Toast.makeText(context, "Ingen kategorier at slette", Toast.LENGTH_LONG).show();
                    return;
                }
                for (int number = 0; number < cats.size(); number++) {
                    Category cg = cats.get(number);
                    cg.StartShaking(context, "delete");
                }
            }
        });

        historytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HistoryDialog hd = new HistoryDialog(context, db);
                RemoveView(cl);
            }
        });


        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveView(cl);
                StopAction(context, cl);
                ArrayList<Category> cats = db.GetAllCategories();
                if(cats.size()==0){
                    Toast.makeText(context, "Ingen kategorier at redigere", Toast.LENGTH_LONG).show();
                    return;
                }
                for (int number = 0; number < cats.size(); number++) {
                    Category cg = cats.get(number);
                    cg.StartShaking(context, "edit");
                }
            }
        });
    }

    private void StopAction(final Context context, final ConstraintLayout cl) {
        final View background = new View(context); // create a new empty view. The view is invisible and exists behind the small button menu. Its purpose is to register a click outside the menu and then close the menu
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT); // make the new view fills the entire screen
        background.setLayoutParams(lp2);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Category> cats = db.GetAllCategories();
                for (int number = 0; number < cats.size(); number++) {
                    Category cg = cats.get(number);
                    cg.StopShaking(context);
                }
                cl.removeView(background);
                RemoveView(cl);
            }
        });

        cl.addView(background, 0);
    }

}




