package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.TokenHuffmanEncoder;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PinPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.banking.LoginService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

public class LoginServiceTest {
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    PinPolicy pinPolicy = mock(PinPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = mock(AesCryptedMessagePolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);

    LoginService service = new LoginService(qrtHeaderPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, pinPolicy);

    String loginCode = "885471";

    @Before
    public void setUp() throws Exception {
        service.setTemplate((byte) 1);
        service.setPin("1234");
        service.setLoginCode(loginCode);
        service.setTimestamp(new Date(1409329200000L));
        when(aesCryptedMessagePolicy.get(service)).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(pinPolicy.get()).thenReturn(new byte[0]);
        when(qrtHeaderPolicy.getHeader(service)).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        //TODO
        String huffmanCode = new TokenHuffmanEncoder().encode(loginCode);
        String expectedBinaryString = "" +
//                "01010100000000001010100000110000" + //timestamp
                "0001" + //template 1
                "00000110" + //length 6
                huffmanCode; //codigo de login 885471
//                "0011000100110010001100110011010000000100"; //pin 1234 + length

        assertArrayEquals(new BinnaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testHeader() throws Exception {
        service.run();
        verify(qrtHeaderPolicy).getHeader(service);
    }

    @Test
    public void testOperations() throws Exception {

        service.run();
        verify(aesCryptedMessagePolicy).get(service);
        verify(timestampPolicy).get();
        verify(pinPolicy).get();
        verify(qrtHeaderPolicy).getHeader(service);
        verify(hmacKeyPolicy).apply(Mockito.<byte[]>anyObject());
    }
}