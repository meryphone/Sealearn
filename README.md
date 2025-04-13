# Integrantes

Eva María López Medina

María Capilla Zapata

Profesor: Jesús Sánchez Cuadrado

# SeaLearn

## Descripción

Consiste en la aplicación de aprendizaje perfecta para aprender los conceptos básicos de la asignatura Tecnologías y Desarrollo de Software. Dispone de distintos niveles de dificultad, estilos de aprendizaje, control de estadísticas y un alegre acompañante que te acompañará en estas lecciones.


# ProyectoPDS

## Modelado del dominio

![Diagrama_clases_sealify](https://github.com/user-attachments/assets/c98fcb03-696d-41f4-b362-624e2ceb719d)

## Modelado del dominio (entidades)

![IMG_4096](https://github.com/user-attachments/assets/77f74fa6-6172-443d-bfa5-78d592bbe27c)

## Casos de uso

### Seleccionar curso

- Precondición: Debe existir al menos un curso en la biblioteca interna.
- Flujo básico:
  - 1. El usuario accede a la pantalla de selección de cursos.
  - 2. El sistema muestra la lista de cursos disponibles.
  - 3. El usuario selecciona un curso de la lista.
  - 4. El sistema muestra una breve descripción del curso.
  - 5. El usuario selecciona una dificultad.
  - 6. El usuario pulsa el botón para comenzar el curso.
  - 7. El sistema carga el curso seleccionado y muestra la primera pregunta o pantalla de aprendizaje.
- Postcondición: El curso queda cargado y listo para ser ejecutado.

### Realizar curso

- Flujo básico:
  - 1. El sistema muestra una pregunta del curso.
  - 2. El usuario responde la pregunta o interactúa con la pantalla de aprendizaje.
  - 3. El sistema valida la respuesta y muestra retroalimentación.
  - 4. El sistema registra el progreso del usuario.
  - 5. Se repite el proceso hasta finalizar el curso o que el usuario decida salir.
- Postcondición: El sistema guarda el progreso del usuario en el curso.

### Elegir estrategia de aprendizaje 

- Flujo básico:
  - 1. El usuario accede a la configuración del curso antes de iniciarlo.
  - 2. El sistema muestra las estrategias de aprendizaje disponibles.
  - 3. El usuario selecciona una estrategia.
  - 4. El sistema ajusta la lógica de presentación de preguntas según la estrategia elegida.
- Postcondición: El curso sigue la estrategia de aprendizaje seleccionada.

### Reanudar curso

- Precondición: Debe existir un progreso guardado de un curso previamente iniciado.
- Flujo básico:
  - 1. El usuario selecciona un curso.
  - 2. El sistema muestra la opción para continuar desde el último punto guardado.
  - 3. El usuario selecciona reanudar el curso.
  - 4. El sistema carga el curso y muestra la última pregunta o pantalla de aprendizaje en la que se encontraba el usuario.
-Postcondición: El usuario puede continuar el curso desde donde lo dejó.

### Exportar feedback

- Precondición: El curso debe estar completado.
- Flujo básico:
  - 1. El usuario accede a la opción de exportar cursos.
  - 2. El sistema muestra la lista de cursos disponibles para exportar.
  - 3. El usuario selecciona un curso.
  - 4. El usuario selecciona una ruta destino.
  - 5. El sistema genera un archivo PDF con el feedback del curso en la ruta indicada.

### Importar curso

- Flujo básico:
  - 1. El usuario accede a la opción de importar cursos.
  - 2. El sistema solicita al usuario seleccionar un archivo JSON/YAML con la estructura de un curso válido.
  - 3. El usuario selecciona el archivo y el sistema lo valida. 
  - 4. Si el archivo es correcto, el sistema lo añade a la biblioteca interna.
  - 5. Si ha habido un errror en la importación se lanzará una excepción.
- Postcondición: El curso importado se almacena y está disponible para su uso.

### Exportar curso

- Flujo básico:
  - 1. El usuario accede a la opción de exportar cursos.
  - 2. El sistema muestra los cursos de la biblioteca interna.
  - 3. El usuario selecciona un curso a exportar.
  - 4. El usuario indica la ruta destino de la exportación.
  - 5. El sistema genera un archivo YAML/JSON en el directorio elegido.

### Consultar estadísticas

- Flujo básico:
  - 1. El usuario accede a la sección de estadísticas.
  - 2. El sistema muestra información como tiempo de uso, racha de días consecutivos de aprendizaje, cantidad de cursos completados, etc.
  - 3. El usuario revisa sus estadísticas.
  
