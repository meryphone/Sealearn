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

## Nota sobre actores

Un mismo usuario puede actuar como **Estudiante** (cuando realiza cursos y consulta estadísticas) y como **Creador de cursos** (cuando importa, exporta o elimina cursos de la biblioteca interna).

## Casos de uso

### Seleccionar curso

- **Actor**: Estudiante
- **Precondición**: Debe existir al menos un curso en la biblioteca interna.
- **Flujo básico**:
  1. El estudiante accede a la pantalla de selección de cursos.
  2. El sistema muestra la lista de cursos disponibles con su descripción.
  3. El estudiante selecciona un curso de la lista.
  4. El estudiante selecciona una dificultad.
  5. El estudiante selecciona una estrategia de aprendizaje.
  6. El estudiante pulsa el botón para comenzar el curso.
  7. El sistema carga el curso seleccionado y muestra la primera pregunta o pantalla de aprendizaje.
- **Postcondición**: El curso queda cargado y listo para ser ejecutado siguiendo la estrategia de aprendizaje elegida.
- **Flujos alternativos**:
  - 3a. No existen cursos disponibles: el sistema informa al estudiante de que debe importar o crear cursos primero.

---

### Realizar curso

- **Actor**: Estudiante
- **Precondición**: El estudiante ha seleccionado previamente un curso y una estrategia de aprendizaje.
- **Flujo básico**:
  1. El sistema muestra una pregunta o pantalla de aprendizaje del curso.
  2. El estudiante responde la pregunta o interactúa con la pantalla.
  3. El sistema valida la respuesta y muestra retroalimentación.
  4. El sistema registra el progreso del estudiante.
  5. Se repite el proceso hasta finalizar el curso o que el estudiante decida salir.
- **Postcondición**: El progreso del estudiante se guarda en el sistema.
- **Flujos alternativos**:
  - 2a. El estudiante decide abandonar el curso: se guarda el progreso actual automáticamente.

---

### Reanudar curso

- **Actor**: Estudiante
- **Precondición**: Debe existir un progreso guardado de un curso previamente iniciado.
- **Flujo básico**:
  1. El estudiante accede a la pantalla de cursos y selecciona uno con progreso guardado.
  2. El sistema ofrece la opción de reanudar el curso.
  3. El estudiante confirma reanudar.
  4. El sistema carga el curso desde la última pregunta o pantalla guardada.
  5. Se continúa con el caso de uso "Realizar curso".
- **Postcondición**: El estudiante continúa el curso desde el punto donde lo dejó.

---

### Reiniciar curso

- **Actor**: Estudiante
- **Precondición**: Debe existir un progreso guardado de un curso previamente iniciado.
- **Flujo básico**:
  1. El estudiante accede a la pantalla de cursos y selecciona uno con progreso guardado.
  2. El sistema ofrece la opción de empezar de nuevo el curso.
  3. El estudiante confirma empezar de nuevo.
  4. El sistema carga el curso desde la primera pregunta o pantalla del curso, con la misma estrategia y dificultad elegida.
  5. Se continúa con el caso de uso "Realizar curso".
- **Postcondición**: El curso seleccionado se reinicia desde el principio, manteniendo la estrategia y dificultad previamente configuradas.

---

### Importar curso

- **Actor**: Creador de cursos
- **Flujo básico**:
  1. El creador de cursos accede a la opción de importar cursos.
  2. El sistema solicita seleccionar un archivo JSON o YAML.
  3. El creador de cursos selecciona un archivo.
  4. El sistema valida el formato y la estructura del archivo.
  5. Si el archivo es válido, el curso se añade a la biblioteca interna.
  6. Si el archivo no es válido, el sistema muestra un error.
- **Postcondición**: El curso importado queda disponible en la biblioteca interna.
- **Flujos alternativos**:
  - 4a. Si ocurre un error al validar el archivo, el sistema muestra un mensaje de error.

---

### Eliminar curso

- **Actor**: Creador de cursos
- **Precondición**: Debe existir al menos un curso almacenado en la biblioteca interna.
- **Flujo básico**:
  1. El creador de cursos accede a la sección de gestión de cursos o a la biblioteca interna.
  2. El sistema muestra la lista de cursos disponibles.
  3. El creador de cursos selecciona el curso que desea eliminar.
  4. El sistema solicita confirmación para eliminar el curso.
  5. El creador de cursos confirma la eliminación.
  6. El sistema elimina el curso de la biblioteca interna.
- **Postcondición**: El curso seleccionado es eliminado de la biblioteca interna del sistema.
- **Flujos alternativos**:
  - 5a. El creador de cursos cancela la eliminación: el sistema mantiene el curso sin cambios.

---

### Exportar estadísticas

- **Actor**: Estudiante
- **Precondición**: El estudiante se encuentra en la ventana principal.
- **Flujo básico**:
  1. El estudiante accede a la opción de exportar estadísticas.
  2. El sistema muestra una ventana para seleccionar el destino del PDF por generar.
  3. El estudiante selecciona una ruta de destino.
  4. El sistema genera un archivo PDF con las estadísticas del usuario en la ruta indicada.
- **Postcondición**: Se crea un archivo PDF con las estadísticas actuales del estudiante.

---

### Consultar estadísticas

- **Actor**: Estudiante
- **Flujo básico**:
  1. El estudiante accede a la sección de estadísticas.
  2. El sistema muestra datos como: tiempo de uso, racha de días consecutivos, número de cursos completados, etc.
  3. El estudiante revisa sus estadísticas.
- **Postcondición**: El estudiante visualiza su progreso y hábitos de estudio.

---

### Restablecer estadísticas

- **Actor**: Estudiante
- **Precondición**: El estudiante se encuentra en la ventana principal y dispone de estadísticas almacenadas.
- **Flujo básico**:
  1. El estudiante accede a la sección de estadísticas en la ventana principal.
  2. El estudiante pulsa el botón "Restablecer estadísticas".
  3. El sistema solicita confirmación para evitar acciones accidentales.
  4. El estudiante confirma que desea restablecer las estadísticas.
  5. El sistema pone a cero todas las estadísticas almacenadas del usuario.
- **Postcondición**: Las estadísticas del estudiante quedan restablecidas a su valor inicial.
- **Flujos alternativos**:
  - 4a. El estudiante cancela la operación: el sistema mantiene las estadísticas originales sin cambios.
