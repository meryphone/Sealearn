package GUI;

import java.awt.*;
import javax.swing.*;

public class RespuestaEscrita extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Color BEIGE = new Color(211, 204, 194);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 14);

    private JTextField textField;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RespuestaEscrita frame = new RespuestaEscrita();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RespuestaEscrita() {
        setTitle("Pregunta Escrita");
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BEIGE);
        getContentPane().setLayout(new BorderLayout(10, 10));

        // Panel superior con imagen
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setBackground(BEIGE);
        getContentPane().add(top, BorderLayout.NORTH);
        
        Component verticalStrut = Box.createVerticalStrut(60);
        top.add(verticalStrut);

        // Panel central
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(BEIGE);
        getContentPane().add(center, BorderLayout.CENTER);

        // Panel de la pregunta
        JPanel pregunta = new JPanel();
        pregunta.setBackground(BEIGE);
        JLabel preguntaLabel = new JLabel("¿Cuál es la capital de España?");
        preguntaLabel.setFont(LABEL_FONT);
        pregunta.add(preguntaLabel);
        center.add(pregunta);

        // Panel de respuesta con campo de texto
        JPanel respuesta = new JPanel();
        respuesta.setBackground(BEIGE);
        textField = new JTextField();
        textField.setFont(TEXT_FONT);
        textField.setColumns(15);
        respuesta.add(textField);
        center.add(respuesta);

        // Panel de barra de progreso con imagen
        JPanel barraProgreso = new JPanel();
        barraProgreso.setBackground(BEIGE);
        JLabel iconLabel = new JLabel(new ImageIcon(getClass().getResource("/imagenes/seal_looking_right.png")));
        barraProgreso.add(iconLabel);
        JProgressBar progressBar = new JProgressBar();
        barraProgreso.add(progressBar);
        center.add(barraProgreso);
    }

}
