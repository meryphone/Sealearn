package vistas;

import java.awt.*;
import javax.swing.*;

import controlador.Controlador;
import dominio.Curso;

import excepciones.ExcepcionCursoActualVacio;

public class Principal {
	
	public final static Color BEIGE = new Color(211, 204, 194);
	public final static Color BUTTON_COLOR = new Color(8, 32, 50);
	private Controlador controlador = Controlador.getInstance();
	private Curso cursoActual;

    private JFrame frame;
    private DefaultListModel<Curso> modeloCursos;
    
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
    
    public JFrame getFrame() {
        return frame;
    }

    private void initialize() {
    	modeloCursos = new DefaultListModel<>();
        frame = new JFrame();
        frame.setBounds(100, 100, 892, 616);
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
        btnIniciar.setPreferredSize(new Dimension(85,45));
        
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

        JButton btnStats = new RoundButton("Estadisticas");
        btnStats.setPreferredSize(new Dimension(100, 40));
        panelButtons.add(btnStats);
        btnStats.addActionListener(e->{
        	EstadisticaView estadistica = new EstadisticaView();
        	estadistica.setVisible(true);
        	frame.dispose();
        });
        
        Component rigidArea = Box.createRigidArea(new Dimension(20, 40));
        rigidArea.setPreferredSize(new Dimension(20, 20));
        rigidArea.setMinimumSize(new Dimension(20, 20));
        panelButtons.add(rigidArea);

        JButton btnExportStats = new RoundButton("Exportar estadisticas");
        btnExportStats.setPreferredSize(new Dimension(150, 40));
        panelButtons.add(btnExportStats);
        Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        rigidArea_1.setPreferredSize(new Dimension(20, 60));
        panelButtons.add(rigidArea_1);

        JButton btnImport = new RoundButton("Importar");
        btnImport.setPreferredSize(new Dimension(100, 40));
        panelButtons.add(btnImport);
        panelButtons.add(Box.createRigidArea(new Dimension(20, 20)));

        JButton btnExport = new RoundButton("Exportar");
        btnExport.setPreferredSize(new Dimension(100, 40));
        panelButtons.add(btnExport);

        JPanel center1 = new JPanel();
        center0.add(center1, BorderLayout.CENTER);
        center1.setLayout(new BorderLayout());
        center1.setBackground(Principal.BEIGE);
        
        DefaultListModel<Curso> model = new DefaultListModel<Curso>();
        
        // Cargar la lista de cursos
        for(Curso curso : controlador.getListaCursos()) {
        	model.addElement(curso);
        }

        JList<Curso> courseList = new JList<>(modeloCursos);

        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseList.setCellRenderer(new CourseCellRenderer());
        courseList.setBackground(Principal.BEIGE);
        
        
        btnIniciar.addActionListener( e -> {
            try {
    	        if (courseList.getSelectedValue() != null) {
    	        	cursoActual = courseList.getSelectedValue();
    	            Configuracion ventana = new Configuracion();
    	            ventana.setVisible(true);
    	        } else {
    	            throw new ExcepcionCursoActualVacio("Seleccione un curso antes de comenzar");
    	        }
    	    } catch (ExcepcionCursoActualVacio ex) {
    	        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
    	    }

        });

        JScrollPane scrollPane = new JScrollPane(courseList);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        center1.add(scrollPane, BorderLayout.CENTER);
                                                 
    }
}


class CourseCellRenderer extends JPanel implements ListCellRenderer<Curso> {
    private static final long serialVersionUID = 1L;
    private JLabel iconLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JPanel textPanel;
    private JPanel leftPanel; // Panel para el icono
    private JPanel rightPanel; // Panel para el botón

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
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Márgenes
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
        leftPanel.add(iconLabel);

        // Espacio flexible abajo para centrar el icono
        leftPanel.add(Box.createVerticalGlue());

        // Panel derecho (botón) con BoxLayout
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
        rightPanel.setBackground(Principal.BEIGE);

        // Espacio flexible arriba para centrar el botón
        rightPanel.add(Box.createVerticalGlue());

        // Espacio flexible abajo para centrar el botón
        rightPanel.add(Box.createVerticalGlue());

        // Nombre del curso
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente más grande para el nombre

        // Descripción del curso
        descriptionLabel = new JLabel();
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Fuente más pequeña para la descripción

        // Panel para el nombre y la descripción
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
        textPanel.setBackground(Principal.BEIGE.brighter());

        // Espacio flexible arriba para centrar el contenido
        textPanel.add(Box.createVerticalGlue());

        // Nombre y descripción
        textPanel.add(nameLabel);
        textPanel.add(descriptionLabel);

        // Espacio flexible abajo para centrar el contenido
        textPanel.add(Box.createVerticalGlue());

        // Añadir componentes al panel principal
        add(leftPanel, BorderLayout.WEST); // Panel izquierdo (icono)
        add(textPanel, BorderLayout.CENTER); // Panel central (nombre y descripción)
        add(rightPanel, BorderLayout.EAST); // Panel derecho (botón)

        // Establecer un tamaño preferido más alto para cada celda
        setPreferredSize(new Dimension(300, 60)); // Ajusta el tamaño según tus necesidades
        
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Curso> list, Curso value, int index, boolean isSelected, boolean cellHasFocus) {
        // Asignar el nombre del curso
        nameLabel.setText(value.getNombre());

        // Asignar una descripción de ejemplo (puedes personalizarla según tus datos)
        descriptionLabel.setText(value.getDescripcion());

        // Cambiar el color de fondo si está seleccionado
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