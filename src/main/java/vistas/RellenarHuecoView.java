package vistas;

import java.awt.*;
import javax.swing.*;
import controlador.Controlador;
import dominio.PreguntaRellenarHueco;
import utils.MensajeError;

public class RellenarHuecoView extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Controlador controlador = Controlador.getInstance();
	private final PreguntaRellenarHueco pregunta;

	public RellenarHuecoView(JFrame owner, PreguntaRellenarHueco pregunta) {
		super(owner, "Pregunta de Rellenar Hueco", true);
		this.pregunta = pregunta;

		inicializarVista();
		pack();
		setLocationRelativeTo(owner);
		
	}

	private void inicializarVista() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(500, 400));
		getContentPane().setBackground(Principal.BEIGE);
		getContentPane().setLayout(new BorderLayout(10, 10));

		// ---------- Panel superior ----------
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(Principal.BEIGE);
		panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/imagenes/seal_looking_right.png")));
		panelSuperior.add(icon);

		JProgressBar progressBar = new JProgressBar();
		int progreso = controlador.getProgreso();
		int total = controlador.getTotalPreguntas();
		progressBar.setValue((int) ((progreso * 100.0f) / total));
		progressBar.setStringPainted(true);
		panelSuperior.add(progressBar);

		getContentPane().add(panelSuperior, BorderLayout.NORTH);

		// ---------- Panel central ----------
		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
		panelCentro.setBackground(Principal.BEIGE);
		getContentPane().add(panelCentro, BorderLayout.CENTER);

		JPanel panelPregunta = new JPanel();
		panelPregunta.setBackground(Principal.BEIGE);
		JLabel labelPregunta = new JLabel(pregunta.getEnunciado());
		labelPregunta.setFont(new Font("Arial", Font.BOLD, 16));
		panelPregunta.add(labelPregunta);
		panelCentro.add(panelPregunta);

		JPanel panelOpciones = new JPanel(new GridLayout(3, 1, 10, 10));
		panelOpciones.setBackground(Principal.BEIGE);
		for (String opcion : pregunta.getListaOpciones()) {
			JButton btn = new RoundButton(opcion);
			btn.addActionListener(e -> validarRespuesta(opcion));
			panelOpciones.add(btn);
		}
		panelCentro.add(panelOpciones);
	}

	private void validarRespuesta(String respuestaSeleccionada) {
		boolean acierto = controlador.corregir(respuestaSeleccionada);

		if (acierto) {
			MensajeError.mostrarConfirmacion(this, "¡Correcto!");
		} else {
			MensajeError.mostrarError(this, "Incorrecto. La respuesta correcta era: " + pregunta.getRespuestaCorrecta());
		}

		dispose();
	}

}
