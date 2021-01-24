package com.calinux.chessdb.api.v1.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPasswordConstraint, String> {

    @Override
    public void initialize(StrongPasswordConstraint strongPasswordConstraint) {
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext cxt) {
        return passwordField != null && passwordField.matches(".*[0-9]+.*") && passwordField.matches(".*[a-zA-Z]+.*") && (passwordField.length() >= 8);
    }
}
