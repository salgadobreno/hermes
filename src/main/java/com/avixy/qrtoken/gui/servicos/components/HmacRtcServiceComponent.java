package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.TextFieldLimited;
import com.avixy.qrtoken.core.extensions.components.TimeZoneField;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.negocio.lib.AvixyKeyDerivator;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.rtc.AbstractHmacRtcService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.bouncycastle.crypto.CryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbee.javafx.scene.layout.MigPane;

import java.security.GeneralSecurityException;

/**
 * @author Breno Salgado <breno@avixy.com>
 *
 * Created on 07/08/2014
 */
public class HmacRtcServiceComponent extends ServiceComponent {
    protected Logger logger = LoggerFactory.getLogger(HmacRtcServiceComponent.class);

    protected Node node;

    protected Label title = new Label();
    protected TimestampField timestampField = new TimestampField();
    protected TimeZoneField timeZoneField = new TimeZoneField();
    protected TextFieldLimited serialNumberField = new TextFieldLimited(10);

    protected AvixyKeyConfiguration avixyKeyConfiguration = AvixyKeyConfiguration.getInstance();


    @Inject
    public HmacRtcServiceComponent(AbstractHmacRtcService service) {
        super(service);
        this.service = service;

        title.setText(service.getServiceName());

        timeZoneField.getSelectionModel().select("Brasilia");
    }

    @Override
    public Service getService() throws CryptoException, GeneralSecurityException, AvixyKeyDerivator.AvixyKeyNotConfigured {
        AbstractHmacRtcService hmacRtcService = (AbstractHmacRtcService) service;

        hmacRtcService.setHmacKey(avixyKeyConfiguration.getHmacKey(serialNumberField.getText()));
        hmacRtcService.setTimestamp(timestampField.getValue());
        hmacRtcService.setTimezone(timeZoneField.getTimeZone());

        return hmacRtcService;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();

        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        migPane.add(new Label("Fuso horário:"));
        migPane.add(timeZoneField, "wrap");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField, "wrap");

        return migPane;
    }

    @Override
    public String getServiceName() { return service.getServiceName(); }

}
