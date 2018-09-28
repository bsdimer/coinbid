package com.madbid.core.validation;

import com.madbid.core.model.Address;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * Created by nikolov.
 */
public class ValidatorUtil {

    public static void addValidationError(String field, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode(field)
                .addConstraintViolation();
    }

    public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = object.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(object);
    }

    public static boolean validateAddress(Address address) {
        if (StringUtils.isNotEmpty(address.getCountry())
                && StringUtils.isNotEmpty(address.getCity())
                && StringUtils.isNotEmpty(address.getStreet1())
                ) {
            return true;
        }

        return false;
    }
}
