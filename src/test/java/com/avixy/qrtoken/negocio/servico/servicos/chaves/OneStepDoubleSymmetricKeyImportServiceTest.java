package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.operations.PinPolicy;
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

public class OneStepDoubleSymmetricKeyImportServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    PinPolicy pinPolicy = mock(PinPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    OneStepDoubleSymmetricKeyImportService service = new OneStepDoubleSymmetricKeyImportService(headerPolicy, timestampPolicy, pinPolicy);

    String expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg =  "0100" +//template4
                "0011" + //keytype3
                "00011" +//keylength3
                "0010" + //keytype2
                "00100" + //keylength4
                "011100110110010101101110011010000110000100110001011100110110010101101110011010000110000100110010" +
                "1011110001110110" + //crc 'senha1senha2' -> 0xBC76 == -17290
                "00111001001110000011011100111001"; //desafio 9879

        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setTemplate((byte)4);
        service.setKeyType1(KeyTypeParam.KeyType.RSA_ENCRYPTION);
        service.setKeyLength1(192);
        service.setKeyType2(KeyTypeParam.KeyType.SYMMETRIC_ENCRYPTION);
        service.setKeyLength2(224);
        service.setKeys("senha1senha2");
        service.setDesafio("9879");
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(33, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(BinnaryMsg.get(expectedMsg), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        verify(headerPolicy).getHeader(service);
        verify(pinPolicy).get();
        verify(timestampPolicy).get();
    }
}