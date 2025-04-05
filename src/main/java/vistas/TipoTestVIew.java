package vistas;

import java.awt.*;
import java.util.Collections;
import javax.swing.*;

import controlador.Controlador;
import dominio.Pregunta;
import dominio.PreguntaHueco;
import dominio.PreguntaRespuestaCorta;
import dominio.PreguntaTest;

public class TipoTestVIew extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Controlador controlador = Controlador.getInstance();


    public TipoTestVIew() {
        initialize();
        setVisible(true); // Muestra esta ventana directamente
    }

    private void initialize() {
        setTitle("SeaLearn");
        setBounds(100, 100, 450, 423);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(Principal.BEIGE);

        JPanel panelQuestions = new JPanel(new BorderLayout());
        getContentPane().add(panelQuestions);
        panelQuestions.setBackground(Principal.BEIGE);

        JPanel top = new JPanel();
        top.setBackground(Principal.BEIGE);
        panelQuestions.add(top, BorderLayout.NORTH);

        PreguntaTest pregunta = (PreguntaTest) controlador.getPreguntaActual();

        JLabel lblQuestion = new JLabel(pregunta.getEnunciado());
        top.add(lblQuestion);
        
        JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
        optionsPanel.setBackground(Principal.BEIGE);
        panelQuestions.add(optionsPanel, BorderLayout.CENTER);
        
        for (String opcion : pregunta.getListaOpciones()) {
            JRadioButton btn = new JRadioButton(opcion);
            btn.setBackground(Principal.BEIGE.brighter());
            buttonGroup.add(btn);
            optionsPanel.add(btn);
        }

        panelQuestions.add(Box.createHorizontalStrut(25), BorderLayout.EAST);
        panelQuestions.add(Box.createHorizontalStrut(25), BorderLayout.WEST);

        JPanel panelHeadline = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelHeadline.setBackground(Principal.BEIGE);
        getContentPane().add(panelHeadline);

        JLabel labelSeal = new JLabel(new ImageIcon(getClass().getResource("/imagenes/seal_looking_right.png")));
        panelHeadline.add(labelSeal);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBackground(Principal.BEIGE.brighter());
        panelHeadline.add(progressBar);

        JPanel down = new JPanel();
        down.setBackground(Principal.BEIGE);
        getContentPane().add(down);

        JButton btnSiguiente = new RoundButton("Siguiente");
        btnSiguiente.setPreferredSize(new Dimension(92, 40));
        down.add(btnSiguiente);
        configurarBotonSiguiente(btnSiguiente);

    }
    
    private String getRespuestaSeleccionada(ButtonGroup group) {
        return Collections.list(group.getElements())
                          .stream()
                          .filter(AbstractButton::isSelected)
                          .map(AbstractButton::getText)
                          .findFirst()
                          .orElse(null);
    }

    
    private void configurarBotonSiguiente(JButton btnSiguiente) {
        btnSiguiente.addActionListener(e -> {
        	String respuestaSeleccionada = getRespuestaSeleccionada(buttonGroup);
        	if (respuestaSeleccionada == null) {
        	    JOptionPane.showMessageDialog(this, "Debes seleccionar una opción.");
        	    return;
        	}

        	boolean acierto = controlador.validarRespuesta(respuestaSeleccionada);
            String correcta = controlador.getPreguntaActual().getRespuestaCorrecta();

            JOptionPane.showMessageDialog(this,
                acierto ? "¡Correcto!" : "Incorrecto\nLa respuesta correcta era: " + correcta,
                "Resultado",
                acierto ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
            );
            
            if (controlador.hayMasPreguntas()) {
            	controlador.avanzarPregunta();
            	Pregunta siguiente = controlador.getPreguntaActual();

            	if (siguiente instanceof PreguntaTest) {
            		new TipoTestVIew();
            	} else if (siguiente instanceof PreguntaHueco) {
            		new RellenarHuecoView();
            	} else if (siguiente instanceof PreguntaRespuestaCorta) {
            		new RespuestaEscritaView();
            	}
            } else {
            	controlador.finalizarSesion();
            	JOptionPane.showMessageDialog(this, "¡Has completado el curso!", "Fin", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
            
        });
    }

}
