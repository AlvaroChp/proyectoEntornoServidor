
package com.ejemplo.gestor.validacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PrioridadValidaValidator.class)
public @interface PrioridadValida {

    String message() default "La prioridad debe ser baja, media o alta";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
