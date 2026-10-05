package com.ejemplo.gestor.dto;

import com.ejemplo.gestor.model.Tarea;

public record TareaResponse(
        int id,
        String titulo,
        String prioridad,
        boolean completada,
        int proyectoId) {

    public static TareaResponse desde(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getPrioridad(),
                tarea.isCompletada(),
                tarea.getProyectoId());
    }
}