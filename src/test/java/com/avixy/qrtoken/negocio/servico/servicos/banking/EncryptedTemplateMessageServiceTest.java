package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class EncryptedTemplateMessageServiceTest {
    AesKeyPolicy aesKeyPolicy =  Mockito.mock(AesKeyPolicy.class);

    EncryptedTemplateMessageService service = new EncryptedTemplateMessageService(aesKeyPolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000, // expected_epoch gmt / timestamp
                0b00110001, // PIN:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00100100, // '$'
                0b00011_001, // template
                0b01000_001, // a param
                (byte) 0b10010000, // another param
        };
        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setTemplate((byte) 3);
        service.setParams(new ByteWrapperParam((byte) 40), new ByteWrapperParam((byte) 50));
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(12, service.getServiceCode());
    }

    @Test
    public void testServiceMsg() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testCrypto() throws Exception {
        service.getData();
        Mockito.verify(aesKeyPolicy).apply(Mockito.<byte[]>anyObject());
    }
}
