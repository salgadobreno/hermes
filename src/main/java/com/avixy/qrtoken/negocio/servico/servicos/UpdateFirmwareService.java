package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binary.TwoBytesWrapper;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.FFHeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.CryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.GeneralSecurityException;

import static org.apache.commons.lang.ArrayUtils.add;
import static org.apache.commons.lang.ArrayUtils.addAll;

/**
 * Created on 14/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
//TODO: atualizar p/ remoção do QrCodePolicy
public class UpdateFirmwareService extends AbstractService implements AesCrypted, HmacAble {
    private Logger logger = LoggerFactory.getLogger(UpdateFirmwareService.class);

    private QrSetup qrSetup;

    /* parâmetros */
    private byte[] content;
    private byte[] encryptedContent;
    private byte moduleOffset;
    private String challengeParam;

    private byte interruptionCounter;
    private byte[] interruptionBytes;

    private HmacKeyPolicy hmacKeyPolicy;
    private AesKeyPolicy aesKeyPolicy;

    @Inject
    public UpdateFirmwareService(QrtHeaderPolicy headerPolicy, AesKeyPolicy aesKeyPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.aesKeyPolicy = aesKeyPolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    /**
     * Qr que inicializa o serviço de atualizar Firmware
     * @return                  O QR de serviço
     * @throws CryptoException
     * @throws GeneralSecurityException
     */
    public byte[] getInitialQr() throws CryptoException, GeneralSecurityException {
        logger.trace("getInitialQr()");
        byte[] initialQr;

        int payloadQrCapacity = qrSetup.getAvailableBytes() - 4; // 4 == length do header dos qr de payload
        int qrQty = ((Double) Math.ceil((double) encryptedContent.length/(double)payloadQrCapacity)).intValue();
        /* firstQr */
        byte[] header, body;
        header = new FFHeaderPolicy().getHeader(this);
        body = new byte[0];

        body = addAll(body, TwoBytesWrapper.get(qrQty));
        body = addAll(body, TwoBytesWrapper.get(encryptedContent.length));
        body = add(body, moduleOffset);

        body = addAll(body, challengeParam.getBytes());
        body = add(body, interruptionCounter);
        body = addAll(body, interruptionBytes);
        initialQr = addAll(header, body);
        initialQr = hmacKeyPolicy.apply(initialQr);
        /* /firstQr */
        return initialQr;
    }

//    /**
//     *
//     * @return       O payload da atualização, dividido conforme o Setup de QR atual
//     *               (e.g.: QR nível 1 com nível de correção de erro 1 -> 17 bytes de capacidade em cada QR,
//     *               o resultado terá o header de payload a cada 17 bytes)
//     * @throws       Exception
//     */
//    @Override
//    public byte[] run() throws Exception {
//        logger.trace("run()");
//        byte[] data;
//        int setupCapacity = qrSetup.getAvailableBytes();
//        int payloadQrCapacity = setupCapacity - 4; // 4 == length do header dos qr de payload
//        logger.trace("payloadQrCapacity = {}", payloadQrCapacity);
//
//        int qrQty = ((Double) Math.ceil((double) encryptedContent.length/(double)payloadQrCapacity)).intValue();
//        logger.trace("qrQty = {}", qrQty);
//
//        /* payload qrs */
//        data = new byte[0];
//        int offset = 0;
//        for (int i = 0; i < qrQty; i++) {
//            boolean last = (i == qrQty - 1);
//            byte[] payloadSize, payloadHeader;
//            byte[] payload, qr;
//
//            if (last) { payload = Arrays.copyOfRange(encryptedContent, payloadQrCapacity * i, encryptedContent.length); }
//            else { payload = Arrays.copyOfRange(encryptedContent, payloadQrCapacity * i, payloadQrCapacity * (i + 1)); }
//
//            payloadSize = TwoBytesWrapper.get(payload.length);
//
//            payloadHeader = addAll(TwoBytesWrapper.get(offset), payloadSize);
//            qr = addAll(payloadHeader, payload);
//            for (int j = qr.length; j < setupCapacity; j++) {
//                qr = add(qr, (byte) 0);
//            }
//
//            offset += payload.length;
//            data = addAll(data, qr);
//        }
//        return data;
//    }

    @Override
    public byte[] getMessage() { return new byte[0]; } // esse método é atravessado pelo run()

    @Override
    public String getServiceName() { return "Atualização de Firmware"; }

    @Override
    public ServiceCode getServiceCode() { return ServiceCode.SERVICE_FIRMWARE_SYM_UPDATE; }

    public void setQrSetup(QrSetup qrSetup) { this.qrSetup = qrSetup; }

    public void setContent(byte[] content) throws CryptoException, GeneralSecurityException {
        this.content = content;
        byte[] iv;
        iv = aesKeyPolicy.getInitializationVector();
        encryptedContent = aesKeyPolicy.apply(content);
        logger.trace("encryptedContent = {}", Hex.encodeHexString(encryptedContent));
        encryptedContent = addAll(encryptedContent, iv);
        encryptedContent = hmacKeyPolicy.apply(encryptedContent);
        logger.trace("iv = {}", Hex.encodeHexString(iv));
        logger.trace("content = {}", Hex.encodeHexString(content));
    }

    public void setModuleOffset(byte moduleOffset) { this.moduleOffset = moduleOffset; }

    public void setChallenge(String challenge) { this.challengeParam = challenge; }

    public void setInterruptionCount(byte interruptionStuff) { this.interruptionCounter = interruptionStuff; }

    public void setInterruptionBytes(byte[] interruptionBytes) { this.interruptionBytes = interruptionBytes; }

    @Override
    public void setAesKey(byte[] key) {
        this.aesKeyPolicy.setKey(key);
    }

    @Override
    public void setHmacKey(byte[] key) {
        this.hmacKeyPolicy.setKey(key);
    }
}