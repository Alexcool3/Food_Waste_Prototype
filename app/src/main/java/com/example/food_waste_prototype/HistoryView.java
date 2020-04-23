package com.example.food_waste_prototype;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryView extends RelativeLayout {

    public HistoryView(Context context) {
        super(context);
        inflate(context, R.layout.dialog_history, this);
        // TODO Auto-generated constructor stub
    }

    public HistoryView(Context context, boolean smallpopup) {
        super(context);
        inflate(context, R.layout.dialog_history, this);
        TextView textView= findViewById(R.id.name);
        textView.setVisibility(GONE);
        SetupButtons(context);


        // TODO Auto-generated constructor stub
    }


    /*public HistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.dialog_history, this);




        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HistoryView);
        //imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
        //textView.text = attributes.getString(R.styleable.BenefitView_text)
        attributes.recycle();


    }*/

    public HistoryView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public void SetText(String text){
        TextView textView= findViewById(R.id.name);
        textView.setText(text);
    }

    public void SetupButtons(final Context context){
        ImageButton delete = findViewById(R.id.deleteButton);
        ImageButton edit = findViewById(R.id.editButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Delete this category",Toast.LENGTH_LONG).show();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Edit this category",Toast.LENGTH_LONG).show();
            }
        });
    }
}
