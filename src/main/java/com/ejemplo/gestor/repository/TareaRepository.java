package com.ejemplo.gestor.repository;

import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Tarea;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class TareaRepository {

    private final List<Tarea> tareas;
    private int siguienteId = 1;

    public TareaRepository(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
    }

    public List<Tarea> buscarTodas() {
        return tareas;
    }

    public Optional<Tarea> buscarPorId(int id) {
        return tareas.stream()
                .filter(tarea -> tarea.getId() == id)
                .findFirst();
    }

    public Tarea guardar(Tarea tarea) {
        if (tarea.getId() == 0) {
            tarea.setId(siguienteId++);
            tareas.add(tarea);
        } else {
            int indice = tareas.indexOf(
                    buscarPorId(tarea.getId()).orElse(null));

            if (indice >= 0) {
                tareas.set(indice, tarea);
            } else {
                tareas.add(tarea);
            }
        }

        return tarea;
    }

    public void eliminar(Tarea tarea) {
        tareas.remove(tarea);
    }
}
