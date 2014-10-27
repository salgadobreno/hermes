package com.avixy.qrtoken.gui.servicos.components.banking;

import com.avixy.qrtoken.core.extensions.components.TextFieldLimited;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.servicos.banking.LoginService;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 23/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.BANCARIO)
public class LoginServiceComponent extends ServiceComponent {
    private LoginService service;

    private TextFieldLimited pinField = new TextFieldLimited();
    private TextFieldLimited loginCodeField = new TextFieldLimited();
    private ComboBox<Integer> templateComboBox = new ComboBox<>();
    private TimestampField timestampField = new TimestampField();

    private ComboBox<Chave> hmacField = new ComboBox<>();
    private ComboBox<Chave> aesField = new ComboBox<>();

    @Inject
    protected LoginServiceComponent(LoginService service, QrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;

        pinField.setMaxlength(4);
        loginCodeField.setMaxlength(6);
        List<Integer> templates = Arrays.asList(1,2,3,4,5,6,7,8,9,1,11,12);
        templateComboBox.setItems(FXCollections.observableList(templates));
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "span, wrap");

        migPane.add(new Label("Template:"));
        migPane.add(templateComboBox, "wrap");

        migPane.add(new Label("PIN:"));
        migPane.add(pinField, "wrap");

        migPane.add(new Label("Código de Login:"));
        migPane.add(loginCodeField, "wrap");

        hmacField.setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.HMAC));
        hmacField.getSelectionModel().select(0);
        migPane.add(new Label("Cifra HMAC:"));
        migPane.add(hmacField, "wrap");

        aesField.setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.AES));
        aesField.getSelectionModel().select(0);
        migPane.add(new Label("Cifra AES:"));
        migPane.add(aesField, "wrap");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        return migPane;
    }

    @Override
    public Service getService() {
        service.setTemplate(templateComboBox.getValue().byteValue());
        service.setPin(pinField.getText());

        service.setLoginCode(loginCodeField.getText());
        service.setTimestamp(timestampField.getValue());

        service.setChaveHmac(hmacField.getValue());
        service.setChaveAes(aesField.getValue());

        return service;
    }
}
