package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.crypto.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.HmacRtcService;
import com.avixy.qrtoken.negocio.servico.Service;
import com.avixy.qrtoken.negocio.servico.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.crypto.Chave;
import com.avixy.qrtoken.negocio.servico.crypto.KeyType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import jfxtras.labs.scene.control.CalendarTextField;
import jfxtras.labs.scene.control.CalendarTimeTextField;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Breno Salgado <breno@avixy.com>
 *
 * Created on 07/08/2014
 */
@ServiceComponent.Category(category = com.avixy.qrtoken.gui.servicos.ServiceCategory.RTC)
@AcceptsKey(keyType = KeyType.HMAC)
public class RtcServiceComponent extends ServiceComponent {
    private ChavesSingleton chaves = ChavesSingleton.getInstance();

    private static final String FXML_PATH = "/fxml/rtcservice.fxml";
    private final HmacRtcService service = new HmacRtcService();

    private Node node;

    @FXML private CalendarTextField dataDatePicker;
    @FXML private ComboBox<String> fusoBox;
    @FXML private CalendarTimeTextField horarioField;
    @FXML private ComboBox<Chave> keyField;

    /* TODO:
     * remover os tooltips padrao dos time fields -> CalendarTextFieldCaspianSkin.java ..
     */

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

        KeyType keyType = service.getKeyPolicy().getClass().getAnnotation(AcceptsKey.class).keyType();
        keyField.setItems(chaves.observableChaveFor(keyType));
        //TODO:
        // OOP melhor aqui
    }

    @Override
    public Service getService(){
        /* data */
        Calendar data = dataDatePicker.getValue();
        /* hora */
        Calendar hora = horarioField.getValue();
        data.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        data.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));

        service.setKey(keyField.getValue().getValor());
        service.setDate(data.getTime());
        service.setTimeZone(TimeZone.getTimeZone(fusoBox.getValue()));

        return service;
    }

    @Override
    public Node getNode() { return node; }

    @Override
    public String getServiceName() { return service.getServiceName(); }

}
