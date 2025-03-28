package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView());
    }

    public LoginView() {
        setTitle("Sealearn");
        setSize(500, 589);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // Panel principal con fondo MainView.BEIGE
        JPanel panel = new JPanel();
        panel.setBackground(MainView.BEIGE);
        panel.setBounds(0, 0, 500, 700);
        panel.setLayout(null);
        getContentPane().add(panel);

        // Cargar la imagen del logo (asegúrate de tener el archivo en el mismo directorio)
        JLabel logoLabel = new JLabel(new ImageIcon(LoginView.class.getResource("/imagenes/SeaLearn-Logo.png"))); 
        logoLabel.setBounds(100, 50, 300, 300); // Ajusta tamaño y posición
        panel.add(logoLabel);

        // Botón "Iniciar"
        JButton iniciarButton = new RoundButton("Comenzar", MainView.BUTTON_COLOR);
        iniciarButton.setFont(new Font("Arial", Font.BOLD, 18));
        iniciarButton.setBounds(100, 400, 140, 60);
        panel.add(iniciarButton);
        iniciarButton.addActionListener(e ->{
        	MainView main = new MainView();
        	main.getFrame().setVisible(true);
        	dispose();  
        });
        

        // Botón "Cancelar"
        JButton cancelarButton = new RoundButton("Cancelar", MainView.BUTTON_COLOR);
        cancelarButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelarButton.setBounds(260, 400, 140, 60);
        panel.add(cancelarButton);
        
        cancelarButton.addActionListener(e -> {
        	dispose();
        });
        setVisible(true);
    }

    // Clase personalizada para hacer los botones redondos con textura
    @SuppressWarnings("serial")
    static class RoundButton extends JButton {
        private Color baseColor;

        public RoundButton(String label, Color baseColor) {
            super(label);
            this.baseColor = baseColor;
            setContentAreaFilled(false); // Elimina el fondo estándar
            setFocusPainted(false); // Elimina el borde de selección
            setBorderPainted(false); // Elimina el borde predeterminado
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gradiente para dar textura estilo botón
            GradientPaint gradient = new GradientPaint(0, 0, baseColor.brighter(), 0, getHeight(), baseColor.darker());
            g2.setPaint(gradient);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40));

            // Sombra para efecto de elevación
            g2.setColor(new Color(0, 0, 0, 50)); // Sombra semitransparente
            g2.fill(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, 40, 40));

            // Dibuja el texto
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 4;
            g2.drawString(getText(), x, y);

            g2.dispose();
        }

      
    }
}
