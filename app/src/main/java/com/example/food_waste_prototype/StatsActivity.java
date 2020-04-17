package com.example.food_waste_prototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView.OnItemSelectedListener;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    private boolean foodwastetoggle = false;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> amounts = new ArrayList<>();
    String[] days = new String[]{"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
    String[] months = new String[]{"Januar", "Februar", "Marts", "April", "Maj", "Juni", "Juli","August", "September", "Oktober", "November", "December" };
    String[] years = new String[]{"2016", "2017", "2018", "2019", "2020"};
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
        DropDown();
        SettingsButton();

    }

    private void DropDown() {
        final Spinner dropdown = findViewById(R.id.dropdown);
        String[] items = new String[]{"Dag", "Måned", "År"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(StatsActivity.this, R.layout.spinner_text, items );
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        setDate("left", dropdown);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                       // showToast("Spinner1: unselected");
                    }
                });
            Arrows(dropdown);
    }

    private void Arrows(final Spinner dropdown) {


        ImageButton leftArrow =  findViewById(R.id.button_arrow_left);
        ImageButton rightArrow =  findViewById(R.id.button_arrow_right);




        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("left", dropdown);
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("rigth" , dropdown);
            }
        });

    }

    private void setDate(String direction, Spinner dropdown){



        String text = dropdown.getSelectedItem().toString();
        final String[] targetarray;
        if(text.equals("Dag")){
            targetarray=days;
        } else if(text.equals("Måned")){
            targetarray=months;
        } else{
            targetarray=years;
        }


        int pos = 0;
        TextView date = findViewById(R.id.date);
        for(int i = 0; i < targetarray.length; i++) {
            if (date.getText().equals(targetarray[i])){
                pos=i;

            }
        }





        if(direction.equals("left")){
            if(pos>-1){

                if(pos==0){
                    pos=targetarray.length;
                }
                date.setText(targetarray[pos-1]);
            }
        }
        else {
            if (pos < targetarray.length) {

                if (pos == targetarray.length - 1) {
                    pos = -1;
                }
                date.setText(targetarray[pos + 1]);
            }
        }
    }


    private void TaskBarButtons() {


        ImageButton inputtaskbarbutton = findViewById(R.id.button_input);


        inputtaskbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("input");
            }
        });
    }

    private void ToggleButton() {

        final ImageButton switchbutton = findViewById(R.id.button_switch);
        final TableLayout table = findViewById(R.id.tableLayout);
       // final ImageView piechart = findViewById(R.id.piechart);

        final ImageButton toggle = findViewById(R.id.button_toggle);
        final TextView toggletext = findViewById(R.id.toggleText);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchToggle(toggle, toggletext);
            }
        });
        switchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchTable(switchbutton, table);
            }
        });
    }

    private void SwitchTable(ImageButton switchy, TableLayout table) {

        PieChart pieChart = findViewById(R.id.pieChart);
        drawChart((pieChart));

        if (table.getVisibility() == View.VISIBLE) {
            table.setVisibility(View.INVISIBLE);
            pieChart.setVisibility(View.VISIBLE);

        } else {
            pieChart.setVisibility(View.INVISIBLE);
            table.setVisibility(View.VISIBLE);
        }
    }

    private void SwitchToggle(ImageButton toggle, TextView toggletext) {
        foodwastetoggle = !foodwastetoggle;

        if (foodwastetoggle) {
            toggletext.setText("Mad Affald");
            toggle.setImageResource(R.drawable.button_toggle_reverse);
        } else {
            toggletext.setText("Mad Spild");
            toggle.setImageResource(R.drawable.button_toggle);
        }
    }


    private void SettingsButton() {

        final ImageButton settingsButton = findViewById(R.id.button_options);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("settings");
            }
        });
    }

    private void FillTable() {
        TextView employeeName, employeeSalary;
        try {
            JSONArray mJsonArray = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                JSONArray mJsonArrayProperty = mJsonObject.getJSONArray("properties");
                for (int j = 0; j < mJsonArrayProperty.length(); j++) {
                    JSONObject mJsonObjectProperty = mJsonArrayProperty.getJSONObject(i);

                    names.add(mJsonObjectProperty.getString("name"));


                }
            }

        } catch (JSONException ex) {
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

    private void SwitchActivity(String target) {

        if (target == "settings") {

            Intent intent = new Intent(this, SettingsActivity.class);

            startActivity(intent);
        }
        if (target == "input") {


            Intent intent = new Intent(this, InputActivity.class);

            startActivity(intent);
        }
    }

    private void drawChart(PieChart pieChart) {


        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(8f, "January", 0));
        yvalues.add(new PieEntry(15f, "February", 1));
        yvalues.add(new PieEntry(12f, "March", 2));
        yvalues.add(new PieEntry(25f, "April", 3));
        yvalues.add(new PieEntry(23f, "May", 4));
        yvalues.add(new PieEntry(17f, "June", 5));

        PieDataSet dataSet = new PieDataSet(yvalues, "ost");
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

    }
}
