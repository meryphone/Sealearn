package dominio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.*;
import utils.DurationAttributeConverter;

/**
 * Representa las estadísticas de estudio de un usuario,
 * incluyendo tiempo total de estudio, número de aciertos y fallos,
 * así como rachas de días consecutivos de estudio.
 */
@Entity
@Table(name = "Estadisticas")
public class Estadistica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Convert(converter = DurationAttributeConverter.class)
    private Duration tiempoTotalEstudio;

    private int totalPreguntasRespondidas;
    private int totalAciertos;
    private int totalFallos;
    private int mejorRacha;
    private int rachaActual;

    private LocalDateTime inicioSesion;
    private LocalDate ultimoDiaEstudio;

    /**
     * Constructor por defecto. Inicializa las estadísticas en valores predeterminados.
     */
    public Estadistica() {
        this.tiempoTotalEstudio = Duration.ZERO;
        this.mejorRacha = 0;
        this.rachaActual = 0;
        this.totalAciertos = 0;
        this.totalPreguntasRespondidas = 0;
        this.totalFallos = 0;
        this.inicioSesion = LocalDateTime.now();
        this.ultimoDiaEstudio = null;
    }

    /**
     * Registra una nueva respuesta del usuario.
     *
     * @param acierto true si la respuesta fue correcta; false si fue incorrecta.
     */
    public void registrarRespuesta(boolean acierto) {
        totalPreguntasRespondidas++;
        if (acierto) {
            totalAciertos++;
        } else {
            totalFallos++;
        }
    }

    /**
     * Registra el día de estudio actual y actualiza la racha correspondiente.
     */
    public void registrarEstudioHoy() {
        LocalDate hoy = LocalDate.now();
        if (ultimoDiaEstudio == null) {
            rachaActual = 1;
        } else {
            long diasEntre = ChronoUnit.DAYS.between(ultimoDiaEstudio, hoy);
            if (diasEntre == 1) {
                rachaActual++;
            } else if (diasEntre > 1) {
                rachaActual = 1;
            }
        }

        if (rachaActual > mejorRacha) {
            mejorRacha = rachaActual;
        }

        ultimoDiaEstudio = hoy;
    }

    /**
     * Finaliza la sesión actual y acumula el tiempo transcurrido al total de estudio.
     */
    public void finalizarSesion() {
        if (inicioSesion != null) {
            Duration duracion = Duration.between(inicioSesion, LocalDateTime.now());
            tiempoTotalEstudio = tiempoTotalEstudio.plus(duracion);
            inicioSesion = null;
        }
    }

    /**
     * Reinicia todas las estadísticas al estado inicial.
     */
    public void reset() {
        this.totalPreguntasRespondidas = 0;
        this.totalAciertos = 0;
        this.totalFallos = 0;
        this.tiempoTotalEstudio = Duration.ZERO;
        this.rachaActual = 0;
        this.mejorRacha = 0;
        this.ultimoDiaEstudio = null;
        this.inicioSesion = LocalDateTime.now();
    }

    /**
     * Exporta las estadísticas actuales a un archivo de texto.
     *
     * @param rutaArchivo Ruta del archivo donde se escribirá la información.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public void exportar(String rutaArchivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write("Estadísticas de estudio\n\n");
            writer.write("Total de preguntas respondidas: " + totalPreguntasRespondidas + "\n");
            writer.write("Total de aciertos: " + totalAciertos + "\n");
            writer.write("Total de fallos: " + totalFallos + "\n");

            if (tiempoTotalEstudio != null) {
                long minutos = tiempoTotalEstudio.toMinutes();
                writer.write("Tiempo total de estudio: " + minutos + " minutos\n");
            }

            writer.write("Mejor racha de días estudiando: " + mejorRacha + "\n");
            writer.write("Racha actual: " + rachaActual + "\n");

            if (ultimoDiaEstudio != null) {
                writer.write("Último día de estudio: "
                        + ultimoDiaEstudio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            }
        }
    }

    // --- Getters y Setters ---

    public int getTotalPreguntasRespondidas() {
        return totalPreguntasRespondidas;
    }

    public int getTotalAciertos() {
        return totalAciertos;
    }

    public int getTotalFallos() {
        return totalFallos;
    }

    public Duration getTiempoTotalEstudio() {
        return tiempoTotalEstudio;
    }

    public int getMejorRacha() {
        return mejorRacha;
    }

    public int getRachaActual() {
        return rachaActual;
    }

    public LocalDate getUltimoDiaEstudio() {
        return ultimoDiaEstudio;
    }

    public LocalDateTime getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(LocalDateTime inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUltimoDiaEstudio(LocalDate ultimoDia) {
        this.ultimoDiaEstudio = ultimoDia;
    }

    public void setRachaActual(int racha) {
        this.rachaActual = racha;
    }
}
