package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;

import java.security.GeneralSecurityException;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class GenerateKtamperService implements Service {
    @Override
    public String getServiceName() {
        return "Gerar K_Tamper";
    }

    @Override
    public int getServiceCode() {
        return 20;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException {
        return new byte[0];
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return null;
    }
}
