package com.avixy.qrtoken.core.extensions.components.validators;

import jidefx.scene.control.validation.ValidationEvent;
import jidefx.scene.control.validation.ValidationObject;
import jidefx.scene.control.validation.Validator;

/**
 * Created on 06/05/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class JideSimpleValidator implements Validator {
    @Override
    public ValidationEvent call(ValidationObject param) {
        if (param.getNewValue() != null && !param.getNewValue().toString().isEmpty()) {
            return ValidationEvent.OK;
        } else {
            return new ValidationEvent(ValidationEvent.VALIDATION_ERROR);
        }
    }
}
