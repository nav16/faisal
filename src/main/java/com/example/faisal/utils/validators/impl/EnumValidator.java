package com.example.faisal.utils.validators.impl;


import com.example.faisal.utils.validators.CheckEnum;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<CheckEnum, String> {

    private Class clazz;

    @Override
    public void initialize(CheckEnum constraintAnnotation) {
        this.clazz = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        return EnumUtils.isValidEnumIgnoreCase(clazz, value);
    }
}
