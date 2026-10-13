
package com.ejemplo.gestor.dto;

import java.time.LocalDate;

import com.ejemplo.gestor.validacion.FechasCoherentes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@FechasCoherentes
public class ProyectoRequest {

    @NotBlank(message = "Es obligatorio añadir un nombre al proyecto")
    @Size(min = 3, max = 80, message = "El nombre del proyecto tiene que tener entre 3 y 80 caracteres")
    private String nombre;

    @Size(max = 500, message = "No puedes escribir más de 500 caracteres en la descripción")
    private String descripcion;

    private boolean activo;

    private int numeroDeIncidencias;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    public ProyectoRequest() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getNumeroDeIncidencias() {
        return numeroDeIncidencias;
    }

    public void setNumeroDeIncidencias(int numeroDeIncidencias) {
        this.numeroDeIncidencias = numeroDeIncidencias;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
