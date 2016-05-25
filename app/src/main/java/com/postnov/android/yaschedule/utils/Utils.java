package com.postnov.android.yaschedule.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by platon on 24.05.2016.
 */
public class Utils
{
    public static void showToast(Context context, String message, int length)
    {
        Toast.makeText(context, message, length).show();
    }

    public static void showToast(Context context, String message)
    {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static String formatTime(String date)
    {
        int startIndex = date.lastIndexOf(" ");
        int endIndex = date.lastIndexOf(":");
        return date.substring(startIndex, endIndex).trim();
    }

    public static String formatDateReverse(int day, int month, int year)
    {
        StringBuilder date = new StringBuilder();
        date.append(year);
        date.append("-");
        date.append(month);
        date.append("-");
        date.append(day);

        return date.toString();
    }

    public static String formatDateNorm(int day, int month, int year)
    {
        StringBuilder date = new StringBuilder();
        date.append(day);
        date.append(".");
        date.append(month);
        date.append(".");
        date.append(year);

        return date.toString();
    }

    public static int getYear()
    {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        return Integer.parseInt(sdf.format(c.getTime()));
    }

    public static int getMonthOfYear()
    {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("M", Locale.getDefault());
        return Integer.parseInt(sdf.format(c.getTime()));
    }

    public static int getDayOfYear()
    {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault());
        return Integer.parseInt(sdf.format(c.getTime()));
    }

    public static long getMaxDayInYear()
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.YEAR, getYear());

        return cal.getTimeInMillis();
    }

    public static long getMinDayInYear()
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, getYear());

        return cal.getTimeInMillis();
    }
}
