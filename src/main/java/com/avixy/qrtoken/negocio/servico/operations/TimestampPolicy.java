package com.avixy.qrtoken.negocio.servico.operations;

import java.util.Date;

/**
 * Created on 10/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface TimestampPolicy {
    byte[] get();

    void setDate(Date date);
}
