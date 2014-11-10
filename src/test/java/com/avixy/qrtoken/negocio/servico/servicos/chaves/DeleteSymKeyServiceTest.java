package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeleteSymKeyServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = mock(AesCryptedMessagePolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    DeleteSymKeyService service = new DeleteSymKeyService(headerPolicy, timestampPolicy, aesCryptedMessagePolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
//                0b01010100,
//                0b00000000,
//                (byte) 0b10101000,
//                0b00110000,     //timestamp
                0b0011_0001, //template_
        };

        service.setTimestamp(new Date(epoch));
        service.setTemplate((byte) 3);
        service.setKeyType(KeyTypeParam.KeyType.TDES);
        service.setAesKey("bla".getBytes());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(36, service.getServiceCode());
    }

    @Test
    public void testServiceMsg() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testOps() throws Exception {
        service.run();
        verify(aesCryptedMessagePolicy).get(service);
        verify(timestampPolicy).get();
        verify(headerPolicy).getHeader(service);
    }
}