package com.example.food_waste_prototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Struct;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART;

public class StatsActivity extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> amounts = new ArrayList<>();

    LocalDate currentSetDate;
    LocalDate currentSetWeek;
    LocalDate currentSetMonth;

    DataBase db;
    Context context;

    public StatsActivity() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        context = StatsActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // make the app fullscreen
        db = DataBase.getInstance(context);
        currentSetDate = LocalDate.now();
        currentSetWeek = LocalDate.now();
        currentSetMonth = LocalDate.now();
        //  Log.d("fre", "inputs:" + 0);
        SetupButtons(context);
        FillTable();
        // FillTable();
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
        String[] items = new String[]{"Dag", "Uge", "Måned"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(StatsActivity.this, R.layout.spinner_text, items);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        setDate("", dropdown, date);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        // showToast("Spinner1: unselected");
                    }
                });

        // arrow to the left of the label
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                setDate("left", dropdown, date);
            }
        });

        // arrow to the right of the label
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                setDate("right", dropdown, date);
            }
        });


        // food waste - food scraps toogle
        if (db.GetEnumToString().equals("Mad Affald")) {
            toggle.setImageResource(R.drawable.button_toggle_reverse);
        } else {
            toggle.setImageResource(R.drawable.button_toggle);
        }
        toggle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                SwitchToggle(toggle, toggletext, dropdown);
            }
        });

        // switching between table
        switchbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean TimeFrame(Spinner dd, DataBase.Input inputtime) {
        return inputtime.getTime() == LocalDate.now();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDate(String direction, Spinner dropdown, TextView date) {

        // get the current selected item from dropdown
        String text = dropdown.getSelectedItem().toString();


        // label between the arrows

        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("dd/MM");
        DateTimeFormatter weekformatter = DateTimeFormatter.ofPattern("ww");
        DateTimeFormatter monthformatter = DateTimeFormatter.ofPattern("MMMM yy");


        //  if(date.getText().equals("December")){
        //     cal.add(Calendar.DAY_OF_MONTH, 1);
        //     date.setText(dayFormat.format(cal.getTime()));

        // }
        //currentSetDate = LocalDate.now();

        if (text.equals("Dag")) {
            if (direction.equals("left")) {
                currentSetDate = currentSetDate.minusDays(1);
                String formattedDateTime = currentSetDate.format(dayformatter);
                date.setText(formattedDateTime);
                FillTable();
            } else if (direction.equals("right")) {
                currentSetDate = currentSetDate.plusDays(1);
                String formattedDateTime = currentSetDate.format(dayformatter);
                date.setText(formattedDateTime);
                FillTable();
            } else {
                String formattedDateTime = currentSetDate.format(dayformatter);
                date.setText(formattedDateTime);
                FillTable();
            }
        }

        if (text.equals("Uge")) {
            if (direction.equals("left")) {
                currentSetWeek = currentSetWeek.minusWeeks(1);
                String formattedDateTime = currentSetWeek.format(weekformatter);
                date.setText(formattedDateTime);
                FillTable();
            } else if (direction.equals("right")) {
                currentSetWeek = currentSetWeek.plusWeeks(1);
                String formattedDateTime = currentSetWeek.format(weekformatter);
                date.setText(formattedDateTime);
                FillTable();
            } else {
                String formattedDateTime = currentSetWeek.format(weekformatter);
                date.setText(formattedDateTime);
                FillTable();
            }
        }

        if (text.equals("Måned")) {
            if (direction.equals("left")) {
                currentSetMonth = currentSetMonth.minusMonths(1);
                String formattedDateTime = currentSetMonth.format(monthformatter);
                date.setText(formattedDateTime);
                FillTable();
            } else if (direction.equals("right")) {
                currentSetMonth = currentSetMonth.plusMonths(1);
                String formattedDateTime = currentSetMonth.format(monthformatter);
                date.setText(formattedDateTime);
                FillTable();
            } else {
                String formattedDateTime = currentSetMonth.format(monthformatter);
                date.setText(formattedDateTime);
                FillTable();
            }
        }
        drawChart();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SwitchTable(ImageButton switchy, TableLayout table, PieChart pieChart, Spinner dd) {


        if (table.getVisibility() == View.VISIBLE) {
            table.setVisibility(View.INVISIBLE);
            pieChart.setVisibility(View.VISIBLE);
            switchy.setImageResource(R.drawable.button_table);
            drawChart();
        } else {
            pieChart.setVisibility(View.INVISIBLE);
            table.setVisibility(View.VISIBLE);
            switchy.setImageResource(R.drawable.button_charterferie);
            FillTable();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SwitchToggle(ImageButton toggle, TextView toggletext, Spinner dd) {

        db.flipEnum();
        toggletext.setText(db.GetEnumToString());
        if (db.GetEnumToString().equals("Mad Affald")) {
            toggle.setImageResource(R.drawable.button_toggle_reverse);
        } else {
            toggle.setImageResource(R.drawable.button_toggle);
        }
        drawChart();
        FillTable();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void FillTable() {
        final Spinner dropdown = findViewById(R.id.dropdown);
        TextView environmentnum = findViewById(R.id.fwnNum6);
        TextView totalweightnum = findViewById(R.id.fwnNum5);
        TextView fwnnumnum = findViewById(R.id.fwnNum);
        TextView totalpricenum = findViewById(R.id.fwnNum4);
        TextView weightnum = findViewById(R.id.fwnNum3);
        TextView pricenum = findViewById(R.id.fwnNum2);

        float[] tb = calculateTableStats(dropdown);
        for (int i = 0; i <= tb.length - 1; i++) {
            if (Float.isNaN(tb[i])) {
                tb[i] = 0;

            }
        }

        DecimalFormat df = new DecimalFormat("#.##");

        fwnnumnum.setText(df.format(tb[3]));
        totalpricenum.setText(df.format(calculatePrice()) + " DKK");
        pricenum.setText(df.format(tb[2]) + " DKK");
        totalweightnum.setText(df.format(tb[0]) + " Kg");
        weightnum.setText(df.format(tb[1]) + " Kg");
    }

    private float calculatePrice() {


        float calculatePrice = 0;
        ArrayList<Category> cats = db.GetAllCategories();
        for (int i = 0; i <= cats.size() - 1; i++) {
            Category cat = cats.get(i);
            calculatePrice += (cat.GetAmountFW() + cat.GetAmountFS()) * cat.GetPricePerUnit();
        }


        return calculatePrice;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private float[] calculateTableStats(Spinner dd) {

        float price = 0;
        float calculateAverageWeigth = 0;
        float foodwaste = 0;
        float w = 0;
        String text = dd.getSelectedItem().toString();

        ArrayList<Category> tempcats = inputsToday(text);
        for (int i = 0; i <= tempcats.size() - 1; i++) {
            Category cat = tempcats.get(i);


            if (db.GetEnumToString().equals("Mad Spild")) {
                w += cat.GetAmountFW();
                calculateAverageWeigth += cat.GetAmountFW();
                price += cat.GetAmountFW() * cat.GetPricePerUnit();
                foodwaste += cat.GetAmountFW();

            } else if (db.GetEnumToString().equals("Mad Affald")) {
                w += cat.GetAmountFS();
                calculateAverageWeigth += cat.GetAmountFS();
                price += cat.GetAmountFS() * cat.GetPricePerUnit();
                foodwaste += cat.GetAmountFS();
            }

        }
        calculateAverageWeigth = calculateAverageWeigth / (tempcats.size());
        price = price / (tempcats.size());
        float calculateFWN = db.purchases / foodwaste;

        if (Float.isInfinite(calculateFWN)) {
            calculateFWN = 0;
        }


        float[] tablestats = new float[]{w, calculateAverageWeigth, price, calculateFWN};


        return tablestats;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Category> inputsToday(String timer) {
        LocalDate inputtime;
        switch (timer) {
            case "Dag":
                inputtime = currentSetDate;
                break;
            case "Uge":
                inputtime = currentSetWeek;
                break;
            case "Måned":
                inputtime = currentSetMonth;
                break;
            default:
                inputtime = currentSetDate;

        }

        ArrayList<DataBase.Input> inputs = db.GetInputs();
        ArrayList<Category> tempcats = new ArrayList<Category>();
        ArrayList<Category> catsdb = db.GetAllCategories();


        for (int i = 0; i <= inputs.size() - 1; i++) {
            DataBase.Input in = inputs.get(i);
            if (in.getTime().isEqual(inputtime)) {
                for (int j = 0; j <= catsdb.size() - 1; j++) {
                    Category cat = catsdb.get(j);
                    Category tempcat = new Category(context, cat.GetName(), cat.GetPricePerUnit());
                    if (!(tempcats.contains(cat))) {
                        tempcats.add(tempcat);
                    }

                    if (tempcat.GetName().equals(in.getName())) {
                        if (db.GetEnumToString().equals("Mad Affald") && in.getfoodScraps()) {
                            tempcat.AddFS(in.getamount());
                        } else if ((db.GetEnumToString().equals("Mad Spild")) && !(in.getfoodScraps())) {
                            tempcat.AddFW(in.getamount());

                        }

                    }
                }
            }
        }
        return tempcats;
    }

    private void Help() {
        new HelpDialog(context, "Food Waste Number er udregnet af total prisen af dit madspild divideret med vægten af dit madpspild. Det kan bruges som en målbar enhed der nemt kan sammenlignes på kryds og tværs af køkkener.");

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        currentSetDate = LocalDate.now();
        drawChart();
        FillTable();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void drawChart() {
        final Spinner dropdown = findViewById(R.id.dropdown);
        final PieChart pieChart = findViewById(R.id.pieChart);


        ArrayList<Category> cats = inputsToday(dropdown.getSelectedItem().toString());

        pieChart.clear();
        pieChart.setUsePercentValues(true);


        //ArrayList<DataBase.Input>  navne = new ArrayList<>();
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        PieDataSet dataSet;
        Log.d("fre", "inputs:" + db.GetInputs().size());
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i <= cats.size() - 1; i++) {
            Category cat = cats.get(i);
            if (cat.GetAmountFW() + cat.GetAmountFS() == 0) {
                continue;
            }
            yvalues.add(new PieEntry(cat.GetAmountFS() + cat.GetAmountFW(), cat.GetName(), i));
            names.add(cat.GetName() + " " + (cat.GetAmountFS() + cat.GetAmountFW()) + " Kg");
        }
        /*for (int i = 0; i <= inputs.size()-1; i++) {
            DataBase.Input in = inputs.get(i);
            if (in.getamount() == 0) {
                continue;
            }

            float amount = in.getamount();
            navne.add(in);
            for (int j = 0; j < inputs.size(); j++) {
                DataBase.Input in2 = inputs.get(i);
                Log.d("fre", "in2; " + in2);
                if (in2.getName().equals(in.getName()) && (!navne.contains(in2)) && !(in2 ==in)){
                    amount += in2.getamount();
                    navne.add(in2);
                    //  inputs.remove(in2);
                }
            }


        }*/

        if (yvalues.size() == 0) {
            //     Toast.makeText(context, "Ikke nok indtastninger til at lave diagram", Toast.LENGTH_SHORT).show();
            return;

        }

        dataSet = new PieDataSet(yvalues, db.GetEnumToString());
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        pieChart.setExtraOffsets(0, 0, 50, 0);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(18f);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setHoleRadius(58f);

        Legend l = pieChart.getLegend();
        l.getEntries();

        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        l.setYEntrySpace(10f);

        l.setWordWrapEnabled(true);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        LegendEntry[] le = new LegendEntry[names.size()];
        for (int i = 0; i <= names.size() - 1; i++) {
            LegendEntry l1 = new LegendEntry(names.get(i), Legend.LegendForm.CIRCLE, 10f, 2f, null, dataSet.getColor(i));
            le[i] = l1;
        }

        l.setCustom(le);
        l.setPosition(RIGHT_OF_CHART);
        //  l.setXOffset(-17);
        l.setYOffset(40);
        l.setTextSize(16f);
        l.setEnabled(true);

        //  dataSet.getColor();
        data.setValueTextSize(22f);

        data.setValueTextColor(Color.BLACK);
    }
}


