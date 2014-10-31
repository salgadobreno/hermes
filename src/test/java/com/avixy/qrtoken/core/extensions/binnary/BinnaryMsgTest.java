package com.avixy.qrtoken.core.extensions.binnary;

import com.avixy.qrtoken.negocio.servico.params.Param;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import javax.naming.BinaryRefAddr;

import static org.junit.Assert.*;

public class BinnaryMsgTest {
    Param irregularParam = new Param() {
        @Override
        public String toBinaryString() {
            return "111"; //3 in length..
        }
    };

    Param retardParam = new Param() {
        @Override
        public String toBinaryString() {
            return "111111111"; //9 in length...
        }
    };

    @Test
    public void testToByteArrayCompletesTheCodeWord() throws Exception {
        assertArrayEquals(new byte[]{(byte) 0b11100000}, BinnaryMsg.create().append(irregularParam).toByteArray());
        assertArrayEquals(new byte[]{(byte) 0b11111111, (byte) 0b10000000}, BinnaryMsg.create().append(retardParam).toByteArray());
    }

    @Test
    public void testZeroesOnTheLeft() throws Exception {
        byte[] expectedResult = {0,0,0,0};
        String binnaryString = "00000000000000000000000000000000";
        assertArrayEquals(new BinnaryMsg(binnaryString).toByteArray(), expectedResult);
    }
}