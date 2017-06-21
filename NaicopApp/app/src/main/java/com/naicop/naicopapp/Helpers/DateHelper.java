package com.naicop.naicopapp.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pazos on 20-Jun-17.
 */
public class DateHelper {
    public static String getTextDateTime(String dateTimeString) {
        try {
            if(dateTimeString.contains("T"))
                dateTimeString = dateTimeString.replace("T"," ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh");
            Date date = sdf.parse(dateTimeString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String dateString = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + " a las: " + calendar.get(Calendar.HOUR_OF_DAY)+" hs";
            return dateString;
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTimeString;
        }

    }
}
