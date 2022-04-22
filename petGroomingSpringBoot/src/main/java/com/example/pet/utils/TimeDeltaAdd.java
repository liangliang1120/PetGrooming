package com.example.pet.utils;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
// import java.text.SimpleDateFormat;

public class TimeDeltaAdd {

    public Date AddHour(Date dateTime) {
        Calendar ca = Calendar.getInstance(Locale.US);
        ca.setTime(dateTime);
        ca.add(Calendar.HOUR, 7);
        Date dateTimeNew = ca.getTime();
        return dateTimeNew;
        // return dateTime;
    }

    public Date ReduceHour(Date dateTime) {
        Calendar ca = Calendar.getInstance(Locale.US);
        ca.setTime(dateTime);
        ca.add(Calendar.HOUR, -7);
        Date dateTimeNew = ca.getTime();
        return dateTimeNew;
        // return dateTime;
    }
}
