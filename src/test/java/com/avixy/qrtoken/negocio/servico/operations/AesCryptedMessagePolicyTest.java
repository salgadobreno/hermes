package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AesCryptedMessagePolicyTest {

    AesCryptedMessagePolicy aesCryptedMessagePolicy = new AesCryptedMessagePolicy();
    Service service;

    byte[] iv128;
    byte[] key128;
    byte[] result128;

    @Before
    public void setUp() throws Exception {
        iv128 = Hex.decodeHex("2FE2B333CEDA8F98F4A99B40D2CD34A8".toCharArray());
        final byte[] text128 = Hex.decodeHex("45CF12964FC824AB76616AE2F4BF0822".toCharArray());
        key128 = Hex.decodeHex("1F8E4973953F3FB0BD6B16662E9A3C17".toCharArray());
        result128 = Hex.decodeHex("0F61C4D44C5147C03C195AD7E2CC12B2".toCharArray());

        service = new Service() {
            @Override
            public String getServiceName() { return null; }
            @Override
            public int getServiceCode() { return 0; }
            @Override
            public byte[] run() throws Exception { return new byte[0]; }
            @Override
            public byte[] getMessage() {
                return text128;
            }
        };
    }

    @Test
    public void testGet() throws Exception {
        byte[] expectedOut = ArrayUtils.addAll(result128, iv128);

        aesCryptedMessagePolicy.setDoPadding(false);
        aesCryptedMessagePolicy.setIv(iv128);
        aesCryptedMessagePolicy.setKey(key128);
        assertArrayEquals(expectedOut, aesCryptedMessagePolicy.get(service));
    }
}