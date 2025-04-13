package vistas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controlador.Controlador;
import dominio.PreguntaRellenarHueco;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.ImageIcon;

public class RellenarHuecoView extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
	private JFrame frame;
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

    public RellenarHuecoView() {
    	frame.setTitle("SeaLearn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 536, 431);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 10));
        contentPane.setBackground(Principal.BEIGE);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Principal.BEIGE);
        contentPane.add(centro, BorderLayout.CENTER);


        PreguntaRellenarHueco preguntaHueco = (PreguntaRellenarHueco) controlador.getPreguntaActual();
        JLabel lblNewLabel = new JLabel(preguntaHueco.getEnunciado());

        PreguntaRellenarHueco preguntaHueco = (PreguntaRellenarHueco) controlador.getPreguntaActual();
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
            	    acierto ? "�Correcto!" : "Incorrecto\nLa respuesta correcta era: " + controlador.getPreguntaActual().getRespuestaCorrecta(),
            	    "Resultado",
            	    acierto ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
            	);
            	dispose();
            });
        }

        JButton opcionB = new RoundButton("at");
        respuestas.add(opcionB);

        JButton opcionC = new RoundButton("an");
        respuestas.add(opcionC);

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
