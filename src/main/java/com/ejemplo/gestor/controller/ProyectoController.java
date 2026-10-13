
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
import com.ejemplo.gestor.error.ConflictoException;
import com.ejemplo.gestor.error.RecursoNoEncontradoException;
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

    private Proyecto buscar(int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }

        return null;
    }

    private void comprobarNombreDuplicado(String nombre, int idExcluido) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() != idExcluido
                    && proyecto.getNombre().equalsIgnoreCase(nombre)) {
                throw new ConflictoException(
                        "Ya existe un proyecto con el nombre: " + nombre);
            }
        }
    }

    @GetMapping
    public List<Proyecto> lista() {
        return proyectos;
    }

    @GetMapping("/{id}")
    public Proyecto detalle(@PathVariable(name = "id") int id) {
        Proyecto proyecto = buscar(id);

        if (proyecto == null) {
            throw new RecursoNoEncontradoException("proyecto", id);
        }

        return proyecto;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Proyecto> crear(
            @Valid @RequestBody ProyectoRequest peticion) {

        comprobarNombreDuplicado(peticion.getNombre(), -1);

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(peticion.getNombre());
        proyecto.setDescripcion(peticion.getDescripcion());
        proyecto.setActivo(peticion.isActivo());
        proyecto.setNumeroDeIncidencias(peticion.getNumeroDeIncidencias());
        proyecto.setFechaInicio(peticion.getFechaInicio());
        proyecto.setFechaFin(peticion.getFechaFin());

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
    public Proyecto modificarCompleta(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoRequest peticion) {

        Proyecto existente = buscar(id);

        if (existente == null) {
            throw new RecursoNoEncontradoException("proyecto", id);
        }

        comprobarNombreDuplicado(peticion.getNombre(), id);

        Proyecto cambios = new Proyecto();
        cambios.setId(id);
        cambios.setNombre(peticion.getNombre());
        cambios.setDescripcion(peticion.getDescripcion());
        cambios.setActivo(peticion.isActivo());
        cambios.setNumeroDeIncidencias(peticion.getNumeroDeIncidencias());
        cambios.setFechaInicio(peticion.getFechaInicio());
        cambios.setFechaFin(peticion.getFechaFin());

        int indice = proyectos.indexOf(existente);
        proyectos.set(indice, cambios);

        return cambios;
    }

    @PatchMapping("/{id}")
    public Proyecto modificarParcial(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoPatchRequest cambios) {

        Proyecto proyecto = buscar(id);

        if (proyecto == null) {
            throw new RecursoNoEncontradoException("proyecto", id);
        }

        if (cambios.getNombre() != null) {
            comprobarNombreDuplicado(cambios.getNombre(), id);
            proyecto.setNombre(cambios.getNombre());
        }

        if (cambios.getDescripcion() != null) {
            proyecto.setDescripcion(cambios.getDescripcion());
        }

        return proyecto;
    }

    @GetMapping("/{id}/tareas")
    public List<TareaResponse> tareasDelProyecto(
            @PathVariable(name = "id") int id) {

        Proyecto proyecto = buscar(id);

        if (proyecto == null) {
            throw new RecursoNoEncontradoException("proyecto", id);
        }

        List<TareaResponse> resultado = new ArrayList<>();

        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == id) {
                resultado.add(TareaResponse.desde(tarea));
            }
        }

        return resultado;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable(name = "id") int id) {

        Proyecto proyecto = buscar(id);

        if (proyecto == null) {
            throw new RecursoNoEncontradoException("proyecto", id);
        }

        proyectos.remove(proyecto);

        return ResponseEntity.noContent().build();
    }
}
