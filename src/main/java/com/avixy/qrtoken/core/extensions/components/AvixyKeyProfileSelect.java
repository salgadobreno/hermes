package com.avixy.qrtoken.core.extensions.components;

import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import javafx.scene.control.ComboBox;

/**
 * Created by Breno on 19/05/2015.
 */
public class AvixyKeyProfileSelect extends ComboBox<AvixyKeyConfiguration> {
    public AvixyKeyProfileSelect() {
        setItems(AvixyKeyConfiguration.getConfigList());
        getSelectionModel().selectFirst();
    }
}
