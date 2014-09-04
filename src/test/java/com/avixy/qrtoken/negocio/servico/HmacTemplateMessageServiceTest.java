package com.avixy.qrtoken.negocio.servico;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created on 02/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HmacTemplateMessageServiceTest {
    private HmacTemplateMessageService service = new HmacTemplateMessageService();

    @Test @Ignore
    public void testHmacTemplateMessage() throws Exception {
        byte[] expectedByteArray = {
                0b01010100,
                0b00000000,
                (byte)0b10101000,
                0b00110000,     // expected_epoch gmt
                '1', // PIN 1234
                '2',
                '3',
                '4',
                '$'
        };
    }
}
