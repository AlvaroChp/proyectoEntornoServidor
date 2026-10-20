package com.ejemplo.gestor.mapper;

import org.springframework.stereotype.Component;

import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.model.Tarea;

@Component
public class TareaMapper {

    public TareaResponse aResponse(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getPrioridad(),
                tarea.isCompletada(),
                tarea.getProyectoId());
    }
}
