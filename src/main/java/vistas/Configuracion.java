package vistas;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.util.ArrayList;


public class Configuracion extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String estrategia;
    private String dificultad;


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
        inicializarVentana();
    }


    private void inicializarVentana() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 468, 316);
        contentPane = new JPanel();
        contentPane.setBackground(Principal.BEIGE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel centro = new JPanel();
        centro.setBackground(Principal.BEIGE);
        contentPane.add(centro, BorderLayout.CENTER);
        centro.setLayout(new BoxLayout(centro, BoxLayout.X_AXIS));
        centro.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 5));
        
        JLabel lblDificultad = new JLabel("Dificultad: ");
        lblDificultad.setFont(new Font("Dialog", Font.BOLD, 15));
        centro.add(lblDificultad);
        
        JComboBox<String> dificultadesComboBox = new JComboBox<String>();
        dificultadesComboBox.setPreferredSize(new Dimension(130, 26));
        dificultadesComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Facil", "Media", "Dificil"}));
        centro.add(dificultadesComboBox);
        
        Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        rigidArea_1.setPreferredSize(new Dimension(10, 20));
        centro.add(rigidArea_1);

        JPanel panelOrden = new JPanel();
        panelOrden.setBackground(Principal.BEIGE);
        panelOrden.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
        rigidArea.setPreferredSize(new Dimension(10, 20));
        panelOrden.add(rigidArea);
        JLabel labelOrden = new JLabel("Orden:");
        labelOrden.setFont(new Font("Dialog", Font.BOLD, 15));
        panelOrden.add(labelOrden);
        contentPane.add(panelOrden, BorderLayout.WEST);
        
        JComboBox<String> estrategiaComboBox = new JComboBox<String>();
        estrategiaComboBox.setPreferredSize(new Dimension(130, 26));
        estrategiaComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Secuencial", "Repeticion espaciada", "Aleatoria"}));
        panelOrden.add(estrategiaComboBox);

        JPanel abajo = new JPanel();
        abajo.setLayout(new BoxLayout(abajo, BoxLayout.X_AXIS));
        abajo.setBackground(Principal.BEIGE);
        abajo.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(abajo, BorderLayout.SOUTH);

        JButton cancelar = new RoundButton("Cancelar");
        cancelar.setMaximumSize(new Dimension(86, 40));
        cancelar.setMinimumSize(new Dimension(86, 40));
        cancelar.addActionListener(e -> {
        	Principal ventana = new Principal();
        	ventana.getFrame().setVisible(true);
        	dispose();
        });
        cancelar.setPreferredSize(new Dimension(86, 31));
      
        JButton comenzar = new RoundButton("Comenzar");
        comenzar.setMaximumSize(new Dimension(94, 40));
        comenzar.setMinimumSize(new Dimension(94, 70));
        comenzar.setPreferredSize(new Dimension(94, 70));
        
        comenzar.addActionListener(e -> {
        	estrategia = (String) estrategiaComboBox.getSelectedItem();
        	dificultad = (String) dificultadesComboBox.getSelectedItem();
        });

        abajo.add(Box.createRigidArea(new Dimension(10, 0)));
        abajo.add(cancelar);
        
        Component horizontalGlue = Box.createHorizontalGlue();
        abajo.add(horizontalGlue);
        
        JLabel seal = new JLabel("");
        seal.setPreferredSize(new Dimension(113, 100));
        seal.setIcon(new ImageIcon(Configuracion.class.getResource("/imagenes/seal_looking_right_peque.png")));
        abajo.add(seal);
        
        Component horizontalGlue_1 = Box.createHorizontalGlue();
        abajo.add(horizontalGlue_1);
        abajo.add(comenzar);
        abajo.add(Box.createRigidArea(new Dimension(10, 0)));
        
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel panelLabel = new JPanel();
        panel.add(panelLabel);
        panelLabel.setBackground(Principal.BEIGE.brighter());
        
        JLabel lblSeleccioneElOrden_1 = new JLabel("Seleccione la estrategia para mostrar las preguntas");
        lblSeleccioneElOrden_1.setFont(new Font("Dialog", Font.BOLD, 14));
        panelLabel.add(lblSeleccioneElOrden_1);
        
        JPanel espacio = new JPanel();
        panel.add(espacio);
        espacio.setBackground(Principal.BEIGE);
        
        Component verticalStrut_1 = Box.createVerticalStrut(20);
        espacio.add(verticalStrut_1);
    }
    
    public static ArrayList<String> mostrarDialogo() {
    	ArrayList<String> parametros = new ArrayList<String>();
    	Configuracion dialog = new Configuracion();
    	parametros.add(dialog.estrategia);
    	parametros.add(dialog.dificultad);
        dialog.setVisible(true);        
        return parametros;
    }
    
}
