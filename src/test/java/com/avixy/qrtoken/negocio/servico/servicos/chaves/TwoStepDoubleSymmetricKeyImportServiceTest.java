package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.TestHelper;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TwoStepDoubleSymmetricKeyImportServiceTest {
    TwoStepDoubleSymmetricKeyImportService service = new TwoStepDoubleSymmetricKeyImportService(TestHelper.getHeaderPolicy());
    String expectedBinaryString = "";

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedBinaryString += "0101_0100_0000_0000_1010_1000_0011_0000" + //epoch
                "0011_0001_0011_0010_0011_0011_0011_0100_0010_0100" + //pin 1234$
                "0100" + //template4
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
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(35, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(new BinnaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }
}