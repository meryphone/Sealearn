# Integrantes

Eva María López Medina

María Capilla Zapata

María Mercader Sánchez

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

- **Actor**: Usuario
- **Precondición**: Debe existir al menos un curso en la biblioteca interna.
- **Flujo básico**:
  1. El usuario accede a la pantalla de selección de cursos.
  2. El sistema muestra la lista de cursos disponibles.
  3. El usuario selecciona un curso de la lista.
  4. El sistema muestra una breve descripción del curso.
  5. El usuario selecciona una dificultad.
  6. El usuario selecciona una estrategia de aprendizaje.
  7. El usuario pulsa el botón para comenzar el curso.
  8. El sistema carga el curso seleccionado y muestra la primera pregunta o pantalla de aprendizaje.
- **Postcondición**: El curso queda cargado y listo para ser ejecutado siguiendo la estrategia de aprendizaje elegida.
- **Flujos alternativos**:
  - 3a. No existen cursos disponibles: el sistema informa al usuario de que debe importar o crear cursos primero.

---

### Realizar curso

- **Actor**: Usuario
- **Precondición**: El usuario ha seleccionado previamente un curso y una estrategia de aprendizaje.
- **Flujo básico**:
  1. El sistema muestra una pregunta o pantalla de aprendizaje del curso.
  2. El usuario responde la pregunta o interactúa con la pantalla.
  3. El sistema valida la respuesta y muestra retroalimentación.
  4. El sistema registra el progreso del usuario.
  5. Se repite el proceso hasta finalizar el curso o que el usuario decida salir.
- **Postcondición**: El progreso del usuario se guarda en el sistema.
- **Flujos alternativos**:
  - 2a. El usuario decide abandonar el curso: se guarda el progreso actual automáticamente.

---

### Reanudar curso

- **Actor**: Usuario
- **Precondición**: Debe existir un progreso guardado de un curso previamente iniciado.
- **Flujo básico**:
  1. El usuario accede a la pantalla de cursos y selecciona uno con progreso guardado.
  2. El sistema ofrece la opción de reanudar el curso.
  3. El usuario confirma reanudar.
  4. El sistema carga el curso desde la última pregunta o pantalla guardada.
  5. Se continúa con el caso de uso "Realizar curso".
- **Postcondición**: El usuario continúa el curso desde el punto donde lo dejó.

---

### Reiniciar curso

- **Actor**: Usuario
- **Precondición**: Debe existir un progreso guardado de un curso previamente iniciado.
- **Flujo básico**:
  1. El usuario accede a la pantalla de cursos y selecciona uno con progreso guardado.
  2. El sistema ofrece la opción de empezar de nuevo el curso.
  3. El usuario confirma empezar de nuevo.
  4. El sistema carga el curso desde la primera pregunta o pantalla del curso, con la misma estrategia y dificultad elegida.
  5. Se continúa con el caso de uso "Realizar curso".
- **Postcondición**: El curso seleccionado se reinicia desde el principio, manteniendo la estrategia y dificultad previamente configuradas.

---

### Importar curso

- **Actor**: Usuario
- **Flujo básico**:
  1. El usuario accede a la opción de importar cursos.
  2. El sistema solicita seleccionar un archivo JSON o YAML.
  3. El usuario selecciona un archivo.
  4. El sistema valida el formato y la estructura del archivo.
  5. Si el archivo es válido, el curso se añade a la biblioteca interna.
  6. Si el archivo no es válido, el sistema muestra un error.
- **Postcondición**: El curso importado queda disponible en la biblioteca interna.
- **Flujos alternativos**:
  - 4a. Si ocurre un error al generar el PDF, el sistema muestra un mensaje de error y no crea el archivo.

---

### Eliminar curso

- **Actor**: Usuario
- **Precondición**: Debe existir al menos un curso almacenado en la biblioteca interna.
- **Flujo básico**:
  1. El usuario accede a la sección de gestión de cursos o a la biblioteca interna.
  2. El sistema muestra la lista de cursos disponibles.
  3. El usuario selecciona el curso que desea eliminar.
  4. El sistema solicita confirmación para eliminar el curso.
  5. El usuario confirma la eliminación.
  6. El sistema elimina el curso de la biblioteca interna.
- **Postcondición**: El curso seleccionado es eliminado de la biblioteca interna del sistema.

- **Flujos alternativos**:
  - 5a. El usuario cancela la eliminación: el sistema mantiene el curso sin cambios.

---

### Exportar estadísticas

- **Actor**: Usuario
- **Precondición**: El usuario se encuentra en la ventana principal.
- **Flujo básico**:
  1. El usuario accede a la opción de exportar estadísticas.
  2. El sistema muestra una ventana para seleccionar el destino del pdf por generar.
  3. El usuario selecciona una ruta de destino.
  4. El sistema genera un archivo PDF con las estadísticas del curso en la ruta indicada.
- **Postcondición**: Se crea un archivo PDF con el resumen del curso.

---

### Consultar estadísticas

- **Actor**: Usuario
- **Flujo básico**:
  1. El usuario accede a la sección de estadísticas.
  2. El sistema muestra datos como: tiempo de uso, racha de días consecutivos, número de cursos completados, etc.
  3. El usuario revisa sus estadísticas.
- **Postcondición**: El usuario visualiza su progreso y hábitos de estudio.

---

### Restablecer estadísticas

- **Actor**: Usuario
- **Precondición**: El usuario se encuentra en la ventana principal y tener estadísticas almacenadas.
- **Flujo básico**:
  1. El usuario accede a la sección de estadísticas en la ventana principal.
  2. El usuario pulsa el botón "Restablecer estadísticas".
  3. El sistema solicita confirmación para evitar acciones accidentales.
  4. El usuario confirma que desea restablecer las estadísticas.
  5. El sistema pone a cero todas las estadísticas almacenadas del usuario.
- **Postcondición**: Las estadísticas del usuario quedan restablecidas a su valor inicial.
- **Flujos alternativos**:
  - 4a. El usuario cancela la operación: el sistema mantiene las estadísticas originales sin cambios.
