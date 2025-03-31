package controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import dominio.Curso;
import dominio.CursoUtils;
import dominio.Pregunta;
import vistas.RellenarHuecoView;
import vistas.RespuestaEscritaView;
import vistas.TipoTestVIew;
import dominio.Estadistica;

public class Controlador {
	
	private Curso cursoActual;
	private int progresoActual;
	private Estadistica estadistica; 
	
	public Controlador() {
		this.cursoActual = null;
	    this.estadistica = new Estadistica();
	    this.progresoActual = 0;
	}
	
	public void importarCurso(String ruta) {
		try {
			cursoActual = CursoUtils.importarCursoDesdeYaml(ruta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		estadistica = new Estadistica();
		progresoActual = cursoActual.getProgreso();
		estadistica.registrarEstudioHoy();
	}
	
	
	public List<Pregunta> estrategiaCurso(String estrategia, List<Pregunta> preguntasBase) {
	    cursoActual.setEstrategia(estrategia);
	    List<Pregunta> preguntasOg = new ArrayList<>(preguntasBase); 

	    switch (estrategia) {
	        case "Secuencial":
	            return preguntasOg;

	        case "Aleatoria":
	            Collections.shuffle(preguntasOg);
	            return preguntasOg;

	        case "Repeticion espaciada":
	            List<Pregunta> resultado = new ArrayList<>();
	            List<Pregunta> preguntasMostradas = new ArrayList<>();
	            int repetirIndex = 0;

	            for (int i = 0; i < preguntasOg.size(); i++) {
	                Pregunta actual = preguntasOg.get(i);
	                resultado.add(actual);
	                preguntasMostradas.add(actual);

	                if ((i + 1) % 3 == 0 && repetirIndex < preguntasMostradas.size()) {
	                    resultado.add(preguntasMostradas.get(repetirIndex));
	                    repetirIndex++;
	                }
	            }

	            return resultado;

	        default:
	            throw new IllegalArgumentException("Estrategia no válida: " + estrategia);
	    }
	}

	
	public List<Pregunta> dificultadCurso(String dificultad) {
		List<Pregunta> preguntasOg = new ArrayList<>(cursoActual.getPreguntas());
		List<Pregunta> clasificadas = new ArrayList<>();
		for(Pregunta p : preguntasOg) {
			if (p.getDificultad().equals(dificultad)) {
				clasificadas.add(p);
			}
		}
		return clasificadas;
	}
	
	
	
	public Pregunta getPreguntaActual() {
	    if (progresoActual >= cursoActual.getPreguntas().size()) {
	        return null;
	    }
	    return cursoActual.getPreguntas().get(progresoActual);
	}

	
	public Curso getCursoActual() {
        return cursoActual;
    }
	
	public void setCursoActual(Curso curso) {
		this.cursoActual = curso;
	}
	
	 public boolean validarRespuesta(String respuestaUsuario) {
	        Pregunta pregunta = getPreguntaActual();
	        boolean acierto = pregunta.validarRespuesta(respuestaUsuario);
	        estadistica.registrarRespuesta(acierto);
	        return acierto;
	 }
	 
	 public void procesarRespuesta(String respuestaUsuario, JFrame ventanaActual) {
		    boolean acierto = validarRespuesta(respuestaUsuario);
		    JOptionPane.showMessageDialog(ventanaActual,
		        acierto ? "¡Correcto!" : "Incorrecto\nLa respuesta correcta era: " + getPreguntaActual().getRespuestaCorrecta(),
		        "Resultado",
		        acierto ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
		    );

		    avanzarPregunta();
		    Pregunta siguiente = getPreguntaActual();

		    if (siguiente == null) {
		        JOptionPane.showMessageDialog(ventanaActual, "¡Curso finalizado!");
		        ventanaActual.dispose();
		        return;
		    }

		    ventanaActual.dispose();

		    SwingUtilities.invokeLater(() -> {
		        if (siguiente instanceof dominio.PreguntaTest) {
		            new TipoTestVIew(this);
		        } else if (siguiente instanceof dominio.PreguntaHueco) {
		            new RellenarHuecoView(this);
		        } else if (siguiente instanceof dominio.PreguntaRespuestaCorta) {
		            new RespuestaEscritaView(this);
		        }
		    });
		}

	 
	 public void avanzarPregunta() {
	        progresoActual++;
	        cursoActual.setProgreso(progresoActual);
	 }
	 
	 public Estadistica getEstadistica() {
	        return estadistica;
	 }
	 
	 public void finalizarSesion() {
	        estadistica.finalizarSesion();
	 }
	 
	 public String getNombreCurso() { return cursoActual.getNombre(); }
	 public String getDescripcionCurso() { return cursoActual.getDescripcion(); }


}
