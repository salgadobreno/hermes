package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.core.extensions.binnary.CRC16CCITT;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrTokenCode;
import com.avixy.qrtoken.negocio.servico.ServiceAssembler;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.RandomGenerator;
import com.avixy.qrtoken.negocio.servico.operations.RandomProxy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.BinaryWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.CrcParam;
import com.avixy.qrtoken.negocio.servico.params.KeyLengthParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.FFHeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class TwoStepSymmetricKeyImportService extends AbstractService implements TimestampAble, PinAble {
    private byte[] secrecyKey;
    private byte[] authKey;

    private byte[] k;
    private byte[] k1;
    private byte[] k2;

    private RandomGenerator randomGenerator;

    @Inject
    public TwoStepSymmetricKeyImportService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy, RandomGenerator randomGenerator) {
        super(headerPolicy);
        this.passwordPolicy = passwordPolicy;
        this.timestampPolicy = timestampPolicy;
        this.randomGenerator = randomGenerator;
    }

    public void setSecrecyKey(byte[] secrecyKey) {
        this.secrecyKey = secrecyKey;
    }

    public void setAuthKey(byte[] authKey) {
        this.authKey = authKey;
    }

    @Override
    public void setTimestamp(Date date) {
        this.timestampPolicy.setDate(date);
    }

    @Override
    public void setPin(String pin) {
        this.passwordPolicy.setPassword(pin);
    }

    private void generateK1() {
        k = ArrayUtils.addAll(secrecyKey, authKey);
        k1 = new byte[k.length];
        randomGenerator.nextBytes(k1);
    }

    private void generateK2() {
        k2 = new byte[k.length];
        for (int i = 0; i < k.length; i++) {
            k2[i] = (byte) (k[i] ^ k1[i]);
        }
    }

    public byte[] getQr1(){
        generateK1();

        KeyLengthParam sKeyLengthParam = new KeyLengthParam(secrecyKey.length);
        KeyLengthParam aKeyLengthParam = new KeyLengthParam(authKey.length);
        BinaryWrapperParam k1Param = new BinaryWrapperParam(k1);
        CrcParam crcParam = new CrcParam(k1);

        return BinnaryMsg.get(sKeyLengthParam.toBinaryString() + aKeyLengthParam.toBinaryString() + k1Param.toBinaryString() + crcParam.toBinaryString());
    }

    public byte[] getQr2(){
        generateK2();

        KeyLengthParam sKeyLengthParam = new KeyLengthParam(secrecyKey.length);
        KeyLengthParam aKeyLengthParam = new KeyLengthParam(authKey.length);
        BinaryWrapperParam k2Param = new BinaryWrapperParam(k2);
        CrcParam crcParam = new CrcParam(k2);

        return BinnaryMsg.get(sKeyLengthParam.toBinaryString() + aKeyLengthParam.toBinaryString() + k2Param.toBinaryString() + crcParam.toBinaryString());
    }

    @Override
    public List<QrTokenCode> getQrs(QrSetup setup) throws Exception {
        ServiceAssembler serviceAssembler = new ServiceAssembler();

        List<QrTokenCode> qrs = new ArrayList<>();
        qrs.add(new QrTokenCode(serviceAssembler.get(this, getQr1(), headerPolicy, timestampPolicy, hmacKeyPolicy, passwordPolicy), setup));
        qrs.add(new QrTokenCode(serviceAssembler.get(this, getQr2(), headerPolicy, timestampPolicy, hmacKeyPolicy, passwordPolicy), setup));

        return qrs;
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }
}
