package GUI;

import java.awt.*;
import javax.swing.*;

public class MainView {
	
	public final static Color BEIGE = new Color(211, 204, 194);
	public final static Color BUTTON_COLOR = new Color(8, 32, 50);

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainView window = new MainView();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 892, 616);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(MainView.BEIGE);

        JPanel top = new JPanel();
        frame.getContentPane().add(top, BorderLayout.NORTH);
        top.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel title = new JLabel(new ImageIcon(MainView.class.getResource("/Titulo.png")));
        top.add(title);
        top.setBackground(BEIGE);

        JPanel left = new JPanel();
        frame.getContentPane().add(left, BorderLayout.WEST);
        left.setBackground(MainView.BEIGE);
        left.add(Box.createRigidArea(new Dimension(150, 20)));

        JPanel right = new JPanel();
        frame.getContentPane().add(right, BorderLayout.EAST);
        right.setBackground(MainView.BEIGE);
        right.add(Box.createRigidArea(new Dimension(150, 20)));

        JPanel down = new JPanel();
        frame.getContentPane().add(down, BorderLayout.SOUTH);
        down.setBackground(MainView.BEIGE);

        JLabel sealLeft = new JLabel(new ImageIcon(MainView.class.getResource("/seal_looking_right.png")));
        down.add(sealLeft);
        down.add(Box.createRigidArea(new Dimension(550, 20)));

        JLabel sealRight = new JLabel(new ImageIcon(MainView.class.getResource("/seal.png")));
        down.add(sealRight);

        JPanel center0 = new JPanel();
        frame.getContentPane().add(center0, BorderLayout.CENTER);
        center0.setLayout(new BorderLayout());
        center0.setBackground(MainView.BEIGE);

        JPanel panelButtons = new JPanel();
        center0.add(panelButtons, BorderLayout.NORTH);
        panelButtons.setBackground(MainView.BEIGE);

        JButton btnStats = new RoundButton("Estadísticas");
        btnStats.setPreferredSize(new Dimension(100, 40));
        panelButtons.add(btnStats);
        Component rigidArea = Box.createRigidArea(new Dimension(20, 40));
        rigidArea.setPreferredSize(new Dimension(20, 20));
        rigidArea.setMinimumSize(new Dimension(20, 20));
        panelButtons.add(rigidArea);

        JButton btnExportStats = new RoundButton("Exportar estadísticas");
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
        center1.setBackground(MainView.BEIGE);

        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Curso 1");
        model.addElement("Curso 2");
        model.addElement("Curso 3");

        JList<String> courseList = new JList<>(model);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseList.setCellRenderer(new CourseCellRenderer());
        courseList.setBackground(MainView.BEIGE);

        JScrollPane scrollPane = new JScrollPane(courseList);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        center1.add(scrollPane, BorderLayout.CENTER);
    }
}


class CourseCellRenderer extends JPanel implements ListCellRenderer<String> {
    private static final long serialVersionUID = 1L;
    private JLabel iconLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JButton startButton;
    private JPanel textPanel;
    private JPanel leftPanel; // Panel para el icono
    private JPanel rightPanel; // Panel para el botón

    public CourseCellRenderer() {
        setLayout(new BorderLayout(10, 10)); // Añadir espacio entre componentes
        setBackground(MainView.BEIGE);

        // Panel izquierdo (icono) con BoxLayout
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
        leftPanel.setBackground(MainView.BEIGE);

        // Espacio flexible arriba para centrar el icono
        leftPanel.add(Box.createVerticalGlue());

        // Icono
        iconLabel = new JLabel(new ImageIcon(MainView.class.getResource("/gorro-graduacion.png")));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Márgenes
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
        leftPanel.add(iconLabel);

        // Espacio flexible abajo para centrar el icono
        leftPanel.add(Box.createVerticalGlue());

        // Panel derecho (botón) con BoxLayout
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Organizar verticalmente
        rightPanel.setBackground(MainView.BEIGE);

        // Espacio flexible arriba para centrar el botón
        rightPanel.add(Box.createVerticalGlue());

        // Botón
        startButton = new RoundButton("Iniciar"); // Botón con tamaño personalizado
        startButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Márgenes internos
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
        startButton.setPreferredSize(new Dimension(70,45));
        rightPanel.add(startButton);

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
        textPanel.setBackground(MainView.BEIGE.brighter());

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
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        // Asignar el nombre del curso
        nameLabel.setText(value);

        // Asignar una descripción de ejemplo (puedes personalizarla según tus datos)
        descriptionLabel.setText("Descripción breve del curso " + value);

        // Cambiar el color de fondo si está seleccionado
        if (isSelected) {
            setBackground(MainView.BEIGE.darker());
            textPanel.setBackground(MainView.BEIGE.darker());
            leftPanel.setBackground(MainView.BEIGE.darker());
            rightPanel.setBackground(MainView.BEIGE.darker());
            iconLabel.setBackground(MainView.BEIGE.darker());
            nameLabel.setBackground(MainView.BEIGE.darker());
            descriptionLabel.setBackground(MainView.BEIGE.darker());
            startButton.setBackground(MainView.BEIGE.darker());
        } else {
            setBackground(MainView.BEIGE.brighter());
            textPanel.setBackground(MainView.BEIGE.brighter());
            leftPanel.setBackground(MainView.BEIGE.brighter());
            rightPanel.setBackground(MainView.BEIGE.brighter());
            iconLabel.setBackground(MainView.BEIGE.brighter());
            nameLabel.setBackground(MainView.BEIGE.brighter());
            descriptionLabel.setBackground(MainView.BEIGE.brighter());
            startButton.setBackground(MainView.BEIGE.brighter());
        }

        return this;
    }
}