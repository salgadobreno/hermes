package com.avixy.qrtoken.negocio.servico.servicos.password;

import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdatePukServiceTest {
    QrtHeaderPolicy headerPolicy = Mockito.mock(QrtHeaderPolicy.class);
    PasswordPolicy passwordPolicy = Mockito.mock(PasswordPolicy.class);
    TimestampPolicy timestampPolicy = Mockito.mock(TimestampPolicy.class);
    UpdatePukService service = new UpdatePukService(headerPolicy, timestampPolicy, passwordPolicy);

    @Before
    public void setUp() throws Exception {
        Long epoch = 1409329200000L;
        service.setPuk("4444");
        service.setNewPuk("3333");
        service.setTimestamp(new Date(epoch));
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testServiceMsg() throws Exception {
        byte[] expectedMsg = {
                0b00000100, // length 4
                0b00110011, // PUK novo:'3'
                0b00110011, // '3'
                0b00110011, // '3'
                0b00110011, // '3'
        };

        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        Mockito.verify(headerPolicy).getHeader(service);
        Mockito.verify(timestampPolicy).get();
        Mockito.verify(passwordPolicy).get();
    }
}
