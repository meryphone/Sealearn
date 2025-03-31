package dominio;

import java.util.ArrayList;
import java.util.List;

public class RepeticionEspaciada implements Estrategia {

    private List<Integer> ordenPreguntas;

    public RepeticionEspaciada(int totalPreguntas) {
        ordenPreguntas = new ArrayList<>();
        int repetirIndex = 0;
        List<Integer> preguntasMostradas = new ArrayList<>();

        for (int i = 0; i < totalPreguntas; i++) {
            ordenPreguntas.add(i);
            preguntasMostradas.add(i);

            if ((i + 1) % 3 == 0 && repetirIndex < preguntasMostradas.size()) {
                ordenPreguntas.add(preguntasMostradas.get(repetirIndex));
                repetirIndex++;
            }
        }
    }

    @Override
    public int mostrarPregunta(int nPregunta) {
        if (nPregunta < ordenPreguntas.size()) {
            return ordenPreguntas.get(nPregunta);
        } else {
            return -1;
        }
    }
}
