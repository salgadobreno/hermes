package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.core.HermesModule;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class OverridePinServiceTest {
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    OverridePinService service = new OverridePinService(qrtHeaderPolicy, timestampPolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
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

        when(qrtHeaderPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testServiceMsg() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(24, service.getServiceCode());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        verify(qrtHeaderPolicy).getHeader(service);
        verify(timestampPolicy).get();
    }
}
