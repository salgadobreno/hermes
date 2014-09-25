package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.HermesModule;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CreateAndExportSymKeyServiceTest {
    CreateAndExportSymKeyService service = HermesModule.getInjector().getInstance(CreateAndExportSymKeyService.class);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        Long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000,     // timestamp
                0b00110001, // '1' PIN
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00100100, // '$'
                0b0011_0001, // keytype3_192bits
                (byte) 0b1_0000000 //192bits_0000000(+padding)
        };

        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setKeyType(KeyTypeParam.KeyType.RSA_ENCRYPTION);
        service.setKeyLength(192);
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(38, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }
}