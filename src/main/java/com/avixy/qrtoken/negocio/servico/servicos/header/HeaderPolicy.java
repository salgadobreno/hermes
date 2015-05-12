package com.avixy.qrtoken.negocio.servico.servicos.header;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.servicos.Service;

import javax.annotation.Nullable;

/**
 * Encapsula a lógica de gerar um header
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public interface HeaderPolicy {
    /**
     * @param service
     * @param serviceCode Optionally pass a {@link ServiceCode} to override the {@link ServiceCode} in the {@link Service}
     * @return header for the <code>service</code>
     */
    byte[] getHeader(Service service, @Nullable ServiceCode serviceCode);
}
