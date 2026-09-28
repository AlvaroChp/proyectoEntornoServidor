package com.ejemplo.gestor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.ejemplo.gestor.model.Tarea;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final List<Tarea> tareas = new ArrayList<>();

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

    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea) {
        tareas.add(tarea);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/tareas/" + tarea.getId())
                .body(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> modificarCompleta(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea cambios) {

        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                tarea.setTitulo(cambios.getTitulo());
                tarea.setPrioridad(cambios.getPrioridad());
                tarea.setCompletada(cambios.isCompletada());

                return ResponseEntity.ok(tarea);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public Tarea modificar(@PathVariable(name = "id") int id,
            @RequestBody Tarea cambios) {

        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {

                if (cambios.getTitulo() != null) {
                    tarea.setTitulo(cambios.getTitulo());
                }

                if (cambios.getPrioridad() != null) {
                    tarea.setPrioridad(cambios.getPrioridad());
                }

                return tarea;
            }
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable(name = "id") int id) {

        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                tareas.remove(tarea);
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

}