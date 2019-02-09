package com.example.tricomenu;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;

public class MealGetter {
//    public static void main(String args[]) {
//        ArrayList<String> result = getMealND(1);
//        for (String x : result) System.out.println(x);
//    }

    public MealGetter(){

    }

    public static boolean isWkend(String date) {
        String[] arr = date.split(",");
        return arr[0].equals("Saturday") || arr[0].equals("Sunday");
    }

    public static String getDate(int isTomorrow) {
        // return format example: Wednesday, Feb. 6

        Date tdate = new Date(System.currentTimeMillis());
        int month = tdate.getMonth();
        int date = tdate.getDate();
        int day = tdate.getDay();

        if (isTomorrow == 1) {
            date++;
            day = (day + 1) % 7;
        }

        String d = "";
        String m = "";
        switch(month) {
            case 0 :
                m = "Jan";
                break;
            case 1 :
                m = "Feb";
                break;
            case 2 :
                m = "Mar";
                break;
            case 3 :
                m = "Apr";
                break;
            case 4 :
                m = "May";
                break;
            case 5 :
                m = "Jun";
                break;
            case 6 :
                m = "July";
                break;
            case 7 :
                m = "Aug";
                break;
            case 8 :
                m = "Sep";
                break;
            case 9 :
                m = "Oct";
                break;
            case 10 :
                m = "Nov";
                break;
            case 11 :
                m = "Dec";
                break;
        }

        switch(day) {
            case 0 :
                d = "Sunday";
                break;
            case 1 :
                d = "Monday";
                break;
            case 2 :
                d = "Tuesday";
                break;
            case 3 :
                d = "Wednesday";
                break;
            case 4 :
                d = "Thursday";
                break;
            case 5 :
                d = "Friday";
                break;
            case 6 :
                d = "Saturday";
                break;
        }

        return d + ", " + m + ". " + date;
    }

    public static ArrayList<String> getMealErd(int isTomorrow) {
        try {
            // get the date
            String date = getDate(isTomorrow);

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

    public static ArrayList<String> getMealND(int isTomorrow) {
        try {
            // get the date
            String date = getDate(isTomorrow);

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
                if (day == 0) {
                    start = 0;
                    end = 2;
                } else {
                    start = 0;
                    end = 3;
                }
            } else {
                if (day == 0) {
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

    public static ArrayList<String> getMealSwat(int isTomorrow) {
        try {
            Date tdate = new Date(System.currentTimeMillis());
            // format month and date data
            int month = tdate.getMonth();
            month++;
            String mm;
            if (month != 10 && month != 11 && month != 12) {
                mm = "0" + month;
            } else {
                mm = "" + month;
            }

            int date = tdate.getDate();
            if (isTomorrow == 1) date++;
            String dd;
            if (date == 1 || date == 2 || date == 3 || date == 4 || date == 5 || date == 6
                    || date == 7 || date == 8 || date == 9) {
                dd = "0" + date;
            } else {
                dd = "" + date;
            }

            // get the html file
            String url = "https://dash.swarthmore.edu/calendar/1768/2019-" + mm + "-" + dd;
            Document doc = Jsoup.connect(url).get();
            // modify the html file to make future analysis easier
            String temp = doc.html().replace("<h4>", "<li>");
            // parse it back to html file
            doc = Jsoup.parse(temp);

            Elements item = doc.select("div.event-body");


            ArrayList<String> meal = new ArrayList<>();


            for (int i = 1; i < 3; i++) {
                if (i == 1) meal.add("Lunch");
                else meal.add("Dinner");
                for (Element x : item.get(i).select("li")) {
                    meal.add(x.text());
                }
            }
            return meal;
        } catch(Exception e) {
            Log.i("mytag", e.toString());
            return null;
        }
    }
}
