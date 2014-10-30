package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.HmacSelect;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.rtc.HmacRtcService;
import com.google.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.TimeZone;

/**
 * @author Breno Salgado <breno@avixy.com>
 *
 * Created on 07/08/2014
 */
public class HmacRtcServiceComponent extends ServiceComponent {
    protected Logger logger = LoggerFactory.getLogger(HmacRtcServiceComponent.class);

    protected final String FXML_PATH = "/fxml/rtcservice.fxml";

    protected Node node;

    @FXML protected Label title;
    @FXML protected TimestampField timestampField;
    @FXML protected ComboBox<String> fusoBox;
    @FXML protected HmacSelect keyField;


    /* TODO:
     * remover os tooltips padrao dos time fields -> CalendarTextFieldCaspianSkin.java ..
     */

    @Inject
    public HmacRtcServiceComponent(HmacRtcService service, QrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_PATH));
        fxmlLoader.setController(this);

        try {
            node = (Node) fxmlLoader.load();
        } catch (IOException e) {
            logger.error("FXML Error: ", e);
        }
        title.setText(service.getServiceName());

        ObservableList<String> observableList = FXCollections.observableList(Arrays.asList(TimeZone.getAvailableIDs()));

        fusoBox.setItems(observableList);
        fusoBox.getSelectionModel().select(TimeZone.getDefault().getID());
    }

    @Override
    public Service getService() {
        HmacRtcService hmacRtcService = (HmacRtcService) service;

        hmacRtcService.setHmacKey(keyField.getValue().getHexValue());
        hmacRtcService.setTimestamp(timestampField.getValue());
        hmacRtcService.setTimezone(TimeZone.getTimeZone(fusoBox.getValue()));

        return hmacRtcService;
    }

    @Override
    public Node getNode() { return node; }

    @Override
    public String getServiceName() { return service.getServiceName(); }

}
