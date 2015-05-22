package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.ImportAvixySymKeySetService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
* Created on 30/01/2015
*
* @author Breno Salgado <breno.salgado@avixy.com>
*/
@ServiceComponent.Category(category = ServiceCategory.CHAVES)
public class ImportAvixySymKeyServiceComponent extends ServiceComponent {
    private final ImportAvixySymKeySetService service;

    private TimestampField timestampField = new TimestampField();

    private SerialNumberField serialNumberField = new SerialNumberField();

    /**
     * @param service
     */
    @Inject
    public ImportAvixySymKeyServiceComponent(ImportAvixySymKeySetService service) {
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

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField, "wrap");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setTimestamp(timestampField.getValue());

        String serialNumber = serialNumberField.getText();
        service.setSecrecyKey(AvixyKeyConfiguration.getSelected().getAesKey(serialNumber));
        service.setAuthKey(AvixyKeyConfiguration.getSelected().getHmacKey(serialNumber));

        return service;
    }
}
