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
    byte[] getHeader(Service service, @Nullable ServiceCode serviceCode);
}
