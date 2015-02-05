package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.UpdateSymmetricKeyService;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 29/01/2015
 *
 * @author I7
 */
public abstract class UpdateSymmetricKeyServiceComponent extends ServiceComponent {
    UpdateSymmetricKeyService service;

    private TimestampField timestampField = new TimestampField();

    private ChaveSelect keySelect = new ChaveSelect();
    private ChaveSelect authSelect = new ChaveSelect();

    private PasswordField pinField = new PasswordField();

    private HmacSelect currentAuth = new HmacSelect();
    private AesSelect currentSecret = new AesSelect();

    /**
     * @param service
     * @param qrCodePolicy
     */
    protected UpdateSymmetricKeyServiceComponent(UpdateSymmetricKeyService service) {
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

        migPane.add(new Label("PIN:"));
        migPane.add(pinField, "wrap");

        migPane.add(new Label("Aes Key:"));
        migPane.add(keySelect, "wrap");

        migPane.add(new Label("Auth Key:"));
        migPane.add(authSelect, "wrap");

        Separator separator = new Separator();
        separator.setPrefWidth(137);
        migPane.add(separator, "span, wrap");

        migPane.add(new Label("Current Aes Key:"));
        migPane.add(currentSecret, "wrap");

        migPane.add(new Label("Current Auth Key:"));
        migPane.add(currentAuth, "wrap");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setTimestamp(timestampField.getValue());
        service.setAesKey(currentSecret.getValue().getHexValue());
        service.setHmacKey(currentAuth.getValue().getHexValue());

        service.setPin(pinField.getText());

        service.setSecretKey(keySelect.getValue().getHexValue());
        service.setAuthKey(authSelect.getValue().getHexValue());

        return service;
    }
}
