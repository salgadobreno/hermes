package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.servico.servicos.PingService;
import com.google.inject.Inject;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
@ServiceComponent.Category(category = ServiceCategory.OUTROS)
public class PingServiceComponent extends NoParamServiceComponent {

    @Inject
    public PingServiceComponent(PingService service) {
        super(service);
    }

}
