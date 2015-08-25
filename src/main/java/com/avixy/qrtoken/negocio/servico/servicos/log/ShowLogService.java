package com.avixy.qrtoken.negocio.servico.servicos.log;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ShowLogService extends AbstractService {

    @Inject
    public ShowLogService(HeaderPolicy headerPolicy) {
        super(headerPolicy);
    }

    @Override
    public String getServiceName() {
        return "SERVICE_SHOW_LOG";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_SHOW_LOG;
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }

}
