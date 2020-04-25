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

import androidx.constraintlayout.widget.ConstraintLayout;

public class DotsMenuView extends RelativeLayout {

    private DataBase db;

    public DotsMenuView(Context context) {
        super(context);
    }

    public View MakeView(Context context, final ConstraintLayout cl, ImageButton button) {

        final View dots = inflate(context, R.layout.input_dotsmenu, this);
        int[] location = new int[2];  // create new int array to store position of window
        button.getLocationOnScreen(location); // gets pos of the button on the category view
        setPadding(location[0] - 375, location[1] + 170, 0, 0); // sets the padding of the small buttons view to be slighty above the category


        final View dummy = new View(context); // create a new empty view. The view is invisible and exists behind the small button menu. Its purpose is to register a click outside the menu and then close the menu
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT); // make the new view fills the entire screen
        dummy.setLayoutParams(lp2);

        dummy.setOnTouchListener(new View.OnTouchListener() { // When someone clicks outside the small menu buttons
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) { //not sure its matters what enum
                    cl.removeView(dots);
                    return false; // returns false so that the click continues on to the parent below the dummy
                }
                return false;
            }
        });

        cl.addView(dummy);
        cl.addView(dots);
        return dots;
    }
}



