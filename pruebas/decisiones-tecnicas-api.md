# Decisiones técnicas de la API

**Proyecto:** Gestor de proyectos y tareas

## 1. Separación entre modelos y DTO

He utilizado DTO para definir los datos que recibe y devuelve la API, en lugar de utilizar directamente las clases del modelo. También he creado mappers para convertir los modelos en sus correspondientes respuestas.

En `ProyectoRequest` no incluyo el identificador del proyecto, porque lo gestiona la API. En cambio, `TareaRequest` incluye `proyectoId`, ya que es necesario indicar a qué proyecto pertenece la tarea.

Los DTO de respuesta tienen los campos que necesita conocer el cliente. `ProyectoResponse` incluye el identificador, el nombre, la descripción, el estado, el número de incidencias y las fechas. `TareaResponse` incluye el identificador, el título, la prioridad, el estado de finalización y el identificador del proyecto.

Esta separación permite controlar qué datos se reciben y se publican, sin exponer directamente las clases internas del modelo.

## 2. Validación personalizada

Además de las validaciones habituales de los campos, he utilizado la anotación personalizada `@FechasCoherentes` para comprobar que la fecha de fin de un proyecto no sea anterior a la fecha de inicio.

Una anotación como `@Pattern` permite comprobar si un valor cumple un patrón, pero no es suficiente para comparar dos fechas distintas. Por eso se necesita una validación que compruebe la relación entre ambos campos.

## 3. Formato común de errores

He centralizado la gestión de errores en `ManejadorDeErrores` y utilizo `ProblemDetail` para devolver respuestas con una estructura común.

Las respuestas incluyen información como el estado HTTP, el mensaje del error y la ruta de la petición. En los errores de validación también se incluye información sobre los campos que no cumplen las condiciones. Esto facilita entender qué ha ocurrido y corregir la petición.

## 4. Uso del código HTTP 409

Utilizo el código `409 Conflict` cuando se intenta crear un proyecto con un nombre que ya existe.

No corresponde utilizar `400` porque la petición tiene una estructura válida, pero entra en conflicto con el estado actual de los datos. Tampoco corresponde `404`, porque el recurso no falta: el problema es que ya existe otro proyecto con ese nombre.

## 5. Nivel de madurez de la API

La API utiliza recursos, métodos HTTP y códigos de respuesta para representar las operaciones, por lo que sigue un enfoque de nivel 2 del modelo de madurez de Richardson.

No he implementado hipermedia, que permitiría incluir enlaces en las respuestas para descubrir otras operaciones disponibles. Por eso no se considera que la API alcance el nivel 3. Esta mejora queda fuera del alcance de esta actividad.