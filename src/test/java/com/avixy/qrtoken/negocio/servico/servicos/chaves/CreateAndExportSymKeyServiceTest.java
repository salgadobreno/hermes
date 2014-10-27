package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.HermesModule;
import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CreateAndExportSymKeyServiceTest {
    CreateAndExportSymKeyService service = HermesModule.getInjector().getInstance(CreateAndExportSymKeyService.class);

    String expectedBinnaryMsg;

    @Before
    public void setUp() throws Exception {
        Long epoch = 1409329200000L;
        expectedBinnaryMsg = "" +
                "01010100" +
                "00000000" +
                "10101000" +
                "00110000" +// timestamp
                "0011" + //keytype3
                "00011" + // 192bits
                "00110001" +// '1' PIN
                "00110010" +// '2'
                "00110011" +// '3'
                "00110100" +// '4'
                "00000100"; // 4

        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setKeyType(KeyTypeParam.KeyType.RSA_ENCRYPTION);
        service.setKeyLength(192);
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(38, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(new BinnaryMsg(expectedBinnaryMsg).toByteArray(), service.getMessage());
    }
}