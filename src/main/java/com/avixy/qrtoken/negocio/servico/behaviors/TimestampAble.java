package com.avixy.qrtoken.negocio.servico.behaviors;

import java.util.Date;

/**
 * Defines a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} which has a Timestamp signature
 *
 * Created on 05/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface TimestampAble {
    void setTimestamp(Date date);
}
