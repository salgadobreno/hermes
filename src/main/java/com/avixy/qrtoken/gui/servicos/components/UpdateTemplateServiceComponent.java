package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateSelect;
import com.avixy.qrtoken.negocio.servico.chaves.ClientKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.UpdateTemplateService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

import java.io.IOException;

/**
 * Created on 03/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.OUTROS)
public class UpdateTemplateServiceComponent extends ServiceComponent {
    private UpdateTemplateService service;

    private TemplateSelect templateSelect = new TemplateSelect();
    private TemplateSlotSelect templateSlotSelect = new TemplateSlotSelect();
    private TimestampField timestampField = new TimestampField();
    private SerialNumberField serialNumberField = new SerialNumberField();

    /**
     * @param service
     */
    @Inject
    public UpdateTemplateServiceComponent(UpdateTemplateService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");

        migPane.add(new Label("Slot:"));
        migPane.add(templateSlotSelect, "wrap");

        migPane.add(new Label("Aplicação:"));
        migPane.add(templateSelect, "wrap");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField, "wrap");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        Button button = new Button("Abrir editor de aplicações:");
        button.setOnAction(event -> {
            try {
                controller.templateEditor();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        migPane.add(button, "newline, skip 1");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setTemplateSlot(templateSlotSelect.getValue().byteValue());
        service.setTimestamp(timestampField.getValue());
        service.setTemplate(templateSelect.getValue());
        service.setAesKey(ClientKeyConfiguration.getSelected().getAesKey(serialNumberField.getText()));
        service.setHmacKey(ClientKeyConfiguration.getSelected().getHmacKey(serialNumberField.getText()));

        return service;
    }
}
