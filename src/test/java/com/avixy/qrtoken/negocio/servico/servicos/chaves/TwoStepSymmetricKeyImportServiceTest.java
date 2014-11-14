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
import static org.mockito.Mockito.when;

public class TwoStepSymmetricKeyImportServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    PinPolicy pinPolicy = mock(PinPolicy.class);
    TwoStepSymmetricKeyImportService service = new TwoStepSymmetricKeyImportService(headerPolicy, timestampPolicy, pinPolicy);
    String expectedBinaryString = "";

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedBinaryString +=  "0100" + //template4
                "0011" + //keytype3
                "00011" + //keylength3
                "0111_0011_0110_0101_0110_1110_0110_1000_0110_0001" + //senha
                "0111_1001_1010_0001" + // crc senha 79A1
                "0011_1001_0011_1000_0011_0111_0011_1001"; //desafio 9879


        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setTemplate((byte) 4);
        service.setKeyComponent(KeyTypeParam.KeyType.RSA_ENCRYPTION, 192, "senha");
        service.setDesafio("9879");

        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(pinPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(new BinnaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(34, service.getServiceCode());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        verify(headerPolicy).getHeader(service);
        verify(timestampPolicy).get();
        verify(pinPolicy).get();
    }
}