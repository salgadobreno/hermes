package com.avixy.qrtoken.core.components;

import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.CalendarTextField;
import jfxtras.labs.scene.control.CalendarTimeTextField;

import java.util.Calendar;
import java.util.Date;

/**
 * Componente que encapsula setagem de data + hora
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 16/09/2014
 */
public class TimestampField extends VBox {
    private Calendar calendar = Calendar.getInstance();
    private CalendarTextField calendarTextField = new CalendarTextField();
    private CalendarTimeTextField calendarTimeTextField = new CalendarTimeTextField();

    public TimestampField() {
        calendarTextField.setValue(calendar);
        calendarTimeTextField.setValue(calendar);
        this.getChildren().addAll(calendarTextField, calendarTimeTextField);
    }

    public Date getValue(){
        Calendar calendar = calendarTextField.getValue();
        int hour = calendarTimeTextField.getValue().get(Calendar.HOUR_OF_DAY);
        int mins = calendarTimeTextField.getValue().get(Calendar.MINUTE);
        int secs = calendarTimeTextField.getValue().get(Calendar.SECOND);
        int mils = calendarTimeTextField.getValue().get(Calendar.MILLISECOND);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, mins);
        calendar.set(Calendar.SECOND, secs);
        calendar.set(Calendar.MILLISECOND, mils);

        return calendar.getTime();
    }
}
