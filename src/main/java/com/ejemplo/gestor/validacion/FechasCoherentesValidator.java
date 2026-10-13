
package com.ejemplo.gestor.validacion;

import com.ejemplo.gestor.dto.ProyectoRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FechasCoherentesValidator
        implements ConstraintValidator<FechasCoherentes, ProyectoRequest> {

    @Override
    public boolean isValid(ProyectoRequest entrada, ConstraintValidatorContext contexto) {
        if (entrada == null
                || entrada.getFechaInicio() == null
                || entrada.getFechaFin() == null) {
            return true;
        }

        if (!entrada.getFechaFin().isBefore(entrada.getFechaInicio())) {
            return true;
        }

        contexto.disableDefaultConstraintViolation();
        contexto.buildConstraintViolationWithTemplate(
                contexto.getDefaultConstraintMessageTemplate())
                .addPropertyNode("fechaFin")
                .addConstraintViolation();

        return false;
    }
}
