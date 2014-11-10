package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.*;

public class UpdateSymmetricKeyServiceTest {
    SettableTimestampPolicy timestampPolicy = Mockito.mock(SettableTimestampPolicy.class);
    QrtHeaderPolicy headerPolicy = Mockito.mock(QrtHeaderPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = Mockito.mock(AesCryptedMessagePolicy.class);
    UpdateSymmetricKeyService service = new UpdateSymmetricKeyService(headerPolicy, timestampPolicy, aesCryptedMessagePolicy);
    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
//                0b01010100,
//                0b00000000,
//                (byte) 0b10101000,
//                0b00110000,     // timestamp
                0b0011_0001, //template3_
                (byte) 0b01111010, //keytype1
                (byte) 0b01111000, //key: "zxcv"
                (byte) 0b01100011,
                (byte) 0b01110110,
        };

        service.setTimestamp(new Date(epoch));
        service.setTemplate((byte) 3);
        service.setKeyType(KeyTypeParam.KeyType.TDES);
        service.setKey("zxcv");
        service.setAesKey("bla".getBytes());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(37, service.getServiceCode());
    }

    @Test
    public void testMsg() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        Mockito.verify(aesCryptedMessagePolicy).get(service);
        Mockito.verify(timestampPolicy).get();
        Mockito.verify(headerPolicy).getHeader(service);
    }
}