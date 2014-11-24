package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
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

public class TwoStepDoubleSymmetricKeyImportServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    TwoStepDoubleSymmetricKeyImportService service = new TwoStepDoubleSymmetricKeyImportService(headerPolicy, timestampPolicy, passwordPolicy);
    String expectedBinaryString = "";

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedBinaryString +=  "0100" + //template4
                "0011" + //keytype3
                "00011" + //keylength3
                "0010" + //keytype2
                "00100" + //keylength3
                "0111_0011_0110_0101_0110_1110_0110_1000_0110_0001_0011_0001_0111_0011_0110_0101_0110_1110_0110_1000_0110_0001_0011_0010" + //senha1senha2
                "1011_1100_0111_0110_" + // crc senha BC76
                "0011_1001_0011_1000_0011_0111_0011_1001"; //desafio 9879


        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setTemplate((byte)4);
        service.setKeyType1(KeyTypeParam.KeyType.RSA_ENCRYPTION);
        service.setKeyLength1(192);
        service.setKeyType2(KeyTypeParam.KeyType.SYMMETRIC_ENCRYPTION);
        service.setKeyLength2(224);
        service.setKeys("senha1senha2");
        service.setDesafio("9879");

        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(35, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(new BinnaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        verify(headerPolicy).getHeader(service);
        verify(passwordPolicy).get();
        verify(timestampPolicy).get();
    }
}