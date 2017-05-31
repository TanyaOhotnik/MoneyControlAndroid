package com.tanyaohotnik.moneycontrol.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */

public class DateFormat {
    public DateFormat() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getNumberDateFormat(Date date) {
//        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
//        Date date = new Date();
        if (date == null) return DateFormat.getNumberDateFormat(new Date());
        String dateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
        String day, month, year;
        return dateStr;
    }

    public static int getYear() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static String getPrevDate() {
        return null;
    }

    public static String getNextDate() {
        return null;
    }

    public static String getRussianMonthYear() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return getMonth(month) + " " + Integer.toString(year);
    }

    public static String getCurrentRussianMonthDay() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(day) + " " + getMonth(month);
    }

    private static String getMonth(int month) {
        String monthStr;
        switch (month + 1) {
            case 1:
                monthStr = "Январь";
                break;
            case 2:
                monthStr = "Февраль";
                break;
            case 3:
                monthStr = "Март";
                break;
            case 4:
                monthStr = "Апрель";
                break;
            case 5:
                monthStr = "Май";
                break;
            case 6:
                monthStr = "Июнь";
                break;
            case 7:
                monthStr = "Июль";
                break;
            case 8:
                monthStr = "Август";
                break;
            case 9:
                monthStr = "Сентябрь";
                break;
            case 10:
                monthStr = "Октябрь";
                break;
            case 11:
                monthStr = "Ноябрь";
                break;
            case 12:
                monthStr = "Декабрь";
                break;
            default:
                monthStr = "error";
        }
        return monthStr;
    }

    public static String makeStringFromDate(Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }

    public static Date makeDateFromString(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {

        }
        return date;
    }
}
