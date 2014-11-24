package com.avixy.qrtoken.gui.servicos.components.password;

import com.avixy.qrtoken.core.extensions.components.PasswordField;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.qrcode.BasicQrCodePolicy;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.password.UpdatePinService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 20/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.PASSWORD)
public class UpdatePinServiceComponent extends ServiceComponent {
    private UpdatePinService service;
    private PasswordField pinField = new PasswordField();
    private PasswordField newPinField = new PasswordField();
    private TimestampField timestampField = new TimestampField();
    /**
     * @param service
     * @param qrCodePolicy
     */
    @Inject
    protected UpdatePinServiceComponent(UpdatePinService service, BasicQrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label label = new Label("Alterar PIN");
        label.setFont(new Font(18));
        migPane.add(label, "wrap");
        migPane.add(new Label("PIN Atual:"));
        migPane.add(pinField, "wrap");
        migPane.add(new Label("Novo PIN:"));
        migPane.add(newPinField, "wrap");
        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField);

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setPin(pinField.getText());
        service.setNewPin(newPinField.getText());
        service.setTimestamp(timestampField.getValue());

        return service;
    }
}
