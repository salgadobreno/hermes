package com.avixy.qrtoken.core.extensions.components;

import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.FormatStringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Componente que encapsula setagem de data + hora
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 16/09/2014
 */
public class TimestampField extends HBox {
    private DatePicker datePicker = new FormattedDatePicker();

    static private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    private RestrictiveTextField timeTextField = new RestrictiveTextField(5);

    public TimestampField() {
        setSpacing(5);
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
