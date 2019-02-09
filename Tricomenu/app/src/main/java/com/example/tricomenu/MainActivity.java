package com.example.tricomenu;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
//import com.example.tricomenu.MealGetter;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public int dhSelected = 0; // hcDC = 0, Erd = 1, ND = 2, Sharp = 3
    public int daySelected = 0; // today = 0, tomorrow =1,
    public MealGetter mealGetter = new MealGetter();
    public String[] menus = new String[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//
//        Spinner date_bar = (Spinner) findViewById(R.id.spinner_day);
//        date_bar.setOnItemSelectedListener(this);
//
//        List<String> categories = new ArrayList<String>();
//        categories.add("Today");
//        categories.add("Tomorrow");
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        date_bar.setAdapter(dataAdapter);

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    ArrayList<String> dcMealToday = mealGetter.getMealHC(0);
                    menus[0]= parseMeal(dcMealToday);
                    ArrayList<String> dcMealTomo = mealGetter.getMealHC(1);
                    menus[1]= parseMeal(dcMealTomo);

                    ArrayList<String> erdMealToday = mealGetter.getMealErd(0);
                    menus[2]= parseMeal(erdMealToday);
                    ArrayList<String> erdMealTomo = mealGetter.getMealErd(1);
                    menus[3]= parseMeal(erdMealTomo);

                    ArrayList<String> ndMealToday = mealGetter.getMealND(0);
                    menus[4]= parseMeal(ndMealToday);
                    ArrayList<String> ndMealTomo = mealGetter.getMealND(1);
                    menus[5]= parseMeal(ndMealTomo);

                    ArrayList<String> shrpMealToday = mealGetter.getMealSwat(0);
                    menus[6]= parseMeal(shrpMealToday);
                    ArrayList<String> shrpMealTomo = mealGetter.getMealSwat(1);
                    menus[7]= parseMeal(shrpMealTomo);

                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();



        final TextView menu = (TextView) findViewById(R.id.menu_window);
        menu.setMovementMethod(new ScrollingMovementMethod());
        menu.setMovementMethod(LinkMovementMethod.getInstance());



        Spinner date_bar = (Spinner) findViewById(R.id.spinner_day);
        date_bar.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Today");
        categories.add("Tomorrow");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_bar.setAdapter(dataAdapter);
//
//        final Button dc = findViewById(R.id.button_dc);
//        final Button erd = findViewById(R.id.button_erd);
//        final Button nd = findViewById(R.id.button_nd);
//        final Button shrp = findViewById(R.id.button_sharp);
        String dc_button = "HC DC";
        final Button dc = findViewById(R.id.button_dc);
        SpannableString spanString_dc;
        spanString_dc = new SpannableString(dc_button);
        spanString_dc.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString_dc.length(), 0);
        dc.setText(spanString_dc);

        String erd_button = "BMC ERDMAN";
        final Button erd = findViewById(R.id.button_erd);
        SpannableString spanString_erd = new SpannableString(erd_button);
        spanString_erd.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString_erd.length(), 0);
        erd.setText(spanString_erd);

        String nd_button = "BMC NEW DORM";
        final Button nd = findViewById(R.id.button_nd);
        SpannableString spanString_nd = new SpannableString(nd_button);
        spanString_nd.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString_nd.length(), 0);
        nd.setText(spanString_nd);

        String shrp_button = "SWAT SHARPLES";
        final Button shrp = findViewById(R.id.button_sharp);
        SpannableString spanString_shrp = new SpannableString(erd_button);
        spanString_shrp.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString_shrp.length(), 0);
        shrp.setText(spanString_shrp);

        dc.setBackgroundColor(Color.rgb(255, 255, 255));
        erd.setBackgroundColor(Color.rgb(255, 255, 255));
        nd.setBackgroundColor(Color.rgb(255, 255, 255));
        shrp.setBackgroundColor(Color.rgb(255, 255, 255));


        erd.setTextColor(Color.rgb(255, 153, 0));
        nd.setTextColor(Color.rgb(255, 153, 0));
        shrp.setTextColor(Color.rgb(255, 153, 0));
        dc.setTextColor(Color.rgb(255, 153, 0));
        //rgb(255, 64, 0)


        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhSelected = 0;
                if(daySelected == 0) {
                    menu.setText(Html.fromHtml(menus[0]));
                    dc.setBackgroundColor(Color.rgb(41, 64, 151));//Color(Color.rgb(47,144,123));
                }
                else{
                    menu.setText(Html.fromHtml(menus[1]));
                    dc.setBackgroundColor(Color.rgb(41, 64, 151));
                }
                erd.setBackgroundColor(Color.rgb(255, 255, 255));
                nd.setBackgroundColor(Color.rgb(255, 255, 255));
                shrp.setBackgroundColor(Color.rgb(255, 255, 255));
            }

        });

//        final Button erd = findViewById(R.id.button_erd);

        erd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhSelected = 1;
                if(daySelected == 0) {
                    menu.setText(Html.fromHtml(menus[2])); //erd today
                    erd.setBackgroundColor(Color.rgb(41, 64, 151));
                }
                else{
                    menu.setText(Html.fromHtml(menus[3]));//erd tomo
                    erd.setBackgroundColor(Color.rgb(41, 64, 151));
                }
                dc.setBackgroundColor(Color.rgb(255, 255, 255));
                nd.setBackgroundColor(Color.rgb(255, 255, 255));
                shrp.setBackgroundColor(Color.rgb(255, 255, 255));
            }

        });

//        final Button nd = findViewById(R.id.button_nd);

        nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhSelected = 2;
                if(daySelected == 0) {
                    menu.setText(Html.fromHtml(menus[4]));//nd today
                    nd.setBackgroundColor(Color.rgb(41, 64, 151));
                }
                else{
                    menu.setText(Html.fromHtml(menus[5]));//nd tomo
                    nd.setBackgroundColor(Color.rgb(41, 64, 151));
                }
                dc.setBackgroundColor(Color.rgb(255, 255, 255));
                erd.setBackgroundColor(Color.rgb(255, 255, 255));
                shrp.setBackgroundColor(Color.rgb(255, 255, 255));

            }

        });

//        final Button shrp = findViewById(R.id.button_sharp);

        shrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhSelected = 3;
                if(daySelected == 0) {
                    menu.setText(Html.fromHtml(menus[6]));//s today
                    shrp.setBackgroundColor(Color.rgb(41, 64, 151));
                }
                else{
                    menu.setText(Html.fromHtml(menus[7]));//s tomo
                    shrp.setBackgroundColor(Color.rgb(41, 64, 151));
                }
                dc.setBackgroundColor(Color.rgb(255, 255, 255));
                erd.setBackgroundColor(Color.rgb(255, 255, 255));
                nd.setBackgroundColor(Color.rgb(255, 255, 255));
            }

        });
    }

    public String parseMeal (ArrayList<String> input){
        String re = "";
        for(int i = 0; i< input.size(); i++)
        {
            if ((input.get(i).contains("Breakfast") && input.get(i).length() < 10)
                    || input.get(i).contains("Continental Breakfast")
                    || input.get(i).contains("Brunch") || input.get(i).contains("Lunch")
                    || (input.get(i).contains("Dinner")) && input.get(i).length() < 7) {
                re = re + "<br>" + input.get(i).toUpperCase() + "<br>";
            } else {
                re = re + "     " + "<a href='https://www.google.com/search?q="
                + input.get(i) + "&rlz=1C5CHFA_enUS788US788&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjD5rL_xK_gAhUCwVkKHdQfBbsQ_AUIDigB&biw=1200&bih=692'>"
                        + input.get(i) + "</a>" + "<br>";
            }
        }
        return re;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        TextView menu = (TextView) findViewById(R.id.menu_window);

        if (item.equals("Tomorrow")) {
            daySelected = 1;
            if (dhSelected == 0) {
                menu.setText(Html.fromHtml(menus[1]));
            } else if (dhSelected == 1) {
                menu.setText(Html.fromHtml(menus[3]));
            } else if (dhSelected == 2){
                menu.setText(Html.fromHtml(menus[5]));
            }
            else {
                menu.setText(Html.fromHtml(menus[7]));
            }
        }
        else if (item.equals("Today")) {
            daySelected = 0;

            if (dhSelected == 0) {
                menu.setText(menus[0]);
            } else if (dhSelected == 1) {
                menu.setText(Html.fromHtml(menus[2]));
            } else if (dhSelected == 2){
                menu.setText(Html.fromHtml(menus[4]));
            }
            else {
                menu.setText(Html.fromHtml(menus[6]));
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
