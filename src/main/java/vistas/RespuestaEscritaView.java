package vistas;

import java.awt.*;
import javax.swing.*;

import controlador.Controlador;
import dominio.PreguntaRespuestaCorta;

public class RespuestaEscritaView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 14);

    private JTextField textField;
    private Controlador controlador;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	Controlador controlador = new Controlador();
                RespuestaEscritaView frame = new RespuestaEscritaView(controlador);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public RespuestaEscritaView(Controlador controlador) {
        this.controlador = controlador;
        inicializarVista(); // mueves el contenido aquí
    }


    private void inicializarVista() {
        setTitle("SeaLearn");
        setBounds(100, 100, 565, 365);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Principal.BEIGE);
        getContentPane().setLayout(new BorderLayout(10, 10));

        // Panel superior con imagen
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		top.setBackground(Principal.BEIGE);
        getContentPane().add(top, BorderLayout.NORTH);
        
        Component verticalStrut = Box.createVerticalStrut(60);
        top.add(verticalStrut);

        // Panel central
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Principal.BEIGE);
        getContentPane().add(center, BorderLayout.CENTER);

        // Panel de la pregunta
        JPanel pregunta = new JPanel();
        pregunta.setBackground(Principal.BEIGE);
        PreguntaRespuestaCorta preguntaRespCorta = (PreguntaRespuestaCorta) controlador.getPreguntaActual();
        JLabel preguntaLabel = new JLabel(preguntaRespCorta.getEnunciado());

        preguntaLabel.setFont(LABEL_FONT);
        pregunta.add(preguntaLabel);
        center.add(pregunta);

        // Panel de respuesta con campo de texto
        JPanel respuesta = new JPanel();
        respuesta.setBackground(Principal.BEIGE);
        textField = new JTextField();
        textField.setFont(TEXT_FONT);
        textField.setColumns(15);
        respuesta.add(textField);
        center.add(respuesta);

        // Panel de barra de progreso con imagen
        JPanel barraProgreso = new JPanel();
        barraProgreso.setBackground(Principal.BEIGE);
        JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/imagenes/seal_looking_right.png")));
        barraProgreso.add(iconLabel);
        JProgressBar progressBar = new JProgressBar();
        barraProgreso.add(progressBar);
        center.add(barraProgreso);
        
     // Panel inferior con el botón Siguiente
        JPanel abajo = new JPanel();
        abajo.setBackground(Principal.BEIGE);
        getContentPane().add(abajo, BorderLayout.SOUTH);

        JButton btnSiguiente = new RoundButton("Siguiente");
        btnSiguiente.setPreferredSize(new Dimension(92, 40));
        abajo.add(btnSiguiente);

        // Lógica del botón en una función aparte
        configurarBotonSiguiente(btnSiguiente);

    }
    
    private void configurarBotonSiguiente(JButton btnSiguiente) {
        btnSiguiente.addActionListener(e -> {
            String respuestaUsuario = textField.getText().trim();

            if (respuestaUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes escribir una respuesta.");
                return;
            }

            boolean acierto = controlador.validarRespuesta(respuestaUsuario);
            JOptionPane.showMessageDialog(this,
                acierto ? "¡Correcto!" : "Incorrecto\nLa respuesta correcta era: " + controlador.getPreguntaActual().getRespuestaCorrecta(),
                "Resultado",
                acierto ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
            );


            dispose();
            
            UtilsVista.avanzarASiguiente(controlador, this);
        });
    }


}
