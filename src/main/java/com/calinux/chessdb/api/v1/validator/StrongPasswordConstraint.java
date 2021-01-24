package com.calinux.chessdb.api.v1.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPasswordConstraint {
    String message() default "Weak password. Must be at least 8 characters and contain 1 letter and 1 number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
