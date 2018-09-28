package com.madbid.web.payment.validation;

import com.madbid.web.payment.utils.CreditCardUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by dimer on 11/6/14.
 */
public class CreditCardTypeValidator implements ConstraintValidator<CreditCardProvider, String> {
    @Override
    public void initialize(CreditCardProvider constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CreditCardUtils.isValidCreditCardType(value);
    }
}
