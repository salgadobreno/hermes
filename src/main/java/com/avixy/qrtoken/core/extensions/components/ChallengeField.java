package com.avixy.qrtoken.core.extensions.components;

/**
 * Created on 26/01/2015
 *
 * @author I7
 */
public class ChallengeField extends RestrictiveTextField {
    public ChallengeField() { super(6); }

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
