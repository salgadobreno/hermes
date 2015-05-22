package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.chaves.ClientKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.ebchat.EBChatSessionKeyService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 16/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.CHAVES)
public class EBChatShowSessionKeyServiceComponent extends ServiceComponent {
    private EBChatSessionKeyService service;

    private TimestampField timestampField = new TimestampField();

    private AesKeySelect sessionKeySelect = new AesKeySelect();
    private HmacKeySelect sessionAuthKeySelect = new HmacKeySelect();

    private ChallengeField challengeField = new ChallengeField();
    private OptionalPasswordField optionalPasswordField = new OptionalPasswordField();

    private SerialNumberField serialNumberField = new SerialNumberField();

    @Inject
    protected EBChatShowSessionKeyServiceComponent(EBChatSessionKeyService service) {
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

        migPane.add(new Label("Challenge:"));
        migPane.add(challengeField, "wrap");

        migPane.add(new Label("PIN:"));
        migPane.add(optionalPasswordField, "wrap");

        migPane.add(new Label("Session AES Key:"));
        migPane.add(sessionKeySelect, "wrap");

        migPane.add(new Label("Session Auth Key:"));
        migPane.add(sessionAuthKeySelect, "wrap");

        Separator separator = new Separator();
        separator.setPrefWidth(200);
        migPane.add(separator, "span, wrap");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField);

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setAesKey(ClientKeyConfiguration.getSelected().getAesKey(serialNumberField.getText()));
        service.setHmacKey(ClientKeyConfiguration.getSelected().getHmacKey(serialNumberField.getText()));
        service.setSessionSecrecyKey(sessionKeySelect.getValue().getHexValue());
        service.setSessionAuthKey(sessionAuthKeySelect.getValue().getHexValue());
        service.setChallenge(challengeField.getText());
        service.setTimestamp(timestampField.getValue());
        service.togglePasswordOptional(optionalPasswordField.isOptional());
        service.setPin(optionalPasswordField.getText());

        return service;
    }
}
