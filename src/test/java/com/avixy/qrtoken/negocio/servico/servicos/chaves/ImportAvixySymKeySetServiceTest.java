package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created on 24/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ImportAvixySymKeySetServiceTest {
    HeaderPolicy headerPolicy = mock(HeaderPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    ImportAvixySymKeySetService service = new ImportAvixySymKeySetService(headerPolicy, timestampPolicy);

    @Before
    public void setUp() throws Exception {
        service.setSecrecyKey(new byte[32]);
        service.setAuthKey(new byte[32]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        String expectedMessage = "" +
                "00101" + //keylength
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" + //aes key;
                "1111000101001100" + //crc 0x5AC6
                "00101" + //keylength
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" +
                "00000000000000000000000000000000" + //hmac key;
                "1111000101001100"; //crc 0x5AC6

        assertArrayEquals(BinaryMsg.get(expectedMessage), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(timestampPolicy).get();
        verify(headerPolicy).getHeader(any(), any());
    }
}