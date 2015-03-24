package com.avixy.qrtoken.core.extensions.components;

/**
 * A <code>TextField</code> that only accepts numbers
 *
 * Created on 19/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class NumberField extends RestrictiveTextField {
    public NumberField() {
        setRestrict("[0-9]");
        setText("0");
    }
}
