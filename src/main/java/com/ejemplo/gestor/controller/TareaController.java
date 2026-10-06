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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class TareaController {

    private final List<Tarea> tareas;
    private final List<Proyecto> proyectos;
    private int siguienteId = 1;

    public TareaController(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
        this.proyectos = memoria.getProyectos();
    }

    @GetMapping("/tareas")
    public List<TareaResponse> lista() {
        return tareas.stream()
                .map(TareaResponse::desde)
                .toList();
    }

    @GetMapping("/tareas/{id}")
    public ResponseEntity<TareaResponse> detalle(
            @PathVariable(name = "id") int id) {

        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return ResponseEntity.ok(TareaResponse.desde(tarea));
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(
            value = "/tareas",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<TareaResponse> crear(
            @Valid @RequestBody TareaRequest peticion) {

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
                .body(TareaResponse.desde(tarea));
    }

    @PutMapping("/tareas/{id}")
    public ResponseEntity<TareaResponse> modificarCompleta(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaRequest peticion) {

        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {

                Tarea cambios = new Tarea();
                cambios.setId(id);
                cambios.setTitulo(peticion.getTitulo());
                cambios.setPrioridad(peticion.getPrioridad());
                cambios.setProyectoId(peticion.getProyectoId());

                if (peticion.getCompletada() != null) {
                    cambios.setCompletada(peticion.getCompletada());
                }

                tareas.set(i, cambios);

                return ResponseEntity.ok(TareaResponse.desde(cambios));
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/tareas/{id}")
    public ResponseEntity<TareaResponse> modificar(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea cambios) {

        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {

                if (cambios.getTitulo() != null) {
                    tarea.setTitulo(cambios.getTitulo());
                }

                if (cambios.getPrioridad() != null) {
                    tarea.setPrioridad(cambios.getPrioridad());
                }

                return ResponseEntity.ok(TareaResponse.desde(tarea));
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(
            value = "/proyectos/{proyectoId}/tareas",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<TareaResponse> crearEnProyecto(
            @PathVariable(name = "proyectoId") int proyectoId,
            @RequestBody Tarea nueva) {

        boolean existe = false;

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == proyectoId) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            return ResponseEntity.notFound().build();
        }

        nueva.setId(siguienteId++);
        nueva.setProyectoId(proyectoId);
        tareas.add(nueva);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/tareas/{id}")
                .buildAndExpand(nueva.getId())
                .toUri();

        return ResponseEntity.created(ubicacion)
                .body(TareaResponse.desde(nueva));
    }

    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable(name = "id") int id) {

        tareas.removeIf(tarea -> tarea.getId() == id);
        return ResponseEntity.noContent().build();
    }
}