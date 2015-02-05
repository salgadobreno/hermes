package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.DeleteSymKeyClientService;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.DeleteSymKeyService;
import com.google.inject.Inject;

/**
 * Created on 30/01/2015
 *
 * @author I7
 */
@ServiceComponent.Category(category = ServiceCategory.CHAVES)
public class DeleteKeySetClientServiceComponent extends DeleteKeySetServiceComponent {
    @Inject
    public DeleteKeySetClientServiceComponent(DeleteSymKeyClientService service) {
        super(service);
    }
}
