package com.avixy.qrtoken.gui.servicos.components.ktamper;

import com.avixy.qrtoken.gui.servicos.components.NoParamServiceComponent;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.ktamper.GenerateKtamperService;
import com.google.inject.Inject;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.KTAMPER)
public class GenerateKtamperServiceComponent extends NoParamServiceComponent {
    @Inject
    public GenerateKtamperServiceComponent(GenerateKtamperService service) {
        super(service);
    }
}
