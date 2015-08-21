package com.avixy.qrtoken.gui.components;

import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import javafx.scene.control.ComboBox;

/**
 * HMAC {@link com.avixy.qrtoken.negocio.servico.chaves.Chave} <code>ComboBox</code>
 *
 * Created on 28/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HmacKeySelect extends ComboBox<Chave> {
    public HmacKeySelect() {
        setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.HMAC));
        getSelectionModel().selectFirst();
    }
}
