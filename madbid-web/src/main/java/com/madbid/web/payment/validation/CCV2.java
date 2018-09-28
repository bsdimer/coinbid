package com.madbid.web.payment.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by dimer on 11/6/14.
 */
@Documented
@Constraint(validatedBy = CCV2Validator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CCV2 {
    String message() default "error.creditCard.invalidCCV2";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
