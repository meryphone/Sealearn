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

    public void registrarRespuesta(boolean acierto) {
		totalPreguntasRespondidas++;

		if (acierto) {
			totalAciertos++;
		} else {
			totalFallos++;
		}
	}

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

    public void finalizarSesion() {
        if (inicioSesion != null) {
            Duration duracion = Duration.between(inicioSesion, LocalDateTime.now());
            tiempoTotalEstudio = tiempoTotalEstudio.plus(duracion);
            inicioSesion = null;
        }
    }

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

    public void exportar(String rutaArchivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write("Estad�sticas de estudio\n\n");
            writer.write("Total de preguntas respondidas: " + totalPreguntasRespondidas + "\n");
            writer.write("Total de aciertos: " + totalAciertos + "\n");
            writer.write("Total de fallos: " + totalFallos + "\n");

            if (tiempoTotalEstudio != null) {
                long minutos = tiempoTotalEstudio.toMinutes();
                writer.write("Tiempo total de estudio: " + minutos + " minutos\n");
            }

            writer.write("Mejor racha de d�as estudiando: " + mejorRacha + "\n");
            writer.write("Racha actual: " + rachaActual + "\n");

            if (ultimoDiaEstudio != null) {
                writer.write("�ltimo d�a de estudio: "
                        + ultimoDiaEstudio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            }
        }
    }

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

    public Long getId() {
        return id;
    }

    // Setter para ID (necesario para JPA)
    public void setId(Long id) {
        this.id = id;
    }
    
    //Necesario para pruebas
	public void setUltimoDiaEstudio(LocalDate ultimoDia) {
		ultimoDiaEstudio = ultimoDia;
		
	}
}
