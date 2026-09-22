package com.ejemplo.gestor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.gestor.model.Tarea;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final List<Tarea> tareas = new ArrayList<>();
    private int siguienteId = 1;

    @PostMapping
    public Tarea crear(@RequestBody Tarea tarea) {
        tarea.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(tarea);
        return tarea;
    }

    @GetMapping
    public List<Tarea> lista(
            @RequestParam(name = "completada", required = false) Boolean completada) {
        if (completada == null) {
            return tareas;
        }
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.isCompletada() == completada) {
                resultado.add(tarea);
            }
        }
        return resultado;
    }

    @GetMapping("/{id}")
    public Tarea detalle(@PathVariable(name = "id") int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }

    @PutMapping("/{id}")
    public Tarea actualizar(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea datos) {

        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                datos.setId(id);
                tareas.set(i, datos);
                return datos;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable(name = "id") int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
    }

}