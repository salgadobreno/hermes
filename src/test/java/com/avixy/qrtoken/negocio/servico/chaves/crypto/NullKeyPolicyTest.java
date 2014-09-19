package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import org.junit.Test;

import static org.junit.Assert.*;

public class NullKeyPolicyTest {
    private NullKeyPolicy nullKeyPolicy = new NullKeyPolicy();

    @Test
    public void testApply() throws Exception {
        // Won't do anything
        String msg = "nonsequiturnonsense";
        assertArrayEquals(msg.getBytes(), nullKeyPolicy.apply(msg.getBytes()));
    }
}