package vistas;

import java.awt.*;
import javax.swing.*;
import controlador.Controlador;
import dominio.PreguntaTest;
import utils.Mensajes;

public class TestView extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Controlador controlador = Controlador.getInstance();
	private final PreguntaTest pregunta;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton[] opcionesRadio;
	private final Runnable onCloseCallback;

	public TestView(JFrame owner, PreguntaTest pregunta, Runnable onCloseCallback) {
		super(owner, "Pregunta tipo Test", true);
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
		setPreferredSize(new Dimension(550, 500));
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
		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
		panelCentro.setBackground(Principal.BEIGE);
		getContentPane().add(panelCentro, BorderLayout.CENTER);

		String html = "<html><body style='width: 400px'>" + pregunta.getEnunciado() + "</body></html>";
		JLabel labelPregunta = new JLabel(html);
		labelPregunta.setFont(new Font("Arial", Font.BOLD, 16));
		labelPregunta.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCentro.add(labelPregunta);

		JPanel panelOpciones = new JPanel(new GridLayout(0, 1, 10, 10));
		panelOpciones.setBackground(Principal.BEIGE);
		opcionesRadio = new JRadioButton[pregunta.getListaOpciones().size()];

		for (int i = 0; i < opcionesRadio.length; i++) {
			JRadioButton radio = new JRadioButton(pregunta.getListaOpciones().get(i));
			radio.setBackground(Principal.BEIGE.brighter());
			opcionesRadio[i] = radio;
			buttonGroup.add(radio);
			panelOpciones.add(radio);
		}
		panelCentro.add(panelOpciones);

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
		String seleccion = obtenerRespuestaSeleccionada();

		if (seleccion == null) {
			Mensajes.mostrarAdvertencia(this, "Debes seleccionar una opción.");
			return;
		}

		boolean acierto = controlador.corregir(seleccion);
		if (acierto) {
			Mensajes.mostrarConfirmacion(this, "¡Correcto!");
		} else {
			Mensajes.mostrarError(this, "Incorrecto. La respuesta correcta era: " + pregunta.getRespuestaCorrecta());
		}

		dispose();
	}

	private String obtenerRespuestaSeleccionada() {
		for (JRadioButton btn : opcionesRadio) {
			if (btn.isSelected()) return btn.getText();
		}
		return null;
	}
	
	private void cerrarSesion() {
		controlador.finalizarSesionCurso();
		if (onCloseCallback != null) {
			onCloseCallback.run();
		}
	}
	
}
