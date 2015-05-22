package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;

import java.util.Date;

/**
 * Created on 05/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class SettableTimestampPolicy implements TimestampPolicy {
    protected Date startDate;

    @Override
    public byte[] get(){
        return BinaryMsg.get(new TimestampParam(startDate).toBinaryString());
    }

    @Override
    public void setDate(Date date) {
        this.startDate = date;
    }
}
