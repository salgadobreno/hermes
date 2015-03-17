package com.avixy.qrtoken.core.extensions.components.templates;

import com.avixy.qrtoken.negocio.template.Text;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

/**
 * Created on 17/03/2015
 *
 * @author I7
 */
public class TextSizeSelect extends ComboBox<Text.Size> {
    public TextSizeSelect() {
        setItems(FXCollections.observableArrayList(Text.Size.SMALL, Text.Size.LARGE, Text.Size.HUGE));
    }
}
