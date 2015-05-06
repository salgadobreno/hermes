package com.avixy.qrtoken.negocio.servico.behaviors;

import com.avixy.qrtoken.negocio.servico.ServiceCode;

import java.util.Date;

/**
 * Defines a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} which has a Timestamp signature
 *
 * Created on 05/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface RangedTimestampAble {
    void setTimestampRange(Date startDate, Date endDate);

    boolean isRanged();
}
