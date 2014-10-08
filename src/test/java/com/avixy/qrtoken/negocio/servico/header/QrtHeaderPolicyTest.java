package com.avixy.qrtoken.negocio.servico.header;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import org.junit.Test;

import java.security.GeneralSecurityException;

import static org.junit.Assert.*;

public class QrtHeaderPolicyTest {
    HeaderPolicy headerPolicy = new QrtHeaderPolicy();
    Service mockService = new Service() {
        @Override
        public int getServiceCode() {
            return 10;
        }
        @Override
        public String getServiceName() { return null; }
        @Override
        public byte[] getData() throws GeneralSecurityException { return new byte[0]; }
        @Override
        public byte[] getMessage() { return new byte[0]; }
    };

    @Test
    public void testHeader() throws Exception {
        // 2 bytes de 'guarda' no início +
        // 8 bits seleção de serviço
        byte[] result = {0, 0, 10};
        assertArrayEquals(headerPolicy.getHeader(mockService), result);
    }
}