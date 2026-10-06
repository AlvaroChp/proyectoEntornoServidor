package com.ejemplo.gestor.controller;

import java.net.URI;
import java.util.ArrayList;
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

import com.ejemplo.gestor.dto.ProyectoPatchRequest;
import com.ejemplo.gestor.dto.ProyectoRequest;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final List<Proyecto> proyectos;
    private final List<Tarea> tareas;
    private int siguienteId = 1;

    public ProyectoController(MemoriaProyecto memoria) {
        this.proyectos = memoria.getProyectos();
        this.tareas = memoria.getTareas();
    }

    @GetMapping
    public List<Proyecto> lista() {
        return proyectos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> detalle(@PathVariable(name = "id") int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return ResponseEntity.ok(proyecto);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Proyecto> crear(
            @Valid @RequestBody ProyectoRequest peticion) {

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(peticion.getNombre());
        proyecto.setDescripcion(peticion.getDescripcion());
        proyecto.setActivo(peticion.isActivo());
        proyecto.setNumeroDeIncidencias(peticion.getNumeroDeIncidencias());

        proyecto.setId(siguienteId);
        siguienteId = siguienteId + 1;
        proyectos.add(proyecto);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proyecto.getId())
                .toUri();

        return ResponseEntity.created(ubicacion).body(proyecto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> modificarCompleta(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoRequest peticion) {

        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getId() == id) {

                Proyecto cambios = new Proyecto();
                cambios.setId(id);
                cambios.setNombre(peticion.getNombre());
                cambios.setDescripcion(peticion.getDescripcion());
                cambios.setActivo(peticion.isActivo());
                cambios.setNumeroDeIncidencias(peticion.getNumeroDeIncidencias());

                proyectos.set(i, cambios);

                return ResponseEntity.ok(cambios);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Proyecto> modificarParcial(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoPatchRequest cambios) {

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {

                if (cambios.getNombre() != null) {
                    proyecto.setNombre(cambios.getNombre());
                }

                if (cambios.getDescripcion() != null) {
                    proyecto.setDescripcion(cambios.getDescripcion());
                }

                return ResponseEntity.ok(proyecto);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/tareas")
    public ResponseEntity<List<TareaResponse>> tareasDelProyecto(
            @PathVariable(name = "id") int id) {

        boolean existe = false;

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            return ResponseEntity.notFound().build();
        }

        List<TareaResponse> resultado = new ArrayList<>();

        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == id) {
                resultado.add(TareaResponse.desde(tarea));
            }
        }

        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        proyectos.removeIf(proyecto -> proyecto.getId() == id);
        return ResponseEntity.noContent().build();
    }
}