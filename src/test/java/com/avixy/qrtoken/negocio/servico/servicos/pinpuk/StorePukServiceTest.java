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
public class StorePukServiceTest {
    Injector injector = Guice.createInjector(new HermesModule());
    StorePukService service = injector.getInstance(StorePukService.class);

    @Test
    public void testServiceMsg() throws Exception {
        byte[] expectedMsg = {
                0b00110100, // '4'
                0b00110100, // '4'
                0b00110100, // '4'
                0b00110100, // '4'
                0b00100100, // '$'
        };
        service.setPuk("4444");
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(25, service.getServiceCode());
    }
}
