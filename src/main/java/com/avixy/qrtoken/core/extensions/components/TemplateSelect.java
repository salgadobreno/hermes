package com.avixy.qrtoken.core.extensions.components;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 28/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplateSelect extends ComboBox<Integer> {
    public TemplateSelect() {
        List<Integer> templates = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 11, 11);
        setItems(FXCollections.observableList(templates));
        getSelectionModel().selectFirst();
    }
}
