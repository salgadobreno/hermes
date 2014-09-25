package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.negocio.servico.servicos.rtc.HmacRtcService;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.Arrays;
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

    @FXML private TimestampField timestampField;
    @FXML private ComboBox<String> fusoBox;
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

        KeyType keyType = service.getKeyPolicy().getClass().getAnnotation(AcceptsKey.class).keyType();
        keyField.setItems(ChavesSingleton.getInstance().observableChavesFor(keyType));
    }

    @Override
    public Service getService(){
        HmacRtcService hmacRtcService = (HmacRtcService) service;

        hmacRtcService.setKey(keyField.getValue().getValor());
        hmacRtcService.setTimestamp(timestampField.getValue());
        hmacRtcService.setTimezone(TimeZone.getTimeZone(fusoBox.getValue()));

        return hmacRtcService;
    }

    @Override
    public Node getNode() { return node; }

    @Override
    public String getServiceName() { return service.getServiceName(); }

}
