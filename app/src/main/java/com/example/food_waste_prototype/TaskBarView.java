package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout ;

public class TaskBarView extends LinearLayout implements View.OnClickListener{
    Context context2;
    public TaskBarView(Context context) {
        super(context);
        context2=context;
        inflate(context, R.layout.taskbar_view, this);
        Log.d("task", "did 3");
        DetermineColor(context);
        SetupButtons(context);
        // TODO Auto-generated constructor stub
    }

    public TaskBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.taskbar_view, this);




        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HistoryView);
        //imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
        //textView.text = attributes.getString(R.styleable.BenefitView_text)
        attributes.recycle();


    }

    public TaskBarView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public void Hej(){
        Log.d("task", "bar");
    }

    public void SetupButtons(Context context){
        ImageButton inputtaskbarbutton = findViewById(R.id.button_input4);
        ImageButton statstaskbarbutton = findViewById(R.id.button_stats2);
        Log.d("task", "did 4");
        inputtaskbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("task", "did 1");
                SwitchActivity("input", context2);
            }
        });

        statstaskbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("task", "did 2");
                SwitchActivity("input", context2);
            }
        });
    }


    @Override
    public void onClick(View v)
    {
        Log.d("task", "bar");
            Toast.makeText(getContext(), "Text", Toast.LENGTH_SHORT).show();

    }

    private void DetermineColor(Context context){
        ImageView statsback = findViewById(R.id.background_stats);
        ImageView inputback = findViewById(R.id.backgroundInput);
        if(context instanceof StatsActivity){

            inputback.setBackgroundColor(getResources().getColor(R.color.Beige));
        } else{
            statsback.setBackgroundColor(getResources().getColor(R.color.Beige));
        }
    }

    private void SwitchActivity(String target, Context context) {

        if (target == "settings") {

            Intent intent = new Intent(context, SettingsActivity.class);


        }
        if (target == "input") {

            Intent intent = new Intent(context, InputActivity.class);

            context.startActivity(intent);
        }
    }
}


