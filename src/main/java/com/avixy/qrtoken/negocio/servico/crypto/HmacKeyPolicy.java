package com.avixy.qrtoken.negocio.servico.crypto;

import com.avixy.qrtoken.gui.servicos.AcceptKey;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * Created on 22/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@AcceptKey(keyType = KeyType.HMAC)
public class HmacKeyPolicy implements KeyPolicy {
    private static Logger logger = LoggerFactory.getLogger(HmacKeyPolicy.class);
    private byte[] key;

    @Override
    public byte[] apply(byte[] msg) throws GeneralSecurityException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA1");
        Mac sha1Mac = Mac.getInstance("HmacSHA1");
        sha1Mac.init(secretKeySpec);
        byte[] mac = sha1Mac.doFinal(msg);
        logger.info("MSG: " + new String(msg));
        logger.info("MAC: " + new String(mac));
        logger.info("HMAC: " + new String(ArrayUtils.addAll(msg, mac)));
        return ArrayUtils.addAll(msg, mac);
    }

    public void setKey(byte[] key) {
        this.key = key;
    }
}
