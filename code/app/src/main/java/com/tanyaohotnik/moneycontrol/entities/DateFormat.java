package com.tanyaohotnik.moneycontrol.entities;

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
    public static String getNumberDateFormat(Date date){
//        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
//        Date date = new Date();
        String dateStr = new SimpleDateFormat("dd-MM-yyyy").format(date);
        String day,month,year;
        return dateStr;
    }
    public static String getPrevDate(){
        return null;
    }
    public static String getNextDate(){
        return null;
    }
    public static String getRussianMonthYear(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String monthStr;
        switch (month){
            case 1: monthStr="Январь";
            case 2: monthStr="Февраль";
            case 3: monthStr="Март";
            case 4: monthStr="Апрель";
            case 5: monthStr="Май";
            case 6: monthStr="Июнь";
            case 7: monthStr="Июль";
            case 8: monthStr="Август";
            case 9: monthStr="Сентябрь";
            case 10: monthStr="Октябрь";
            case 11: monthStr="Ноябрь";
            case 12: monthStr="Декабрь";
            default: monthStr="error";
        }
        return monthStr + " " + Integer.toString(year);
    }
}
