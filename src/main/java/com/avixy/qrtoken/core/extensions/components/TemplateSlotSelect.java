package com.avixy.qrtoken.core.extensions.components;

import com.avixy.qrtoken.negocio.Token;
import com.avixy.qrtoken.negocio.template.Template;
import com.avixy.qrtoken.negocio.template.TemplateSize;
import com.avixy.qrtoken.negocio.template.TemplatesSingleton;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

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
        setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer templateIndex) {
                TemplateSize templateSize = TemplateSize.getTemplateSizeFor(templateIndex);

                return templateIndex + " - " + templateSize.name().toLowerCase();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string.substring(1));
            }
        });
    }

    public Template getTemplate(){
        return TemplatesSingleton.getInstance().getObservableTemplates().get(getValue());
    }
}
