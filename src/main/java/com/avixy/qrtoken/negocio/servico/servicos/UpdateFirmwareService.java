package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.crypto.CryptoException;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * Created on 14/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdateFirmwareService extends AbstractService {
    private static final Charset CHARSET = Charset.forName("ISO-8859-1");
    private HmacKeyPolicy hmacKeyPolicy;

    private QrSetup qrSetup;

    private byte[] content;
    private byte moduleOffset;
    private String challengeParam;
    private byte interrumptionStuff;

    @Inject
    public UpdateFirmwareService(QrtHeaderPolicy headerPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_FIRMWARE_SYM_UPDATE";
    }

    @Override
    public int getServiceCode() {
        return 63;
    }

    @Override
    public byte[] getMessage() {
        return RandomStringUtils.randomAlphabetic(200).getBytes();
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        byte[] firstQr, data;
        int setupCapacity = qrSetup.getAvailableBytes();
        int payloadQrCapacity = setupCapacity - 4; // length do header dos qr de payload

        //firstQr stuff
        QrtHeaderPolicy qrtHeaderPolicy = new QrtHeaderPolicy();
        int qrQty;
        qrQty = ((Double) Math.ceil((double)content.length/(double)payloadQrCapacity)).intValue();
        byte[] body = new byte[0];
        body = ArrayUtils.addAll(body, TwoBitBitset.get(qrQty));
        body = ArrayUtils.addAll(body, TwoBitBitset.get(content.length));
        body = ArrayUtils.add(body, moduleOffset);

        byte[] bodyBytes = ArrayUtils.addAll(body, challengeParam.getBytes());
        byte[] header = qrtHeaderPolicy.getHeader(this);
        firstQr = ArrayUtils.addAll(header, bodyBytes);

        firstQr = StringUtils.rightPad(new String(firstQr, CHARSET), setupCapacity, (char)0).getBytes(); // add padding
        data = firstQr;

        for (int i = 0; i < qrQty; i++) {
            boolean last = i == qrQty - 1;
            byte[] qrIndex, payloadSize, payloadHeader;
            byte[] payload, qr;

            if (last) { payload = Arrays.copyOfRange(content, payloadQrCapacity * i, content.length); }
            else { payload = Arrays.copyOfRange(content, payloadQrCapacity * i, payloadQrCapacity * (i + 1)); }

            qrIndex = TwoBitBitset.get(i);
            payloadSize = TwoBitBitset.get(payload.length);

            payloadHeader = ArrayUtils.addAll(qrIndex, payloadSize);
            qr = ArrayUtils.addAll(payloadHeader, payload);
            for (int j = qr.length; j < setupCapacity; j++) {
                qr = ArrayUtils.add(qr, (byte)0);
            }

            data = ArrayUtils.addAll(data, qr);
        }
        return data;
    }

    public void setQrSetup(QrSetup qrSetup) {
        this.qrSetup = qrSetup;
    }

    public void setHmacKeyPolicy(HmacKeyPolicy hmacKeyPolicy) {
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setModuleOffset(byte moduleOffset) {
        this.moduleOffset = moduleOffset;
    }

    public void setChallenge(String challenge) {
        this.challengeParam = challenge;
    }

    public void setInterruptionStuff(byte interruptionStuff) {
        this.interrumptionStuff = interruptionStuff;
    }

    static class TwoBitBitset {
        public static byte[] get(Integer i) {
            byte[] bytes = new byte[2];
            String val = StringUtils.leftPad(Integer.toBinaryString(i.shortValue()), 16, '0');
            String[] strings = {
                    val.substring(0, val.length()/2),
                    val.substring(val.length()/2)
            };
            for (int j = 0; j < bytes.length; j++) {
                bytes[j] = ((byte) Integer.parseInt(strings[j], 2));
            }
            return bytes;
        }
    }
}