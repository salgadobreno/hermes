package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.TwoBytesWrapper;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.FFHeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.apache.commons.lang.ArrayUtils.add;
import static org.apache.commons.lang.ArrayUtils.addAll;

/**
 * Created on 14/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdateFirmwareService extends AbstractService {
    private static final Charset CHARSET = Charset.forName("ISO-8859-1");

    private QrSetup qrSetup;

    /* parâmetros */
    private byte[] content;
    private byte moduleOffset;
    private String challengeParam;

    private byte interruptionCounter;
    private byte[] interruptionBytes;

    @Inject
    public UpdateFirmwareService(QrtHeaderPolicy headerPolicy) {
        super(headerPolicy);
    }

    public byte[] getInitialQr() {
        byte[] initialQr;

        int payloadQrCapacity = qrSetup.getAvailableBytes() - 4; // 4 == length do header dos qr de payload

        int qrQty = ((Double) Math.ceil((double)content.length/(double)payloadQrCapacity)).intValue();
        /* firstQr */
        byte[] header, body;
        header = new FFHeaderPolicy().getHeader(this);
        body = new byte[0];

        body = addAll(body, TwoBytesWrapper.get(qrQty));
        body = addAll(body, TwoBytesWrapper.get(content.length));
        body = add(body, moduleOffset);

        body = addAll(body, challengeParam.getBytes());
        body = add(body, interruptionCounter);
        body = addAll(body, interruptionBytes);
        initialQr = addAll(header, body);
        /* /firstQr */
        return initialQr;
    }

    @Override
    public byte[] run() throws Exception {
        byte[] data;
        int setupCapacity = qrSetup.getAvailableBytes();
        int payloadQrCapacity = setupCapacity - 4; // 4 == length do header dos qr de payload

        int qrQty = ((Double) Math.ceil((double)content.length/(double)payloadQrCapacity)).intValue();

        /* payload qrs */
        data = new byte[0];
        int offset = 0;
        for (int i = 0; i < qrQty; i++) {
            boolean last = i == qrQty - 1;
            byte[] payloadSize, payloadHeader;
            byte[] payload, qr;

            if (last) { payload = Arrays.copyOfRange(content, payloadQrCapacity * i, content.length); }
            else { payload = Arrays.copyOfRange(content, payloadQrCapacity * i, payloadQrCapacity * (i + 1)); }

            payloadSize = TwoBytesWrapper.get(payload.length);

            payloadHeader = addAll(TwoBytesWrapper.get(offset), payloadSize);
            qr = addAll(payloadHeader, payload);
            for (int j = qr.length; j < setupCapacity; j++) {
                qr = add(qr, (byte) 0);
            }

            offset += payload.length;
            data = addAll(data, qr);
        }
        return data;
    }

    @Override
    public byte[] getMessage() { return new byte[0]; } // esse método é atravessado pelo run()

    @Override
    public String getServiceName() { return "SERVICE_FIRMWARE_SYM_UPDATE"; }

    @Override
    public int getServiceCode() { return 63; }

    public void setHmacKeyPolicy(HmacKeyPolicy hmacKeyPolicy) {
    }

    public void setQrSetup(QrSetup qrSetup) { this.qrSetup = qrSetup; }

    public void setContent(byte[] content) { this.content = content; }

    public void setModuleOffset(byte moduleOffset) { this.moduleOffset = moduleOffset; }

    public void setChallenge(String challenge) { this.challengeParam = challenge; }

    public void setInterruptionCount(byte interruptionStuff) { this.interruptionCounter = interruptionStuff; }

    public void setInterruptionBytes(byte[] interruptionBytes) { this.interruptionBytes = interruptionBytes; }
}