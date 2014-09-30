package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 02/09/2014
 */
public class EncryptedHmacTemplateMessageServiceTest {
    AesKeyPolicy aesKeyPolicy = mock(AesKeyPolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);

    EncryptedHmacTemplateMessageService service = new EncryptedHmacTemplateMessageService(aesKeyPolicy, hmacKeyPolicy);
    byte[] expectedByteArray;

    @Before
    public void setUp() throws Exception {
        expectedByteArray = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000,     // expected_epoch gmt / timestamp
                0b00110001, // PIN:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00100100, // '$'
                0b00001_001, // template
                0b01000_001, // a param
                (byte) 0b10010000, // another param
        };

        Integer epoch = 1409329200;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("utc"));
        calendar.set(2014, Calendar.AUGUST, 29);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        service.setTimestamp(calendar.getTime());
        service.setPin("1234");
        service.setTemplate((byte) 1);
        service.setParams(new ByteWrapperParam((byte) 40), new ByteWrapperParam((byte) 50));
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(10, service.getServiceCode());
    }

    @Test
    public void testHmacTemplateMessage() throws Exception {
        assertArrayEquals(expectedByteArray, service.getMessage());
    }

    @Test
    public void testCrypto() throws Exception {
        service.getData();
        verify(aesKeyPolicy).apply(Matchers.<byte[]>anyObject());
        verify(hmacKeyPolicy).apply(Matchers.<byte[]>anyObject());
    }
}
