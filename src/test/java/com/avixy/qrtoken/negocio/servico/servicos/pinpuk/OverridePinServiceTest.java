package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.core.HermesModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class OverridePinServiceTest {
    Injector injector = Guice.createInjector(new HermesModule());
    OverridePinService service = injector.getInstance(OverridePinService.class);

    @Test
    public void testServiceMsg() throws Exception {
        long epoch = 1409329200000L;
        byte[] expectedMsg = {
                0b01010100,
                0b00000000,
                (byte)0b10101000,
                0b00110000,     // timestamp
                0b00110100, // PUK:'4'
                0b00110100, // '4'
                0b00110100, // '4'
                0b00110100, // '4'
                0b00000100, // length 4
                0b00110001, // PIN:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00000100, // length 4
        };
        service.setPin("1234");
        service.setPuk("4444");
        service.setTimestamp(new Date(epoch));

        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(24, service.getServiceCode());
    }
}
