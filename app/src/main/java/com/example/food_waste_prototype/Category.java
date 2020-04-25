package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Category extends LinearLayout{

    private String name;
    private float pricePerUnit;
    private float amountFW;
    private float amountFS;
    private boolean inflated = false;


    public Category(Context context) {
        super(context);
       // inflate(context, R.layout.category_view, this);
        //MakeLayout(context);
        Log.d("cat", "cat"+ this.name);
        // TODO Auto-generated constructor stub
    }

    public Category(Context context, String name, float pricePerUnit) {
        super(context);
        this.name = name;
        this.pricePerUnit=pricePerUnit;
        this.amountFW=0;
        this.amountFS=0;
        Log.d("cat", "cat"+ this.name);
       // inflate(context, R.layout.category_view, this);
    }

    public Category(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.name = name;
        this.amountFW=0;
        this.amountFS=0;
        Log.d("cat", "cat"+ this.name);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CategoryView);
        //MakeLayout(context);
        //imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
        //textView.text = attributes.getString(R.styleable.BenefitView_text)
       // inflate(context, R.layout.category_view, this);
        attributes.recycle();
    }

    public Category(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
       // inflate(context, R.layout.category_view, this);
    }

    public String GetName(){
        return name;
    }

    public float GetPricePerUnit(){
        return pricePerUnit;
    }

    public float GetAmountFW(){
        return amountFW;
    }

    public float GetAmountFS(){
        return amountFS;
    }

    public void SetName(String name) {
        this.name = name;
        if(inflated){
            TextView text = findViewById(R.id.name); // sets the label under the category picture
            text.setText(name);
        }
    }

    public void SetPricePerUnit(float amount) {
        this.pricePerUnit = amount;
    }

    public void AddFW(float amount) {
        amountFW+=amount;
    }

    public void AddFS(float amount) {
        amountFS+=amount;
    }

    public void MakeLayout(final Context context, final ConstraintLayout cl){
        if(!inflated) { // if the category has not been inflated when loading the input page before
            inflated = true;
            Log.d("cat", "cat" + this.name);
            final View catview = inflate(context, R.layout.category_view, this);

            TextView text = findViewById(R.id.name); // sets the label under the category picture
            text.setText(name);

            final ImageButton image = this.findViewById(R.id.image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new InputDialog(context, Category.this );
                }
            });

            image.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) { //when long click open the small menu above the view and start shaking
                    catview.startAnimation(AnimationUtils.loadAnimation(context,R.anim.shakeanim));
                    new HistoryView(context, cl, image,catview, Category.this);
                    return true;
                }
            });

        }
    }
}