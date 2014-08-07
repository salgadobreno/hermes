package com.avixy.qrtoken.gui.services;

import com.avixy.qrtoken.negocio.servico.RtcService;
import com.avixy.qrtoken.negocio.servico.Service;
import extfx.scene.control.DatePicker;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Date;

/**
 * Created by I7 on 07/08/2014.
 */
//TODO: service component
public class RtcServiceController {

    private static final String FXML_PATH = "/fxml/rtcservice.fxml";

    private final RtcService service = new RtcService();

    @FXML
    DatePicker dataDatePicker;

    @FXML
    TextField horarioField;

    public void initialize(){
    }

    public Service getService(){
        System.out.println("getfilledservice");
        return service;
    }

    public static String getFxmlPath() {
        return FXML_PATH;
    }

}
