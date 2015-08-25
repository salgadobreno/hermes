package com.avixy.qrtoken.core;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * This class serves to simplify the process of instantiating {@link Service}s in a context outside Hermes
 *
 * Created by Breno on 20/08/2015.
 */
public class ServiceFactory {
    private static Injector injector = Guice.createInjector(new HermesModule());
    public static Service get(Class<? extends Service> service) {
        return injector.getInstance(service);
    }
}
