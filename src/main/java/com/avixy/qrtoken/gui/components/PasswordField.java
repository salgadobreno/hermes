package com.avixy.qrtoken.gui.components;

/**
 * PIN/PUK <code>TextField</code>, limited to 16 characters
 *
 * Created on 28/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class PasswordField extends TextFieldLimited {
    public PasswordField() {
        super(16);
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
