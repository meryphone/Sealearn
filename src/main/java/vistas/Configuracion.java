package vistas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Configuracion extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String estrategia;
    private String dificultad;

    public Configuracion(JFrame owner) {
        super(owner, "Configuración", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 449, 309);

        contentPane = new JPanel();
        contentPane.setBackground(Principal.BEIGE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // ---------- Panel superior con mensaje ----------
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        contentPane.add(panel, BorderLayout.NORTH);

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(Principal.BEIGE.brighter());
        panel.add(panelLabel);

        JLabel lblSeleccione = new JLabel("Seleccione la estrategia y dificultad para realizar el curso");
        lblSeleccione.setFont(new Font("Dialog", Font.BOLD, 14));
        panelLabel.add(lblSeleccione);

        JPanel espacio = new JPanel();
        espacio.setBackground(Principal.BEIGE);
        panel.add(espacio);
        espacio.add(Box.createVerticalStrut(20));

        // ---------- Panel central con ComboBoxes ----------
        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        centro.setBackground(Principal.BEIGE);
        contentPane.add(centro, BorderLayout.CENTER);

        JComboBox<String> posiblesEstrategias = new JComboBox<>(new String[]{"Secuencial", "Repeticion Espaciada", "Aleatoria"});
        posiblesEstrategias.setPreferredSize(new Dimension(130, 26));
        centro.add(posiblesEstrategias);

        JComboBox<String> posiblesDificultades = new JComboBox<>(new String[]{"Facil", "Media", "Dificil"});
        posiblesDificultades.setPreferredSize(new Dimension(130, 26));
        centro.add(posiblesDificultades);

        // ---------- Panel inferior con botones e imagen ----------
        JPanel abajo = new JPanel();
        abajo.setLayout(new BoxLayout(abajo, BoxLayout.X_AXIS));
        abajo.setBackground(Principal.BEIGE);
        abajo.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(abajo, BorderLayout.SOUTH);

        JButton cancelar = new RoundButton("Cancelar");
        cancelar.setPreferredSize(new Dimension(95, 80));
        cancelar.addActionListener((ActionEvent e) -> dispose()); // cerrar al cancelar
        abajo.add(Box.createRigidArea(new Dimension(10, 0)));
        abajo.add(cancelar);

        abajo.add(Box.createHorizontalGlue());

        JLabel seal = new JLabel(new ImageIcon(getClass().getResource("/imagenes/seal_looking_right_peque.png")));
        seal.setPreferredSize(new Dimension(113, 100));
        abajo.add(seal);

        abajo.add(Box.createHorizontalGlue());

        JButton comenzar = new RoundButton("Comenzar");
        comenzar.setPreferredSize(new Dimension(95, 80));
        comenzar.addActionListener(e -> {
            estrategia = (String) posiblesEstrategias.getSelectedItem();
            dificultad = (String) posiblesDificultades.getSelectedItem();
            dispose(); // cerrar y devolver valores
        });
        abajo.add(comenzar);
        abajo.add(Box.createRigidArea(new Dimension(10, 0)));
    }

    public String getEstrategia() {
        return estrategia;
    }

    public String getDificultad() {
        return dificultad;
    }

    /**
     * Muestra el diálogo de configuración y devuelve los parámetros seleccionados
     */
    public static ArrayList<String> mostrarDialogo(JFrame owner) {
        ArrayList<String> parametros = new ArrayList<>();
        Configuracion config = new Configuracion(owner);
        config.setVisible(true); // Espera a que el usuario elija
        parametros.add(config.getEstrategia());
        parametros.add(config.getDificultad());
        return parametros;
    }
}
