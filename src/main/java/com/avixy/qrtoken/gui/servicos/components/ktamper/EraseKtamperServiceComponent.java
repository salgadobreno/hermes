package com.avixy.qrtoken.gui.servicos.components.ktamper;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.ktamper.EraseKtamperService;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import jidefx.scene.control.decoration.DecorationPane;
import jidefx.scene.control.validation.ValidationUtils;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.KTAMPER)
public class EraseKtamperServiceComponent extends ServiceComponent {
    private TimestampField timestampField = new TimestampField();
    private SerialNumberField serialNumberField = new SerialNumberField();

    @Inject
    public EraseKtamperServiceComponent(EraseKtamperService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField, "wrap");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        EraseKtamperService eraseKtamperService = (EraseKtamperService) service;
        eraseKtamperService.setTimestamp(timestampField.getValue());
        eraseKtamperService.setHmacKey(AvixyKeyConfiguration.getSelected().getHmacKey(serialNumberField.getText()));

        return service;
    }
}
