package com.avixy.qrtoken.gui.servicos.components.banking;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.banking.LoginService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 23/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.BANCARIO)
public class LoginServiceComponent extends ServiceComponent {
    private LoginService service;

    private PasswordField passwordField = new PasswordField();
    private TextFieldLimited loginCodeField = new TextFieldLimited(6);
    private TemplateSlotSelect templateSlotSelect = new TemplateSlotSelect();
    private TimestampField timestampField = new TimestampField();

    private HmacSelect hmacField = new HmacSelect();
    private AesSelect aesField = new AesSelect();

    @Inject
    protected LoginServiceComponent(LoginService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "span, wrap");

        migPane.add(new Label("Slot:"));
        migPane.add(templateSlotSelect, "wrap");

        migPane.add(new Label("PIN:"));
        migPane.add(passwordField, "wrap");

        migPane.add(new Label("Código de Login:"));
        migPane.add(loginCodeField, "wrap");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        migPane.add(new Label("Chave HMAC:"));
        migPane.add(hmacField, "wrap");

        migPane.add(new Label("Chave AES:"));
        migPane.add(aesField, "wrap");

        return migPane;
    }

    @Override
    public Service getService() {
//        service.setTemplateSlot(templateComboBox.getValue().byteValue());
        service.setTemplateSlot(templateSlotSelect.getValue().byteValue());
        service.setPin(passwordField.getText());

        service.setLoginCode(loginCodeField.getText());
        service.setTimestamp(timestampField.getValue());

        service.setHmacKey(hmacField.getValue().getHexValue());
        service.setAesKey(aesField.getValue().getHexValue());

        return service;
    }
}
