package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aleatoria implements Estrategia {

    private List<Integer> ordenPreguntas;

    public Aleatoria(int totalPreguntas) {
        ordenPreguntas = new ArrayList<>();
        for (int i = 0; i < totalPreguntas; i++) {
            ordenPreguntas.add(i);
        }
        Collections.shuffle(ordenPreguntas);
    }

    @Override
    public int mostrarPregunta(int nPregunta) {
        if (nPregunta < ordenPreguntas.size()) {
            return ordenPreguntas.get(nPregunta);
        } else {
            return -1; // fuera de rango
        }
    }
}