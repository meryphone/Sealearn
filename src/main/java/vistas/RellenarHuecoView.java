package vistas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import dominio.PreguntaHueco;

import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.ImageIcon;

public class RellenarHuecoView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
	private Controlador controlador = Controlador.getInstance();
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RellenarHuecoView frame = new RellenarHuecoView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public RellenarHuecoView () {
    	inicializarVista();
    }

    public void inicializarVista() {
    	setTitle("SeaLearn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 536, 431);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 10));
        contentPane.setBackground(Principal.BEIGE);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Principal.BEIGE);
        contentPane.add(centro, BorderLayout.CENTER);

        PreguntaHueco preguntaHueco = (PreguntaHueco) controlador.getPreguntaActual();
        JLabel lblNewLabel = new JLabel(preguntaHueco.getEnunciado());

        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        JPanel pregunta = new JPanel();
        pregunta.add(lblNewLabel);
        pregunta.setBackground(Principal.BEIGE);
        centro.add(pregunta);

        JPanel respuestas = new JPanel(new GridLayout(3, 1, 10, 10));
        respuestas.setBackground(Principal.BEIGE);
        centro.add(respuestas);

        for (String opcion : preguntaHueco.getListaOpciones()) {
            JButton btn = new RoundButton(opcion);
            respuestas.add(btn);
            btn.addActionListener(e -> {
            	boolean acierto = controlador.validarRespuesta(opcion);
            	JOptionPane.showMessageDialog(this,
            	    acierto ? "¡Correcto!" : "Incorrecto\nLa respuesta correcta era: " + controlador.getPreguntaActual().getRespuestaCorrecta(),
            	    "Resultado",
            	    acierto ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
            	);
            	dispose();
            });
        }


        JPanel arriba = new JPanel();
        contentPane.add(arriba, BorderLayout.NORTH);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(getClass().getResource("/imagenes/seal_looking_right.png")));
        arriba.add(lblNewLabel_1);
        arriba.setBackground(Principal.BEIGE);
        
        JProgressBar progressBar = new JProgressBar();
        arriba.add(progressBar);
    }


}
