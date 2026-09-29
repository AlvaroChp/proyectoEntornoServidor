# Gestor de proyectos y tareas

Aplicación web desarrollada con Spring Boot para gestionar proyectos y tareas en memoria.

## Requisitos

* Java 21
* Maven Wrapper incluido en el proyecto

## Arranque

Desde la carpeta del proyecto:

```bash
./mvnw spring-boot:run
```

La aplicación se inicia en:

```text
http://localhost:8080
```

## API

### Proyectos

```text
GET    /proyectos
GET    /proyectos/{id}
POST   /proyectos
PUT    /proyectos/{id}
PATCH  /proyectos/{id}
DELETE /proyectos/{id}
GET    /proyectos/{id}/tareas
```

### Tareas

```text
GET    /tareas
GET    /tareas/{id}
POST   /tareas
PUT    /tareas/{id}
PATCH  /tareas/{id}
DELETE /tareas/{id}
```

El endpoint `GET /proyectos/{id}/tareas` devuelve las tareas asociadas a un proyecto. Si el proyecto existe pero no tiene tareas, devuelve `200` con un array vacío. Si el proyecto no existe, devuelve `404`.

## Postman

La colección y el entorno de Postman se encuentran en la carpeta `postman/`:

* `CRUD-en-memoria.postman_collection.json`
* `Local.postman_environment.json`

El entorno utiliza:

```text
baseUrl = http://localhost:8080
```

La colección incluye las pruebas de los endpoints de proyectos, tareas y de la relación entre ambos.

## Datos en memoria

Los proyectos y las tareas se almacenan únicamente en memoria mediante `MemoriaProyecto`.

Por este motivo, **todos los datos se pierden al reiniciar la aplicación** y los identificadores vuelven a empezar desde `1`.

## Comportamiento de la API

* Al crear un proyecto o una tarea, el servidor asigna automáticamente su `id`.
* En las actualizaciones, el `id` utilizado es el que aparece en la URL.
* Las peticiones `DELETE` devuelven `204 No Content`, incluso si el recurso indicado no existe.
* Si un proyecto no tiene tareas, `/proyectos/{id}/tareas` devuelve `200` con `[]`.
* Si el proyecto no existe, `/proyectos/{id}/tareas` devuelve `404`.
* Los cuerpos JSON se procesan mediante Jackson y los campos deben corresponder con los atributos esperados por los modelos.

## Pruebas

Las pruebas se han realizado mediante la colección de Postman, incluyendo la creación de proyectos y tareas, la relación entre ambos, el caso de un proyecto sin tareas y la respuesta `404` cuando el proyecto no existe.

También se ha comprobado la colección después de reiniciar la aplicación, teniendo en cuenta que los datos almacenados en memoria se pierden al reiniciar.
