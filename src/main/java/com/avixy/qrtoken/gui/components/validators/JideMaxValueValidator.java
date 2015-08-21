package com.avixy.qrtoken.gui.components.validators;

import jidefx.scene.control.validation.ValidationEvent;
import jidefx.scene.control.validation.ValidationObject;
import jidefx.scene.control.validation.Validator;

/**
 * Created by Breno on 21/05/2015.
 */
public class JideMaxValueValidator implements Validator {
    private int maxInt;

    public JideMaxValueValidator(int maxInt) {
        this.maxInt = maxInt;
    }

    @Override
    public ValidationEvent call(ValidationObject param) {
        if (param.getNewValue() != null && !param.getNewValue().toString().isEmpty() && Integer.parseInt(param.getNewValue().toString()) <= maxInt) {
            return ValidationEvent.OK;
        } else {
            return new ValidationEvent(ValidationEvent.VALIDATION_ERROR);
        }
    }
}
