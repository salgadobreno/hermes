package com.avixy.qrtoken.negocio.servico.servicos.header;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.Token;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class QrtHeaderPolicyTest {
    HeaderPolicy headerPolicy = new QrtHeaderPolicy();
    Service mockService = Mockito.mock(Service.class);

    @Test
    public void testHeader() throws Exception {
        Mockito.when(mockService.getServiceCode()).thenReturn(ServiceCode.values()[10]);
        // 2 bytes de 'guarda' no início +
        // 1 byte da versão do protocolo
        // 8 bits seleção de serviço
        byte[] result = {0, 0, Token.PROTOCOL_VERSION,10};
        assertArrayEquals(headerPolicy.getHeader(mockService), result);
    }
}