package com.example.food_waste_prototype;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

public class AddCategoryView extends ConstraintLayout {

        public AddCategoryView(Context context) {
            super(context);
            inflate(context, R.layout.category_addButton, this);
    }
}
