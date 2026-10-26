package com.ejemplo.gestor.service;

import com.ejemplo.gestor.dto.ProyectoPatchRequest;
import com.ejemplo.gestor.dto.ProyectoRequest;
import com.ejemplo.gestor.error.ConflictoException;
import com.ejemplo.gestor.error.RecursoNoEncontradoException;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;
import com.ejemplo.gestor.repository.ProyectoRepository;
import com.ejemplo.gestor.repository.TareaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final TareaRepository tareaRepository;

    public ProyectoService(
            ProyectoRepository proyectoRepository,
            TareaRepository tareaRepository) {
        this.proyectoRepository = proyectoRepository;
        this.tareaRepository = tareaRepository;
    }

    public List<Proyecto> listar(Boolean activo) {
        return proyectoRepository.buscarTodos().stream()
                .filter(proyecto -> activo == null
                        || proyecto.isActivo() == activo)
                .toList();
    }

    public Proyecto buscarPorId(int id) {
        return proyectoRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("proyecto", id));
    }

    public Proyecto crear(ProyectoRequest peticion) {
        comprobarNombreDuplicado(peticion.getNombre(), -1);

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(peticion.getNombre());
        proyecto.setDescripcion(peticion.getDescripcion());
        proyecto.setActivo(peticion.isActivo());
        proyecto.setNumeroDeIncidencias(peticion.getNumeroDeIncidencias());
        proyecto.setFechaInicio(peticion.getFechaInicio());
        proyecto.setFechaFin(peticion.getFechaFin());

        return proyectoRepository.guardar(proyecto);
    }

    public Proyecto reemplazar(int id, ProyectoRequest peticion) {
        buscarPorId(id);
        comprobarNombreDuplicado(peticion.getNombre(), id);

        Proyecto cambios = new Proyecto();
        cambios.setId(id);
        cambios.setNombre(peticion.getNombre());
        cambios.setDescripcion(peticion.getDescripcion());
        cambios.setActivo(peticion.isActivo());
        cambios.setNumeroDeIncidencias(peticion.getNumeroDeIncidencias());
        cambios.setFechaInicio(peticion.getFechaInicio());
        cambios.setFechaFin(peticion.getFechaFin());

        return proyectoRepository.guardar(cambios);
    }

    public Proyecto modificar(int id, ProyectoPatchRequest peticion) {
        Proyecto proyecto = buscarPorId(id);

        if (peticion.getNombre() != null) {
            comprobarNombreDuplicado(peticion.getNombre(), id);
            proyecto.setNombre(peticion.getNombre());
        }

        if (peticion.getDescripcion() != null) {
            proyecto.setDescripcion(peticion.getDescripcion());
        }

        return proyectoRepository.guardar(proyecto);
    }

    public List<Tarea> tareasDelProyecto(int id) {
        buscarPorId(id);

        return tareaRepository.buscarTodas().stream()
                .filter(tarea -> tarea.getProyectoId() == id)
                .toList();
    }

    public void eliminar(int id) {
        Proyecto proyecto = buscarPorId(id);
        proyectoRepository.eliminar(proyecto);
    }

    private void comprobarNombreDuplicado(String nombre, int idExcluido) {
        boolean duplicado = proyectoRepository.buscarTodos().stream()
                .anyMatch(proyecto ->
                        proyecto.getId() != idExcluido
                                && proyecto.getNombre().equalsIgnoreCase(nombre));

        if (duplicado) {
            throw new ConflictoException(
                    "Ya existe un proyecto con el nombre: " + nombre);
        }
    }
}
