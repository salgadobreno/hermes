package com.avixy.qrtoken.core.extensions;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;

/**
 * Created by I7 on 08/08/2014.
 */
public class HorarioField extends TextField {
    private final RegularExpression regex = new RegularExpression("[0-9]*:[0-9]*");


    @Override
    public void replaceText(IndexRange indexRange, String text) {
        System.out.println("replaceText: " + indexRange + "," + text);
        if (regex.matches(text)){
            super.replaceText(indexRange, text);
        }
    }

    @Override
    public void replaceText(int start, int end, String text) {
        System.out.println("replaceText2: " + start + "," + end + "," + text);
        if (regex.matches(text)){
            super.replaceText(start, end, text);
        }
    }
}
