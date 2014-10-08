package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.StoreUserInfoService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.Arrays;

/**
 * Created on 01/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category
public class StoreUserInfoServiceComponent extends ServiceComponent {
    private StoreUserInfoService service;

    private TextField nomeField = new TextField();
    private TextField emailField = new TextField();
    private TextField clienteField = new TextField();
    private TextField cpfField = new TextField();
    private TextField agenciaField = new TextField();
    private TextField contaField = new TextField();
    private TextField foneField = new TextField();

    private ComboBox<Integer> templateField = new ComboBox<>();
    private ComboBox<Chave> hmacField = new ComboBox<>();
    private ComboBox<Chave> aesField = new ComboBox<>();

    @Inject
    public StoreUserInfoServiceComponent(StoreUserInfoService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "span, wrap");

        templateField.setItems(FXCollections.observableList(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14)));
        migPane.add(new Label("Template:"));
        migPane.add(templateField, "wrap");

        migPane.add(new Label("Nome:"));
        migPane.add(nomeField, "wrap");

        migPane.add(new Label("Email:"));
        migPane.add(emailField, "wrap");

        migPane.add(new Label("Cliente:"));
        migPane.add(clienteField, "wrap");

        migPane.add(new Label("CPF:"));
        migPane.add(cpfField, "wrap");

        migPane.add(new Label("Agencia:"));
        migPane.add(agenciaField, "wrap");

        migPane.add(new Label("Conta Corrente:"));
        migPane.add(contaField, "wrap");

        migPane.add(new Label("Telefone:"));
        migPane.add(foneField, "wrap");

        hmacField.setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.HMAC));
        migPane.add(new Label("Cifra HMAC:"));
        migPane.add(hmacField, "wrap");

        aesField.setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.AES));
        migPane.add(new Label("Cifra AES:"));
        migPane.add(aesField, "wrap");

        return migPane;
    }

    @Override
    public Service getService() {
        service.setTemplate(templateField.getValue());
        service.setNome(nomeField.getText());
        service.setEmail(emailField.getText());
        service.setConta(contaField.getText());
        service.setCpf(cpfField.getText());
        service.setTelefone(foneField.getText());
        service.setAgencia(agenciaField.getText());
        service.setCliente(clienteField.getText());

        service.setAesKey(aesField.getValue().getValor().getBytes());
        service.setHmacKey(hmacField.getValue().getValor().getBytes());

        return super.getService();
    }
}
