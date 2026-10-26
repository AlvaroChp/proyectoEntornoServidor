# Auditoría del contrato de la API

**Proyecto:** Gestor de proyectos y tareas
**Fecha:** 23/10/2026

## 1. Rúbrica de auditoría

| Criterio | Sesión 9 | Hoy |
|---|---|---|
| 1. Las rutas representan recursos y no acciones | Sí | Sí |
| 2. Los recursos tienen una dirección estable | Sí | Sí |
| 3. Las colecciones utilizan nombres en plural | Sí | Sí |
| 4. Los métodos HTTP indican la operación que se realiza | Sí | Sí |
| 5. Las peticiones GET no modifican los datos | Sí | Sí |
| 6. Se utilizan códigos HTTP adecuados | Sí | Sí |
| 7. Las relaciones entre proyectos y tareas se consultan de forma coherente | Sí | Sí |
| 8. Los mismos datos mantienen nombres y tipos coherentes en las respuestas | Sí | Sí |
| 9. Se evita devolver campos internos del modelo | Sí | Sí |
| 10. Las respuestas incluyen enlaces para navegar entre recursos | No | No |

## 2. Comparación entre la sesión 9 y hoy

La API ya funcionaba en la sesión 9. En esta sesión se ha trabajado en mejorar la organización del código mediante DTO y mappers, separar los modelos internos de las respuestas de la API y unificar los errores utilizando ProblemDetail. También se han revisado las validaciones y se ha documentado el resultado de varias pruebas, incluyendo errores HTTP, fechas incoherentes y conflictos por nombres de proyectos duplicados.

La API sigue sin implementar hipermedia, por lo que el criterio 10 continúa sin cumplirse. Las pruebas realizadas están documentadas en `resultado-pruebas-api.md`.