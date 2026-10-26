# Registro de pruebas de la API

**Proyecto:** Gestor de proyectos y tareas
**Fecha:** 21/10/2026
**Entorno:** Spring Boot, ejecutado en local (`localhost:8080`)

## 1. Errores HTTP

| Prueba | Petición | Estado obtenido | Resultado |
|---|---|---:|---|
| Ruta inexistente | `GET /ruta-inexistente` | 404 | Correcto |
| Método no permitido | `PATCH /tareas` | 405 | Correcto |
| Tipo de contenido no admitido | `POST /tareas` con `text/plain` | 415 | Correcto |
| JSON mal formado | `POST /tareas` con JSON inválido | 400 | Correcto |

En todos los casos se recibió `Content-Type: application/problem+json`, con el estado, la ruta y un mensaje de error.

## 2. Validación de campos

| Prueba | Datos enviados | Estado obtenido | Resultado |
|---|---|---:|---|
| Título vacío | `titulo: ""` y prioridad válida | 400 | Correcto |
| Prioridad inválida | `prioridad: "urgente"` | 400 | Correcto |

La respuesta incluye `invalidParams`, con el campo y el motivo de validación.

## 3. Validación personalizada

| Prueba | Datos enviados | Estado obtenido | Resultado |
|---|---|---:|---|
| Fecha de fin anterior a la inicial | Inicio: `2026-10-20`; fin: `2026-10-10` | 400 | Correcto |
| Fechas coherentes | Inicio: `2026-10-10`; fin: `2026-10-20` | 201 | Correcto |

La anotación personalizada `@FechasCoherentes` rechaza las fechas incoherentes y permite crear el proyecto cuando las fechas son válidas.

## 4. Recursos inexistentes y conflictos

| Prueba | Petición | Estado obtenido | Resultado |
|---|---|---:|---|
| Proyecto inexistente | `GET /proyectos/99999` | 404 | Correcto |
| Proyecto duplicado | `POST /proyectos` con el mismo nombre | 409 | Correcto |

Las respuestas identifican el recurso inexistente o explican el conflicto por nombre duplicado.

## 5. Conclusión

Las pruebas realizadas confirman el funcionamiento de los errores HTTP comprobados, la validación de campos, la validación personalizada de fechas y el conflicto por nombre duplicado.

**Pendiente de comprobar:** el formato de los errores internos `500` mediante una prueba específica y cualquier otro caso que todavía no se haya verificado expresamente.

### Pruebas de filtros opcionales

Se han comprobado los filtros de los listados mediante peticiones HTTP a la API.

- `GET /proyectos?activo=true`: devuelve el proyecto activo creado para la prueba (`200 OK`).
- `GET /proyectos?activo=false`: devuelve una lista vacía (`200 OK`).
- `GET /tareas?completada=false`: devuelve la tarea pendiente (`200 OK`).
- `GET /tareas?completada=true`: devuelve una lista vacía (`200 OK`).
- `GET /tareas?prioridad=alta`: devuelve la tarea con prioridad alta (`200 OK`).
- `GET /tareas?proyectoId=1`: devuelve la tarea asociada al proyecto 1 (`200 OK`).

**Resultado:** los seis casos comprobados han devuelto los resultados esperados.