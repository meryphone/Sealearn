package vistas;

import java.awt.*;
import javax.swing.*;

import controlador.Controlador;
import dominio.Curso;

public class Principal {
	
	public final static Color BEIGE = new Color(211, 204, 194);
	public final static Color BUTTON_COLOR = new Color(8, 32, 50);

    private JFrame frame;
    private Controlador controlador;
    private DefaultListModel<Curso> modeloCursos;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	Controlador controlador = new Controlador();
                Principal window = new Principal(controlador);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Principal(Controlador controlador) {
        this.controlador = controlador;
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
        down.add(Box.createRigidArea(new Dimension(550, 20)));

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
        	EstadisticaView estadistica = new EstadisticaView(controlador);
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
        btnImport.addActionListener(e ->{
        	manejarImportacionCurso();
        });

        JButton btnExport = new RoundButton("Exportar");
        btnExport.setPreferredSize(new Dimension(100, 40));
        panelButtons.add(btnExport);

        JPanel center1 = new JPanel();
        center0.add(center1, BorderLayout.CENTER);
        center1.setLayout(new BorderLayout());
        center1.setBackground(Principal.BEIGE);

        JList<Curso> courseList = new JList<>(modeloCursos);

        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseList.setCellRenderer(new CourseCellRenderer());
        courseList.setBackground(Principal.BEIGE);

        JScrollPane scrollPane = new JScrollPane(courseList);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        center1.add(scrollPane, BorderLayout.CENTER);
        
     // Panel debajo del listado
        JPanel panelIniciar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelIniciar.setBackground(Principal.BEIGE);

        JButton btnIniciarCurso = new RoundButton("Iniciar curso");
        btnIniciarCurso.setPreferredSize(new Dimension(150, 40));
        panelIniciar.add(btnIniciarCurso);

        // A馻dir debajo del listado
        center0.add(panelIniciar, BorderLayout.SOUTH);

        // L骻ica del bot髇
        btnIniciarCurso.addActionListener(e -> {
            Curso seleccionado = courseList.getSelectedValue();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona un curso primero.");
                return;
            }

            controlador.setCursoActual(seleccionado); // Asigna el curso seleccionado al controlador
            new Configuracion(controlador).setVisible(true); // Pasas el controlador a la nueva ventana
            frame.dispose();
        });
                                                 


    }
    
    private void manejarImportacionCurso() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un curso YAML");

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();

            try {
                controlador = new Controlador();
                controlador.importarCurso(ruta);

                Curso cursoImportado = controlador.getCursoActual(); 
                modeloCursos.addElement(cursoImportado); 

                JOptionPane.showMessageDialog(null, "Curso '" + cursoImportado.getNombre() + "' importado correctamente.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al importar el curso:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
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
    private JPanel rightPanel; // Panel para el bot贸n

    public CourseCellRenderer() {
        setLayout(new BorderLayout(10, 10)); // A帽adir espacio entre componentes
        setBackground(Principal.BEIGE);

        // Panel izquierdo (icono) con BoxLayout
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
        leftPanel.setBackground(Principal.BEIGE);

        // Espacio flexible arriba para centrar el icono
        leftPanel.add(Box.createVerticalGlue());

        // Icono
        iconLabel = new JLabel(new ImageIcon(Principal.class.getResource("/imagenes/gorro-graduacion.png")));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // M谩rgenes
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
        leftPanel.add(iconLabel);

        // Espacio flexible abajo para centrar el icono
        leftPanel.add(Box.createVerticalGlue());

        // Panel derecho (bot贸n) con BoxLayout
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
        rightPanel.setBackground(Principal.BEIGE);

        // Espacio flexible arriba para centrar el bot贸n
        rightPanel.add(Box.createVerticalGlue());

        // Espacio flexible abajo para centrar el bot贸n
        rightPanel.add(Box.createVerticalGlue());

        // Nombre del curso
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente m谩s grande para el nombre

        // Descripci贸n del curso
        descriptionLabel = new JLabel();
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Fuente m谩s peque帽a para la descripci贸n

        // Panel para el nombre y la descripci贸n
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
        textPanel.setBackground(Principal.BEIGE.brighter());

        // Espacio flexible arriba para centrar el contenido
        textPanel.add(Box.createVerticalGlue());

        // Nombre y descripci贸n
        textPanel.add(nameLabel);
        textPanel.add(descriptionLabel);

        // Espacio flexible abajo para centrar el contenido
        textPanel.add(Box.createVerticalGlue());

        // A帽adir componentes al panel principal
        add(leftPanel, BorderLayout.WEST); // Panel izquierdo (icono)
        add(textPanel, BorderLayout.CENTER); // Panel central (nombre y descripci贸n)
        add(rightPanel, BorderLayout.EAST); // Panel derecho (bot贸n)

        // Establecer un tama帽o preferido m谩s alto para cada celda
        setPreferredSize(new Dimension(300, 60)); // Ajusta el tama帽o seg煤n tus necesidades
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Curso> list, Curso value, int index, boolean isSelected, boolean cellHasFocus) {
        // Asignar el nombre del curso
        nameLabel.setText(value.getNombre());

        // Asignar una descripci贸n de ejemplo (puedes personalizarla seg煤n tus datos)
        descriptionLabel.setText(value.getDescripcion());

        // Cambiar el color de fondo si est谩 seleccionado
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