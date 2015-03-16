package com.avixy.qrtoken.core.extensions.components.templates;

import com.avixy.qrtoken.negocio.template.Text;
import javafx.beans.binding.Bindings;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Created on 06/03/2015
 *
 * @author I7
 */
public class TemplateTextTextField extends HBox {
    private CheckBox checkBox;
    private TextField textField;

    public TemplateTextTextField() {
        this.checkBox = new CheckBox("Argument");
        this.textField = new TextField();
        Bindings.bindBidirectional(checkBox.selectedProperty(), textField.disableProperty());
        setSpacing(10);
        checkBox.setPrefHeight(30);
        getChildren().addAll(textField, checkBox);
    }

    public String getValue(){
        if (checkBox.isSelected()) {
            return Text.TEXT_FROM_ARGUMENT;
        } else {
            return textField.getText();
        }
    }

    public void setValue(String value){
        if (Text.TEXT_FROM_ARGUMENT.equals(value)) {
            checkBox.setSelected(true);
        } else {
            checkBox.setSelected(false);
            textField.setText(value);
        }
    }
}
