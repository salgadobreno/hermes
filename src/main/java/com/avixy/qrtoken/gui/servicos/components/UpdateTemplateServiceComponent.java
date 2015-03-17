package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.HmacSelect;
import com.avixy.qrtoken.core.extensions.components.PasswordField;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateSelect;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.UpdateTemplateService;
import com.avixy.qrtoken.negocio.template.Template;
import com.avixy.qrtoken.negocio.template.TemplatesSingleton;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

import java.io.IOException;

/**
 * Created on 03/03/2015
 *
 * @author I7
 */
@ServiceComponent.Category(category = ServiceCategory.OUTROS)
public class UpdateTemplateServiceComponent extends ServiceComponent {
    private UpdateTemplateService service;

    private TemplatesSingleton templatesSingleton = TemplatesSingleton.getInstance();
    private TemplateSelect templateSelect = new TemplateSelect();
    private ComboBox<Integer> slotCombobox = new ComboBox<>();
    private HmacSelect hmacSelect = new HmacSelect();
    private TimestampField timestampField = new TimestampField();
    private PasswordField passwordField = new PasswordField();

    /**
     * @param service
     */
    @Inject
    public UpdateTemplateServiceComponent(UpdateTemplateService service) {
        super(service);
        this.service = service;

        for (int i = 0; i <= 10; i++) {
            slotCombobox.getItems().add(i);
        }
        slotCombobox.getSelectionModel().select(0);
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");
        migPane.add(new Label("Slot:"));
        migPane.add(slotCombobox, "wrap");
        migPane.add(new Label("Template:"));
        migPane.add(templateSelect, "wrap");
        migPane.add(new Label("HMAC Key:"));
        migPane.add(hmacSelect, "wrap");
        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");
        migPane.add(new Label("PIN:"));
        migPane.add(passwordField, "wrap");

        Button button = new Button("Abrir editor de templates");
        button.setOnAction(event -> {
            try {
                controller.templates();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button.setDefaultButton(true);
        migPane.add(button, "newline, skip 1");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setPin(passwordField.getText());
        service.setTemplateSlot(slotCombobox.getValue().byteValue());
        service.setTimestamp(timestampField.getValue());
        service.setHmacKey(hmacSelect.getValue().getHexValue());
        service.setTemplate(templateSelect.getTemplate());

        return service;
    }
}
