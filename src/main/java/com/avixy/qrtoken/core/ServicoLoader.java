package com.avixy.qrtoken.core;

import com.avixy.qrtoken.gui.servicos.ServicoComponent;
import com.avixy.qrtoken.negocio.servico.ServicoCategory;
import org.reflections.Reflections;

import java.util.*;

/**
 * Created on 12/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ServicoLoader {
    private ServicoLoader(){}

    private static Reflections reflections = new Reflections("com.avixy.qrtoken.gui.servicos");

    public static Map<ServicoComponent.Category, List<Class<? extends ServicoComponent>>> getListServicos(){
        Set<Class<? extends ServicoComponent>> classes = reflections.getSubTypesOf(ServicoComponent.class);
        Map<ServicoComponent.Category, List<Class<? extends ServicoComponent>>> categoryListMap = new HashMap<>();
        for (ServicoComponent.Category category : ServicoComponent.Category.values()) {
            categoryListMap.put(category, new ArrayList<Class<? extends ServicoComponent>>());

            for (Class<? extends ServicoComponent> component : classes) {
                if (component.getAnnotation(ServicoCategory.class).category() == category) { //TODO: meio fragil
                    categoryListMap.get(category).add(component);
                }
            }
        }
        return categoryListMap;
    }
}
