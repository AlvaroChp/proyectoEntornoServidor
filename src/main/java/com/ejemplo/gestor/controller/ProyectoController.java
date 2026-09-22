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

import com.ejemplo.gestor.model.Proyecto;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final List<Proyecto> proyectos = new ArrayList<>();
    private int siguienteId = 1;

    @GetMapping
    public List<Proyecto> lista(
            @RequestParam(name = "activo", required = false) Boolean activo) {

        if (activo == null) {
            return proyectos;
        }

        List<Proyecto> resultado = new ArrayList<>();

        for (Proyecto proyecto : proyectos) {
            if (proyecto.isActivo() == activo) {
                resultado.add(proyecto);
            }
        }

        return resultado;
    }

    @PostMapping
    public Proyecto crear(@RequestBody Proyecto proyecto) {
        proyecto.setId(siguienteId);
        siguienteId = siguienteId + 1;
        proyectos.add(proyecto);
        return proyecto;
    }

    @GetMapping("/{id}")
    public Proyecto detalle(@PathVariable(name = "id") int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }
        return null;
    }

    @PutMapping("/{id}")
    public Proyecto actualizar(
            @PathVariable(name = "id") int id,
            @RequestBody Proyecto datos) {

        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getId() == id) {
                datos.setId(id);
                proyectos.set(i, datos);
                return datos;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable(name = "id") int id) {
        proyectos.removeIf(proyecto -> proyecto.getId() == id);
    }
}