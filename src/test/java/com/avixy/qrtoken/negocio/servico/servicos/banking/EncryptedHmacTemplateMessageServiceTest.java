package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
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
    String expectedBinaryString;

    @Before
    public void setUp() throws Exception {
        expectedBinaryString =  "01010100000000001010100000110000" +     // expected_epoch gmt / timestamp
                "0011000100110010001100110011010000100100" + // PIN:1234%
                "0001" + // template 1
                "00101000" + // param
                "00110010"; // another param

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
        assertArrayEquals(new BinnaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testCrypto() throws Exception {
        service.getData();
        verify(aesKeyPolicy).apply(Matchers.<byte[]>anyObject());
        verify(hmacKeyPolicy).apply(Matchers.<byte[]>anyObject());
    }
}
