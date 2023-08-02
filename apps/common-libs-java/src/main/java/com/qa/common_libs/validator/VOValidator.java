package com.qa.common_libs.validator;

import com.qa.common_libs.exception.ValidateException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import java.util.List;
import java.util.Set;

public class VOValidator {

    private VOValidator() {}

    public static <T> void validate(T target) {
        Set<ConstraintViolation<T>> violations =
                Validation.buildDefaultValidatorFactory().getValidator().validate(target);

        if (violations.size() > 0) {
            List<String> messages =
                    violations.stream().map(ConstraintViolation::getMessage).toList();
            throw new ValidateException(messages);
        }
    }
}
