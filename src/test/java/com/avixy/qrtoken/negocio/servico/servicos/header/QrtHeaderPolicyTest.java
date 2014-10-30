package com.avixy.qrtoken.negocio.servico.servicos.header;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.GeneralSecurityException;

import static org.junit.Assert.*;

public class QrtHeaderPolicyTest {
    HeaderPolicy headerPolicy = new QrtHeaderPolicy();
    Service mockService = Mockito.mock(Service.class);

    public void testHeader() throws Exception {
        // 2 bytes de 'guarda' no início +
        // 8 bits seleção de serviço
        byte[] result = {0, 0, 10};
        assertArrayEquals(headerPolicy.getHeader(mockService), result);
    }
}