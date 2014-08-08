package com.avixy.qrtoken.gui.services;

import com.avixy.qrtoken.core.extensions.MaskTextField;
import com.avixy.qrtoken.negocio.servico.HmacRtcService;
import com.avixy.qrtoken.negocio.servico.Service;
import extfx.scene.control.DatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by I7 on 07/08/2014.
 */
public class RtcServiceComponent implements ServiceComponent {

    private static final String FXML_PATH = "/fxml/rtcservice.fxml";
    private final HmacRtcService service = new HmacRtcService();

    @FXML
    DatePicker dataDatePicker = new DatePicker();
    @FXML
    TextField horarioField; //TODO: por uma mascara aqui

    @Override
    public String getServiceName() {
        return service.getServiceName();
    }

    @Override
    public Service getService(){
        // Setar a data
        Calendar data = Calendar.getInstance();
        Date carai = dataDatePicker.getValue();
        data.setTime(dataDatePicker.getValue());
        // Setar a hora
        String hora = horarioField.getText();
        String[] horaMinuto = hora.split(":");
        data.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaMinuto[0]));
        data.set(Calendar.MINUTE, Integer.parseInt(horaMinuto[1]));

        service.setDate(data.getTime());

        return service;
    }

    public String getFxmlPath() {
        return FXML_PATH;
    }

}
