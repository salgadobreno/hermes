package com.avixy.qrtoken.core.extensions.customControls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * <code>PopOver</code> which is not detachable by default
 *
 * Created on 18/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class PopOver extends org.controlsfx.control.PopOver {
    public PopOver() { super(); }

    public PopOver(Node content) { super(content); }

    {
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!isFocused()) {
                hide();
            }
        });
        setDetachable(false);
    }
}
