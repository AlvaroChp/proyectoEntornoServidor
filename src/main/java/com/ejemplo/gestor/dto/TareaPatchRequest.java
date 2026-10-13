
package com.ejemplo.gestor.dto;

import com.ejemplo.gestor.validacion.PrioridadValida;
import jakarta.validation.constraints.Size;

public class TareaPatchRequest {

    @Size(min = 3, max = 120,
            message = "El título debe tener entre 3 y 120 caracteres")
    private String titulo;

    @PrioridadValida
    private String prioridad;

    public TareaPatchRequest() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
