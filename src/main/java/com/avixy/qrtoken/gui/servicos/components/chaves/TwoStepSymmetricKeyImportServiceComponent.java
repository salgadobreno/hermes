package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.gui.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.TwoStepSymmetricKeyImportService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 03/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class TwoStepSymmetricKeyImportServiceComponent extends ServiceComponent {
    private TwoStepSymmetricKeyImportService service;

    private TimestampField timestampField = new TimestampField();
    private OptionalPasswordField optionalPasswordField = new OptionalPasswordField();

    private AesKeySelect keySelect = new AesKeySelect();
    private HmacKeySelect authSelect = new HmacKeySelect();

    @Inject
    protected TwoStepSymmetricKeyImportServiceComponent(TwoStepSymmetricKeyImportService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());

        title.setFont(new Font(18));
        migPane.add(title, "span, wrap");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        migPane.add(new Label("PIN:"));
        migPane.add(optionalPasswordField, "wrap");

        migPane.add(new Label("Aes Key:"));
        migPane.add(keySelect, "wrap");

        migPane.add(new Label("Auth Key::"));
        migPane.add(authSelect, "wrap");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setSecrecyKey(keySelect.getValue().getHexValue());
        service.setAuthKey(authSelect.getValue().getHexValue());
        service.setTimestamp(timestampField.getValue());
        service.togglePasswordOptional(optionalPasswordField.isOptional());
        service.setPin(optionalPasswordField.getText());

        return service;
    }
}
