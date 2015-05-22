package com.avixy.qrtoken.core.extensions.components;

import com.avixy.qrtoken.core.extensions.components.validators.JideSizeValidator;
import javafx.beans.binding.Bindings;
import jidefx.scene.control.validation.ValidationMode;
import jidefx.scene.control.validation.ValidationUtils;

/**
 * Created on 06/05/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class SerialNumberField extends TextFieldLimited {
    private static SerialNumberField lastSerialNumber;

    public SerialNumberField() {
        super(10);
        ValidationUtils.install(this, new JideSizeValidator(10), ValidationMode.ON_DEMAND);
        ValidationUtils.install(this, new JideSizeValidator(10), ValidationMode.ON_FLY);
        if (lastSerialNumber != null) {
            this.textProperty().bindBidirectional(lastSerialNumber.textProperty());
        }
        lastSerialNumber = this;
    }

}
