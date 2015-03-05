package com.avixy.qrtoken.core.extensions.components;

/**
 * Created on 19/02/2015
 *
 * @author I7
 */
public class NumberField extends RestrictiveTextField {
    public NumberField() {
        setRestrict("[0-9]");
        setText("0");
    }
}
