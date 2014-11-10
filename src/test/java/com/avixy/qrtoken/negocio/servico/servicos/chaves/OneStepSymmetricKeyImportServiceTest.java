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

public class OneStepSymmetricKeyImportServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    PinPolicy pinPolicy = mock(PinPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    OneStepSymmetricKeyImportService service = new OneStepSymmetricKeyImportService(headerPolicy, timestampPolicy, pinPolicy);
    String expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg =  "0100" +  //template4_
                "0011" +  //keytype3
                "00011" + //_keylength3
                "01110011" + // 's'
                "01100101" + // 'e'
                "01101110" + // 'n'
                "01101000" + // 'h'
                "01100001" + // 'a'
                "01111001" + // crc
                "10100001" + // crc-ccitt 'senha' -> 0x79A1 == 31137 http://www.lammertbies.nl/comm/info/crc-calculation.html
                "00110000" + // desafio 0372
                "00110011" +
                "00110111" +
                "00110010";

        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setTemplate((byte)4);
        service.setKeyComponent(KeyTypeParam.KeyType.RSA_ENCRYPTION, 192, "senha");
        service.setDesafio("0372");
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(BinnaryMsg.get(expectedMsg), service.getMessage());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(32, service.getServiceCode());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        verify(headerPolicy).getHeader(service);
        verify(pinPolicy).get();
        verify(timestampPolicy).get();

    }
}