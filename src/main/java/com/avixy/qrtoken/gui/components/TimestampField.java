package com.avixy.qrtoken.gui.components;

import javafx.scene.control.DatePicker;
import javafx.scene.layout.FlowPane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

/**
 * Encapsulates input of Date + Hour/Minutes
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 16/09/2014
 */
public class TimestampField extends FlowPane {
    //Using FlowPane instead of HBox because alignment gets wrong when using with MigPane
    private DatePicker datePicker = new FormattedDatePicker();

    static private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    private RestrictiveTextField timeTextField = new RestrictiveTextField(5);

    public TimestampField() {
        setHgap(5);
        timeTextField.setRestrict("[0-9:]");

        datePicker.setMaxWidth(100);
        timeTextField.setMaxWidth(44);

        datePicker.setValue(LocalDate.now());
        timeTextField.setText(simpleDateFormat.format(new Date()));

        this.getChildren().addAll(datePicker, timeTextField);
    }

    public Date getValue(){
        /* pega hora e minutos do field de tempo */
        Calendar calendarTime = null;
        try {
            calendarTime = Calendar.getInstance();
            calendarTime.setTime(simpleDateFormat.parse(timeTextField.getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int hour = calendarTime.get(Calendar.HOUR_OF_DAY);
        int mins = calendarTime.get(Calendar.MINUTE);

        LocalDateTime localDate = datePicker.getValue().atTime(hour, mins, 0);

        return Date.from(localDate.toInstant(ZoneOffset.UTC));
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public RestrictiveTextField getTimeTextField() {
        return timeTextField;
    }
}
