package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

/**
* Created on 30/01/2015
*
* @author Breno Salgado <breno.salgado@avixy.com>
*/
public class ImportAvixySymKeySetService extends AbstractImportSymKeySetService {

    @Inject
    public ImportAvixySymKeySetService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy) {
        super(headerPolicy, timestampPolicy);
    }

    @Override
    public String getServiceName() {
        return "Importar chave simétrica - Avixy";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_IMPORT_AVIXY_SYM_KEYSET;
    }
}
