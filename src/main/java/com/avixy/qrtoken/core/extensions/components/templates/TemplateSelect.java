package com.avixy.qrtoken.core.extensions.components.templates;

import com.avixy.qrtoken.negocio.template.Template;
import com.avixy.qrtoken.negocio.template.TemplatesSingleton;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Combobox de seleção de Template
 * Created on 28/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplateSelect extends ComboBox<Integer> {
    //TODO: make ComboBox<Template>?
    public TemplateSelect() {
        List<Integer> templates = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        setItems(FXCollections.observableList(templates));
        getSelectionModel().selectFirst();

        setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer number) {
                return number + " - " + TemplatesSingleton.getInstance().getObservableTemplates().get(number).getName();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string.substring(0, 1));
            }
        });
    }

    public Template getTemplate(){
        return TemplatesSingleton.getInstance().getObservableTemplates().get(getValue());
    }
}
