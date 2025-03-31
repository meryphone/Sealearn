package vistas;

import javax.swing.*;

import controlador.Controlador;
import dominio.Pregunta;

public class UtilsVista {

    public static void avanzarASiguiente(Controlador controlador, JFrame ventanaActual) {
        controlador.avanzarPregunta();
        Pregunta siguiente = controlador.getPreguntaActual();

        if (siguiente == null) {
            JOptionPane.showMessageDialog(null, "¡Curso finalizado!");
            ventanaActual.dispose();
            Principal ventana = new Principal(controlador);
            ventana.getFrame().setVisible(true);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            if (siguiente instanceof dominio.PreguntaTest) {
                TipoTestVIew ventana = new TipoTestVIew(controlador);
                ventana.setVisible(true);
            } else if (siguiente instanceof dominio.PreguntaHueco) {
                RellenarHuecoView ventana = new RellenarHuecoView(controlador);
                ventana.setVisible(true);
            } else if (siguiente instanceof dominio.PreguntaRespuestaCorta) {
                RespuestaEscritaView ventana = new RespuestaEscritaView(controlador);
                ventana.setVisible(true);
            }
        });
    }
}
