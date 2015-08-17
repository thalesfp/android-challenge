package me.thales.androidchallenge.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thales on 8/16/15.
 */
public class DateTools {

    private static String DateFormatIn = "yyyy-MM-dd HH:mm:ss";
    private static String DateFormatOut = "MM/dd/yyyy";

    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormatOut);
        return formatter.format(date);
    }

    public static Date stringToDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormatIn);

        Date convertedDate = new Date();

        try {
            convertedDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }
}
