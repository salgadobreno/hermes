package com.avixy.qrtoken.core.extensions.components;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.util.Date;

/**
 * A TimestampField that has an option of setting up a time range
 *
 * @author Breno/Italo
 *
 * Created on 12/05/2015
 */
public class RangedTimestampField extends VBox {
    private TimestampField startTimestamp = new TimestampField();
    private TimestampField endTimestamp = new TimestampField();
    private CheckBox checkBox = new CheckBox("Ranged Timestamp");

    public RangedTimestampField() {
        setSpacing(5);
        getChildren().addAll(startTimestamp, endTimestamp, checkBox);
        endTimestamp.disableProperty().bind(checkBox.selectedProperty().not());
    }

    public Date getStartDateValue() {
        return startTimestamp.getValue();
    }

    public Date getEndDateValue() {
        if (checkBox.isSelected()) {
            return endTimestamp.getValue();
        } else {
            return null;
        }
    }
}
