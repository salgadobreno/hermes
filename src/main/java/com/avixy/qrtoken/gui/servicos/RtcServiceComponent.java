package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.HmacRtcService;
import com.avixy.qrtoken.negocio.servico.Service;
import com.avixy.qrtoken.negocio.servico.ServiceCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfxtras.labs.scene.control.CalendarTextField;
import jfxtras.labs.scene.control.CalendarTimeTextField;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.spi.TimeZoneNameProvider;

/**
 * Created on 07/08/2014
 * @author I7
 */
@ServiceCategory(category = ServiceComponent.Category.RTC)
public class RtcServiceComponent extends ServiceComponent {

    private static final String FXML_PATH = "/fxml/rtcservice.fxml";
    private final HmacRtcService service = new HmacRtcService();

    private Node node;

    @FXML private CalendarTextField dataDatePicker;
    @FXML private ComboBox<String> fusoBox;
    @FXML private CalendarTimeTextField horarioField;
    @FXML private TextField keyField;

    public RtcServiceComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_PATH));
        fxmlLoader.setController(this);

        try {
            node = (Node) fxmlLoader.load();
        } catch (IOException e) {
            getLogger().error("FXML Error: ", e);
        }

        ObservableList<String> observableList = FXCollections.observableList(Arrays.asList(TimeZone.getAvailableIDs()));
        fusoBox.setItems(observableList);
        fusoBox.getSelectionModel().select(TimeZone.getDefault().getID());
        horarioField.setValue(Calendar.getInstance());
        dataDatePicker.setValue(Calendar.getInstance());
    }

    @Override
    public Service getService(){
        // data
        Calendar data = dataDatePicker.getValue();
        // hora
        Calendar hora = horarioField.getValue();
        data.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        data.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));

        service.setKey(keyField.getText());
        service.setData(data.getTime());
        service.setTimeZone(TimeZone.getTimeZone(fusoBox.getValue()));

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
