package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.HmacRtcServico;
import com.avixy.qrtoken.negocio.servico.Servico;
import com.avixy.qrtoken.negocio.servico.ServicoCategory;
//import extfx.scene.control.DatePicker;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by I7 on 07/08/2014.
 */
@ServicoCategory(category = ServicoComponent.Category.RTC)
public class RtcServicoComponent extends ServicoComponent {

    private static final String FXML_PATH = "/fxml/rtcservice.fxml";
    private final HmacRtcServico service = new HmacRtcServico();

//    @FXML
//    DatePicker dataDatePicker = new DatePicker();
    @FXML
    TextField horarioField; //TODO: por uma mascara aqui

    @Override
    public String getServiceName() {
        return service.getServiceName();
    }

    @Override
    public Servico getService(){
        // Setar a data
        Calendar data = Calendar.getInstance();
//        Date carai = dataDatePicker.getValue();
//        data.setTime(dataDatePicker.getValue());
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
