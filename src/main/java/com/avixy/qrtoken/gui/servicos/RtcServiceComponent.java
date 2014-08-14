package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.HmacRtcService;
import com.avixy.qrtoken.negocio.servico.Service;
import com.avixy.qrtoken.negocio.servico.ServiceCategory;
import extfx.scene.control.DatePicker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created on 07/08/2014
 * @author I7
 */
@ServiceCategory(category = ServiceComponent.Category.RTC)
public class RtcServiceComponent extends ServiceComponent {

    private static final String FXML_PATH = "/fxml/rtcservice.fxml";
    private final HmacRtcService service = new HmacRtcService();

    private Node node;

    @FXML private DatePicker dataDatePicker;
    @FXML private TextField horarioField; //TODO: por uma mascara aqui

    public RtcServiceComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_PATH));
        fxmlLoader.setController(this);
        try {
            node = (Node) fxmlLoader.load();
        } catch (IOException e) {
            getLogger().error("Missing FXML: ", e);
        }
    }

    @Override
    public Service getService(){
        // geta a data
        Calendar data = Calendar.getInstance();
        // geta a hora
        String hora = horarioField.getText();
        String[] horaMinuto = hora.split(":");
        data.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaMinuto[0]));
        data.set(Calendar.MINUTE, Integer.parseInt(horaMinuto[1]));

        service.setDate(data.getTime());

        return service;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public String getServiceName() {
        return service.getServiceName();
    }

}
