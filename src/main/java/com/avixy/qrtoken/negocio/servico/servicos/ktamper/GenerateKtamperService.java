package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.*;
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
    private SerialNumberParam serialNumber;
    private StringWithLengthParam hardwareVersion;
    private StringWithLengthParam pin;
    private StringWithLengthParam puk;

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
        return BinaryMsg.create().append(timezone, hardwareVersion, serialNumber, pin, puk).toByteArray();
    }

    @Override
    public void setTimestamp(Date date) {
        this.timestampPolicy.setDate(date);
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = new TimeZoneParam(timezone);
    }

    public void setSerialNumber(String serialNumber) { this.serialNumber = new SerialNumberParam(serialNumber); }

    public void setHWVersion(String HWVersion) { this.hardwareVersion = new StringWithLengthParam(HWVersion); }

    public void setPin(String pin) {
        this.pin = new StringWithLengthParam(pin);
    }

    public void setPuk(String puk) {
        this.puk = new StringWithLengthParam(puk);
    }
}
