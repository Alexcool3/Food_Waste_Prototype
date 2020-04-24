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

public class AddCategoryView extends ConstraintLayout {



        public AddCategoryView(Context context) {
            super(context);
            inflate(context, R.layout.category_addbutton, this);
    }
}
