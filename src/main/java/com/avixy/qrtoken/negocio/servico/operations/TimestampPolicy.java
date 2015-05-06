package com.avixy.qrtoken.negocio.servico.operations;

import java.util.Date;

public interface TimestampPolicy {
    byte[] get();

    void setDate(Date date);
}
