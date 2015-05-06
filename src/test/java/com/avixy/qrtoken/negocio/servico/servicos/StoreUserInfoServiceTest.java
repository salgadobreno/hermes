package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StoreUserInfoServiceTest {
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = mock(AesCryptedMessagePolicy.class);
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    StoreUserInfoService service = new StoreUserInfoService(headerPolicy, aesCryptedMessagePolicy, hmacKeyPolicy);

    String nome = "italo";
    String email = "italo@italo.com";
    String cpf = "53962087168";
    String cliente = "banco do brasil";
    String conta = "268232"; // deposite mil reais
    String agencia = "34754";
    String telefone = "6155558722";

    @Before
    public void setUp() throws Exception {
        service.setNome(nome);
        service.setEmail(email);
        service.setCpf(cpf);
        service.setConta(conta);
        service.setAgencia(agencia);
        service.setCliente(cliente);
        service.setTelefone(telefone);
        service.setHmacKey("hmac".getBytes());
        service.setAesKey("aes".getBytes());

        when(aesCryptedMessagePolicy.get(service)).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
    }

    @Test
    public void testGetMessage() throws Exception {
        String expectedBinaryString =
                "00000101_0110100101110100011000010110110001101111" + //5 italo
                "00001111_011010010111010001100001011011000110111101000000011010010111010001100001011011000110111100101110011000110110111101101101" + //15 email
                "00001011_0011010100110011001110010011011000110010001100000011100000110111001100010011011000111000" + //11 cpf
                "00001111_011000100110000101101110011000110110111100100000011001000110111100100000011000100111001001100001011100110110100101101100" + //15 cliente
                "00000101_0011001100110100001101110011010100110100" + //5 agencia
                "00000110_001100100011011000111000001100100011001100110010" + //6 conta
                "00001010_00110110001100010011010100110101001101010011010100111000001101110011001000110010"; //10 telefone

        assertArrayEquals(new BinaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        Mockito.verify(headerPolicy).getHeader(any(), any());
        Mockito.verify(aesCryptedMessagePolicy).get(service);
        Mockito.verify(hmacKeyPolicy).apply(Mockito.<byte[]>any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameLimit() throws Exception {
        String str = StringUtils.leftPad("x", 31, 'c');
        service.setNome(str);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCPFLimit() throws Exception {
        String str = StringUtils.leftPad("x", 21, 'c');
        service.setCpf(str);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmailLimit() throws Exception {
        String str = StringUtils.leftPad("x", 41, 'c');
        service.setEmail(str);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClienteLimit() throws Exception {
        String str = StringUtils.leftPad("x", 21, 'c');
        service.setCliente(str);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgenciaLimit() throws Exception {
        String str = StringUtils.leftPad("x", 11, 'c');
        service.setAgencia(str);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContaLimit() throws Exception {
        String str = StringUtils.leftPad("x", 11, 'c');
        service.setConta(str);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTelefoneLimit() throws Exception {
        String str = StringUtils.leftPad("x", 21, 'c');
        service.setTelefone(str);
    }
}