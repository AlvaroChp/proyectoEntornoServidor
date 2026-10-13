
package com.ejemplo.gestor.validacion;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrioridadValidaValidator
        implements ConstraintValidator<PrioridadValida, String> {

    private static final List<String> VALIDAS =
            List.of("baja", "media", "alta");

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext contexto) {
        if (valor == null) {
            return true;
        }

        return VALIDAS.contains(valor);
    }
}
