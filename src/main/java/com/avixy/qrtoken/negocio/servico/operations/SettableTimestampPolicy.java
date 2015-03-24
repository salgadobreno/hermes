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
    private Date date;

    @Override
    public byte[] get(){
        TimestampParam param = new TimestampParam(date);
        return BinaryMsg.get(param.toBinaryString());
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }
}
