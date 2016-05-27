package com.postnov.android.yaschedule.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     *
     * @param date yyyy-mm-dd HH:mm:ss
     * @return HH:mm
     */
    public static String cutDate(String date)
    {
        int startIndex = date.lastIndexOf(" ");
        int endIndex = date.lastIndexOf(":");
        return date.substring(startIndex, endIndex).trim();
    }

    /**
     *
     * @param date example yyyy-mm-dd HH:mm:ss
     * @return 'day month', example '25 may'
     */
    public static String toShortDate(String date)
    {
        int month = Integer.valueOf(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
        int day = Integer.valueOf(date.substring(date.lastIndexOf("-") + 1, date.lastIndexOf(" ")));

        return toShortDate(day, month - 1, getYear());
    }

    /**
     *
     * @param day dd
     * @param month mm
     * @param year yyyy
     * @return yyyy-mm-dd
     */
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

    /**
     *
     * @param day dd
     * @param month mm
     * @param year yyyy
     * @return 'day month', example '25 may'
     */
    public static String toShortDate(int day, int month, int year)
    {
        return day + " " + getFullNameMonth(month);
    }

    /**
     *
     * @param month mm
     * @return full name month, example 'may'
     */
    public static String getFullNameMonth(int month)
    {
        switch (month)
        {
            case Const.JAN:
                return "января";
            case Const.FEB:
                return "февраля";
            case Const.MAR:
                return "марта";
            case Const.APR:
                return "апреля";
            case Const.MAY:
                return "мая";
            case Const.JUN:
                return "июня";
            case Const.JUL:
                return "июля";
            case Const.AUG:
                return "августа";
            case Const.SEP:
                return "сентября";
            case Const.OCT:
                return "октября";
            case Const.NOV:
                return "ноября";
            case Const.DEC:
                return "декабря";
            default:
                return null;
        }
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

    public static int getDayOfMonth()
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
