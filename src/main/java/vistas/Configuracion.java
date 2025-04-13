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
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;  // Importa la clase Font


public class Configuracion extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String estrategia;

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
        setBounds(100, 100, 449, 309);
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
        
        JComboBox<String> posiblesEtrategias = new JComboBox<String>();
        centro.add(posiblesEtrategias);
        posiblesEtrategias.setPreferredSize(new Dimension(130, 26));
        posiblesEtrategias.setModel(new DefaultComboBoxModel<>(new String[] {"Secuencial", "Repetición espaciada", "Aleatoria"}));


        JPanel abajo = new JPanel();
        abajo.setLayout(new BoxLayout(abajo, BoxLayout.X_AXIS));
        abajo.setBackground(Principal.BEIGE);
        abajo.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(abajo, BorderLayout.SOUTH);

        JButton cancelar = new RoundButton("Cancelar");
        cancelar.setMaximumSize(new Dimension(86, 40));
        cancelar.setMinimumSize(new Dimension(86, 40));
        cancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        cancelar.setPreferredSize(new Dimension(86, 31));
      
        JButton comenzar = new RoundButton("Comenzar");
        comenzar.setMaximumSize(new Dimension(94, 40));
        comenzar.setMinimumSize(new Dimension(94, 70));
        comenzar.setPreferredSize(new Dimension(94, 70));
        comenzar.addActionListener(e -> {
        	estrategia =(String) posiblesEtrategias.getSelectedItem();
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
    
}
