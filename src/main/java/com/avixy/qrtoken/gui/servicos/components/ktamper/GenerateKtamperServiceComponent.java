package com.avixy.qrtoken.gui.servicos.components.ktamper;

import com.avixy.qrtoken.gui.components.*;
import com.avixy.qrtoken.gui.servicos.components.NoParamServiceComponent;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.ktamper.GenerateKtamperService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.KTAMPER)
public class GenerateKtamperServiceComponent extends NoParamServiceComponent {
    private TimestampField timestampField = new TimestampField();
    private TimeZoneField timeZoneField = new TimeZoneField();
    private TextFieldLimited hwVersionField = new TextFieldLimited(10);
    private SerialNumberField serialNumberField = new SerialNumberField();
    private PasswordField pinField = new PasswordField();
    private PasswordField pukField = new PasswordField();

    @Inject
    public GenerateKtamperServiceComponent(GenerateKtamperService service) {
        super(service);
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();

        Label title = new Label(service.getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        migPane.add(new Label("Fuso horário:"));
        migPane.add(timeZoneField, "wrap");

        migPane.add(new Label("Versão de Hardware:"));
        migPane.add(hwVersionField, "wrap");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField, "wrap");

        migPane.add(new Label("PIN:"));
        migPane.add(pinField, "wrap");

        migPane.add(new Label("PUK:"));
        migPane.add(pukField, "wrap");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        GenerateKtamperService generateKtamperService = (GenerateKtamperService) service;
        generateKtamperService.setTimestamp(timestampField.getValue());
        generateKtamperService.setTimezone(timeZoneField.getTimeZone());
        generateKtamperService.setHWVersion(hwVersionField.getText());
        generateKtamperService.setSerialNumber(serialNumberField.getText());
        generateKtamperService.setPin(pinField.getText());
        generateKtamperService.setPuk(pukField.getText());

        return generateKtamperService;
    }
}
