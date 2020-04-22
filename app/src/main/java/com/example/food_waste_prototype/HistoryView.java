package com.example.food_waste_prototype;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HistoryView extends LinearLayout {

    public HistoryView(Context context) {
        super(context);
        inflate(context, R.layout.dialog_history, this);
        // TODO Auto-generated constructor stub
    }

    public HistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.dialog_history, this);




        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HistoryView);
        //imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
        //textView.text = attributes.getString(R.styleable.BenefitView_text)
        attributes.recycle();


    }

    public HistoryView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public void SetText(String text){
        TextView textView= findViewById(R.id.name);
        textView.setText(text);
    }
}
