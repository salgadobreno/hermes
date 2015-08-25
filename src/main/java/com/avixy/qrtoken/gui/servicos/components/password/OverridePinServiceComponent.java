package com.avixy.qrtoken.gui.servicos.components.password;

import com.avixy.qrtoken.gui.components.PasswordField;
import com.avixy.qrtoken.gui.components.TimestampField;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.password.OverridePinService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 20/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.PASSWORD)
public class OverridePinServiceComponent extends ServiceComponent {
    private OverridePinService service;
    private PasswordField pukField = new PasswordField();
    private PasswordField newPinField = new PasswordField();
    private TimestampField timestampField = new TimestampField();
    /**
     * @param service
     * @param qrCodePolicy
     */
    @Inject
    protected OverridePinServiceComponent(OverridePinService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label("Sobreescrever PIN");
        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");
        migPane.add(new Label("PUK Atual:"));
        migPane.add(pukField, "wrap");
        migPane.add(new Label("Novo PIN:"));
        migPane.add(newPinField, "wrap");
        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField);

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setPuk(pukField.getText());
        service.setPin(newPinField.getText());
        service.setTimestamp(timestampField.getValue());

        return service;
    }
}
