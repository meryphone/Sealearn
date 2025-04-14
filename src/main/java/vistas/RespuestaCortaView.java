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
	private JTextField textField;

	// Estilos comunes
	private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 16);
	private static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 14);

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			PreguntaRespuestaCorta ejemplo = new PreguntaRespuestaCorta("¿Cuál es la capital de España?", "Madrid",
					"media");
			RespuestaCortaView vista = new RespuestaCortaView(null, ejemplo);
			vista.setVisible(true);
		});
	}

	public RespuestaCortaView(JFrame owner, PreguntaRespuestaCorta pregunta) {
		super(owner, "Pregunta de Respuesta Corta", true); // Diálogo modal
		this.pregunta = pregunta;
		inicializarVista();
		pack();
		setLocationRelativeTo(owner);
	}

	private void inicializarVista() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Principal.BEIGE);
		getContentPane().setLayout(new BorderLayout(10, 10));
		setPreferredSize(new Dimension(565, 365));

		// ---------- Panel superior con sello e icono ----------
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelSuperior.setBackground(Principal.BEIGE);
		JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/imagenes/seal_looking_right.png")));
		panelSuperior.add(iconLabel);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);

		// ---------- Panel central con enunciado y campo de texto ----------
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.setBackground(Principal.BEIGE);
		getContentPane().add(panelCentral, BorderLayout.CENTER);

		// Enunciado de la pregunta
		JLabel labelEnunciado = new JLabel(pregunta.getEnunciado());
		labelEnunciado.setFont(LABEL_FONT);
		labelEnunciado.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel panelEnunciado = new JPanel();
		panelEnunciado.setBackground(Principal.BEIGE);
		panelEnunciado.add(labelEnunciado);
		panelCentral.add(panelEnunciado);

		// Campo de texto para respuesta
		textField = new JTextField(15);
		textField.setFont(TEXT_FONT);
		JPanel panelRespuesta = new JPanel();
		panelRespuesta.setBackground(Principal.BEIGE);
		panelRespuesta.add(textField);
		panelCentral.add(panelRespuesta);

		// ---------- Panel inferior con botón ----------
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(Principal.BEIGE);
		getContentPane().add(panelInferior, BorderLayout.SOUTH);

		JButton btnSiguiente = new RoundButton("Siguiente");
		btnSiguiente.setPreferredSize(new Dimension(92, 40));
		btnSiguiente.addActionListener(e -> validarRespuesta());
		panelInferior.add(btnSiguiente);
	}

	/**
	 * Valida la respuesta del usuario contra la respuesta correcta Muestra
	 * confirmación o error, y cierra la vista.
	 */
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

		dispose(); // Cerrar la ventana tras responder
	}
}
