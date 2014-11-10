package com.avixy.qrtoken.core.extensions.components;

import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import javafx.scene.control.ComboBox;

/**
 * Combobox de seleção de chave p/ HMAC
 * Created on 28/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HmacSelect extends ComboBox<Chave> {
    public HmacSelect() {
        setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.HMAC));
        getSelectionModel().selectFirst();
    }
}
