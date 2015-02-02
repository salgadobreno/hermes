package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.OneStepSymmetricKeyImportService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.apache.commons.codec.binary.Hex;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.Collections;


/**
 * Created on 26/01/2015
 *
 * @author I7
 */
public abstract class OneStepSymmetricKeyImportServiceComponent extends ServiceComponent {
    private OneStepSymmetricKeyImportService service;

    private TimestampField timestampField = new TimestampField();
    private PasswordField passwordField = new PasswordField();

    private ChaveSelect keySelect = new ChaveSelect();
    private ChaveSelect authSelect = new ChaveSelect();

    /**
     * @param service
     * @param qrCodePolicy
     */
    public OneStepSymmetricKeyImportServiceComponent(OneStepSymmetricKeyImportService service, QrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
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
        migPane.add(passwordField, "wrap");

        migPane.add(new Label("Aes Key:"));
        migPane.add(keySelect, "wrap");

        migPane.add(new Label("Auth Key::"));
        migPane.add(authSelect, "wrap");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setTimestamp(timestampField.getValue());
        service.setPin(passwordField.getText());

        service.setSecrecyKey(keySelect.getValue().getHexValue());
        service.setAuthKey(authSelect.getValue().getHexValue());

        return service;
    }
}
