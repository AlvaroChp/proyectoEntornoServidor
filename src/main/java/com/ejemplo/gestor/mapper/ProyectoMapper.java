package com.ejemplo.gestor.mapper;

import org.springframework.stereotype.Component;

import com.ejemplo.gestor.dto.ProyectoResponse;
import com.ejemplo.gestor.model.Proyecto;

@Component
public class ProyectoMapper {

    public ProyectoResponse aResponse(Proyecto proyecto) {
        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.isActivo(),
                proyecto.getNumeroDeIncidencias(),
                proyecto.getFechaInicio(),
                proyecto.getFechaFin());
    }
}
