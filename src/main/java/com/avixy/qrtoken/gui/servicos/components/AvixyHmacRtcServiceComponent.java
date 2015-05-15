package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.SerialNumberField;
import com.avixy.qrtoken.core.extensions.components.TextFieldLimited;
import com.avixy.qrtoken.core.extensions.components.TimeZoneField;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.core.extensions.components.validators.JideSizeValidator;
import com.avixy.qrtoken.negocio.lib.AvixyKeyDerivator;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.rtc.AbstractHmacRtcService;
import com.avixy.qrtoken.negocio.servico.servicos.rtc.AvixyRtcService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import jidefx.scene.control.decoration.DecorationPane;
import jidefx.scene.control.validation.ValidationMode;
import jidefx.scene.control.validation.ValidationUtils;
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
@ServiceComponent.Category(category = ServiceCategory.RTC)
public class AvixyHmacRtcServiceComponent extends ServiceComponent {
    protected Logger logger = LoggerFactory.getLogger(AvixyHmacRtcServiceComponent.class);

    protected Node node;

    protected Label title = new Label();
    protected TimestampField timestampField = new TimestampField();
    protected TimeZoneField timeZoneField = new TimeZoneField();
    protected SerialNumberField serialNumberField = new SerialNumberField();

    @Inject
    public AvixyHmacRtcServiceComponent(AvixyRtcService service) {
        super(service);
        this.service = service;

        title.setText(service.getServiceName());

        timeZoneField.getSelectionModel().select("Brasilia");
    }

    @Override
    public Service getService() throws CryptoException, GeneralSecurityException, AvixyKeyDerivator.AvixyKeyNotConfigured {
        AbstractHmacRtcService hmacRtcService = (AbstractHmacRtcService) service;

        hmacRtcService.setHmacKey(AvixyKeyConfiguration.getSelected().getHmacKey(serialNumberField.getText()));
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
