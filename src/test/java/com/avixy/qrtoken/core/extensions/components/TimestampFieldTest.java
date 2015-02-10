package com.avixy.qrtoken.core.extensions.components;

import com.avixy.qrtoken.JavaFXThreadingRule;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TimestampFieldTest {
    @Rule
    public JavaFXThreadingRule javaFXThreadingRule = new JavaFXThreadingRule();

    @Test
    public void testValue() throws Exception {
        TimestampField timestampField = new TimestampField();

        long expectedTimestamp = 1405786800000L; // Epoch 1405786800 19/07/14 16:20

        Calendar inputCalendarDate = Calendar.getInstance();
        inputCalendarDate.set(2014, Calendar.JULY, 19); // seta so a data

        Calendar inputCalendarTime = Calendar.getInstance();
        inputCalendarTime.set(Calendar.HOUR_OF_DAY, 16);
        inputCalendarTime.set(Calendar.MINUTE, 20); // seta a hora

        timestampField.getDatePicker().setValue(LocalDate.of(2014, Month.JULY, 19));
        timestampField.getTimeTextField().setText("16:20");

        assertEquals(new Date(expectedTimestamp), timestampField.getValue());
    }
}