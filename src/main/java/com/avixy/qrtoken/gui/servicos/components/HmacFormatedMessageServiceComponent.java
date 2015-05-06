package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateSelect;
import com.avixy.qrtoken.negocio.servico.servicos.HmacFormatedMessageService;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.template.TemplatesSingleton;
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
public class HmacFormatedMessageServiceComponent extends ServiceComponent {
    private HmacFormatedMessageService service;

    private TemplatesSingleton templatesSingleton = TemplatesSingleton.getInstance();
    private TemplateSelect templateSelect = new TemplateSelect();
    private HmacKeySelect hmacKeySelect = new HmacKeySelect();
    private RangedTimestampField rangedTimestamp = new RangedTimestampField();
    private OptionalPasswordField optionalPasswordField = new OptionalPasswordField();
    private AesKeySelect keySelect = new AesKeySelect();


    /**
     * @param service
     */
    @Inject
    public HmacFormatedMessageServiceComponent(HmacFormatedMessageService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");

        migPane.add(new Label("Aplicação:"));
        migPane.add(templateSelect, "wrap");

        migPane.add(new Label("Aes Key:"));
        migPane.add(keySelect, "wrap");

        migPane.add(new Label("HMAC Key:"));
        migPane.add(hmacKeySelect, "wrap");

        migPane.add(new Label("Timestamp:"));
        migPane.add(rangedTimestamp, "wrap");

        migPane.add(new Label("PIN:"));
        migPane.add(optionalPasswordField, "wrap");

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
        service.togglePasswordOptional(optionalPasswordField.isOptional());
        service.setTimestampRange(rangedTimestamp.getStartDateValue(), rangedTimestamp.getEndDateValue());
        service.setPin(optionalPasswordField.getText());
        service.setAesKey(keySelect.getValue().getHexValue());
        service.setHmacKey(hmacKeySelect.getValue().getHexValue());
        service.setTemplate(templateSelect.getValue());

        return service;
    }
}
