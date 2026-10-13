
package com.ejemplo.gestor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TareaRequest {

    @NotBlank(message = "Es obligatorio añadir un título a la tarea")
    @Size(min = 3, max = 120, message = "El título debe tener entre 3 y 120 caracteres")
    private String titulo;

    @NotNull(message = "Hay que asignar una prioridad")
    @Pattern(regexp = "baja|media|alta", message = "La prioridad tiene que serbaja, media o alta")
    private String prioridad;

    @NotNull(message = "Todas las tareas deben pertenecer a un proyecto")
    @Positive(message = "El identificador tiene que ser un número positivo")
    private Integer proyectoId;

    private Boolean completada;

    public TareaRequest() {
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

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }

    public Boolean getCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }
}
