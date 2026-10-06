package com.ejemplo.gestor.dto;

import jakarta.validation.constraints.Size;

public class ProyectoPatchRequest {

    @Size(min = 3, max = 80)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    public ProyectoPatchRequest() {
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
}