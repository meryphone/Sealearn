package vistas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import dominio.PreguntaTest;
import controlador.Controlador;
import utils.*;

public class TestView extends JDialog {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton[] opcionesRadio;
	private PreguntaTest pregunta;
	private Controlador controlador = Controlador.getInstance();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TestView frame = new TestView(null, new PreguntaTest("¿Cuál es la capital de Francia?", "Paris", List.of("Francia", "Dublín", "París"), "facil"));
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public TestView(JFrame owner, PreguntaTest pregunta) {
	    super(owner, "Pregunta Test", true); // 'true' => modal
	    this.pregunta = pregunta;
	    initialize();
	    pack();
	    setLocationRelativeTo(owner);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("SeaLearn");
		frame.setBounds(100, 100, 450, 423);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().setBackground(Principal.BEIGE);

		JPanel panelQuestions = new JPanel();
		panelQuestions.setLayout(new BorderLayout(0, 0));
		panelQuestions.setBackground(Principal.BEIGE);
		frame.getContentPane().add(panelQuestions);

		JPanel top = new JPanel();
		top.setBackground(Principal.BEIGE);
		panelQuestions.add(top, BorderLayout.NORTH);

		JLabel lblQuestion = new JLabel(pregunta.getEnunciado());
		top.add(lblQuestion);

		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		optionsPanel.setBackground(Principal.BEIGE);
		panelQuestions.add(optionsPanel, BorderLayout.CENTER);

		List<String> opciones = pregunta.getListaOpciones();
		opcionesRadio = new JRadioButton[opciones.size()];

		for (int i = 0; i < opciones.size(); i++) {
			JRadioButton radio = new JRadioButton(opciones.get(i));
			radio.setBackground(Principal.BEIGE.brighter());
			buttonGroup.add(radio);
			opcionesRadio[i] = radio;
			optionsPanel.add(radio);
		}

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalGlue_1.setPreferredSize(new Dimension(25, 0));
		panelQuestions.add(horizontalGlue_1, BorderLayout.EAST);

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setPreferredSize(new Dimension(25, 0));
		panelQuestions.add(horizontalGlue, BorderLayout.WEST);

		JPanel panelHeadline = new JPanel();
		panelHeadline.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelHeadline.setBackground(Principal.BEIGE);
		frame.getContentPane().add(panelHeadline);

		JLabel labelSeal = new JLabel(new ImageIcon(TestView.class.getResource("/imagenes/seal_looking_right.png")));
		panelHeadline.add(labelSeal);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBackground(Principal.BEIGE.brighter());
		panelHeadline.add(progressBar);

		JPanel down = new JPanel();
		down.setBackground(Principal.BEIGE);
		frame.getContentPane().add(down);

		JButton btnSalir = new RoundButton("Salir");
		btnSalir.setPreferredSize(new Dimension(70, 40));
		down.add(btnSalir);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(230, 0));
		down.add(horizontalStrut);

		JButton btnSiguiente = new RoundButton("Siguiente");
		btnSiguiente.setPreferredSize(new Dimension(92, 40));
		btnSiguiente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String seleccion = obtenerRespuestaSeleccionada();
				if (seleccion != null) {
					if(controlador.corregir(seleccion)) {
						MensajeError.mostrarConfirmacion(frame, "Respuesta correcta!");
					}else {
						MensajeError.mostrarError(frame, "Respuesta incorrecta!");
					}
					controlador.avanzarCurso();	
					dispose();
				} else {
					MensajeError.mostrarAdvertencia(frame, "Debe seleccionar una opción.");
				}
			}
		});
		down.add(btnSiguiente);
	}

	private String obtenerRespuestaSeleccionada() {
		for (JRadioButton btn : opcionesRadio) {
			if (btn.isSelected()) {
				return btn.getText();
			}
		}
		return null;
	}
}
