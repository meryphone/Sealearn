package utils;

import dominio.Estrategia;
import dominio.RepeticionEspaciada;

public class EstrategiaFactory {

    public static Estrategia crearEstrategia(String nombre, int totalPreguntas) {
        try {
        	nombre = nombre.replace(" ", "");
            Class<?> clazz = Class.forName("dominio." + nombre);
            Estrategia estrategia = (Estrategia) clazz.getDeclaredConstructor().newInstance();
            if(estrategia instanceof RepeticionEspaciada) {
            	estrategia.setTotalPreguntas(totalPreguntas*2);
            }else {
            	estrategia.setTotalPreguntas(totalPreguntas);
            }           
            return estrategia;
        } catch (Exception e) {        	
        	e.printStackTrace();
            throw new RuntimeException("Estrategia no válida: " + nombre, e);           
        }
    }
}
