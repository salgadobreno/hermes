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
    public HexField() {
        setRestrict("[0-9a-f]");
    }

    public HexField(int maxLength) {
        super(maxLength);
        setRestrict("[0-9a-f]");
    }

    public byte[] getValue() {
        try {
            return Hex.decodeHex(getText().toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }
}
