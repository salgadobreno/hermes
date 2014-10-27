package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.core.HermesModule;
import com.avixy.qrtoken.negocio.servico.servicos.ktamper.EraseKtamperService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class EraseKtamperServiceTest {
    Injector injector = Guice.createInjector(new HermesModule());
    EraseKtamperService service = injector.getInstance(EraseKtamperService.class);

    byte[] expectedOut;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000l;

        expectedOut = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000, // timestamp
                0b00110001, // '1' PIN
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00000100, // length 4
        };

        service.setTimestamp(new Date(epoch));

    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(21, service.getServiceCode());
    }

    @Test
    public void testServiceMessage() throws Exception {
//                0b00110100, // '4' PUK
//                0b00110100, // '4'
//                0b00110100, // '4'
//                0b00110100, // '4'
//                0b00100100, // '$'
        service.setPin("1234");

        assertArrayEquals(expectedOut, service.getMessage());
    }

    @Test
    public void testServiceMessageComPUK() throws Exception {
        /* "é obrigatório OU o PIN OU PUK" */
        expectedOut = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000, // timestamp
                0b00110100, // '4' PUK
                0b00110100, // '4'
                0b00110100, // '4'
                0b00110100, // '4'
                0b00000100, // length 4
        };

        service.setPuk("4444");
        assertArrayEquals(expectedOut, service.getMessage());
    }
}
