package com.postnov.android.yaschedule.utils;

import android.content.Context;
import android.widget.Toast;

import com.postnov.android.yaschedule.R;

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
    public static String getTime(String date)
    {
        int startIndex = date.lastIndexOf(" ");
        int endIndex = date.lastIndexOf(":");
        return date.substring(startIndex, endIndex).trim();
    }

    /**
     *
     * @param date example yyyy-mm-dd HH:mm:ss
     * @return 'dd M', example '25 may'
     */
    public static String toShortDate(String date)
    {
        int month = Integer.valueOf(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
        int day = Integer.valueOf(date.substring(date.lastIndexOf("-") + 1, date.lastIndexOf(" ")));

        return toShortDate(day, month - 1);
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
     * @param day dd, example 01
     * @param month mm, example 06
     * @return d MMMM, example 1 June
     */
    public static String toShortDate(int day, int month)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM", Locale.getDefault());

        return sdf.format(c.getTime());
    }

    /**
     *
     * @param seconds, like this 120s
     * @return minutes, like this 2 min.
     */
    public static String convertSecToMinutes(String seconds)
    {
        if (seconds != null)
        {
            int sec = Integer.valueOf(seconds);
            int min = sec / 60;

            return min + " мин.";
        }

        return "";
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

    public static int getTransportImage(String tt)
    {
        final int TRAIN_DRAWABLE =  R.drawable.ic_railway_green;
        final int PLANE_DRAWABLE = R.drawable.ic_flight_green;
        final int BUS_DRAWABLE = R.drawable.ic_bus_green;
        final int SUBURBAN_DRAWABLE = R.drawable.ic_suburban_green;

        switch (tt)
        {
            case TransportTypes.TRAIN:
                return TRAIN_DRAWABLE;

            case TransportTypes.BUS:
                return BUS_DRAWABLE;

            case TransportTypes.PLANE:
                return PLANE_DRAWABLE;

            case TransportTypes.SUBURBAN:
                return SUBURBAN_DRAWABLE;

            default:
                return 0;
        }
    }
}
