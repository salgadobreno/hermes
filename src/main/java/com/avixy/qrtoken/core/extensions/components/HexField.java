package com.avixy.qrtoken.core.extensions.components;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * A <code>TextField</code> that only accepts Hexadecimal characters
 *
 * Created on 11/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HexField extends RestrictiveTextField {
    {
        setRestrict("[0-9A-f]");
    }
    public HexField() {
    }

    public HexField(int maxLength) {
        super(maxLength);
    }

    public byte[] getValue() {
        try {
            return Hex.decodeHex(getText().toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }
}
