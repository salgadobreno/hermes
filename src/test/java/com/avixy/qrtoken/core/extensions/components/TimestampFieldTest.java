package com.avixy.qrtoken.core.extensions.components;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TimestampFieldTest {

    @Test
    public void testValue() throws Exception {
        TimestampField timestampField = new TimestampField();

        long expectedTimestamp = 1405786800000L; // Epoch 1405786800 19/07/14 16:20

        Calendar inputCalendarDate = Calendar.getInstance();
        inputCalendarDate.set(2014, Calendar.JULY, 19); // seta so a data

        Calendar inputCalendarTime = Calendar.getInstance();
        inputCalendarTime.set(Calendar.HOUR_OF_DAY, 16);
        inputCalendarTime.set(Calendar.MINUTE, 20); // seta a hora

        timestampField.getCalendarTextField().setValue(inputCalendarDate);
        timestampField.getCalendarTimeTextField().setValue(inputCalendarTime);

        assertEquals(new Date(expectedTimestamp), timestampField.getValue());

    }
}