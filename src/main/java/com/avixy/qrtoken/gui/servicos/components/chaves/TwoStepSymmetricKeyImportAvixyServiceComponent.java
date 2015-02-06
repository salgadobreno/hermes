package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.TwoStepSymmetricKeyImportAvixyService;
import com.google.inject.Inject;

/**
 * Created on 03/02/2015
 *
 * @author I7
 */
@ServiceComponent.Category(category = ServiceCategory.CHAVES)
public class TwoStepSymmetricKeyImportAvixyServiceComponent extends TwoStepSymmetricKeyImportServiceComponent {
    /**
     * @param service
     * @param qrCodePolicy
     */
    @Inject
    protected TwoStepSymmetricKeyImportAvixyServiceComponent(TwoStepSymmetricKeyImportAvixyService service) {
        super(service);
    }
}
