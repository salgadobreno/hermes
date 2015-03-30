package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.StringWithLengthParam;
import com.avixy.qrtoken.negocio.servico.params.StringWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.TimeZoneParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class GenerateKtamperService extends AbstractService implements TimestampAble {

    protected TimeZoneParam timezone;
    private StringWithLengthParam serialNumber;
    private StringWithLengthParam hardwareVersion;

    @Inject
    public GenerateKtamperService(QrtHeaderPolicy headerPolicy, TimestampPolicy timestampPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
    }

    @Override
    public String getServiceName() {
        return "Gerar K_Tamper";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_GENERATE_KTAMPER;
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(timezone, hardwareVersion, serialNumber).toByteArray();
    }

    @Override
    public void setTimestamp(Date date) {
        this.timestampPolicy.setDate(date);
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = new TimeZoneParam(timezone);
    }

    public void setSerialNumber(String serialNumber) { this.serialNumber = new StringWithLengthParam(serialNumber); }

    public void setHWVersion(String HWVersion) { this.hardwareVersion = new StringWithLengthParam(HWVersion); }

}

