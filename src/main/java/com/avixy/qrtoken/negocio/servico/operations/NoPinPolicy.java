package com.avixy.qrtoken.negocio.servico.operations;

public class NoPinPolicy extends PinPolicy {
    @Override
    public byte[] get() {
        return new byte[0];
    }

    @Override
    public void setPin(String pin) {
        throw new RuntimeException("NoPinPolicy shouldn't be set");
    }
}
