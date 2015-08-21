package com.avixy.qrtoken.gui.components.templates;

import com.avixy.qrtoken.negocio.template.Text;
import javafx.beans.binding.Bindings;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * Created on 06/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplateTextTextArea extends VBox {
    private CheckBox checkBox;
    private TextArea textArea;

    public TemplateTextTextArea(int rowCount, int columnCount) {
        this.checkBox = new CheckBox("Argument");
        this.textArea = new TextArea();
        textArea.setPrefRowCount(rowCount);
        textArea.setPrefColumnCount(columnCount);
        Bindings.bindBidirectional(checkBox.selectedProperty(), textArea.disableProperty());
        setSpacing(10);
//        checkBox.setPrefHeight(30);
        getChildren().addAll(textArea, checkBox);
    }

    public String getValue(){
        if (checkBox.isSelected()) {
            return Text.TEXT_FROM_ARGUMENT;
        } else {
            return textArea.getText();
        }
    }

    public void setValue(String value) {
        if (Text.TEXT_FROM_ARGUMENT.equals(value)) {
            checkBox.setSelected(true);
        } else {
            textArea.setText(value);
            checkBox.setSelected(false);
        }
    }
}
