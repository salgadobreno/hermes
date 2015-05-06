package com.avixy.qrtoken.core.extensions.components.validators;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 * Created on 30/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class SizeValidator implements Validator<String> {

    private int maxLength;

    public SizeValidator(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ValidationResult apply(Control control, String s) {
        if (control instanceof TextField) {
            TextField textField = (TextField) control;
            if (textField.getText().length() > maxLength) {
                return ValidationResult.fromError(control, "Maximum length is " + maxLength);
            } else {
                return new ValidationResult();
            }
        } else {
            throw new IllegalArgumentException("control must be a TextField");
        }
    }
}
