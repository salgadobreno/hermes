package com.avixy.qrtoken.core.extensions.components;

import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import javafx.scene.control.ComboBox;

/**
 * Created on 27/01/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ChaveSelect extends ComboBox<Chave> {

    public ChaveSelect(KeyType keyType) {
        if (keyType != null) {
            setItems(ChavesSingleton.getInstance().observableChavesFor(keyType));
        } else {
            setItems(ChavesSingleton.getInstance().getObservableChaves());
        }
        getSelectionModel().selectFirst();
    }

    public ChaveSelect() {
        this(null);
    }
}
