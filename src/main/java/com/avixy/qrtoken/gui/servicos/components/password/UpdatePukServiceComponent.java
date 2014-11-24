package com.avixy.qrtoken.gui.servicos.components.password;

import com.avixy.qrtoken.core.extensions.components.PasswordField;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.qrcode.BasicQrCodePolicy;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.password.UpdatePinService;
import com.avixy.qrtoken.negocio.servico.servicos.password.UpdatePukService;
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
public class UpdatePukServiceComponent extends ServiceComponent {
    private UpdatePukService service;
    private PasswordField pukField = new PasswordField();
    private PasswordField newPukField = new PasswordField();
    private TimestampField timestampField = new TimestampField();
    /**
     * @param service
     * @param qrCodePolicy
     */
    @Inject
    protected UpdatePukServiceComponent(UpdatePukService service, BasicQrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label("Alterar PUK");
        title.setFont(new Font(18));
        migPane.add(title, "wrap");
        migPane.add(new Label("PUK Atual:"));
        migPane.add(pukField, "wrap");
        migPane.add(new Label("Novo PUK:"));
        migPane.add(newPukField, "wrap");
        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField);

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setPuk(pukField.getText());
        service.setNewPuk(newPukField.getText());
        service.setTimestamp(timestampField.getValue());

        return service;
    }
}
