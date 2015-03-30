package com.avixy.qrtoken.core.extensions.components.templates;

import com.avixy.qrtoken.core.extensions.components.NumberField;
import com.avixy.qrtoken.negocio.template.TemplateAlignment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;

/**
 * Created on 17/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TextAlignmentSelect extends Control {
    private ComboBox<TemplateAlignment.Preset> presetComboBox = new ComboBox<>();
    private NumberField customAlignmentTextField = new NumberField();

    public TextAlignmentSelect() {
        customAlignmentTextField.setMaxWidth(44);
        presetComboBox.valueProperty().addListener(new ChangeListener<TemplateAlignment.Preset>() {
            @Override
            public void changed(ObservableValue<? extends TemplateAlignment.Preset> observable, TemplateAlignment.Preset oldValue, TemplateAlignment.Preset newValue) {
                customAlignmentTextField.setVisible(newValue == TemplateAlignment.Preset.CUSTOM);
            }
        });
        presetComboBox.setItems(FXCollections.observableArrayList(TemplateAlignment.Preset.LEFT, TemplateAlignment.Preset.CENTER, TemplateAlignment.Preset.RIGHT, TemplateAlignment.Preset.ARGUMENT, TemplateAlignment.Preset.CENTER));

        getChildren().addAll(presetComboBox, customAlignmentTextField);
    }

    public TemplateAlignment getValue() {
        TemplateAlignment templateAlignment = TemplateAlignment.get(presetComboBox.getValue());
        if (presetComboBox.getValue() == TemplateAlignment.Preset.CUSTOM) {
            templateAlignment.setxPosition(Integer.parseInt(customAlignmentTextField.getText()));
        }
        return templateAlignment;
    }

    public void setValue(TemplateAlignment alignment) {
        presetComboBox.setValue(alignment.getPreset());
        customAlignmentTextField.setText(String.valueOf(alignment.getxPosition()));
    }
}
