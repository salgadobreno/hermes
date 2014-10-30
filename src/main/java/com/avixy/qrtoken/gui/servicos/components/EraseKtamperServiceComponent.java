package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.PinField;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.servicos.ktamper.EraseKtamperService;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.CHAVES)
public class EraseKtamperServiceComponent extends ServiceComponent {
    private TimestampField timestampField = new TimestampField();
    private PinField pinField = new PinField();

    @Inject
    public EraseKtamperServiceComponent(EraseKtamperService service, QrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "wrap");
        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");
        migPane.add(new Label("PIN:"));
        migPane.add(pinField);

        return migPane;
    }

    @Override
    public Service getService() {
        EraseKtamperService eraseKtamperService = (EraseKtamperService) service;
        eraseKtamperService.setTimestamp(timestampField.getValue());
        eraseKtamperService.setPin(pinField.getText());

        return super.getService();
    }
}
