package com.avixy.qrtoken.negocio.servico.operations;

public class NoPasswordPolicy extends PasswordPolicy {
    @Override
    public byte[] get() {
        return new byte[0];
    }

    @Override
    public void setPassword(String password) {
        throw new RuntimeException("NoPinPolicy shouldn't be set");
    }
}
