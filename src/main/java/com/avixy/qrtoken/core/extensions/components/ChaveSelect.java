package com.avixy.qrtoken.core.extensions.components;

import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import javafx.scene.control.ComboBox;

/**
 * Created on 27/01/2015
 *
 * @author I7
 */
public class ChaveSelect extends ComboBox<Chave> {
    public ChaveSelect() {
        setItems(ChavesSingleton.getInstance().getObservableChaves());
        getSelectionModel().selectFirst();
    }
}
