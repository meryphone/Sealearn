package vistas;

import java.awt.*;
import javax.swing.*;
import controlador.Controlador;
import dominio.PreguntaRellenarHueco;
import utils.Mensajes;

public class RellenarHuecoView extends JDialog {

	private static final long serialVersionUID = 1L;
	
	Controlador controlador = Controlador.getInstance();
	private final PreguntaRellenarHueco pregunta;
	private final Runnable onCloseCallback;

	public RellenarHuecoView(JFrame owner, PreguntaRellenarHueco pregunta, Runnable onCloseCallback) {
		super(owner, "Pregunta de Rellenar Hueco", true);
		this.pregunta = pregunta;
		this.onCloseCallback = onCloseCallback;

		inicializarVista();
		pack();
		setLocationRelativeTo(owner);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				cerrarSesion();
			}
		});
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
		progressBar.setValue(controlador.getPorcentajeProgreso());
		progressBar.setStringPainted(true);
		panelSuperior.add(progressBar);

		getContentPane().add(panelSuperior, BorderLayout.NORTH);

		// ---------- Panel central ----------
		JPanel panelCentro = new JPanel(new BorderLayout()); // Cambiado a BorderLayout
		panelCentro.setBackground(Principal.BEIGE);
		getContentPane().add(panelCentro, BorderLayout.CENTER);

		JPanel panelPregunta = new JPanel(new GridBagLayout()); // Usamos GridBagLayout para centrado preciso
		panelPregunta.setBackground(Principal.BEIGE);

		String html = "<html><div style='width: 400px; text-align: center;'>" + pregunta.getEnunciado()
				+ "</div></html>";
		JLabel labelPregunta = new JLabel(html);
		labelPregunta.setFont(new Font("Arial", Font.BOLD, 16));
		labelPregunta.setHorizontalAlignment(SwingConstants.CENTER); // Centrado horizontal

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // M�rgenes
		panelPregunta.add(labelPregunta, gbc);

		panelCentro.add(panelPregunta, BorderLayout.CENTER); // Centramos en el panel principal

		JPanel panelOpciones = new JPanel(new GridLayout(3, 1, 10, 10));
		panelOpciones.setBackground(Principal.BEIGE);
		for (String opcion : pregunta.getListaOpciones()) {
			JButton btn = new RoundButton(opcion);
			btn.addActionListener(e -> validarRespuesta(opcion));
			panelOpciones.add(btn);
		}
		panelCentro.add(panelOpciones, BorderLayout.SOUTH); // Movemos las opciones al sur
	}

	private void validarRespuesta(String respuestaSeleccionada) {
		boolean acierto = controlador.corregir(respuestaSeleccionada);

		if (acierto) {
			Mensajes.mostrarConfirmacion(this, "¡Correcto!");
		} else {
			Mensajes.mostrarError(this,
					"Incorrecto. La respuesta correcta era: " + pregunta.getRespuestaCorrecta());
		}

		dispose();
	}

	private void cerrarSesion() {
		if (onCloseCallback != null) {
			onCloseCallback.run();
		}
	}

}