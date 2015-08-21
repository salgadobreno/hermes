package com.avixy.qrtoken.gui.components.validators;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 * Created on 30/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ExactSizeValidator implements Validator<String> {
    private int length;

    public ExactSizeValidator(int length) {
        this.length = length;
    }

    public ValidationResult apply(Control control, String s) {
        if (control instanceof TextField) {
            TextField textField = (TextField) control;
            if (textField.getText().length() > length) {
                return ValidationResult.fromError(control, "Length is " + length); //TODO
            } else if (textField.getText().length() < length) {
                return ValidationResult.fromError(control, "Length is " + length);
            }
        } else {
            throw new IllegalArgumentException("control must be a TextField");
        }
        return new ValidationResult();
    }
}
