package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrTokenCode;
import com.avixy.qrtoken.negocio.servico.ServiceAssembler;
import com.avixy.qrtoken.negocio.servico.behaviors.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.RandomGenerator;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.BinaryWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.CrcParam;
import com.avixy.qrtoken.negocio.servico.params.KeyLengthParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.PasswordOptionalAbstractService;
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
public abstract class TwoStepSymmetricKeyImportService extends PasswordOptionalAbstractService implements TimestampAble, PinAble, PasswordOptional {
    private byte[] secrecyKey;
    private byte[] authKey;

    private byte[] k; /* k é a concatenação das duas chaves */
    private byte[] randomComponent;
    private byte[] keyComponent;

    private RandomGenerator randomGenerator;

    @Inject
    public TwoStepSymmetricKeyImportService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy, RandomGenerator randomGenerator) {
        super(headerPolicy, passwordPolicy);
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

    private void generateRandomComponent() {
        k = ArrayUtils.addAll(secrecyKey, authKey);
        randomComponent = new byte[k.length];
        randomGenerator.nextBytes(randomComponent);
    }

    private void generateKeyComponent() {
        keyComponent = new byte[k.length];
        for (int i = 0; i < k.length; i++) {
            keyComponent[i] = (byte) (k[i] ^ randomComponent[i]);
        }
    }

    public byte[] getQr1(){
        generateRandomComponent();

        KeyLengthParam sKeyLengthParam = new KeyLengthParam(secrecyKey.length);
        KeyLengthParam aKeyLengthParam = new KeyLengthParam(authKey.length);
        BinaryWrapperParam k1Param = new BinaryWrapperParam(randomComponent);
        CrcParam crcParam = new CrcParam(randomComponent);

        return BinaryMsg.get(sKeyLengthParam.toBinaryString() + aKeyLengthParam.toBinaryString() + k1Param.toBinaryString() + crcParam.toBinaryString());
    }

    public byte[] getQr2(){
        generateKeyComponent();

        KeyLengthParam sKeyLengthParam = new KeyLengthParam(secrecyKey.length);
        KeyLengthParam aKeyLengthParam = new KeyLengthParam(authKey.length);
        BinaryWrapperParam k2Param = new BinaryWrapperParam(keyComponent);
        CrcParam crcParam = new CrcParam(keyComponent);

        return BinaryMsg.get(sKeyLengthParam.toBinaryString() + aKeyLengthParam.toBinaryString() + k2Param.toBinaryString() + crcParam.toBinaryString());
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
