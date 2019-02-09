package com.example.rayrw.myapplication;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Date;

public class MealGetter {

    public static void main(String args[]) {

        ArrayList<String> result = getMealErd("Sunday, Feb. 10");
        for (String x : result) System.out.println(x);

//        ArrayList<String> result = getMealHC(0);
//        for (String x : result) System.out.println(x);

    }

    public static boolean isWkend(String date) {
        String[] arr = date.split(",");
        return arr[0].equals("Saturday") || arr[0].equals("Sunday");
    }

    public static ArrayList<String> getMealErd(String date) {
        try {
            // get the html file
            Document doc = Jsoup.connect("https://www.brynmawr.edu/dining/menus-and-hours/erdman-dining-hall-menu").get();
            // modify the html file to make future analysis easier
            String temp = doc.html().replace("<br>", "@");
            // parse it back to html file
            doc = Jsoup.parse(temp);

            Elements dataOfWeek = doc.select("div.field-item.even");
            Elements item = dataOfWeek.select("strong");

            ArrayList<String> meal = new ArrayList<>();

            for (int i = 0; i < item.size(); i++) {
                // if reach the desired date
                if (item.get(i).text().equals(date)) {
                    // determine the number of meals that day
                    int num;
                    if (isWkend(date)) {
                        num = 5;
                    } else {
                        num = 7;
                    }
                    // process each line representing meals
                    for (int j = 1; j < num; j++) {
                        String[] food = item.get(i + j).text().split("@");
                        for (String x : food) {
                            // remove leading whitespaces
                            x = x.trim();
                            meal.add(x);
                        }
                    }
                }
            }
            return meal;
        } catch(Exception e) {
            Log.i("mytag", e.toString());
            return null;
        }
    }

    public static ArrayList<String> getMealND(String date) {
        try {
            // get the html file
            Document doc = Jsoup.connect("https://www.brynmawr.edu/dining/menus-and-hours/new-dorm-dining-hall-menu").get();
            // modify the html file to make future analysis easier
            String temp = doc.html().replace("<br>", "@");
            // parse it back to html file
            doc = Jsoup.parse(temp);

            Elements dataOfWeek = doc.select("div.field-item.even");
            Elements item = dataOfWeek.select("strong");

            ArrayList<String> meal = new ArrayList<>();

            for (int i = 0; i < item.size(); i++) {
                // if reach the desired date
                if (item.get(i).text().equals(date)) {
                    // determine the number of meals that day
                    int num = 5; // since nd only offers 2 meals each day
                    // process each line representing meals
                    for (int j = 1; j < num; j++) {
                        String[] food = item.get(i + j).text().split("@");
                        for (String x : food) {
                            // remove leading whitespaces
                            x = x.trim();
                            meal.add(x);
                        }
                    }
                }
            }
            return meal;
        } catch(Exception e) {
            Log.i("mytag", e.toString());
            return null;
        }
    }

    public static ArrayList<String> getMealHC(int isTomorrow) {
        try {
            // get the html file
            Document doc = Jsoup.connect("https://www.haverford.edu/dining-services/dining-center").get();
            // modify the html file to make future analysis easier
            String temp = doc.html().replace("<br>", "@");
            temp = temp.replace("<h4>", "@");
            temp = temp.replace("</h4>", "@");
            temp = temp.replace("Add to calendar", "");
            // parse it back to html file
            doc = Jsoup.parse(temp);

            Elements item = doc.select("div.meal-container");

            ArrayList<String> meal = new ArrayList<>();

            // get the current day
            Date date = new Date(System.currentTimeMillis());
            int day = date.getDay();
            int start, end;
            // determine which meals to return in the dataset
            // `isTomorrow` = 0 if want to return data for today, 1 if tomorrow.
            if (isTomorrow == 0) {
                if (day == 7) {
                    start = 0;
                    end = 2;
                } else {
                    start = 0;
                    end = 3;
                }
            } else {
                if (day == 7) {
                    start = 2;
                    end = 5;
                } else if (day == 6) {
                    start = 3;
                    end = 5;
                } else {
                    start = 3;
                    end = 6;
                }
            }

            for (int i = start; i < end; i++) {
                String[] food = item.get(i).text().split("@");
                for (String x : food) {
                    if (!x.equals("")) {
                        // remove leading whitespaces
                        x = x.trim();
                        meal.add(x);
                    }
                }
            }
            return meal;
        } catch(Exception e) {
            Log.i("mytag", e.toString());
            return null;
        }
    }
}
