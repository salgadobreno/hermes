package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.core.HermesModule;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 02/09/2014
 */
public class EncryptedTemplateMessageServiceTest {
    private EncryptedTemplateMessageService service = HermesModule.getInjector().getInstance(EncryptedTemplateMessageService.class);

    @Test
    public void testServiceCode() throws Exception { assertEquals(10, service.getServiceCode()); }

    @Test
    public void testHmacTemplateMessage() throws Exception {
        byte[] expectedByteArray = {
                0b01010100,
                0b00000000,
                (byte)0b10101000,
                0b00110000,     // expected_epoch gmt / timestamp
                '1', // PIN 1234
                '2',
                '3',
                '4',
                '$',
                1, // template
                40, // a param
                50, // another param
        };

        Integer epoch = 1409329200;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("utc"));
        calendar.set(2014, Calendar.AUGUST, 29);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        service.setDate(new TimestampParam(calendar.getTime()));
        service.setPin(new PinParam("1234"));
        service.setTemplate(new ByteWrapperParam((byte) 1));
        service.setParams(new ByteWrapperParam((byte) 40), new ByteWrapperParam((byte) 50));

        assertArrayEquals(expectedByteArray, service.getMessage());
    }
}
