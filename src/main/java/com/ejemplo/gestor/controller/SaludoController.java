package com.ejemplo.gestor.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoController {

    @GetMapping("/saludo")
    public String saludo(
            @RequestParam(name = "nombre", defaultValue = "mundo") String nombre) {
        return "Hola, " + nombre + ".";
    }

    @GetMapping("/incidencias")
    public String buscar(
            @RequestParam(name = "estado", defaultValue = "todas") String estado,
            @RequestParam(name = "pagina", defaultValue = "1") int pagina) {
        return "Buscando incidencias con estado " + estado
                + ", página " + pagina;
    }

    @GetMapping("/informes")
    public String informes(
            @RequestParam(name = "desde") LocalDate desde,
            @RequestParam(name = "activo", defaultValue = "true") boolean activo) {
        return "Desde " + desde + " (día " + desde.getDayOfMonth()
                + " del mes " + desde.getMonthValue() + "), activo=" + activo;
    }

}