package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
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
                "0010" + //keytype2
                "00011"; // 192bits
//                "00110001" +// '1' PIN
//                "00110010" +// '2'
//                "00110011" +// '3'
//                "00110100" +// '4'
//                "00000100"; // 4

        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setKeyType(KeyType.HMAC);
        service.setKeyLength(24);

        when(qrtHeaderPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(new BinaryMsg(expectedBinnaryMsg).toByteArray(), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(qrtHeaderPolicy).getHeader(service);
        verify(passwordPolicy).get();
        verify(timestampPolicy).get();
    }
}