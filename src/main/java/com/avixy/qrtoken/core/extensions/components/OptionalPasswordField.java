package com.avixy.qrtoken.core.extensions.components;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 * Created on 14/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class OptionalPasswordField extends VBox {
    private PasswordField passwordField = new PasswordField();
    private CheckBox checkBox = new CheckBox("Desabilitar PIN");

    public OptionalPasswordField() {
        setSpacing(5);
        getChildren().addAll(passwordField, checkBox);
        passwordField.disableProperty().bind(checkBox.selectedProperty());
    }

    public boolean isOptional() {
        return checkBox.isSelected();
    }

    public String getText() {
        return passwordField.getText();
    }
}
