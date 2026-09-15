package com.ejemplo.gestor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @GetMapping
    public String lista(
            @RequestParam(name = "estado", defaultValue = "todos") String estado) {
        return "Lista de proyectos con estado " + estado;
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable(name = "id") int id) {
        return "Ficha del proyecto " + id;
    }

    @GetMapping("/{id}/incidencias")
    public String incidencias(@PathVariable(name = "id") int id) {
        return "Incidencias del proyecto " + id;
    }

    @GetMapping("/{proyectoId}/incidencias/{incidenciaId}")
    public String incidenciaConcreta(
            @PathVariable(name = "proyectoId") int proyectoId,
            @PathVariable(name = "incidenciaId") int incidenciaId) {
        return "Incidencia " + incidenciaId + " del proyecto " + proyectoId;
    }

    @GetMapping("/incidencias")
    public String buscarIncidencias(
            @RequestParam(name = "prioridad") String prioridad,
            @RequestParam(name = "pagina") int pagina) {
        return "Incidencias con prioridad " + prioridad
                + " en la página " + pagina;
    }

}
