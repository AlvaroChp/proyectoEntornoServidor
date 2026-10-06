package com.ejemplo.gestor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProyectoRequest {

    @NotBlank
    @Size(min = 3, max = 80)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    private boolean activo;

    private int numeroDeIncidencias;

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
}