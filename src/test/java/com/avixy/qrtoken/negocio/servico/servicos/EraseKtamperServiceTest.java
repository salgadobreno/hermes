package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.HermesModule;
import com.avixy.qrtoken.negocio.servico.servicos.EraseKtamperService;
import com.google.inject.Guice;
import com.google.inject.Injector;
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
    private EraseKtamperService service = injector.getInstance(EraseKtamperService.class);

    @Test
    public void testServiceCode() throws Exception {
        int expectedCode = 21;
        assertEquals(expectedCode, service.getServiceCode());
    }

    @Test
    public void testServiceMessage() throws Exception {
        long epoch = 1409329200000l;

        byte[] expectedOut = {
                0b01010100,
                0b00000000,
                (byte)0b10101000,
                0b00110000,     // expected_epoch gmt / timestamp
                0b00110001, // PIN:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00100100, // '$'
//                '1',
//                '2',
//                '3',
//                '4',
//                '$' // pin/puk
        };

        service.setPin("1234");
        service.setTimestamp(new Date(epoch));

        assertArrayEquals(expectedOut, service.getMessage());
    }
}
