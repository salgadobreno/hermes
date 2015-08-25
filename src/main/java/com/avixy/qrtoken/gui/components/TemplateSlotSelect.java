package com.avixy.qrtoken.gui.components;

import com.avixy.qrtoken.negocio.Token;
import com.avixy.qrtoken.negocio.template.Template;
import com.avixy.qrtoken.negocio.template.TemplatesSingleton;
import javafx.scene.control.ComboBox;

/**
 * Created on 19/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplateSlotSelect extends ComboBox<Integer> {
    public TemplateSlotSelect() {
        for (int i = 0; i < Token.TEMPLATE_QTY; i++) {
            getItems().add(i);
        }
        getSelectionModel().select(0);
    }

    public Template getTemplate(){
        return TemplatesSingleton.getInstance().getObservableTemplates().get(getValue());
    }
}
