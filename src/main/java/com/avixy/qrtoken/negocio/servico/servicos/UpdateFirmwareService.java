package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.TwoBitBitset;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;
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

    private byte[] initialQr;

    /* parâmetros */
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
    public String getServiceName() { return "SERVICE_FIRMWARE_SYM_UPDATE"; }

    @Override
    public int getServiceCode() { return 63; }

    public byte[] getInitialQr() {
        /* Sempre chama o getData() para que initialQr esteja preenchido/atualizado */
        try {
            getData();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected");
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        return initialQr;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        byte[] firstQr, data;
        int setupCapacity = qrSetup.getAvailableBytes();
        int payloadQrCapacity = setupCapacity - 4; // length do header dos qr de payload

        /* firstQr */
        QrtHeaderPolicy qrtHeaderPolicy = new QrtHeaderPolicy();
        int qrQty;
        qrQty = ((Double) Math.ceil((double)content.length/(double)payloadQrCapacity)).intValue();
        byte[] body = new byte[0];
        body = ArrayUtils.addAll(body, TwoBitBitset.get(qrQty));
        body = ArrayUtils.addAll(body, TwoBitBitset.get(content.length));
        body = ArrayUtils.add(body, moduleOffset);

        byte[] bodyBytes = ArrayUtils.addAll(body, challengeParam.getBytes());
        bodyBytes = ArrayUtils.add(bodyBytes, interrumptionStuff);
        byte[] header = qrtHeaderPolicy.getHeader(this);
        firstQr = ArrayUtils.addAll(header, bodyBytes);
        initialQr = firstQr;

        data = new byte[0];
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

    @Override
    public byte[] getMessage() { return new byte[0]; } // aqui, atravessamos o getMessage pelo getData

    public void setHmacKeyPolicy(HmacKeyPolicy hmacKeyPolicy) { this.hmacKeyPolicy = hmacKeyPolicy; }

    public void setQrSetup(QrSetup qrSetup) { this.qrSetup = qrSetup; }

    public void setContent(byte[] content) { this.content = content; }

    public void setModuleOffset(byte moduleOffset) { this.moduleOffset = moduleOffset; }

    public void setChallenge(String challenge) { this.challengeParam = challenge; }

    public void setInterruptionStuff(byte interruptionStuff) { this.interrumptionStuff = interruptionStuff; }

}