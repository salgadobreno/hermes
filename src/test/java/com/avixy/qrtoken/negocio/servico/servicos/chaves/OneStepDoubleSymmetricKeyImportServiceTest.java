package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.TestHelper;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OneStepDoubleSymmetricKeyImportServiceTest {
    OneStepDoubleSymmetricKeyImportService service = new OneStepDoubleSymmetricKeyImportService(TestHelper.getHeaderPolicy());

    String expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
//        expectedMsg = new byte[]{
//                /* 'shiftando' os bits de cada parâmetro manualmente */
//                0b01010100,
//                0b00000000,
//                (byte) 0b10101000,
//                0b00110000, // expected_epoch gmt / timestamp
//                0b00110001, // PIN:'1'
//                0b00110010, // '2'
//                0b00110011, // '3'
//                0b00110100, // '4'
//                0b00100100, // length 4
//                0b0100_0011,  //template4_
//                (byte) 0b00011_001, //keytype3_keylength3_keytype2
//                (byte) 0b0_00100_01, //keylength4
//                (byte) 0b11_001101, //senha1senha2
//                (byte) 0b10_010101,
//                (byte) 0b10_111001,
//                (byte) 0b10_100001,
//                (byte) 0b10_000100,
//                (byte) 0b11_000101,
//                (byte) 0b11_001101,
//                (byte) 0b10_010101,
//                (byte) 0b10_111001,
//                (byte) 0b10_100001,
//                (byte) 0b10_000100,
//                (byte) 0b11_001010,
//                (byte) 0b11_110001, //crc 'senha1senha2' -> 0xBC76 == -17290
//                (byte) 0b11_011000,
//                (byte) 0b11_100100, //desafio 9879
//                (byte) 0b11_100000,
//                (byte) 0b11_011100,
//                (byte) 0b11_100100
//        };
        expectedMsg = "01010100" +
                "00000000" +
                "10101000" +
                "00110000" + // expected_epoch gmt / timestamp
                "0100" +//template4
                "0011" + //keytype3
                "00011" +//keylength3
                "0010" + //keytype2
                "00100" + //keylength4
                "011100110110010101101110011010000110000100110001011100110110010101101110011010000110000100110010" +
                "1011110001110110" + //crc 'senha1senha2' -> 0xBC76 == -17290
                "00111001001110000011011100111001" + //desafio 9879
                "00110001" + // PIN:'1'
                "00110010" + // '2'
                "00110011" + // '3'
                "00110100" + // '4'
                "00000100"; // length 4

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
        assertArrayEquals(BinnaryMsg.get(expectedMsg), service.getMessage());
    }
}