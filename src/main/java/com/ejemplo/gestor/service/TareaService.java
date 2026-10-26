package com.ejemplo.gestor.service;

import com.ejemplo.gestor.dto.TareaPatchRequest;
import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.error.RecursoNoEncontradoException;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;
import com.ejemplo.gestor.repository.ProyectoRepository;
import com.ejemplo.gestor.repository.TareaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;

    public TareaService(
            TareaRepository tareaRepository,
            ProyectoRepository proyectoRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public List<Tarea> listar(
            Boolean completada, String prioridad, Integer proyectoId) {

        return tareaRepository.buscarTodas().stream()
                .filter(tarea -> completada == null
                        || tarea.isCompletada() == completada)
                .filter(tarea -> prioridad == null
                        || tarea.getPrioridad().equalsIgnoreCase(prioridad))
                .filter(tarea -> proyectoId == null
                        || tarea.getProyectoId() == proyectoId)
                .toList();
    }

    public Tarea buscarPorId(int id) {
        return tareaRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("tarea", id));
    }

    public Tarea crear(TareaRequest peticion) {
        comprobarProyecto(peticion.getProyectoId());

        Tarea tarea = new Tarea();
        tarea.setTitulo(peticion.getTitulo());
        tarea.setPrioridad(peticion.getPrioridad());
        tarea.setProyectoId(peticion.getProyectoId());

        if (peticion.getCompletada() != null) {
            tarea.setCompletada(peticion.getCompletada());
        }

        return tareaRepository.guardar(tarea);
    }

    public Tarea crearEnProyecto(int proyectoId, TareaRequest peticion) {
        comprobarProyecto(proyectoId);

        Tarea tarea = new Tarea();
        tarea.setTitulo(peticion.getTitulo());
        tarea.setPrioridad(peticion.getPrioridad());
        tarea.setProyectoId(proyectoId);

        if (peticion.getCompletada() != null) {
            tarea.setCompletada(peticion.getCompletada());
        }

        return tareaRepository.guardar(tarea);
    }

    public Tarea reemplazar(int id, TareaRequest peticion) {
        buscarPorId(id);
        comprobarProyecto(peticion.getProyectoId());

        Tarea cambios = new Tarea();
        cambios.setId(id);
        cambios.setTitulo(peticion.getTitulo());
        cambios.setPrioridad(peticion.getPrioridad());
        cambios.setProyectoId(peticion.getProyectoId());

        if (peticion.getCompletada() != null) {
            cambios.setCompletada(peticion.getCompletada());
        }

        return tareaRepository.guardar(cambios);
    }

    public Tarea modificar(int id, TareaPatchRequest peticion) {
        Tarea tarea = buscarPorId(id);

        if (peticion.getTitulo() != null) {
            tarea.setTitulo(peticion.getTitulo());
        }

        if (peticion.getPrioridad() != null) {
            tarea.setPrioridad(peticion.getPrioridad());
        }

        return tareaRepository.guardar(tarea);
    }

    public void eliminar(int id) {
        Tarea tarea = buscarPorId(id);
        tareaRepository.eliminar(tarea);
    }

    private Proyecto comprobarProyecto(int proyectoId) {
        return proyectoRepository.buscarPorId(proyectoId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("proyecto", proyectoId));
    }
}
