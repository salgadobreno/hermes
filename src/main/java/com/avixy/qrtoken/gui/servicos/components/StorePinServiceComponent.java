package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.PinField;
import com.avixy.qrtoken.negocio.qrcode.BasicQrCodePolicy;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.pinpuk.StorePinService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 18/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.PASSWORD)
public class StorePinServiceComponent extends ServiceComponent {
    StorePinService service;
    TextField pin = new PinField();

    /**
     * @param service
     * @param qrCodePolicy
     */
    @Inject
    protected StorePinServiceComponent(StorePinService service, BasicQrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label label = new Label("Armazenar PIN");
        label.setFont(new Font(18));
        migPane.add(label, "wrap");
        migPane.add(new Label("PIN:"));
        migPane.add(pin);
        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setPin(pin.getText());

        return service;
    }
}
