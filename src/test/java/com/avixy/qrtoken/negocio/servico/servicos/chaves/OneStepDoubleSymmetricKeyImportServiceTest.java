package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OneStepDoubleSymmetricKeyImportServiceTest {
    OneStepDoubleSymmetricKeyImportService service = new OneStepDoubleSymmetricKeyImportService();

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                /* 'shiftando' os bits de cada parâmetro manualmente */
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000, // expected_epoch gmt / timestamp
                0b00110001, // PIN:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00100100, // '$'
                0b0100_0011,  //template4_
                (byte) 0b00011_001, //keytype3_keylength3_keytype2
                (byte) 0b0_00100_01, //keylength4
                (byte) 0b11_001101, //senha1senha2
                (byte) 0b10_010101,
                (byte) 0b10_111001,
                (byte) 0b10_100001,
                (byte) 0b10_000100,
                (byte) 0b11_000101,
                (byte) 0b11_001101,
                (byte) 0b10_010101,
                (byte) 0b10_111001,
                (byte) 0b10_100001,
                (byte) 0b10_000100,
                (byte) 0b11_001010,
                (byte) 0b11_110001, //crc 'senha1senha2' -> 0xBC76 == -17290
                (byte) 0b11_011000,
                (byte) 0b11_100100, //desafio 9879
                (byte) 0b11_100000,
                (byte) 0b11_011100,
                (byte) 0b11_100100
        };
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
        assertEquals(33, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }
}