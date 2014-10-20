package com.avixy.qrtoken.negocio.qrcode;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Created on 16/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class DoubledQrCodePolicy extends BasicQrCodePolicy {
    @Override
    public List<QrTokenCode> getQrs(Service service, QrSetup setup) throws GeneralSecurityException, CryptoException {
        List<QrTokenCode> qrCodes = super.getQrs(service, setup);
        qrCodes.add(qrCodes.get(0));
        return qrCodes;
    }
}
