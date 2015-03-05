package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;

public class AutorizarTransferenciaBancariaServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = mock(AesCryptedMessagePolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    AutorizarTransferenciaBancariaService service = new AutorizarTransferenciaBancariaService(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, passwordPolicy);

    int template = 1;
    String nome = "Ítalo Augusto";
    String agencia = "1234-5";
    String conta = "12345-X";

    String paraNome = "Augusto Ítalo";
    String paraAgencia = "4321-5";
    String paraConta = "34125-X";

    String data = "23/10/2014";
    String valor = "25.000,00";

    String expectedBinnaryString;
    {
        expectedBinnaryString = "0001" + // Template 0
                "00101011" + // Comprimento 43
                "01001100" + //Í
                "0110000" + //t
                "1000011" + //a
                "0111100" + //l
                "0111110" + //o
                "1110001011" +//
                "1110001010" +//A
                "0011110" + //u
                "0101000" + //g
                "0011110" + //u
                "0111101" + //s
                "0110000" + //t
                "0111110" + //o
                "1100011010" +// \n
                "1110001010" +//A
                "0101000" + //g
                "11000000" + //ê
                "1010100" + //n
                "1011100" + //c
                "1001111" + //i
                "1000011" + //a
                "11100000" + //:
                "1110001011" +//
                "0011001" + //1
                "0010000" + //2
                "0010111" + //3
                "0011000" + //4
                "0010011" + //-
                "0010001" + //5
                "1100011010" +// \n
                "11110100" + //Conta
                "11100000" + //:
                "0011001" + //1
                "0010000" + //2
                "0010111" + //3
                "0011000" + //4
                "0010001" + //5
                "0010011" + //-
                "0100010" + //X
                "00101011" + // Comprimento 43
                "1110001010" +//A
                "0011110" + //u
                "0101000" + //g
                "0011110" + //u
                "0111101" + //s
                "0110000" + //t
                "0111110" + //o
                "1110001011" +//
                "01001100" + //Í
                "0110000" + //t
                "1000011" + //a
                "0111100" + //l
                "0111110" + //o
                "1100011010" +// \n
                "1110001010" +//A
                "0101000" + //g
                "11000000" + //ê
                "1010100" + //n
                "1011100" + //c
                "1001111" + //i
                "1000011" + //a
                "11100000" + //:
                "1110001011" +//
                "0011000" + //4
                "0010111" + //3
                "0010000" + //2
                "0011001" + //1
                "0010011" + //-
                "0010001" + //5
                "1100011010" +// \n
                "11110100" + //Conta
                "11100000" + //:
                "0010111" + //3
                "0011000" + //4
                "0011001" + //1
                "0010000" + //2
                "0010001" + //5
                "0010011" + //-
                "0100010" + //X
                "00011110" + // Comprimento 30
                "0010000" + // 2
                "0010111" + // 3
                "11011001" + // /10
                "11010000" + // /2014
                "1100011010" +// \n
                "0001000" + // Valor
                "11100000" + // :
                "1110001011" +//
                "11110101" + // R$
                "0010000" + //2
                "0010001" + //5
                "1110001001" +//.
                "0110101" + //0
                "0110101" + //0
                "0110101" + //0
                "1110110001" +//,
                "0110101" + //0
                "0110101" +  //0
                //tan
                "00000100" + //comprimento 4
                "0011001" + //1
                "0010000" + //2
                "0010111" + //3
                "0011000"; //4
//                "0011000100110010001100110011010000000100"; //pin 1234
    }

    @Before
    public void setUp() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(2014, Calendar.OCTOBER, 23);
        //nome, conta, agencia de origem
        service.setTemplate((byte) template);
        service.setNomeOrigem(nome);
        service.setAgenciaOrigem(agencia);
        service.setContaOrigem(conta);
        //nome, conta, agencia de destino
        service.setNomeDestino(paraNome);
        service.setAgenciaDestino(paraAgencia);
        service.setContaDestino(paraConta);
        //valor, data, tan, timestamp
        service.setValor(valor);
        service.setData(date.getTime());
        service.setTan("1234");
        service.setTimestamp(new Date());
        //chaves, pin
        service.setPin("1234");

        when(aesCryptedMessagePolicy.get(service)).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        Assert.assertArrayEquals(new BinnaryMsg(expectedBinnaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(aesCryptedMessagePolicy).get(service);
        verify(headerPolicy).getHeader(service);
        verify(hmacKeyPolicy).apply(Mockito.<byte[]>any());
        verify(passwordPolicy).get();

    }
}