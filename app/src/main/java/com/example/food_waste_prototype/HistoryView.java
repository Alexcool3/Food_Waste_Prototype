package com.example.food_waste_prototype;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

public class HistoryView extends RelativeLayout {

    private DataBase db;

    public HistoryView(final Context context, final DataBase.Input input, final HistoryDialog hd) {
        super(context);

        inflate(context, R.layout.dialog_history, this);
        db = DataBase.getInstance(context);
        ImageButton delete = findViewById(R.id.deleteButton);
        ImageButton edit = findViewById(R.id.editButton);

        delete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                db.DeleteInput(input);
                hd.Populate(context, db);

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                InputDialog id = new InputDialog(context, input, hd);
                hd.Populate(context, db);
            }
        });
    }

    public HistoryView(final Context context, final ConstraintLayout cl, ImageButton image, final View categoryView, final Category cg) {

        //This constructor turns the history view into the small menu that appears after long clicking
        super(context);
        db = DataBase.getInstance(context);
        final View newView = inflate(context, R.layout.dialog_history, this); // find the historyview layout
        TextView textView = findViewById(R.id.name); //identify the text view inside the layout
        textView.setVisibility(GONE); //remove the text view so only the buttons remain

        ImageButton delete = findViewById(R.id.deleteButton);
        ImageButton edit = findViewById(R.id.editButton);


        final View dummy = new View(context); // create a new empty view. The view is invisible and exists behind the small button menu. Its purpose is to register a click outside the menu and then close the menu
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT); // make the new view fills the entire screen
        dummy.setLayoutParams(lp2);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.DeleteCategory(cg.GetName());
                //  cl.removeView(categoryView);
                InputActivity.getInstance().populate(context);
                //  Toast.makeText(context, "Deleted " + cg.GetName(), Toast.LENGTH_LONG).show();

                RemoveSmallMenu(cl, newView, dummy, categoryView);

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCategory(context, cg);
                RemoveSmallMenu(cl, newView, dummy, categoryView);
                //Toast.makeText(context, "Edit this category", Toast.LENGTH_LONG).show();
            }
        });


        dummy.setOnTouchListener(new View.OnTouchListener() { // When someone clicks outside the small menu buttons
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) { //not sure its matters what enum
                    RemoveSmallMenu(cl, newView, dummy, categoryView);
                    return false; // returns false so that the click continues on to the parent below the dummy
                }
                return false;
            }
        });

        int[] location = new int[2];  // create new int array to store position of window
        image.getLocationOnScreen(location); // gets pos of the button on the category view
        setPadding(location[0] + 20, location[1] - 80, 0, 0); // sets the padding of the small buttons view to be slighty above the category

        cl.addView(dummy); // add the views to the constrant view at the base on input page
        cl.addView(newView);


    }

    public void RemoveSmallMenu(ConstraintLayout cl, View smallmenu, View dummy, View categoryView) {
        categoryView.clearAnimation(); // makes the category stop shaking
        cl.removeView(smallmenu);
        cl.removeView(dummy); // remove both the dummy and the small menu buttons
    }

    /*public HistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.dialog_history, this);




        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HistoryView);
        //imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
        //textView.text = attributes.getString(R.styleable.BenefitView_text)
        attributes.recycle();


    }*/

    public HistoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void SetText(String text) {
        TextView textView = findViewById(R.id.name);
        textView.setText(text);
    }

    public void SetupButtons(final Context context, final Category cg, final View v, final ConstraintLayout cl, View smallmenu, final View dummy, View categoryView) {

    }


    public void EditCategory(final Context context, final Category cat) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.new_category_dialog);
        final EditText nameInput = dialog.findViewById(R.id.name_input);
        final EditText priceInput = dialog.findViewById(R.id.price_input);
        final TextView catName = dialog.findViewById(R.id.dialog_title);
        Button confrimButton = dialog.findViewById(R.id.confirm_button);
        Button cancelButton = dialog.findViewById(R.id.cancel_button);
        dialog.show();

        catName.setText("Rediger Kategori");
        nameInput.setHint("Name: " + cat.GetName());
        priceInput.setHint("Pris per kilo: " + String.valueOf(cat.GetPricePerUnit()));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        confrimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < db.GetInputs().size(); i++) {
                    DataBase.Input in = db.GetInputs().get(i);
                    if (in.getName().equals(cat.GetName()) && !(nameInput.getText().toString().equals(""))) {
                        in.SetName(nameInput.getText().toString());
                    }
                }

                if (nameInput.getText().toString().equals("")) {
                    cat.SetName(cat.GetName());
                } else {
                    cat.SetName(nameInput.getText().toString());
                }

                if (priceInput.getText().toString().equals("")) {
                    cat.SetPricePerUnit(cat.GetPricePerUnit());
                } else {
                    cat.SetPricePerUnit(Float.parseFloat(String.valueOf(priceInput.getText())));
                }


                // populate(context, tb);
                dialog.dismiss();
            }
        });
    }
}
