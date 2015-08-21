package com.avixy.qrtoken.gui.components;

import javafx.scene.control.TextField;

/**
 * A <code>TextField</code> which limits the input size
 *
 * Created on 10/10/2014
 *
 * Source: http://stackoverflow.com/questions/15159988/javafx-2-2-textfield-maxlength
 */
public class TextFieldLimited extends TextField {
    protected int maxlength;
    public TextFieldLimited() {
        this.maxlength = 999;
    }

    public TextFieldLimited(int maxlength) {
        this.maxlength = maxlength;
    }

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        // Delete or backspace user input.
        if (text.equals("")) {
            super.replaceText(start, end, text);
        } else if (getText().length() < maxlength) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        // Delete or backspace user input.
        if (text.equals("")) {
            super.replaceSelection(text);
        } else if (getText().length() < maxlength) {
            // Add characters, but don't exceed maxlength.
            if (text.length() > maxlength - getText().length()) {
                text = text.substring(0, maxlength- getText().length());
            }
            super.replaceSelection(text);
        }
    }
}

