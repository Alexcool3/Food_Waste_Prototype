package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView.OnItemSelectedListener;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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
        SetupButtons();
        FillTable();
    }

    private void SetupButtons(){

        ImageButton leftArrow =  findViewById(R.id.button_arrow_left);
        ImageButton rightArrow =  findViewById(R.id.button_arrow_right);
        ImageButton inputtaskbarbutton = findViewById(R.id.button_input);
        final ImageButton switchbutton = findViewById(R.id.button_switch);
        final TableLayout table = findViewById(R.id.tableLayout);
        final ImageButton settingsButton = findViewById(R.id.button_options);
        final ImageButton toggle = findViewById(R.id.button_toggle);
        final TextView toggletext = findViewById(R.id.toggleText);
        final Spinner dropdown = findViewById(R.id.dropdown);
        final PieChart pieChart = findViewById(R.id.pieChart);

        //setup the dropdown
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

        // arrow to the left of the label
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("left", dropdown);
            }
        });

        // arrow to the right of the label
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("rigth" , dropdown);
            }
        });

        // button that leads to inputpage
        inputtaskbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("input");
            }
        });

        // food waste - food scraps toogle
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchToggle(toggle, toggletext);
            }
        });

        // switching between table
        switchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchTable(switchbutton, table, pieChart);
            }
        });

        // options button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("settings");
            }
        });
    }

    private void setDate(String direction, Spinner dropdown){

        // get the current selected item from dropdown
        String text = dropdown.getSelectedItem().toString();

        // array to contain days, months or years based on dropdown
        final String[] targetarray;

        // current position in array
        int pos = 0;

        // label between the arrows
        TextView date = findViewById(R.id.date);

        if(text.equals("Dag")){
            targetarray=days;
        } else if(text.equals("Måned")){
            targetarray=months;
        } else{
            targetarray=years;
        }

        // find out whats in the label and set the current pos to that
        for(int i = 0; i < targetarray.length; i++) {
            if (date.getText().equals(targetarray[i])){
                pos=i;
            }
        }

        // move the entry to the left one
        if(direction.equals("left")){
            if(pos>-1){
                if(pos==0){
                    pos=targetarray.length;
                }
                date.setText(targetarray[pos-1]);
            }
        }
        // move the entry to the right one
        else {
            if (pos < targetarray.length) {

                if (pos == targetarray.length - 1) {
                    pos = -1;
                }
                date.setText(targetarray[pos + 1]);
            }
        }
    }

    private void SwitchTable(ImageButton switchy, TableLayout table, PieChart pieChart) {


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

    private void FillTable() {
        TextView employeeName, employeeSalary;

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
