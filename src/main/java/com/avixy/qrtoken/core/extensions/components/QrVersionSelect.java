package com.avixy.qrtoken.core.extensions.components;

import com.google.zxing.qrcode.decoder.Version;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

/**
 * QR Version <code>ComboBox</code> selection field
 *
 * Created on 28/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class QrVersionSelect extends ComboBox<Version> {
    public QrVersionSelect() {
        /* Seta os niveis de QR no combo */
        List<Version> levels = new ArrayList<>();
        for (int i = 2; i <= 40; i++) {
            levels.add(Version.getVersionForNumber(i));
        }
        ObservableList<Version> list = FXCollections.observableList(levels);
        setItems(list);
        getSelectionModel().selectFirst();
    }
}
