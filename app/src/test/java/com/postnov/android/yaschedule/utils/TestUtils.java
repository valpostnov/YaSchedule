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
    public void testToShortDate() throws Exception
    {
        int month = 7;
        int day = 9;

        assertEquals("9 Августа", Utils.toShortDate(day, month));
    }

    @Test
    public void testGetOnlyDate() throws Exception
    {
        String date = "2016-5-3 12:12:00";

        assertEquals("2016-5-3", Utils.getOnlyDate(date));
    }

    @Test
    public void testDivideString() throws Exception
    {
        String title = "Москва (Ярославский вокзал)";

        assertEquals("Москва\n(Ярославский вокзал)", Utils.splitTitle(title));
    }
}
