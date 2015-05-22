package com.avixy.qrtoken.core.extensions.components;

import com.avixy.qrtoken.negocio.servico.chaves.ClientKeyConfiguration;
import javafx.scene.control.ComboBox;

/**
 * Created by Breno on 19/05/2015.
 */
public class ClientKeyProfileSelect extends ComboBox<ClientKeyConfiguration> {
    public ClientKeyProfileSelect() {
        setItems(ClientKeyConfiguration.getConfigList());
        getSelectionModel().selectFirst();
    }
}
