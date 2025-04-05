package dominio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Estadistica {
	
	private int totalPreguntasRespondidas;
    private int totalAciertos;
    private int totalFallos;
    private LocalDateTime inicioSesion;
    private Duration tiempoTotalEstudio;
    private int mejorRacha; // preguntas seguidas correctas
    private int rachaActual;
    private LocalDate ultimoDiaEstudio;

    public Estadistica() {
        this.tiempoTotalEstudio = Duration.ZERO;
        this.mejorRacha = 0;
        this.rachaActual = 0;
        this.totalAciertos = 0;
        this.totalPreguntasRespondidas = 0;
        this.totalFallos=0;
        this.inicioSesion = LocalDateTime.now();
        this.ultimoDiaEstudio = null;
    }

    public int getTotalPreguntasRespondidas() {
		return totalPreguntasRespondidas;
	}

	public void setTotalPreguntasRespondidas(int totalPreguntasRespondidas) {
		this.totalPreguntasRespondidas = totalPreguntasRespondidas;
	}

	public int getTotalAciertos() {
		return totalAciertos;
	}

	public void setTotalAciertos(int totalAciertos) {
		this.totalAciertos = totalAciertos;
	}

	public int getTotalFallos() {
		return totalFallos;
	}

	public void setTotalFallos(int totalFallos) {
		this.totalFallos = totalFallos;
	}

	public LocalDateTime getInicioSesion() {
		return inicioSesion;
	}

	public void setInicioSesion(LocalDateTime inicioSesion) {
		this.inicioSesion = inicioSesion;
	}

	public Duration getTiempoTotalEstudio() {
		return tiempoTotalEstudio;
	}

	public void setTiempoTotalEstudio(Duration tiempoTotalEstudio) {
		this.tiempoTotalEstudio = tiempoTotalEstudio;
	}

	public int getMejorRacha() {
		return mejorRacha;
	}

	public void setMejorRacha(int mejorRacha) {
		this.mejorRacha = mejorRacha;
	}

	public int getRachaActual() {
		return rachaActual;
	}

	public void setRachaActual(int rachaActual) {
		this.rachaActual = rachaActual;
	}

	public LocalDate getUltimoDiaEstudio() {
		return ultimoDiaEstudio;
	}

	public void setUltimoDiaEstudio(LocalDate ultimoDiaEstudio) {
		this.ultimoDiaEstudio = ultimoDiaEstudio;
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
    	        inicioSesion = null; // Marcar como cerrada esta sesión
    	    }
    }
    
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
                writer.write("Último día de estudio: " + ultimoDiaEstudio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            }
        }
    }

}