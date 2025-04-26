package vistas;

import java.awt.*;
import javax.swing.*;
import controlador.Controlador;
import dominio.PreguntaRespuestaCorta;
import utils.MensajeError;

public class RespuestaCortaView extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Controlador controlador = Controlador.getInstance();
	private final PreguntaRespuestaCorta pregunta;
	private final Runnable onCloseCallback;
	private JTextField textField;

	private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 16);
	private static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 14);

	public RespuestaCortaView(JFrame owner, PreguntaRespuestaCorta pregunta, Runnable onCloseCallback) {
		super(owner, "Pregunta de Respuesta Corta", true);
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
		setPreferredSize(new Dimension(565, 365));
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
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.setBackground(Principal.BEIGE);
		getContentPane().add(panelCentral, BorderLayout.CENTER);

		String html = "<html><body style='width: 400px'>" + pregunta.getEnunciado() + "</body></html>";
		JLabel labelPregunta = new JLabel(html);
		labelPregunta.setFont(LABEL_FONT);
		labelPregunta.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel panelEnunciado = new JPanel();
		panelEnunciado.setBackground(Principal.BEIGE);
		panelEnunciado.add(labelPregunta);
		panelCentral.add(panelEnunciado);

		textField = new JTextField(15);
		textField.setFont(TEXT_FONT);
		JPanel panelRespuesta = new JPanel();
		panelRespuesta.setBackground(Principal.BEIGE);
		panelRespuesta.add(textField);
		panelCentral.add(panelRespuesta);

		// ---------- Panel inferior ----------
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(Principal.BEIGE);
		getContentPane().add(panelInferior, BorderLayout.SOUTH);

		JButton btnSiguiente = new RoundButton("Siguiente");
		btnSiguiente.setPreferredSize(new Dimension(92, 40));
		btnSiguiente.addActionListener(e -> validarRespuesta());
		panelInferior.add(btnSiguiente);
	}

	private void validarRespuesta() {
		String respuestaUsuario = textField.getText().trim();

		if (respuestaUsuario.isEmpty()) {
			MensajeError.mostrarAdvertencia(this, "Por favor, introduce una respuesta.");
			return;
		}

		boolean acierto = controlador.corregir(respuestaUsuario);
		if (acierto) {
			MensajeError.mostrarConfirmacion(this, "¡Correcto!");
		} else {
			MensajeError.mostrarError(this,
				"Incorrecto. La respuesta correcta era: " + pregunta.getRespuestaCorrecta());
		}

		dispose();
	}
	
	private void cerrarSesion() {
		controlador.finalizarSesionCurso();
		if (onCloseCallback != null) {
			onCloseCallback.run();
		}
	}

}
