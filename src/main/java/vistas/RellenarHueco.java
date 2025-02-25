package vistas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

public class RellenarHueco extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RellenarHueco frame = new RellenarHueco();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RellenarHueco() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 10));
        contentPane.setBackground(new Color(230, 221, 206));

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(new Color(230, 221, 206));
        contentPane.add(centro, BorderLayout.CENTER);

        JLabel lblNewLabel = new JLabel("I ___________ a teacher, too.");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        JPanel pregunta = new JPanel();
        pregunta.add(lblNewLabel);
        pregunta.setBackground(new Color(230, 221, 206));
        centro.add(pregunta);

        JPanel respuestas = new JPanel(new GridLayout(3, 1, 10, 10));
        respuestas.setBackground(new Color(230, 221, 206));
        centro.add(respuestas);

        JButton opcionA = new JButton("am");
        styleButton(opcionA);
        respuestas.add(opcionA);

        JButton opcionB = new JButton("at");
        styleButton(opcionB);
        respuestas.add(opcionB);

        JButton opcionC = new JButton("an");
        styleButton(opcionC);
        respuestas.add(opcionC);

        JPanel arriba = new JPanel();
        contentPane.add(arriba, BorderLayout.NORTH);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(RellenarHueco.class.getResource("/resources/logo(2)-Photoroom.png")));
        arriba.add(lblNewLabel_1);
        arriba.setBackground(new Color(230, 221, 206));
        
        JProgressBar progressBar = new JProgressBar();
        arriba.add(progressBar);
    }

    // Método para estilizar los botones
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(29, 37, 56));
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.LIGHT_GRAY));
        button.setFocusPainted(false);
    }
}
