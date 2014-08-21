package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.gui.ChavesSingleton;
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
import javafx.util.StringConverter;
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
 * @author Breno Salgado <breno@avixy.com>
 */
@ServiceCategory(category = ServiceComponent.Category.RTC)
public class RtcServiceComponent extends ServiceComponent {

    private static final String FXML_PATH = "/fxml/rtcservice.fxml";
    private final HmacRtcService service = new HmacRtcService();

    private Node node;

    @FXML private CalendarTextField dataDatePicker;
    @FXML private ComboBox<String> fusoBox;
    @FXML private CalendarTimeTextField horarioField;
    @FXML private ComboBox<ChavesSingleton.Chave> keyField;

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

        keyField.setItems(ChavesSingleton.getObservableChaves());
        keyField.setConverter(new StringConverter<ChavesSingleton.Chave>() {
            @Override
            public String toString(ChavesSingleton.Chave chave) {
                return chave.getId() + " - " + chave.getAlgoritmo();
            }

            @Override
            public ChavesSingleton.Chave fromString(String s) {
                return new ChavesSingleton.Chave(null, null, null); //FIXME
            }
        });
    }

    @Override
    public Service getService(){
        // data
        Calendar data = dataDatePicker.getValue();
        // hora
        Calendar hora = horarioField.getValue();
        data.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        data.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));

        service.setKey(keyField.getValue().getValor());
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
