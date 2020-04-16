package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    private boolean foodwastetoggle = false;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> amounts = new ArrayList<>();

    public StatsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        final Context context = StatsActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // make the app fullscreen
        TaskBarButtons(); // activate the taskbar buttons
        FillTable();
        ToggleButton();
    }


    private void TaskBarButtons() {


        ImageButton inputtaskbarbutton = findViewById(R.id.button_input);


        inputtaskbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity();
            }
        });
    }

    private void ToggleButton() {

        final ImageButton switchbutton = findViewById(R.id.button_switch);
        final TableLayout table = findViewById(R.id.tableLayout);


        final ImageButton toggle = findViewById(R.id.button_toggle);
        final TextView toggletext= findViewById(R.id.toggleText);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchToggle(toggle,toggletext);
            }
        });
        switchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   SwitchTable(switchbutton,table);
            }
        });
    }

    private void SwitchTable(){

    }

    private void SwitchToggle(ImageButton toggle, TextView toggletext){
        foodwastetoggle=!foodwastetoggle;

            if (foodwastetoggle){
                toggletext.setText("Mad Spild");
                toggle.setImageResource(R.drawable. button_toggle_reverse);
            } else {
            toggletext.setText("Mad Affald");
                toggle.setImageResource(R.drawable. button_toggle);
        }
    }

    private void FillTable() {
        TextView employeeName, employeeSalary;
        try
        {
                JSONArray mJsonArray = new JSONArray(loadJSONFromAsset());
                for (int i = 0; i < mJsonArray.length(); i++) {
                    JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                    JSONArray mJsonArrayProperty = mJsonObject.getJSONArray("properties");
                    for (int j = 0; j < mJsonArrayProperty.length(); j++) {
                        JSONObject mJsonObjectProperty = mJsonArrayProperty.getJSONObject(i);

                        names.add(mJsonObjectProperty.getString("name"));


                    }
                }

        }  catch (JSONException ex) {
                ex.printStackTrace();
            Toast.makeText(StatsActivity.this,
                    "Failed", Toast.LENGTH_LONG).show();
            }
        //Toast.makeText(StatsActivity.this,
               // names.get(0), Toast.LENGTH_LONG).show();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.foodwaste);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void SwitchActivity() {


    Intent intent = new Intent(this, InputActivity.class);

    startActivity(intent);
    }
}
