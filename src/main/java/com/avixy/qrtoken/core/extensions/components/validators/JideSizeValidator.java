package com.avixy.qrtoken.core.extensions.components.validators;

import jidefx.scene.control.validation.ValidationEvent;
import jidefx.scene.control.validation.ValidationObject;

/**
 * Created on 06/05/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class JideSizeValidator extends JideSimpleValidator {
    private int size;

    public JideSizeValidator(int size) {
        this.size = size;
    }

    @Override
    public ValidationEvent call(ValidationObject param) {
        super.call(param);

        if (param.getNewValue().toString().length() == size) {
            return ValidationEvent.OK;
        } else {
            return new ValidationEvent(ValidationEvent.VALIDATION_ERROR);
        }
    }
}
