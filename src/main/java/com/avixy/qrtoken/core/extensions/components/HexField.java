package com.avixy.qrtoken.core.extensions.components;

/**
 * A <code>TextField</code> that only accepts Hexadecimal characters
 *
 * Created on 11/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HexField extends RestrictiveTextField {
    public HexField() {
        setRestrict("[0-9A-f]");
    }

    public HexField(int maxLength) {
        super(maxLength);
        setRestrict("[0-9A-f]");
    }
}
