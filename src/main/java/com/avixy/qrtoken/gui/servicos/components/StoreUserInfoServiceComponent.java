package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.TextFieldLimited;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.StoreUserInfoService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.apache.commons.lang.RandomStringUtils;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 01/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.CICLO_DE_VIDA)
public class StoreUserInfoServiceComponent extends ServiceComponent {
    private StoreUserInfoService service;

    private TextFieldLimited nomeField = new TextFieldLimited();
    private TextFieldLimited emailField = new TextFieldLimited();
    private TextFieldLimited clienteField = new TextFieldLimited();
    private TextFieldLimited cpfField = new TextFieldLimited();
    private TextFieldLimited agenciaField = new TextFieldLimited();
    private TextFieldLimited contaField = new TextFieldLimited();
    private TextFieldLimited foneField = new TextFieldLimited();

    private ComboBox<Chave> hmacField = new ComboBox<>();
    private ComboBox<Chave> aesField = new ComboBox<>();

    @Inject
    public StoreUserInfoServiceComponent(StoreUserInfoService service, QrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;

        nomeField.setMaxlength(30);
        emailField.setMaxlength(40);
        cpfField.setMaxlength(20);
        clienteField.setMaxlength(20);
        contaField.setMaxlength(10);
        agenciaField.setMaxlength(10);
        foneField.setMaxlength(20);

        //random
        nomeField.setText(RandomStringUtils.randomAlphabetic(10));
        emailField.setText(RandomStringUtils.randomAlphabetic(10));
        clienteField.setText(RandomStringUtils.randomAlphabetic(6));
        cpfField.setText(RandomStringUtils.randomNumeric(11));
        contaField.setText(RandomStringUtils.randomNumeric(6));
        agenciaField.setText(RandomStringUtils.randomNumeric(5));
        foneField.setText(RandomStringUtils.randomNumeric(12));
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "span, wrap");

        migPane.add(new Label("Nome:"));
        migPane.add(nomeField, "wrap");

        migPane.add(new Label("Email:"));
        migPane.add(emailField, "wrap");

        migPane.add(new Label("CPF:"));
        migPane.add(cpfField, "wrap");

        migPane.add(new Label("Cliente:"));
        migPane.add(clienteField, "wrap");

        migPane.add(new Label("Agencia:"));
        migPane.add(agenciaField, "wrap");

        migPane.add(new Label("Conta Corrente:"));
        migPane.add(contaField, "wrap");

        migPane.add(new Label("Telefone:"));
        migPane.add(foneField, "wrap");

        hmacField.setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.HMAC));
        hmacField.getSelectionModel().select(0);
        migPane.add(new Label("Cifra HMAC:"));
        migPane.add(hmacField, "wrap");

        aesField.setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.AES));
        aesField.getSelectionModel().select(0);
        migPane.add(new Label("Cifra AES:"));
        migPane.add(aesField, "wrap");

        return migPane;
    }

    @Override
    public Service getService() {
        service.setNome(nomeField.getText());
        service.setEmail(emailField.getText());
        service.setConta(contaField.getText());
        service.setCpf(cpfField.getText());
        service.setTelefone(foneField.getText());
        service.setAgencia(agenciaField.getText());
        service.setCliente(clienteField.getText());

        service.setAesKey(aesField.getValue().getHexValue());
        service.setHmacKey(hmacField.getValue().getHexValue());

        return super.getService();
    }

}
