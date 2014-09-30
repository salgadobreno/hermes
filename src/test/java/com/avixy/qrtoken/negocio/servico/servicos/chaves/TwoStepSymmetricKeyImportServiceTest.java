package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TwoStepSymmetricKeyImportServiceTest {
    TwoStepSymmetricKeyImportService service = new TwoStepSymmetricKeyImportService(null);
    String expectedBinaryString = "";
    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedBinaryString += "0101_0100_0000_0000_1010_1000_0011_0000" + //epoch
                "0011_0001_0011_0010_0011_0011_0011_0100_0010_0100" + //pin 1234$
                "00100" + //template4
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
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(new BinnaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(34, service.getServiceCode());
    }

}