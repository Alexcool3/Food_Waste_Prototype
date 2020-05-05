package com.example.food_waste_prototype;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Category extends LinearLayout {

    private String name;
    private float pricePerUnit;
    private float amountFW;
    private float amountFS;
    private int id;
    private boolean inflated = false;
    public boolean edittarget = false;
    public boolean deletetarget = false;


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
                    for (int i = 0; i <= DataBase.getInstance(context).GetAllCategories().size() - 1; i++) {
                        Category cg = DataBase.getInstance(context).GetAllCategories().get(i);
                        cg.StopShaking(context);
                    }
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


                            for (int i = 0; i < DataBase.getInstance(context).GetInputs().size(); i++) {
                                DataBase.Input in = DataBase.getInstance(context).GetInputs().get(i);
                                if (in.getName().equals(GetName()) && !(nameInput.getText().toString().equals(""))) {
                                    in.SetName(nameInput.getText().toString());
                                }
                            }
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

                            BackgroundTask backgroundTask = new BackgroundTask(context);
                            Log.d("Check", "Username: " + DataBase.username + " id: " + id + " cat_name:" + nameInput.getText().toString() + "price: " + priceInput.getText().toString());
                            backgroundTask.execute("editCategory", DataBase.username, String.valueOf(id), nameInput.getText().toString(), priceInput.getText().toString());
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

                    for (int i = 0; i <= DataBase.getInstance(context).GetAllCategories().size() - 1; i++) {
                        Category cg = DataBase.getInstance(context).GetAllCategories().get(i);
                        cg.StopShaking(context);
                    }

                    //  cl.removeView(categoryView);
                    InputActivity.getInstance().populate(context);

                }
            });
        }
    }

    public void StopShaking(final Context context) {
        clearAnimation();
        edittarget = false;
        deletetarget = false;
        final ImageButton image = this.findViewById(R.id.image);
        image.setOnClickListener(null);
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

            switch (name.toLowerCase()) {
                case "alkohol":
                    image.setImageResource(R.drawable.alcohol);
                    break;
                case "æbler":
                    image.setImageResource(R.drawable.apple);
                    break;
                case "æble":
                    image.setImageResource(R.drawable.apple);
                    break;
                case "avocadoer":
                    image.setImageResource(R.drawable.avocado);
                    break;
                case "avocado":
                    image.setImageResource(R.drawable.avocado);
                    break;
                case "bananer":
                    image.setImageResource(R.drawable.banana);
                    break;
                case "banan":
                    image.setImageResource(R.drawable.banana);
                    break;
                case "øller":
                    image.setImageResource(R.drawable.beer);
                    break;
                case "øl":
                    image.setImageResource(R.drawable.beer);
                    break;
                case "brød":
                    image.setImageResource(R.drawable.bread);
                    break;
                case "burgere":
                    image.setImageResource(R.drawable.burger);
                    break;
                case "burger":
                    image.setImageResource(R.drawable.burger);
                    break;
                case "kål":
                    image.setImageResource(R.drawable.cabbage);
                    break;
                case "kager":
                    image.setImageResource(R.drawable.cake);
                    break;
                case "kage":
                    image.setImageResource(R.drawable.cake);
                    break;
                case "gulerod":
                    image.setImageResource(R.drawable.carrot);
                    break;
                case "gulerødder":
                    image.setImageResource(R.drawable.carrot);
                    break;
                case "ost":
                    image.setImageResource(R.drawable.cheese);
                    break;
                case "oste":
                    image.setImageResource(R.drawable.cheese);
                    break;
                case "bær":
                    image.setImageResource(R.drawable.cherry);
                    break;
                case "kylling":
                    image.setImageResource(R.drawable.chicken);
                    break;
                case "kaffe":
                    image.setImageResource(R.drawable.coffe);
                    break;
                case "cola":
                    image.setImageResource(R.drawable.cola);
                    break;
                case "korn":
                    image.setImageResource(R.drawable.corn);
                    break;
                case "agurk":
                    image.setImageResource(R.drawable.cucumber);
                    break;
                case "agurker":
                    image.setImageResource(R.drawable.cucumber);
                    break;
                case "muffin":
                    image.setImageResource(R.drawable.cupcake);
                    break;
                case "muffins":
                    image.setImageResource(R.drawable.cupcake);
                    break;
                case "donut":
                    image.setImageResource(R.drawable.donut);
                    break;
                case "donuts":
                    image.setImageResource(R.drawable.donut);
                    break;
                case "drinks":
                    image.setImageResource(R.drawable.drinks);
                    break;
                case "æg":
                    image.setImageResource(R.drawable.egg);
                    break;
                case "aubergine":
                    image.setImageResource(R.drawable.eggplant);
                    break;
                case "auberginer":
                    image.setImageResource(R.drawable.eggplant);
                    break;
                case "fastfood":
                    image.setImageResource(R.drawable.fasttfood);
                    break;
                case "fisk":
                    image.setImageResource(R.drawable.fish);
                    break;
                case "pomfritter":
                    image.setImageResource(R.drawable.fries);
                    break;
                case "fritter":
                    image.setImageResource(R.drawable.fries);
                    break;
                case "frugt":
                    image.setImageResource(R.drawable.fruit);
                    break;
                case "frugter":
                    image.setImageResource(R.drawable.fruit);
                    break;
                case "druer":
                    image.setImageResource(R.drawable.grapes);
                    break;
                case "vindruer":
                    image.setImageResource(R.drawable.grapes);
                    break;
                case "grøntsager":
                    image.setImageResource(R.drawable.greenybois);
                    break;
                case "grill":
                    image.setImageResource(R.drawable.grill);
                    break;
                case "urter":
                    image.setImageResource(R.drawable.herbs);
                    break;
                case "krydderier":
                    image.setImageResource(R.drawable.herbs);
                    break;
                case "hotdog":
                    image.setImageResource(R.drawable.hotdog);
                    break;
                case "hotdogs":
                    image.setImageResource(R.drawable.hotdog);
                    break;
                case "is":
                    image.setImageResource(R.drawable.icecream);
                    break;
                case "lemon":
                    image.setImageResource(R.drawable.lemon);
                    break;
                case "kød":
                    image.setImageResource(R.drawable.meatslice);
                    break;
                case "mælk":
                    image.setImageResource(R.drawable.milk);
                    break;
                case "svampe":
                    image.setImageResource(R.drawable.mushroom);
                    break;
                case "champignons":
                    image.setImageResource(R.drawable.mushroom);
                    break;
                case "spaghetti":
                    image.setImageResource(R.drawable.nuddel);
                    break;
                case "nudler":
                    image.setImageResource(R.drawable.nuddel);
                    break;
                case "løg":
                    image.setImageResource(R.drawable.onion);
                    break;
                case "appelsin":
                    image.setImageResource(R.drawable.orange);
                    break;
                case "appelsiner":
                    image.setImageResource(R.drawable.orange);
                    break;
                case "fersken":
                    image.setImageResource(R.drawable.peach);
                    break;
                case "ærter":
                    image.setImageResource(R.drawable.peas);
                    break;
                case "rød peber":
                    image.setImageResource(R.drawable.pepper);
                    break;
                case "græskar":
                    image.setImageResource(R.drawable.pumpkin);
                    break;
                case "pølse":
                    image.setImageResource(R.drawable.sausage);
                    break;
                case "pølser":
                    image.setImageResource(R.drawable.sausage);
                    break;
                case "suppe":
                    image.setImageResource(R.drawable.soup);
                    break;
                case "supper":
                    image.setImageResource(R.drawable.soup);
                    break;
                case "spyd":
                    image.setImageResource(R.drawable.stick);
                    break;
                case "jordbær":
                    image.setImageResource(R.drawable.strawberry);
                    break;
                case "stuvning":
                    image.setImageResource(R.drawable.stuvning);
                    break;
                case "blæksprutte":
                    image.setImageResource(R.drawable.squid);
                    break;
                case "sprutte":
                    image.setImageResource(R.drawable.squid);
                    break;
                case "tacos":
                    image.setImageResource(R.drawable.tacos);
                    break;
                case "mexicansk":
                    image.setImageResource(R.drawable.tacos);
                    break;
                case "sushi":
                    image.setImageResource(R.drawable.sushi);
                    break;
                case "te":
                    image.setImageResource(R.drawable.tea);
                    break;
                case "the":
                    image.setImageResource(R.drawable.tea);
                    break;
                case "tomater":
                    image.setImageResource(R.drawable.tomat);
                    break;
                case "vandmelon":
                    image.setImageResource(R.drawable.watermelon);
                    break;
                case "vand melon":
                    image.setImageResource(R.drawable.watermelon);
                    break;
                case "vin":
                    image.setImageResource(R.drawable.wine);
                    break;
                default:
                    image.setImageResource(R.drawable.button_default);
                    break;
            }


            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputDialog id = new InputDialog(context, Category.this);
                    hideBorder();
                    //  id.getWindow();
                }
            });

            image.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) { //when long click open the small menu above the view and start shaking
                    catview.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shakeanim));
                    hideBorder();
                    new HistoryView(context, cl, image, catview, Category.this);
                    return true;
                }
            });

        } else {
            hideBorder();
        }
    }

    private void hideBorder() {
        ImageView border = findViewById(R.id.border);
        border.setVisibility(INVISIBLE);
    }

    public void SetID(int id){
        this.id = id;
    }

    public int GetID(){
        return id;
    }
}