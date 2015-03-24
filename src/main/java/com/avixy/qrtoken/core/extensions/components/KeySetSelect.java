package com.avixy.qrtoken.core.extensions.components;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 1/15/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
 public class KeySetSelect extends ComboBox<Integer> {
    public KeySetSelect() {
        List<Integer> keySets = Arrays.asList(0, 1, 2, 3, 4);
        setItems(FXCollections.observableList(keySets));
        getSelectionModel().selectFirst();
    }
}
