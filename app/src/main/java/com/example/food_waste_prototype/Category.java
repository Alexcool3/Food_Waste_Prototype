package com.example.food_waste_prototype;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Category extends LinearLayout {

    private String name;
    private float pricePerUnit;
    private float amountFW;
    private float amountFS;
    private boolean inflated = false;

    public Category(Context context) {
        super(context);
       // inflate(context, R.layout.category_view, this);
        MakeLayout(context);
        Log.d("cat", "cat"+ this.name);
        // TODO Auto-generated constructor stub
    }

    public Category(Context context, String name, float pricePerUnit) {
        super(context);
        this.name = name;
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

    public void MakeLayout(Context context){
        if(!inflated) {
            inflated = true;
            Log.d("cat", "cat" + this.name);
            inflate(context, R.layout.category_view, this);
            TextView text = findViewById(R.id.name);
            text.setText(name);
        }
    }
}