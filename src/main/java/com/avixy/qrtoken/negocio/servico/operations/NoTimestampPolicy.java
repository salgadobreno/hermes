package com.avixy.qrtoken.negocio.servico.operations;

import java.util.Date;

public class NoTimestampPolicy implements TimestampPolicy {
    @Override
    public byte[] get() { return new byte[0]; }

    @Override
    public void setDate(Date date) {
        throw new RuntimeException("NoTimestampPolicy shouldn't be set");
    }

}
