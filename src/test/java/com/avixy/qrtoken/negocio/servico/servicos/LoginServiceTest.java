package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.TokenHuffman;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;

public class LoginServiceTest {
    AesKeyPolicy aesKeyPolicy =  Mockito.mock(AesKeyPolicy.class);
    HmacKeyPolicy hmacKeyPolicy = Mockito.mock(HmacKeyPolicy.class);

    LoginService service = new LoginService(new QrtHeaderPolicy(), aesKeyPolicy, hmacKeyPolicy);

    String loginCode = "885471";

    @Before
    public void setUp() throws Exception {
        service.setTemplate((byte) 1);
        service.setPin("1234");
        service.setLoginCode(loginCode);
        service.setTimestamp(new Date(1409329200000L));
    }

    @Test
    public void testMessage() throws Exception {
        String huffmanCode = new TokenHuffman().encode(loginCode);
        String expectedBinaryString = "" +
                "01010100000000001010100000110000" + //timestamp
                "0001" + //template 1
                "0011000100110010001100110011010000100100" + //pin 1234$
                huffmanCode; //codigo de login 885471

        assertArrayEquals(new BinnaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testCrypto() throws Exception {
        service.getData();
        Mockito.verify(aesKeyPolicy).apply(Mockito.<byte[]>anyObject());
        Mockito.verify(hmacKeyPolicy).apply(Mockito.<byte[]>anyObject());
    }
}