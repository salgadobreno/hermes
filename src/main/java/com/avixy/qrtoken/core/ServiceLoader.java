package com.avixy.qrtoken.core;

import com.avixy.qrtoken.gui.servicos.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.ServiceComponent;
import org.reflections.Reflections;

import java.util.*;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 12/08/2014
 */
public class ServiceLoader {
    /* TODO: mudar static p/ object? */
    private ServiceLoader(){}

    private static Reflections reflections = new Reflections("com.avixy.qrtoken.gui.servicos");

    /**
     * Encontra por reflection os subtipos de {@link ServiceComponent}
     *
     * @return Mapa dos subtipos de {@link ServiceComponent}, mapeados por {@link ServiceComponent.Category}. Subtipos sem definição de <code>ServiceCategory</code> são ignorados.
     */
    public static Map<ServiceCategory, List<Class<? extends ServiceComponent>>> getServiceComponentMap(){
        Set<Class<? extends ServiceComponent>> classes = reflections.getSubTypesOf(ServiceComponent.class);
        Map<ServiceCategory, List<Class<? extends ServiceComponent>>> categoryListMap = new LinkedHashMap<>();

        for (ServiceCategory category : ServiceCategory.values()) {
            categoryListMap.put(category, new ArrayList<Class<? extends ServiceComponent>>());

            for (Class<? extends ServiceComponent> component : classes) {
                // Se o ServiceComponent não tiver a anotação ServiceCategory, não é posto na lista
                ServiceComponent.Category componentAnnotation = component.getAnnotation(ServiceComponent.Category.class);
                if (componentAnnotation != null) {
                    if (component.getAnnotation(ServiceComponent.Category.class).category() == category) {
                        categoryListMap.get(category).add(component);
                    }
                }
            }
        }
        return categoryListMap;
    }
}