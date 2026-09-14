package com.ejemplo.gestor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {
    @GetMapping("/hola")
    public String hola() {
        return "Hola, Álvaro. Bienvenido a Spring Boot!";
    }

    @GetMapping("/prueba")
    public String prueba() {
        return "Esta es una prueba de la aplicación Spring Boot";
    }

    @GetMapping("/prueba/int")
    public int pruebaInt() {
        return 288;
    }

    @GetMapping("/estado")
    public String estado() {
        return "Servidor en funcionamiento";
    }

    @GetMapping("/prestamos/resumen")
    public String resumen() {
        return "Esta aplicación gestionará los préstamos de material";
    }
}