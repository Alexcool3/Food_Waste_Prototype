package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatsActivity extends AppCompatActivity {

    private boolean foodwastetoggle = false;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> amounts = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    Calendar currentSet = Calendar.getInstance();
    DataBase db;


    public StatsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        final Context context = StatsActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // make the app fullscreen
        db = DataBase.getInstance(context);
        SetupButtons(context);
        FillTable();
       // db.CreateCategory("ost", 33);
      ///  db.CreateCategory("kød", 33);
       // db.AddFoodWaste("ost", 500, false);
      //  db.AddFoodWaste("kød", 250, false);
       // db.AddFoodWaste("kød", 250, true);
    }

    private void SetupButtons(Context context) {

        final TaskBarView taskbar = findViewById(R.id.taskBarView);
        taskbar.taskings(context);

        ImageButton leftArrow = findViewById(R.id.button_arrow_left);
        ImageButton rightArrow = findViewById(R.id.button_arrow_right);
        final TextView date = findViewById(R.id.date);
        final ImageButton switchbutton = findViewById(R.id.button_switch);
        final ImageButton helpbutton = findViewById(R.id.button_information);
        final TableLayout table = findViewById(R.id.tableLayout);
        final ImageButton settingsButton = findViewById(R.id.button_options);
        final ImageButton toggle = findViewById(R.id.button_toggle);
        final TextView toggletext = findViewById(R.id.toggleText);
        final Spinner dropdown = findViewById(R.id.dropdown);
        final PieChart pieChart = findViewById(R.id.pieChart);

        //setup the dropdown
        String[] items = new String[]{"Dag", "Måned", "År"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(StatsActivity.this, R.layout.spinner_text, items);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        setDate("left", dropdown, date);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        // showToast("Spinner1: unselected");
                    }
                });

        // arrow to the left of the label
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("left", dropdown, date);
            }
        });

        // arrow to the right of the label
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate("rigth", dropdown, date);
            }
        });


        // food waste - food scraps toogle
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchToggle(toggle, toggletext, pieChart, dropdown);
            }
        });

        // switching between table
        switchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchTable(switchbutton, table, pieChart, dropdown);
            }
        });

        // options button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity("settings");
            }
        });

        // help button
        helpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Help();
            }
        });
    }

    private boolean TimeFrame(Calendar inputday, Spinner dd) {

        //TODO: make the inputs user the same time format

        String text = dd.getSelectedItem().toString();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate=currentSet;
        if(text.equals("Dag")){
            startDate=currentSet;

        } else if(text.equals("Måned")){
            startDate.set(Calendar.DAY_OF_MONTH, 1);
        } else{
            startDate.set(Calendar.DAY_OF_YEAR, 1);
        }

        return inputday.after(startDate.getTime()) && inputday.before(endDate.getTime());
    }

    private void setDate(String direction, Spinner dropdown, TextView date) {

        // get the current selected item from dropdown
        String text = dropdown.getSelectedItem().toString();


        // label between the arrows

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        if(date.getText().equals("December")){
            cal.add(Calendar.DAY_OF_MONTH, 1);
            date.setText(dayFormat.format(cal.getTime()));

        }


        if (text.equals("Dag")) {
            // move the entry to the left one
            if (direction.equals("left")) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
                currentSet=cal;
                date.setText(dayFormat.format(cal.getTime()));

            } else {
                cal.add(Calendar.DAY_OF_MONTH, +1);
                currentSet=cal;
                date.setText(dayFormat.format(cal.getTime()));
            }
        } else if (text.equals("Måned")) {
            if (direction.equals("left")) {
                cal.add(Calendar.MONTH, -1);
                currentSet=cal;
                date.setText(monthFormat.format(cal.getTime()));
            } else {
                cal.add(Calendar.MONTH, +1);
                currentSet=cal;
                date.setText(monthFormat.format(cal.getTime()));
            }
        } else if (text.equals("År")) {
            if (direction.equals("left")) {
                cal.add(Calendar.YEAR, -1);
                currentSet=cal;
                date.setText(yearFormat.format(cal.getTime()));
            } else {
                cal.add(Calendar.YEAR, +1);
                currentSet=cal;
                date.setText(yearFormat.format(cal.getTime()));
            }
        }
    }

    private void SwitchTable(ImageButton switchy, TableLayout table, PieChart pieChart, Spinner dd) {


        drawChart(pieChart,dd);

        if (table.getVisibility() == View.VISIBLE) {
            table.setVisibility(View.INVISIBLE);
            pieChart.setVisibility(View.VISIBLE);

        } else {
            pieChart.setVisibility(View.INVISIBLE);
            table.setVisibility(View.VISIBLE);
        }
    }

    private void SwitchToggle(ImageButton toggle, TextView toggletext, PieChart pie, Spinner dd) {
        foodwastetoggle = !foodwastetoggle;

        if (foodwastetoggle) {
            toggletext.setText("Mad Affald");
            toggle.setImageResource(R.drawable.button_toggle_reverse);
        } else {
            toggletext.setText("Mad Spild");
            toggle.setImageResource(R.drawable.button_toggle);
        }

        drawChart(pie,dd);
    }

    private void FillTable() {
        TextView employeeName, employeeSalary;

    }

    private void Help() {
        HistoryDialog hd = new HistoryDialog(StatsActivity.this, db);

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

    private void drawChart(PieChart pieChart, Spinner dd) {

        //TODO: iteratre over the inputs instead and use the TimeFrame() function to make sure they are within the requested date
        pieChart.clear();
        pieChart.setUsePercentValues(true);

        ArrayList<Category> cat = db.GetAllCategories();
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        PieDataSet dataSet;
        for (int i = 0; i < cat.size(); i++) {
            Category ca = cat.get(i);

            if (foodwastetoggle) {
                if (ca.GetAmountFW() == 0) {
                    continue;
                }
                yvalues.add(new PieEntry(ca.GetAmountFW(), ca.GetName(), i));
            } else {
                if (ca.GetAmountFS() == 0) {
                    continue;
                }
                yvalues.add(new PieEntry(ca.GetAmountFS(), ca.GetName(), i));
            }
        }
        if (foodwastetoggle) {
            dataSet = new PieDataSet(yvalues, "Mad Affald");
        } else {
            dataSet = new PieDataSet(yvalues, "Mad Spild");
        }

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
