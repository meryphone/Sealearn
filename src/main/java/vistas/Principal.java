package vistas;

import controlador.Controlador;
import dominio.Curso;
import dominio.CursoEnProgreso;
import dominio.Pregunta;
import dominio.PreguntaRellenarHueco;
import dominio.PreguntaRespuestaCorta;
import dominio.PreguntaTest;
import excepciones.CursoSinPreguntasCiertaDificultad;
import excepciones.ExcepcionCursoActualVacio;
import excepciones.ExcepcionCursoDuplicado;
import utils.CursoUtils;
import utils.Mensajes;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.*;

public class Principal {

	public final static Color BEIGE = new Color(211, 204, 194);
	public final static Color BUTTON_COLOR = new Color(8, 32, 50);
	private Controlador controlador = Controlador.getInstance();
	private CursoEnProgreso cursoActual;
	DefaultListModel<Curso> model = new DefaultListModel<Curso>();

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Principal window = new Principal();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Principal() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1032, 666);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Principal.BEIGE);

		JPanel top = new JPanel();
		frame.getContentPane().add(top, BorderLayout.NORTH);
		top.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel title = new JLabel(new ImageIcon(Principal.class.getResource("/imagenes/Titulo.png")));
		top.add(title);
		top.setBackground(BEIGE);

		JPanel left = new JPanel();
		frame.getContentPane().add(left, BorderLayout.WEST);
		left.setBackground(Principal.BEIGE);
		left.add(Box.createRigidArea(new Dimension(150, 20)));

		JPanel right = new JPanel();
		frame.getContentPane().add(right, BorderLayout.EAST);
		right.setBackground(Principal.BEIGE);
		right.add(Box.createRigidArea(new Dimension(150, 20)));

		JPanel down = new JPanel();
		frame.getContentPane().add(down, BorderLayout.SOUTH);
		down.setBackground(Principal.BEIGE);

		JLabel sealLeft = new JLabel(new ImageIcon(Principal.class.getResource("/imagenes/seal_looking_right.png")));
		down.add(sealLeft);

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setPreferredSize(new Dimension(200, 0));
		horizontalGlue.setMaximumSize(new Dimension(55, 55));
		horizontalGlue.setMinimumSize(new Dimension(55, 0));
		down.add(horizontalGlue);

		JButton btnIniciar = new RoundButton("Iniciar");
		down.add(btnIniciar);
		btnIniciar.setPreferredSize(new Dimension(85, 45));

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalGlue_1.setPreferredSize(new Dimension(200, 0));
		horizontalGlue_1.setMinimumSize(new Dimension(55, 0));
		horizontalGlue_1.setMaximumSize(new Dimension(55, 55));
		down.add(horizontalGlue_1);

		JLabel sealRight = new JLabel(new ImageIcon(Principal.class.getResource("/imagenes/seal.png")));
		down.add(sealRight);

		JPanel center0 = new JPanel();
		frame.getContentPane().add(center0, BorderLayout.CENTER);
		center0.setLayout(new BorderLayout());
		center0.setBackground(Principal.BEIGE);

		JPanel panelButtons = new JPanel();
		center0.add(panelButtons, BorderLayout.NORTH);
		panelButtons.setBackground(Principal.BEIGE);

		JButton btnStats = new RoundButton("Ver estadisticas");
		btnStats.setPreferredSize(new Dimension(120, 40));
		panelButtons.add(btnStats);
		btnStats.addActionListener(e -> {
			EstadisticaView stats = new EstadisticaView(controlador.getEstadistica());
			stats.setVisible(true);
		});

		Component rigidArea = Box.createRigidArea(new Dimension(20, 40));
		rigidArea.setPreferredSize(new Dimension(20, 20));
		rigidArea.setMinimumSize(new Dimension(20, 20));
		panelButtons.add(rigidArea);
		

		JButton btnExportStats = new RoundButton("Exportar estadisticas");
		btnExportStats.setPreferredSize(new Dimension(170, 40));
		panelButtons.add(btnExportStats);
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_1.setPreferredSize(new Dimension(20, 60));
		panelButtons.add(rigidArea_1);
		btnExportStats.addActionListener(e -> {
			exportarEstadisticas();
		});

		JPanel center1 = new JPanel();
		center0.add(center1, BorderLayout.CENTER);
		center1.setLayout(new BorderLayout());
		center1.setBackground(Principal.BEIGE);

		// Cargar la lista de cursos
		for (Curso curso : CursoUtils.cargarTodosLosCursos()) {
			model.addElement(curso);
		}

		JList<Curso> courseList = new JList<Curso>(model);
		courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		courseList.setCellRenderer(new CourseCellRenderer());
		courseList.setBackground(Principal.BEIGE);

		JButton btnImport = new RoundButton("Importar Curso");
		btnImport.setPreferredSize(new Dimension(120, 40));
		panelButtons.add(btnImport);
		panelButtons.add(Box.createRigidArea(new Dimension(20, 20)));
		btnImport.addActionListener(e -> {
			importarCurso();
		});

		JButton btnEliminarCurso = new RoundButton("Eliminar Curso");
		btnEliminarCurso.setPreferredSize(new Dimension(130, 40));
		panelButtons.add(btnEliminarCurso);

		btnEliminarCurso.addActionListener(e -> {
			Curso cursoSeleccionado = courseList.getSelectedValue();
			eliminarCurso(cursoSeleccionado);
		});

		btnIniciar.addActionListener(e -> {
			Curso cursoSeleccionado = courseList.getSelectedValue();
			iniciarCurso(cursoSeleccionado);
		});

		JScrollPane scrollPane = new JScrollPane(courseList);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		center1.add(scrollPane, BorderLayout.CENTER);
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				controlador.finalizarSesionEstadistica();
			}
		});

	}

	private void iniciarCurso(Curso cursoSeleccionado) {
		CursoEnProgreso cursoEnProgreso = controlador.reanudarCurso(cursoSeleccionado);
		try {
			if (cursoEnProgreso != null) {
				ReanudarCursoView dialog = new ReanudarCursoView(frame);
				dialog.setVisible(true);

				switch (dialog.getOpcionSeleccionada()) {
				case REANUDAR:
					cursoActual = cursoEnProgreso;
					realizarCurso();
					break;
				case RESTABLECER:
					cursoActual = controlador.restablecerCurso();
					realizarCurso();
					break;
				case CANCELAR:
					break;
				}

			} else {
				ArrayList<String> parametros = Configuracion.mostrarDialogo(frame);
				if (!parametros.isEmpty()) {
					cursoActual = controlador.iniciarCurso(cursoSeleccionado, parametros.get(0), parametros.get(1));
					realizarCurso();
				}
			}

		} catch (ExcepcionCursoActualVacio ex) {
			Mensajes.mostrarAdvertencia(frame, ex.getMessage());
		} catch (CursoSinPreguntasCiertaDificultad ex) {
			Mensajes.mostrarError(frame, ex.getMessage());
		}
	}

	private void realizarCurso() {
		AtomicBoolean cursoCancelado = new AtomicBoolean(false);

		Runnable onClose = () -> {
			cursoActual = null;
			cursoCancelado.set(true);
		};

		while (cursoActual != null && !cursoCancelado.get()) {
			Pregunta preguntaActual = cursoActual.getPreguntaActual();

			if (preguntaActual == null) {
				Mensajes.mostrarConfirmacion(frame, "!Curso completado!");
				cursoActual = controlador.finalizarSesionCurso();
				break;
			}

			if (preguntaActual instanceof PreguntaTest) {
				new TestView(frame, (PreguntaTest) preguntaActual, onClose).setVisible(true);
			} else if (preguntaActual instanceof PreguntaRellenarHueco) {
				new RellenarHuecoView(frame, (PreguntaRellenarHueco) preguntaActual, onClose).setVisible(true);
			} else if (preguntaActual instanceof PreguntaRespuestaCorta) {
				new RespuestaCortaView(frame, (PreguntaRespuestaCorta) preguntaActual, onClose).setVisible(true);
			}

			if (cursoCancelado.get()) {
				cursoActual = controlador.finalizarSesionCurso();
				break;
			}
			
			controlador.avanzarProgreso();
		}
	}

	private void importarCurso() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Selecciona un archivo YAML de curso");

		int resultado = fileChooser.showOpenDialog(frame);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			String archivo = fileChooser.getSelectedFile().getName();
			try {
				Curso cursoAagregar = controlador.importarCurso(archivo);
				model.addElement(cursoAagregar);
			} catch (IOException e1) {
				Mensajes.mostrarError(frame, "Error al leer el archivo: " + e1.getMessage());
			} catch (ExcepcionCursoDuplicado e1) {
				Mensajes.mostrarAdvertencia(frame, "El curso seleccionado ya está importado.");
			}
		}
	}

	private void eliminarCurso(Curso cursoSeleccionado) {
		if (cursoSeleccionado != null) {
			int confirm = Mensajes.mostrarSIoNO(frame, "¿Desea eliminar el curso seleccionado?");
			if (confirm == JOptionPane.YES_OPTION) {
				controlador.eliminarCurso(cursoSeleccionado);
				model.removeElement(cursoSeleccionado);
			}
		} else {
			Mensajes.mostrarAdvertencia(frame, "Seleccione un curso a eliminar");
		}
	}

	private void exportarEstadisticas() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Exportar estadísticas a PDF");

		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
			if (!rutaArchivo.endsWith(".txt")) {
				rutaArchivo += ".txt";
			}
			try {
				controlador.exportarEstadisticas(rutaArchivo);
				Mensajes.mostrarConfirmacion(frame, "¡Estadísticas exportadas correctamente!");
			} catch (IOException ex) {
				ex.printStackTrace();
				Mensajes.mostrarError(frame, "Error al exportar las estadísticas");
			}
		}
	}

}

class CourseCellRenderer extends JPanel implements ListCellRenderer<Curso> {
	private static final long serialVersionUID = 1L;
	private JLabel iconLabel;
	private JLabel nameLabel;
	private JLabel descriptionLabel;
	private JPanel textPanel;
	private JPanel leftPanel; // Panel para el icono
	private JPanel rightPanel; // Panel para el bot�n

	public CourseCellRenderer() {
		setLayout(new BorderLayout(10, 10)); // Añadir espacio entre componentes
		setBackground(Principal.BEIGE);

		// Panel izquierdo (icono) con BoxLayout
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
		leftPanel.setBackground(Principal.BEIGE);

		// Espacio flexible arriba para centrar el icono
		leftPanel.add(Box.createVerticalGlue());

		// Icono
		iconLabel = new JLabel(new ImageIcon(Principal.class.getResource("/imagenes/gorro-graduacion.png")));
		iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // M�rgenes
		iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
		leftPanel.add(iconLabel);

		// Espacio flexible abajo para centrar el icono
		leftPanel.add(Box.createVerticalGlue());

		// Panel derecho (bot�n) con BoxLayout
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
		rightPanel.setBackground(Principal.BEIGE);

		// Espacio flexible arriba para centrar el bot�n
		rightPanel.add(Box.createVerticalGlue());

		// Espacio flexible abajo para centrar el bot�n
		rightPanel.add(Box.createVerticalGlue());

		// Nombre del curso
		nameLabel = new JLabel();
		nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente m�s grande para el nombre

		// Descripci�n del curso
		descriptionLabel = new JLabel();
		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Fuente m�s peque�a para la descripci�n

		// Panel para el nombre y la descripci�n
		textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
		textPanel.setBackground(Principal.BEIGE.brighter());

		// Espacio flexible arriba para centrar el contenido
		textPanel.add(Box.createVerticalGlue());

		// Nombre y descripci�n
		textPanel.add(nameLabel);
		textPanel.add(descriptionLabel);

		// Espacio flexible abajo para centrar el contenido
		textPanel.add(Box.createVerticalGlue());

		// A�adir componentes al panel principal
		add(leftPanel, BorderLayout.WEST); // Panel izquierdo (icono)
		add(textPanel, BorderLayout.CENTER); // Panel central (nombre y descripci�n)
		add(rightPanel, BorderLayout.EAST); // Panel derecho (bot�n)

		// Establecer un tama�o preferido m�s alto para cada celda
		setPreferredSize(new Dimension(300, 60)); // Ajusta el tama�o seg�n tus necesidades

	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Curso> list, Curso value, int index,
			boolean isSelected, boolean cellHasFocus) {
		// Asignar el nombre del curso
		nameLabel.setText(value.getNombre());

		// Asignar una descripci�n de ejemplo (puedes personalizarla seg�n tus datos)
		descriptionLabel.setText(value.getDescripcion());

		// Cambiar el color de fondo si est� seleccionado
		if (isSelected) {
			setBackground(Principal.BEIGE.darker());
			textPanel.setBackground(Principal.BEIGE.darker());
			leftPanel.setBackground(Principal.BEIGE.darker());
			rightPanel.setBackground(Principal.BEIGE.darker());
			iconLabel.setBackground(Principal.BEIGE.darker());
			nameLabel.setBackground(Principal.BEIGE.darker());
			descriptionLabel.setBackground(Principal.BEIGE.darker());
		} else {
			setBackground(Principal.BEIGE.brighter());
			textPanel.setBackground(Principal.BEIGE.brighter());
			leftPanel.setBackground(Principal.BEIGE.brighter());
			rightPanel.setBackground(Principal.BEIGE.brighter());
			iconLabel.setBackground(Principal.BEIGE.brighter());
			nameLabel.setBackground(Principal.BEIGE.brighter());
			descriptionLabel.setBackground(Principal.BEIGE.brighter());
		}

		return this;
	}
}