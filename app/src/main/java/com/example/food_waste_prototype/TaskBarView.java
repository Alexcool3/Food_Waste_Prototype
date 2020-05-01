package com.example.food_waste_prototype;

import android.app.Activity;
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

import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Vibrator;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class TaskBarView extends ConstraintLayout implements View.OnClickListener {
    Context context2;
    ImageButton inputtaskbarbutton;
    ImageButton statstaskbarbutton;

    public TaskBarView(Context context) {
        super(context);
        context2 = context;
        inflate(context, R.layout.taskbar_view, this);
        // TODO Auto-generated constructor stub
    }

    public TaskBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.taskbar_view, this);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HistoryView);
        attributes.recycle();
    }

    public TaskBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void taskings(Context context) {
        Log.d("task", "did 3");
        DetermineColor(context);
        SetupButtons(context);
    }

    public void SetupButtons(final Context context) {
        Log.d("task", "did 4");
        final Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        ImageButton inputtaskbarbutton = this.findViewById(R.id.button_input4);
        ImageButton statstaskbarbutton = this.findViewById(R.id.button_stats2);

        statstaskbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("task", "did 2");
                assert v != null;
                v.vibrate(400);
                SwitchActivity("stats", context);
            }
        });

        inputtaskbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert v != null;
                v.vibrate(400);
                SwitchActivity("input", context);
            }
        });
    }


    @Override
    public void onClick(View v) {
        Log.d("task", "bar");
        Toast.makeText(getContext(), "Text", Toast.LENGTH_SHORT).show();

    }

    private void DetermineColor(Context context) {
        ImageView statsback = findViewById(R.id.background_stats);
        ImageView inputback = findViewById(R.id.backgroundInput);
        if (context instanceof StatsActivity) {
            inputback.setBackgroundColor(getResources().getColor(R.color.Beige));
        } else if (context instanceof InputActivity) {
            statsback.setBackgroundColor(getResources().getColor(R.color.Beige));
        } else {
            statsback.setBackgroundColor(getResources().getColor(R.color.Beige));
            inputback.setBackgroundColor(getResources().getColor(R.color.Beige));
        }
    }

    public void SwitchActivity(String target, Context context) {

        if (target == "stats") {
            // ((Activity) context).finish();
            Intent intent = new Intent(context, StatsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            context.startActivity(intent);


        }
        if (target == "input") {

            Intent intent = new Intent(context, InputActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            context.startActivity(intent);

        }
    }
}


