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
import utils.Mensajes;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	public final static Color BEIGE = new Color(211, 204, 194);
	public final static Color BUTTON_COLOR = new Color(8, 32, 50);
	private Controlador controlador = Controlador.getInstance();
	private CursoEnProgreso cursoActual;
	DefaultListModel<Curso> model = new DefaultListModel<Curso>();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Principal ventana = new Principal();
				ventana.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Principal() {
		initialize();
	}

	private void initialize() {
		setBounds(100, 100, 1032, 666);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Principal.BEIGE);

		JPanel top = new JPanel();
		getContentPane().add(top, BorderLayout.NORTH);
		top.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel title = new JLabel(new ImageIcon(Principal.class.getResource("/imagenes/Titulo.png")));
		top.add(title);
		top.setBackground(BEIGE);

		JPanel left = new JPanel();
		getContentPane().add(left, BorderLayout.WEST);
		left.setBackground(Principal.BEIGE);
		left.add(Box.createRigidArea(new Dimension(150, 20)));

		JPanel right = new JPanel();
		getContentPane().add(right, BorderLayout.EAST);
		right.setBackground(Principal.BEIGE);
		right.add(Box.createRigidArea(new Dimension(150, 20)));

		JPanel down = new JPanel();
		getContentPane().add(down, BorderLayout.SOUTH);
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
		getContentPane().add(center0, BorderLayout.CENTER);
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

		panelButtons.add(Box.createRigidArea(new Dimension(20, 20)));

		JButton btnExportStats = new RoundButton("Exportar estadisticas");
		btnExportStats.setPreferredSize(new Dimension(170, 40));
		panelButtons.add(btnExportStats);
		panelButtons.add(Box.createRigidArea(new Dimension(20, 60)));
		btnExportStats.addActionListener(e -> {
			exportarEstadisticas();
		});

		JPanel center1 = new JPanel();
		center0.add(center1, BorderLayout.CENTER);
		center1.setLayout(new BorderLayout());
		center1.setBackground(Principal.BEIGE);

		for (Curso curso : controlador.getCursos()) {
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
	}

	private void iniciarCurso(Curso cursoSeleccionado) {
		CursoEnProgreso cursoEnProgreso = controlador.reanudarCurso(cursoSeleccionado);
		try {
			if (cursoEnProgreso != null) {
				ReanudarCursoView dialog = new ReanudarCursoView(this);
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
				ArrayList<String> parametros = Configuracion.mostrarDialogo(this);
				if (!parametros.isEmpty()) {
					cursoActual = controlador.iniciarCurso(cursoSeleccionado, parametros.get(0), parametros.get(1));
					realizarCurso();
				}
			}
		} catch (ExcepcionCursoActualVacio ex) {
			Mensajes.mostrarAdvertencia(this, ex.getMessage());
		} catch (CursoSinPreguntasCiertaDificultad ex) {
			Mensajes.mostrarError(this, ex.getMessage());
		}
	}

	private void realizarCurso() {
		AtomicBoolean cursoCancelado = new AtomicBoolean(false);
		Runnable onClose = () -> cursoCancelado.set(true);

		while (cursoActual != null && !cursoCancelado.get()) {
			Pregunta preguntaActual = cursoActual.getPreguntaActual();

			if (preguntaActual == null) {
				Mensajes.mostrarConfirmacion(this, "!Curso completado!");
				cursoActual = controlador.finalizarSesionCurso();
				break;
			}

			if (preguntaActual instanceof PreguntaTest) {
				new TestView(this, (PreguntaTest) preguntaActual, onClose).setVisible(true);
			} else if (preguntaActual instanceof PreguntaRellenarHueco) {
				new RellenarHuecoView(this, (PreguntaRellenarHueco) preguntaActual, onClose).setVisible(true);
			} else if (preguntaActual instanceof PreguntaRespuestaCorta) {
				new RespuestaCortaView(this, (PreguntaRespuestaCorta) preguntaActual, onClose).setVisible(true);
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

		int resultado = fileChooser.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File archivo = fileChooser.getSelectedFile();
			try {
				Curso cursoAagregar = controlador.importarCurso(archivo);
				model.addElement(cursoAagregar);
			} catch (IOException e1) {
				Mensajes.mostrarError(this, "Error al leer el archivo: " + e1.getMessage());
			} catch (ExcepcionCursoDuplicado e1) {
				Mensajes.mostrarAdvertencia(this, "El curso seleccionado ya está importado.");
			}
		}
	}

	private void eliminarCurso(Curso cursoSeleccionado) {
		if (cursoSeleccionado != null) {
			int confirm = Mensajes.mostrarSIoNO(this, "¿Desea eliminar el curso seleccionado?");
			if (confirm == JOptionPane.YES_OPTION) {
				controlador.eliminarCurso(cursoSeleccionado);
				model.removeElement(cursoSeleccionado);
			}
		} else {
			Mensajes.mostrarAdvertencia(this, "Seleccione un curso a eliminar");
		}
	}

	private void exportarEstadisticas() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Exportar estadísticas a PDF");

		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
			if (!rutaArchivo.endsWith(".txt")) {
				rutaArchivo += ".txt";
			}
			try {
				controlador.exportarEstadisticas(rutaArchivo);
				Mensajes.mostrarConfirmacion(this, "¡Estadísticas exportadas correctamente!");
			} catch (IOException ex) {
				ex.printStackTrace();
				Mensajes.mostrarError(this, "Error al exportar las estadísticas");
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
	private JPanel leftPanel; 
	private JPanel rightPanel; 

	public CourseCellRenderer() {
		setLayout(new BorderLayout(10, 10)); 
		setBackground(Principal.BEIGE);

		// Panel izquierdo (icono) con BoxLayout
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
		leftPanel.setBackground(Principal.BEIGE);

		// Espacio flexible arriba para centrar el icono
		leftPanel.add(Box.createVerticalGlue());

		// Icono
		iconLabel = new JLabel(new ImageIcon(Principal.class.getResource("/imagenes/gorro-graduacion.png")));
		iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); 
		iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
		leftPanel.add(iconLabel);

		// Espacio flexible abajo para centrar el icono
		leftPanel.add(Box.createVerticalGlue());

		// Panel derecho (bot�n) con BoxLayout
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
		rightPanel.setBackground(Principal.BEIGE);

		// Espacio flexible arriba para centrar el boton
		rightPanel.add(Box.createVerticalGlue());

		// Espacio flexible abajo para centrar el boton
		rightPanel.add(Box.createVerticalGlue());

		// Nombre del curso
		nameLabel = new JLabel();
		nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); 

		// Descripcion del curso
		descriptionLabel = new JLabel();
		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 10)); 

		// Panel para el nombre y la descripcion
		textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); 
		textPanel.setBackground(Principal.BEIGE.brighter());

		// Espacio flexible arriba para centrar el contenido
		textPanel.add(Box.createVerticalGlue());

		// Nombre y descripcion
		textPanel.add(nameLabel);
		textPanel.add(descriptionLabel);

		// Espacio flexible abajo para centrar el contenido
		textPanel.add(Box.createVerticalGlue());
		
		add(leftPanel, BorderLayout.WEST); 
		add(textPanel, BorderLayout.CENTER); 
		add(rightPanel, BorderLayout.EAST); 


		setPreferredSize(new Dimension(300, 60)); 

	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Curso> list, Curso value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		nameLabel.setText(value.getNombre());

		descriptionLabel.setText(value.getDescripcion());

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