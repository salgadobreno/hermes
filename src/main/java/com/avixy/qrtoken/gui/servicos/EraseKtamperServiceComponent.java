package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.core.components.TimestampField;
import com.avixy.qrtoken.negocio.servico.EraseKtamperService;
import com.avixy.qrtoken.negocio.servico.Service;
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
    private TextField pinField = new TextField();

    public EraseKtamperServiceComponent() {
        this.service = injector.getInstance(EraseKtamperService.class);
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
