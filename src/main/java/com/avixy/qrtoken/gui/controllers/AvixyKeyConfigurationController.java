package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.core.extensions.components.HexField;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.apache.commons.codec.binary.Hex;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 27/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AvixyKeyConfigurationController extends MigPane {
    private AvixyKeyConfiguration avixyKeyConfiguration = AvixyKeyConfiguration.getInstance();

    private HexField component1Field = new HexField(64);
    private HexField component2Field = new HexField(64);
    private HexField component3Field = new HexField(64);

    private HexField kAes1Field = new HexField(64);
    private HexField kAes2Field = new HexField(64);
    private HexField kHmac1Field = new HexField(64);
    private HexField kHmac2Field = new HexField(64);

    private Button saveButton = new Button("Salvar");

    public AvixyKeyConfigurationController() {
        try {
            component1Field.setText(Hex.encodeHexString(avixyKeyConfiguration.getAvixyKeyDerivator().getkComponent1()));
            component2Field.setText(Hex.encodeHexString(avixyKeyConfiguration.getAvixyKeyDerivator().getkComponent2()));
            component3Field.setText(Hex.encodeHexString(avixyKeyConfiguration.getAvixyKeyDerivator().getkComponent3()));
            kAes1Field.setText(Hex.encodeHexString(avixyKeyConfiguration.getAvixyKeyDerivator().getkAes1()));
            kAes2Field.setText(Hex.encodeHexString(avixyKeyConfiguration.getAvixyKeyDerivator().getkAes2()));
            kHmac1Field.setText(Hex.encodeHexString(avixyKeyConfiguration.getAvixyKeyDerivator().getkHmac1()));
            kHmac2Field.setText(Hex.encodeHexString(avixyKeyConfiguration.getAvixyKeyDerivator().getkHmac2()));
        } catch (Exception ignored) {
            //TODO: log
        }

        Label title = new Label("Configurar de Chave Avixy");

        title.setFont(new Font(18));
        add(title, "span, wrap");

        Label componentesLabel = new Label("Componentes de chave");
        componentesLabel.setFont(new Font(16));
        add(componentesLabel, "span, wrap");

        add(new Label("Componente 1:"));
        add(component1Field, "wrap");

        add(new Label("Componente 2:"));
        add(component2Field, "wrap");

        add(new Label("Componente 3:"));
        add(component3Field, "wrap");

        Label derivacaoLabel = new Label("Constantes de derivação");
        derivacaoLabel.setFont(new Font(16));
        add(derivacaoLabel, "span, wrap");

        add(new Label("AES:"), "wrap");
        add(new HBox(10, kAes1Field, kAes2Field), "span, wrap");

        add(new Label("HMAC:"), "wrap");
        add(new HBox(10, kHmac1Field, kHmac2Field), "span, wrap");

        add(saveButton);

        saveButton.setDefaultButton(true);
        saveButton.setOnAction(event -> {
            avixyKeyConfiguration.setKeyComponents(
                    component1Field.getValue(),
                    component2Field.getValue(),
                    component3Field.getValue()
            );
            avixyKeyConfiguration.setAesConstants(
                    kAes1Field.getValue(),
                    kAes2Field.getValue()
            );
            avixyKeyConfiguration.setHmacConstants(
                    kHmac1Field.getValue(),
                    kHmac2Field.getValue()
            );

            avixyKeyConfiguration.persist();
            this.getScene().getWindow().hide();
        });
    }
}
