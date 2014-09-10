package com.avixy.qrtoken.negocio.servico;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created on 03/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class PingServiceTest {

    PingService pingService = new PingService(null);

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(1, pingService.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        byte[] expectedBytes = {
                0b00000001
        };

        assertArrayEquals(expectedBytes, pingService.getMessage());
    }
}
