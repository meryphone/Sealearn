# GUIA DE USO - SeaLearnApp

## Requisitos previos

- Java 11 o superior
- Maven (si desea compilar)
- Eclipse (opcional para Opción 2)
- Archivo `basedatos.db` (debe estar accesible desde el mismo directorio o bien configurado)

---

## Opcion 1: Ejecutar .jar

**Paso 1**: Accede al directorio ProyectoPDS/SeaLearnApp/
**Paso 2**: Abre una terminal, situate dentro de este directorio y ejecuta la siguiente línea:
-$ java -jar ProyectoPDS-1.0-SNAPSHOT.jar

**Importante**. Aunque no necesariamente deben de estar en la misma carpeta, asegurese de que existe el archivo basedatos.db ya que este es necesario para la correcta ejecución y persistencia de los datos.

## Opcion 2: Ejecutar desde Eclipse

**Paso 1**: Importe el proyecto en Eclipse
**Paso 2**: Accede a src/java/main abre el paquete de vistas
**Paso 3**: Ejecute la clase Principal

A través de la ventana principal tiene acceso a el resto de ventanas y todas las funcionalidades implementadas.

## Opcion 3: Compilar el código fuente

Si desea compilar el código fuente, se han realizado los siguientes pasos para generar el archivo .jar:

**Paso 1**: Abre una terminal y sitúate en el directorio del proyecto
**Paso 2**: Ejecute la siguiente orden: "mvn clean package"
**Paso 3**: Si la compilación es correcta, dentro del directorio target podremos encontrar el ejecutable .jar
