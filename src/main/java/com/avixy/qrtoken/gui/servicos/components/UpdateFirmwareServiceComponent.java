package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.SerialNumberField;
import com.avixy.qrtoken.core.extensions.components.TemplateSlotSelect;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateSelect;
import com.avixy.qrtoken.core.extensions.customControls.PopOver;
import com.avixy.qrtoken.negocio.servico.chaves.ClientKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.UpdateFirmwareService;
import com.avixy.qrtoken.negocio.servico.servicos.UpdateTemplateService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.tbee.javafx.scene.layout.MigPane;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created on 03/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.OUTROS)
public class UpdateFirmwareServiceComponent extends ServiceComponent {
    private UpdateFirmwareService service;

    private SerialNumberField serialNumberField = new SerialNumberField();

    /**
     * @param service
     */
    @Inject
    public UpdateFirmwareServiceComponent(UpdateFirmwareService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField, "wrap");

        Button button = new Button("Selecionar arquivos");
        button.setOnAction(event -> {
            // Stub
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Firmware file", "*.bin");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(controller.getQrDisplayVBox().getScene().getWindow());
            return;
        });
        migPane.add(button, "newline, skip 1");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setAesKey(ClientKeyConfiguration.getSelected().getAesKey(serialNumberField.getText()));
        service.setHmacKey(ClientKeyConfiguration.getSelected().getHmacKey(serialNumberField.getText()));

        return service;
    }
}
