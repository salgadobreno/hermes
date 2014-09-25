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
public class UpdatePinServiceTest {
    Injector injector = Guice.createInjector(new HermesModule());
    UpdatePinService service = injector.getInstance(UpdatePinService.class);

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(23, service.getServiceCode());
    }

    @Test
    public void testServiceMessage() throws Exception {
        Long epoch = 1409329200000L;
        byte[] expectedMsg = {
                0b01010100,
                0b00000000,
                (byte)0b10101000,
                0b00110000,     // expected_epoch gmt / timestamp
                0b00110100, // PIN antigo:'4'
                0b00110011, // '3'
                0b00110010, // '2'
                0b00110001, // '1'
                0b00100100, // '$'
                0b00110001, // PIN novo:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00100100, // '$'

        };

        service.setOldPin("4321");
        service.setNewPin("1234");
        service.setTimestamp(new Date(epoch));

        assertArrayEquals(expectedMsg, service.getMessage());
    }
}
