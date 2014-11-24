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
import static org.mockito.Mockito.*;

public class CreateAndExportSymKeyServiceTest {
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    CreateAndExportSymKeyService service = new CreateAndExportSymKeyService(qrtHeaderPolicy, timestampPolicy, passwordPolicy);

    String expectedBinnaryMsg;

    @Before
    public void setUp() throws Exception {
        Long epoch = 1409329200000L;
        expectedBinnaryMsg = "" +
//                "01010100" +
//                "00000000" +
//                "10101000" +
//                "00110000" +// timestamp
                "0011" + //keytype3
                "00011"; // 192bits
//                "00110001" +// '1' PIN
//                "00110010" +// '2'
//                "00110011" +// '3'
//                "00110100" +// '4'
//                "00000100"; // 4

        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setKeyType(KeyTypeParam.KeyType.RSA_ENCRYPTION);
        service.setKeyLength(192);

        when(qrtHeaderPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(38, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(new BinnaryMsg(expectedBinnaryMsg).toByteArray(), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        verify(qrtHeaderPolicy).getHeader(service);
        verify(passwordPolicy).get();
        verify(timestampPolicy).get();
    }
}