package com.avixy.qrtoken.core.extensions.customControls;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Created on 18/03/2015
 *
 * @author I7
 */
public class PopOver extends org.controlsfx.control.PopOver {
    public PopOver() { super(); }

    public PopOver(Node content) { super(content); }

    {
        setDetachable(false);
        setOnAutoHide(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                event.consume();
                if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                    getScene().getOnMouseClicked().handle((MouseEvent)event);
                }
            }
        });
    }
}
