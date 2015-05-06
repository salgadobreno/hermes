package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;

import java.util.Date;

/**
 * Created by Avixy on 5/5/2015.
 */
public class RangedTimestampPolicy extends SettableTimestampPolicy {
    private Date endDate;

    @Override
    public void setDate(Date date) {
        super.setDate(date);
        endDate = null;
    }

    public void setRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public byte[] get() {
        if (endDate == null) {
            return super.get();
        } else {
            return BinaryMsg.create().append(new TimestampParam(startDate), new TimestampParam(endDate)).toByteArray();
        }
    }

    public boolean isRanged() {
        return endDate != null;
    };
}
