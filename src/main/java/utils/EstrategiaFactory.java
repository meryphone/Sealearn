package utils;

import dominio.Estrategia;

public class EstrategiaFactory {

    public static Estrategia crearEstrategia(String nombre, int totalPreguntas) {
        try {
        	nombre = nombre.replace(" ", "");
            Class<?> clazz = Class.forName("dominio." + nombre);
            Estrategia estrategia = (Estrategia) clazz.getDeclaredConstructor().newInstance();
            estrategia.setTotalPreguntas(totalPreguntas);
            return estrategia;
        } catch (Exception e) {
        	System.exit(1);
        	e.printStackTrace();
            throw new RuntimeException("Estrategia no válida: " + nombre, e);           
        }
    }
}
