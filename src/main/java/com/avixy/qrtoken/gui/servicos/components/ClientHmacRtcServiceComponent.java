package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.rtc.ClientRtcService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.Arrays;

/**
 * Created on 02/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.RTC)
@AcceptsKey(keyType = KeyType.HMAC)
public class ClientHmacRtcServiceComponent extends HmacRtcServiceComponent {
    /* TODO: Node e getService tão hack... */
    private ComboBox<Integer> templateField = new ComboBox<>();

    private ClientRtcService service;

    @Inject
    public ClientHmacRtcServiceComponent(ClientRtcService service) {
        super(service);
        this.service = service;

        templateField.setItems(FXCollections.observableList(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14)));
    }

    @Override
    public String getServiceName() {
        return service.getServiceName();
    }

    @Override
    public Service getService() {
        super.getService();
        return service;
    }
}
