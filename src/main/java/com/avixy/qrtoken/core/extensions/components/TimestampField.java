package com.avixy.qrtoken.core.extensions.components;

import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.CalendarTextField;
import jfxtras.labs.scene.control.CalendarTimeTextField;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
        Calendar calendarDate = calendarTextField.getValue();

        /* pega hora e minutos do field de tempo */
        Calendar calendarTime = calendarTimeTextField.getValue();
        int hour = calendarTime.get(Calendar.HOUR_OF_DAY);
        int mins = calendarTime.get(Calendar.MINUTE);

        calendarDate.set(Calendar.HOUR_OF_DAY, hour);
        calendarDate.set(Calendar.MINUTE, mins);
        calendarDate.set(Calendar.SECOND, 0);
        calendarDate.set(Calendar.MILLISECOND, 0);

        /* Setar o timezone depois de setar a hora pra não dar treta na conversão.. */
        calendarDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        return calendarDate.getTime();
    }

    public CalendarTextField getCalendarTextField() {
        return calendarTextField;
    }

    public CalendarTimeTextField getCalendarTimeTextField() {
        return calendarTimeTextField;
    }
}
