package com.avixy.qrtoken.core;

import com.avixy.qrtoken.gui.servicos.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.ServiceCategory;
import org.reflections.Reflections;

import java.util.*;

/**
 *
 * Created on 12/08/2014
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ServiceLoader {
    private ServiceLoader(){}

    private static Reflections reflections = new Reflections("com.avixy.qrtoken.gui.servicos");

    /**
     * Encontra por reflection os subtipos de <code>ServiceComponent</code>
     *
     * @return Mapa dos subtipoes de <code>ServiceComponent</code>, mapeados por <code>ServiceComponent.Category</code>.
     * subtipos sem annotation de <code>ServiceCategory</code> são ignorados.
     */
    public static Map<ServiceComponent.Category, List<Class<? extends ServiceComponent>>> getServiceComponentMap(){
        Set<Class<? extends ServiceComponent>> classes = reflections.getSubTypesOf(ServiceComponent.class);
        Map<ServiceComponent.Category, List<Class<? extends ServiceComponent>>> categoryListMap = new LinkedHashMap<>();

        for (ServiceComponent.Category category : ServiceComponent.Category.values()) {
            categoryListMap.put(category, new ArrayList<Class<? extends ServiceComponent>>());

            for (Class<? extends ServiceComponent> component : classes) {
                // Se o ServiceComponent não tiver a anotação ServiceCategory, não é posto na lista
                ServiceCategory componentAnnotation = component.getAnnotation(ServiceCategory.class);
                if (componentAnnotation != null) {
                    if (component.getAnnotation(ServiceCategory.class).category() == category) {
                        categoryListMap.get(category).add(component);
                    }
                }
            }
        }
        return categoryListMap;
    }
}
