package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 22/08/2014
 */
@AcceptsKey(keyType = KeyType.HMAC)
public class HmacKeyPolicy extends AbstractKeyPolicy {
    private static Logger logger = LoggerFactory.getLogger(HmacKeyPolicy.class);

    @Override
    public byte[] apply(byte[] msg) throws GeneralSecurityException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        Mac sha1Mac = Mac.getInstance("HmacSHA256");
        sha1Mac.init(secretKeySpec);
        byte[] mac = sha1Mac.doFinal(msg);
        logger.info("key = " + Hex.encodeHexString(key));
        logger.info("MSG: " + Hex.encodeHexString(msg));
        logger.info("HMAC: " + Hex.encodeHexString(mac));
        return ArrayUtils.addAll(msg, mac);
    }
}
