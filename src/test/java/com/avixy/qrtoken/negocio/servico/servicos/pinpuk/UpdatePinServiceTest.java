package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdatePinServiceTest {
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    UpdatePinService service = new UpdatePinService(qrtHeaderPolicy, timestampPolicy);
    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        Long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b00110100, // PIN antigo:'4'
                0b00110011, // '3'
                0b00110010, // '2'
                0b00110001, // '1'
                0b00000100, // length 4
                0b00110001, // PIN novo:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00000100, // length 4

        };

        service.setOldPin("4321");
        service.setNewPin("1234");
        service.setTimestamp(new Date(epoch));

        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(qrtHeaderPolicy.getHeader(service)).thenReturn(new byte[0]);
    }


    @Test
    public void testServiceCode() throws Exception {
        assertEquals(23, service.getServiceCode());
    }

    @Test
    public void testServiceMessage() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testOps() throws Exception {
        service.run();
        verify(qrtHeaderPolicy).getHeader(service);
        verify(timestampPolicy).get();
    }
}
