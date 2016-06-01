package com.postnov.android.yaschedule.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Created by platon on 25.05.2016.
 */
public class TestUtils
{
    @Test
    public void testFormatDate() throws Exception
    {
        String formattedDate = "25 мая";
        String date = "2016-05-25 12:00:00";

        assertEquals(formattedDate, Utils.toShortDate(date));
    }

    @Test
    public void testGetStringMonth() throws Exception
    {
        int month = 7;

        assertEquals("Авг.", Utils.getStringMonth(month));
    }
}
