package com.postnov.android.yaschedule.utils;

import android.content.Context;
import android.widget.Toast;

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
}
