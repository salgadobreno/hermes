package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import com.avixy.qrtoken.core.extensions.binary.CRC16CCITT;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created on 24/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class Crc16Test {
    @Test
    public void testCrc16() throws Exception {
        String input = "123456789";
        int expectedCrc = 0x29B1;

        int crc = CRC16CCITT.calc(input.getBytes());

        assertEquals(expectedCrc, crc);
    }
}
