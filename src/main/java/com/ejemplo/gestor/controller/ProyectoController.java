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

import com.ejemplo.gestor.model.Proyecto;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final List<Proyecto> proyectos = new ArrayList<>();

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

    @PostMapping
    public ResponseEntity<Proyecto> crear(@RequestBody Proyecto proyecto) {
        proyectos.add(proyecto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/proyectos/" + proyecto.getId())
                .body(proyecto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> modificarCompleta(
            @PathVariable(name = "id") int id,
            @RequestBody Proyecto cambios) {

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                proyecto.setNombre(cambios.getNombre());
                proyecto.setDescripcion(cambios.getDescripcion());
                proyecto.setActivo(cambios.isActivo());
                proyecto.setNumeroDeIncidencias(cambios.getNumeroDeIncidencias());

                return ResponseEntity.ok(proyecto);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Proyecto> modificarParcial(
            @PathVariable(name = "id") int id,
            @RequestBody Proyecto cambios) {

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {

                if (cambios.getNombre() != null) {
                    proyecto.setNombre(cambios.getNombre());
                }

                if (cambios.getDescripcion() != null) {
                    proyecto.setDescripcion(cambios.getDescripcion());
                }

                proyecto.setActivo(cambios.isActivo());

                return ResponseEntity.ok(proyecto);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable(name = "id") int id) {

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                proyectos.remove(proyecto);
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }
}