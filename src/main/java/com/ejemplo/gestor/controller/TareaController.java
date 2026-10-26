
package com.ejemplo.gestor.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejemplo.gestor.dto.TareaPatchRequest;
import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.error.RecursoNoEncontradoException;
import com.ejemplo.gestor.mapper.TareaMapper;
import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class TareaController {

    private final List<Tarea> tareas;
    private final List<Proyecto> proyectos;
    private final TareaMapper tareaMapper;
    private int siguienteId = 1;

    public TareaController(
            MemoriaProyecto memoria,
            TareaMapper tareaMapper) {
        this.tareas = memoria.getTareas();
        this.proyectos = memoria.getProyectos();
        this.tareaMapper = tareaMapper;
    }

    private Tarea buscar(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }

        return null;
    }

    private Proyecto buscarProyecto(int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }

        return null;
    }

    @GetMapping("/tareas")
    public List<TareaResponse> lista(
            @RequestParam(name = "completada", required = false) Boolean completada,
            @RequestParam(name = "prioridad", required = false) String prioridad,
            @RequestParam(name = "proyectoId", required = false) Integer proyectoId) {

        return tareas.stream()
                .filter(tarea -> completada == null
                        || tarea.isCompletada() == completada)
                .filter(tarea -> prioridad == null
                        || tarea.getPrioridad().equalsIgnoreCase(prioridad))
                .filter(tarea -> proyectoId == null
                        || tarea.getProyectoId() == proyectoId)
                .map(tareaMapper::aResponse)
                .toList();
    }

    @GetMapping("/tareas/{id}")
    public TareaResponse detalle(
            @PathVariable(name = "id") int id) {

        Tarea tarea = buscar(id);

        if (tarea == null) {
            throw new RecursoNoEncontradoException("tarea", id);
        }

        return tareaMapper.aResponse(tarea);
    }

    @PostMapping(
            value = "/tareas",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<TareaResponse> crear(
            @Valid @RequestBody TareaRequest peticion) {

        if (buscarProyecto(peticion.getProyectoId()) == null) {
            throw new RecursoNoEncontradoException(
                    "proyecto", peticion.getProyectoId());
        }

        Tarea tarea = new Tarea();
        tarea.setTitulo(peticion.getTitulo());
        tarea.setPrioridad(peticion.getPrioridad());
        tarea.setProyectoId(peticion.getProyectoId());

        if (peticion.getCompletada() != null) {
            tarea.setCompletada(peticion.getCompletada());
        }

        tarea.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(tarea);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarea.getId())
                .toUri();

        return ResponseEntity.created(ubicacion)
                .body(tareaMapper.aResponse(tarea));
    }

    @PutMapping("/tareas/{id}")
    public TareaResponse modificarCompleta(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaRequest peticion) {

        Tarea existente = buscar(id);

        if (existente == null) {
            throw new RecursoNoEncontradoException("tarea", id);
        }

        if (buscarProyecto(peticion.getProyectoId()) == null) {
            throw new RecursoNoEncontradoException(
                    "proyecto", peticion.getProyectoId());
        }

        Tarea cambios = new Tarea();
        cambios.setId(id);
        cambios.setTitulo(peticion.getTitulo());
        cambios.setPrioridad(peticion.getPrioridad());
        cambios.setProyectoId(peticion.getProyectoId());

        if (peticion.getCompletada() != null) {
            cambios.setCompletada(peticion.getCompletada());
        }

        int indice = tareas.indexOf(existente);
        tareas.set(indice, cambios);

        return tareaMapper.aResponse(cambios);
    }

    @PatchMapping("/tareas/{id}")
    public TareaResponse modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaPatchRequest cambios) {

        Tarea tarea = buscar(id);

        if (tarea == null) {
            throw new RecursoNoEncontradoException("tarea", id);
        }

        if (cambios.getTitulo() != null) {
            tarea.setTitulo(cambios.getTitulo());
        }

        if (cambios.getPrioridad() != null) {
            tarea.setPrioridad(cambios.getPrioridad());
        }

        return tareaMapper.aResponse(tarea);
    }

    @PostMapping(
            value = "/proyectos/{proyectoId}/tareas",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<TareaResponse> crearEnProyecto(
            @PathVariable(name = "proyectoId") int proyectoId,
            @Valid @RequestBody TareaRequest peticion) {

        if (buscarProyecto(proyectoId) == null) {
            throw new RecursoNoEncontradoException(
                    "proyecto", proyectoId);
        }

        Tarea nueva = new Tarea();
        nueva.setTitulo(peticion.getTitulo());
        nueva.setPrioridad(peticion.getPrioridad());
        nueva.setProyectoId(proyectoId);

        if (peticion.getCompletada() != null) {
            nueva.setCompletada(peticion.getCompletada());
        }

        nueva.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(nueva);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/tareas/{id}")
                .buildAndExpand(nueva.getId())
                .toUri();

        return ResponseEntity.created(ubicacion)
                .body(tareaMapper.aResponse(nueva));
    }

    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable(name = "id") int id) {

        Tarea tarea = buscar(id);

        if (tarea == null) {
            throw new RecursoNoEncontradoException("tarea", id);
        }

        tareas.remove(tarea);

        return ResponseEntity.noContent().build();
    }
}
