package utils;

import dominio.Aleatoria;
import dominio.Estrategia;
import dominio.RepeticionEspaciada;
import dominio.Secuencial;

public class EstrategiaFactory {

    public static Estrategia crearEstrategia(String nombre, int totalPreguntas) {
    
    switch (nombre) {
	case "Repeticion Espaciada": 
		return new RepeticionEspaciada(totalPreguntas);
	case "Aleatoria":
		return new Aleatoria(totalPreguntas);
	case "Secuencial":	
		return new Secuencial(totalPreguntas);
	default:
		throw new IllegalArgumentException("Unexpected value: " + nombre);
	}
    }
}
