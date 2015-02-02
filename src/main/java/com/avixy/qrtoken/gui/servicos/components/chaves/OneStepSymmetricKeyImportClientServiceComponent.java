package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.OneStepSymmetricKeyImportClientService;
import com.google.inject.Inject;

/**
 * Created on 30/01/2015
 *
 * @author I7
 */
@ServiceComponent.Category(category = ServiceCategory.CHAVES)
public class OneStepSymmetricKeyImportClientServiceComponent extends OneStepSymmetricKeyImportServiceComponent {
    /**
     * @param service
     * @param qrCodePolicy
     */
    @Inject
    public OneStepSymmetricKeyImportClientServiceComponent(OneStepSymmetricKeyImportClientService service, QrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
    }
}
