package com.example.food_waste_prototype;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Category extends LinearLayout {

    private String name;
    private float pricePerUnit;
    private float amountFW;
    private float amountFS;
    private boolean inflated = false;
    private boolean edittarget = false;
    private boolean deletetarget = false;


    public Category(Context context) {
        super(context);
        // inflate(context, R.layout.category_view, this);
        //MakeLayout(context);
        Log.d("cat", "cat" + this.name);
        // TODO Auto-generated constructor stub
    }

    public Category(Context context, String name, float pricePerUnit) {
        super(context);
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.amountFW = 0;
        this.amountFS = 0;
        Log.d("cat", "cat" + this.name);
        // inflate(context, R.layout.category_view, this);
    }

    public Category(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.name = name;
        this.amountFW = 0;
        this.amountFS = 0;
        Log.d("cat", "cat" + this.name);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CategoryView);
        //MakeLayout(context);
        //imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
        //textView.text = attributes.getString(R.styleable.BenefitView_text)
        // inflate(context, R.layout.category_view, this);
        attributes.recycle();
    }

    public Category(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // inflate(context, R.layout.category_view, this);
    }

    public String GetName() {
        return name;
    }

    public float GetPricePerUnit() {
        return pricePerUnit;
    }

    public float GetAmountFW() {
        return amountFW;
    }

    public float GetAmountFS() {
        return amountFS;
    }

    public void SetName(String name) {
        this.name = name;
        if (inflated) {
            TextView text = findViewById(R.id.name); // sets the label under the category picture
            text.setText(name);
        }
    }

    public void SetPricePerUnit(float amount) {
        this.pricePerUnit = amount;
    }

    public void AddFW(float amount) {
        amountFW += amount;
    }

    public void AddFS(float amount) {
        amountFS += amount;
    }

    public void StartShaking(final Context context, String shakereason) {
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.shakeanim));
        final ImageButton image = this.findViewById(R.id.image);
        if (shakereason.equals("edit")) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StopShaking(context);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.new_category_dialog);
                    final EditText nameInput = dialog.findViewById(R.id.name_input);
                    final EditText priceInput = dialog.findViewById(R.id.price_input);
                    final TextView catName = dialog.findViewById(R.id.dialog_title);
                    Button confrimButton = dialog.findViewById(R.id.confirm_button);
                    Button cancelButton = dialog.findViewById(R.id.cancel_button);
                    dialog.show();

                    catName.setText("Rediger Kategori");
                    nameInput.setHint("Name: " + GetName());
                    priceInput.setHint("Pris per kilo: " + String.valueOf(GetPricePerUnit()));

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    confrimButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (nameInput.getText().toString().equals("")) {
                                SetName(GetName());
                            } else {
                                SetName(nameInput.getText().toString());
                            }

                            if (priceInput.getText().toString().equals("")) {
                                SetPricePerUnit(GetPricePerUnit());
                            } else {
                                SetPricePerUnit(Float.parseFloat(String.valueOf(priceInput.getText())));
                            }


                            // populate(context, tb);
                            dialog.dismiss();
                        }
                    });
                }
            });
        } else {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StopShaking(context);
                    DataBase.getInstance(context).DeleteCategory(GetName());
                    //  cl.removeView(categoryView);
                    InputActivity.getInstance().populate(context);
                }
            });
        }
    }

    public void StopShaking(final Context context) {
        clearAnimation();
        final ImageButton image = this.findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InputDialog(context, Category.this);
            }
        });
    }

    public void MakeLayout(final Context context, final ConstraintLayout cl) {
        if (!inflated) { // if the category has not been inflated when loading the input page before
            inflated = true;
            Log.d("cat", "cat" + this.name);
            final View catview = inflate(context, R.layout.category_view, this);

            TextView text = findViewById(R.id.name); // sets the label under the category picture
            text.setText(name);

            final ImageButton image = this.findViewById(R.id.image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new InputDialog(context, Category.this);
                }
            });

            image.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) { //when long click open the small menu above the view and start shaking
                    catview.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shakeanim));
                    new HistoryView(context, cl, image, catview, Category.this);
                    return true;
                }
            });

        }
    }
}