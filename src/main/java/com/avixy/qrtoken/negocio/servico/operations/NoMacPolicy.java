package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;

import java.security.GeneralSecurityException;

public class NoMacPolicy extends HmacKeyPolicy {
    @Override
    public byte[] apply(byte[] msg) {
        return msg;
    }

    @Override
    public void setKey(byte[] key) {
        throw new RuntimeException("NoMacPolicy shouldn't be set");
    }
}
