package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.HmacRtcService;
import com.avixy.qrtoken.negocio.servico.Service;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.params.TimeZoneParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
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
@ServiceComponent.Category(category = ServiceCategory.RTC)
@AcceptsKey(keyType = KeyType.HMAC)
public class RtcServiceComponent extends ServiceComponent {

    private final String FXML_PATH = "/fxml/rtcservice.fxml";

    private Node node;

    @FXML private CalendarTextField dataDatePicker;
    @FXML private ComboBox<String> fusoBox;
    @FXML private CalendarTimeTextField horarioField;
    @FXML private ComboBox<Chave> keyField;

    /* TODO:
     * remover os tooltips padrao dos time fields -> CalendarTextFieldCaspianSkin.java ..
     */

    public RtcServiceComponent() {
        service = injector.getInstance(HmacRtcService.class);

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
        keyField.setItems(ChavesSingleton.getInstance().observableChavesFor(keyType));
    }

    @Override
    public Service getService(){
        HmacRtcService hmacRtcService = (HmacRtcService) service;
        /* data */
        Calendar data = dataDatePicker.getValue();
        /* hora */
        Calendar hora = horarioField.getValue();
        data.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        data.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));

        hmacRtcService.setKey(keyField.getValue().getValor());
        hmacRtcService.setDate(new TimestampParam(data.getTime()));
        hmacRtcService.setTimeZone(new TimeZoneParam(TimeZone.getTimeZone(fusoBox.getValue())));

        return hmacRtcService;
    }

    @Override
    public Node getNode() { return node; }

    @Override
    public String getServiceName() { return service.getServiceName(); }

}
