package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.HmacSelect;
import com.avixy.qrtoken.core.extensions.components.TimeZoneField;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.rtc.AbstractHmacRtcService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbee.javafx.scene.layout.MigPane;

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
    protected HmacSelect keyField = new HmacSelect();


    @Inject
    public HmacRtcServiceComponent(AbstractHmacRtcService service) {
        super(service);
        this.service = service;

        title.setText(service.getServiceName());

        timeZoneField.getSelectionModel().select("Brasilia");
    }

    @Override
    public Service getService() {
        AbstractHmacRtcService hmacRtcService = (AbstractHmacRtcService) service;

        hmacRtcService.setHmacKey(keyField.getValue().getHexValue());
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
//        migPane.add(fusoBox, "wrap");
        migPane.add(timeZoneField, "wrap");

        migPane.add(new Label("HMAC Key:"));
        migPane.add(keyField, "wrap");

        return migPane;
    }

    @Override
    public String getServiceName() { return service.getServiceName(); }

}
