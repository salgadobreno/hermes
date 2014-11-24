package com.avixy.qrtoken.negocio.qrcode;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto que orquestra a criação dos Qr Codes para o Qr Token
 * @author Breno Salgado <breno.salgado@axivy.com>
 *
 * Created on 08/07/2014.
 */
public class BasicQrCodePolicy implements QrCodePolicy {
    public static int HEADER_SIZE = 3;

    public BasicQrCodePolicy() {
    }

    /**
     * @param service
     * @param setup
     * @return Um <code>List</code> de <code>QrTokenCode</code>. Não necessáriamente será mais de um, o container deve
     * se virar p/ tratar 0, 1 ou muitos QRs de retorno apropriadamente.
     */
    public List<QrTokenCode> getQrs(Service service, QrSetup setup) throws Exception {
        // verifica se precisa de mais de 1 qr ...
        byte[] data = service.run();
        QrTokenCode tokenCode = new QrTokenCode(data, setup);
        List<QrTokenCode> tokenCodeList = new ArrayList<>();
        tokenCodeList.add(tokenCode);

        return tokenCodeList;
    }
}
