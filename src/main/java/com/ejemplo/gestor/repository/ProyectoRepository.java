package com.ejemplo.gestor.repository;

import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Proyecto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ProyectoRepository {

    private final List<Proyecto> proyectos;
    private int siguienteId = 1;

    public ProyectoRepository(MemoriaProyecto memoria) {
        this.proyectos = memoria.getProyectos();
    }

    public List<Proyecto> buscarTodos() {
        return proyectos;
    }

    public Optional<Proyecto> buscarPorId(int id) {
        return proyectos.stream()
                .filter(proyecto -> proyecto.getId() == id)
                .findFirst();
    }

    public Proyecto guardar(Proyecto proyecto) {
        if (proyecto.getId() == 0) {
            proyecto.setId(siguienteId++);
            proyectos.add(proyecto);
        } else {
            int indice = proyectos.indexOf(
                    buscarPorId(proyecto.getId()).orElse(null));

            if (indice >= 0) {
                proyectos.set(indice, proyecto);
            } else {
                proyectos.add(proyecto);
            }
        }

        return proyecto;
    }

    public void eliminar(Proyecto proyecto) {
        proyectos.remove(proyecto);
    }
}
