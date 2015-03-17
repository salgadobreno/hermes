package com.avixy.qrtoken.core.extensions.components.templates;

import com.avixy.qrtoken.negocio.template.Text;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

/**
 * Created on 17/03/2015
 *
 * @author I7
 */
public class TextAlignmentSelect extends ComboBox<Text.Alignment> {
    public TextAlignmentSelect() {
        setItems(FXCollections.observableArrayList(Text.Alignment.LEFT, Text.Alignment.CENTER, Text.Alignment.RIGHT));
    }
}
