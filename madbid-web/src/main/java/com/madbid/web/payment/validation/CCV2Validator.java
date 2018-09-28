package com.madbid.web.payment.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by dimer on 11/6/14.
 */
public class CCV2Validator implements ConstraintValidator<CCV2, String> {
    @Override
    public void initialize(CCV2 constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() != 3 || value.matches("\\D+")) {
            return false;
        }
        return true;
    }
}
