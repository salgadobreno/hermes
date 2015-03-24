package com.avixy.qrtoken.core.extensions.binary;

import com.avixy.qrtoken.negocio.servico.params.Param;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryMsgTest {
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
        assertArrayEquals(new byte[]{(byte) 0b11100000}, BinaryMsg.create().append(irregularParam).toByteArray());
        assertArrayEquals(new byte[]{(byte) 0b11111111, (byte) 0b10000000}, BinaryMsg.create().append(retardParam).toByteArray());
    }

    @Test
    public void testZeroesOnTheLeft() throws Exception {
        byte[] expectedResult = {0,0,0,0};
        String binnaryString = "00000000000000000000000000000000";
        assertArrayEquals(new BinaryMsg(binnaryString).toByteArray(), expectedResult);
    }
}