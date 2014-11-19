package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.core.HermesModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StorePinServiceTest {
    Injector injector = Guice.createInjector(new HermesModule());
    StorePinService service = injector.getInstance(StorePinService.class);

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(22, service.getServiceCode());
    }

    @Test
    public void testServiceMessage() throws Exception {
        byte[] expectedMsg = {
                0b00000100, // length 4
                0b00110001, // '1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
        };
        service.setPin("1234");

        assertArrayEquals(expectedMsg, service.getMessage());
    }
}
