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
import com.ejemplo.gestor.mapper.TareaMapper;
import com.ejemplo.gestor.model.Tarea;
import com.ejemplo.gestor.service.TareaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class TareaController {

    private final TareaService tareaService;
    private final TareaMapper tareaMapper;

    public TareaController(
            TareaService tareaService,
            TareaMapper tareaMapper) {
        this.tareaService = tareaService;
        this.tareaMapper = tareaMapper;
    }

    @GetMapping("/tareas")
    public List<TareaResponse> lista(
            @RequestParam(name = "completada", required = false) Boolean completada,
            @RequestParam(name = "prioridad", required = false) String prioridad,
            @RequestParam(name = "proyectoId", required = false) Integer proyectoId) {

        return tareaService.listar(completada, prioridad, proyectoId)
                .stream()
                .map(tareaMapper::aResponse)
                .toList();
    }

    @GetMapping("/tareas/{id}")
    public TareaResponse detalle(
            @PathVariable(name = "id") int id) {

        return tareaMapper.aResponse(tareaService.buscarPorId(id));
    }

    @PostMapping(
            value = "/tareas",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<TareaResponse> crear(
            @Valid @RequestBody TareaRequest peticion) {

        Tarea tarea = tareaService.crear(peticion);

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

        return tareaMapper.aResponse(
                tareaService.reemplazar(id, peticion));
    }

    @PatchMapping("/tareas/{id}")
    public TareaResponse modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaPatchRequest cambios) {

        return tareaMapper.aResponse(
                tareaService.modificar(id, cambios));
    }

    @PostMapping(
            value = "/proyectos/{proyectoId}/tareas",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<TareaResponse> crearEnProyecto(
            @PathVariable(name = "proyectoId") int proyectoId,
            @Valid @RequestBody TareaRequest peticion) {

        Tarea nueva = tareaService.crearEnProyecto(proyectoId, peticion);

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

        tareaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
