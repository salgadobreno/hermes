package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.gui.components.RestrictiveTextField;
import com.avixy.qrtoken.gui.components.SerialNumberField;
import com.avixy.qrtoken.gui.components.TextFieldLimited;
import com.avixy.qrtoken.negocio.servico.chaves.ClientKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.StoreUserInfoService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 01/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.CICLO_DE_VIDA)
public class StoreUserInfoServiceComponent extends ServiceComponent {
    private StoreUserInfoService service;

    private TextFieldLimited nomeField = new TextFieldLimited(30);
    private TextFieldLimited emailField = new TextFieldLimited(40);
    private TextFieldLimited clienteField = new TextFieldLimited(20);
    private RestrictiveTextField cpfField = new RestrictiveTextField(11);
    {
        cpfField.setRestrict("[0-9]");
    }
    private TextFieldLimited agenciaField = new TextFieldLimited(10);
    private TextFieldLimited contaField = new TextFieldLimited(10);
    private TextFieldLimited foneField = new TextFieldLimited(20);

    private SerialNumberField serialNumberField = new SerialNumberField();

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

        migPane.add(new Label("Instituição:"));
        migPane.add(clienteField, "wrap");

        migPane.add(new Label("Cliente final:"));
        migPane.add(nomeField, "wrap");

        migPane.add(new Label("Email:"));
        migPane.add(emailField, "wrap");

        migPane.add(new Label("CPF:"));
        migPane.add(cpfField, "wrap");

        migPane.add(new Label("Agencia:"));
        migPane.add(agenciaField, "wrap");

        migPane.add(new Label("Conta Corrente:"));
        migPane.add(contaField, "wrap");

        migPane.add(new Label("Telefone (XX) XXXXXXXX:"));
        migPane.add(foneField, "wrap");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField);

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setNome(nomeField.getText());
        service.setEmail(emailField.getText());
        service.setConta(contaField.getText());
        service.setCpf(cpfField.getText());
        service.setTelefone(foneField.getText());
        service.setAgencia(agenciaField.getText());
        service.setCliente(clienteField.getText());

        service.setAesKey(ClientKeyConfiguration.getSelected().getAesKey(serialNumberField.getText()));
        service.setHmacKey(ClientKeyConfiguration.getSelected().getHmacKey(serialNumberField.getText()));

        return service;
    }

}
