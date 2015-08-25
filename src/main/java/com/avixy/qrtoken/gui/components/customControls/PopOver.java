package com.avixy.qrtoken.gui.components.customControls;

import javafx.scene.Node;

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
