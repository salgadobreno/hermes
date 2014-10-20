package com.avixy.qrtoken.negocio.qrcode;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 16/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class MultipleQrCodePolicy implements QrCodePolicy {
    @Override
    public List<QrTokenCode> getQrs(Service service, QrSetup setup) throws GeneralSecurityException, CryptoException {
        List<QrTokenCode> qrCodes = new ArrayList<>();
        byte[] data = service.getData();

        // verifica se precisa de mais de 1 qr ...
        Integer contentLength = data.length;
        Double qrQty = Math.ceil(contentLength.doubleValue() / setup.getAvailableBytes());
        for (int i = 0; i < qrQty; i++) {
            byte[] slice;
            QrTokenCode qrCode;
            boolean last = i == qrQty - 1;
            if (last){
                slice = Arrays.copyOfRange(data, setup.getAvailableBytes() * i, contentLength);
                qrCodes.add(new QrTokenCode(slice, setup, setup.getAvailableBytes()));
            } else {
                slice = Arrays.copyOfRange(data, setup.getAvailableBytes() * i, setup.getAvailableBytes() * (i + 1));
                qrCodes.add(new QrTokenCode(slice, setup, setup.getAvailableBytes()));
            }
        }

        return qrCodes;
    }
}
