package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class StoreUserInfoServiceTest {
    HmacKeyPolicy hmacKeyPolicy = Mockito.mock(HmacKeyPolicy.class);
    AesKeyPolicy aesKeyPolicy = Mockito.mock(AesKeyPolicy.class);
    StoreUserInfoService service = new StoreUserInfoService(aesKeyPolicy, hmacKeyPolicy);

    String nome = "italo";
    String email = "italo@italo.com";
    String cpf = "1234";
    String conta = "222";
    String agencia = "111";
    String cliente = "banco";
    String telefone = "123456";

    @Before
    public void setUp() throws Exception {
        service.setNome(nome);
        service.setEmail(email);
        service.setCpf(cpf);
        service.setConta(conta);
        service.setAgencia(agencia);
        service.setCliente(cliente);
        service.setTelefone(telefone);
        service.setTemplate(1);
        service.setHmacKey("hmac".getBytes());
        service.setAesKey("aes".getBytes());
    }

    @Test
    public void testGetServiceCode() throws Exception {
        assertEquals(2, service.getServiceCode());
    }

    @Test
    public void testGetMessage() throws Exception {
        String expectedBinaryString = "0001" + //template 1
                "0000_0101_0110100101110100011000010110110001101111" + //nome
                "00001111011010010111010001100001011011000110111101000000011010010111010001100001011011000110111100101110011000110110111101101101" + // email
                "0000010000110001001100100011001100110100" + // cpf
                "00000011001100100011001000110010" + // conta
                "00000011001100010011000100110001" + // agencia
                "000001010110001001100001011011100110001101101111" + // cliente
                "00000110001100010011001000110011001101000011010100110110"; //telefone

        assertArrayEquals(new BinnaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testCrypto() throws Exception {
        service.getData();
        Mockito.verify(aesKeyPolicy).apply(Mockito.<byte[]>any());
        Mockito.verify(hmacKeyPolicy).apply(Mockito.<byte[]>any());
    }
}