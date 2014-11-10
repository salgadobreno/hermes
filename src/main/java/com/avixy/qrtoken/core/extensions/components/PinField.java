package com.avixy.qrtoken.core.extensions.components;

/**
 * TextField para input de PIN
 * Created on 28/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class PinField extends TextFieldLimited {
    public PinField() {
        super(12);
    }

    @Override
    public void replaceText(int start, int end, String text) {
        // Delete or backspace user input.
        super.replaceText(start, end, text.toUpperCase());
    }

    @Override
    public void replaceSelection(String text) {
        super.replaceSelection(text.toUpperCase());
    }
}
