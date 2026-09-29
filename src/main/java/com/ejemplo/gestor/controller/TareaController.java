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

import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Tarea;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final List<Tarea> tareas;
    private int siguienteId = 1;

    public TareaController(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
    }

    @GetMapping
    public List<Tarea> lista() {
        return tareas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> detalle(@PathVariable(name = "id") int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return ResponseEntity.ok(tarea);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea) {
        tarea.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(tarea);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarea.getId())
                .toUri();

        return ResponseEntity.created(ubicacion).body(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> modificarCompleta(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea cambios) {

        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                cambios.setId(id);
                tareas.set(i, cambios);

                return ResponseEntity.ok(cambios);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tarea> modificar(
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

                return ResponseEntity.ok(tarea);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
        return ResponseEntity.noContent().build();
    }
}