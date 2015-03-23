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
public class TemplateSelect extends ComboBox<Template> {
    TemplatesSingleton templatesSingleton = TemplatesSingleton.getInstance();

    public TemplateSelect() {
        setItems(FXCollections.observableList(TemplatesSingleton.getInstance().getObservableTemplates()));
        getSelectionModel().selectFirst();

//        setConverter(new StringConverter<Template>() {
//            @Override
//            public String toString(Template template) {
//                return templatesSingleton.getObservableTemplates().indexOf(template) + " - " + template.getName();
//            }
//
//            @Override
//            public Template fromString(String string) {
//                return templatesSingleton.getObservableTemplates().get(Integer.parseInt(string.substring(0, 1)));
//            }
//        });
    }
}
