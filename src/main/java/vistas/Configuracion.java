package vistas;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;  // Importa la clase Font


public class Configuracion extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Configuracion frame = new Configuracion();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Configuracion() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(230, 221, 206));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel centro = new JPanel();
        centro.setBackground(new Color(230, 221, 206));
        contentPane.add(centro, BorderLayout.CENTER);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        Font labelFont = new Font("Arial", Font.BOLD, 16); // Fuente para las etiquetas

        JPanel panelDificultad = new JPanel();
        panelDificultad.setBackground(new Color(230, 221, 206));
        panelDificultad.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel labelDificultad = new JLabel("Dificultad:");
        labelDificultad.setFont(labelFont);
        panelDificultad.add(labelDificultad);
        JComboBox<String> dificultad = new JComboBox<>();
        dificultad.setModel(new DefaultComboBoxModel<>(new String[] {"Fácil", "Medio", "Difícil"}));
        dificultad.setFont(labelFont);
        panelDificultad.add(dificultad);
        centro.add(panelDificultad);
        panelDificultad.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 5));


        JPanel panelOrden = new JPanel();
        panelOrden.setBackground(new Color(230, 221, 206));
        panelOrden.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel labelOrden = new JLabel("Orden:");
        labelOrden.setFont(labelFont);
        panelOrden.add(labelOrden);
        JComboBox<String> orden = new JComboBox<>();
        orden.setModel(new DefaultComboBoxModel<>(new String[] {"Secuencial", "Repetición espaciada", "Aleatoria"}));
        orden.setFont(labelFont);
        panelOrden.add(orden);
        centro.add(panelOrden);

        JPanel abajo = new JPanel();
        abajo.setLayout(new BoxLayout(abajo, BoxLayout.X_AXIS));
        abajo.setBackground(new Color(230, 221, 206));
        abajo.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(abajo, BorderLayout.SOUTH);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(new Font("Arial", Font.PLAIN, 14));  // Establece la fuente para los botones
        JButton comenzar = new JButton("Comenzar");
        comenzar.setFont(new Font("Arial", Font.PLAIN, 14));
        comenzar.setForeground(Color.WHITE);
        comenzar.setBackground(new Color(29, 37, 56));

        abajo.add(Box.createRigidArea(new Dimension(10, 0)));
        abajo.add(cancelar);
        abajo.add(Box.createHorizontalGlue());
        abajo.add(comenzar);
        abajo.add(Box.createRigidArea(new Dimension(10, 0)));

        JPanel logo = new JPanel();
        logo.setBackground(new Color(230, 221, 206));
        contentPane.add(logo, BorderLayout.NORTH);
        
        Component verticalStrut = Box.createVerticalStrut(40);
        logo.add(verticalStrut);
        
                JLabel lblNewLabel = new JLabel("");
                contentPane.add(lblNewLabel, BorderLayout.WEST);
                lblNewLabel.setIcon(new ImageIcon(Configuracion.class.getResource("/resources/focaSinFondo(3).png")));
    }
}
