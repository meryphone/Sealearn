package GUI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

public class RoundButton extends JButton {
    private static final long serialVersionUID = -1168121373270615839L;
	private Color baseColor;

    public RoundButton(String label) {
        super(label);
        this.baseColor = new Color(8, 32, 50);
        setContentAreaFilled(false); // Elimina el fondo estándar
        setFocusPainted(false); // Elimina el borde de selección
        setBorderPainted(false); // Elimina el borde predeterminado
    }

    @Override    
    public void paintComponent(Graphics g) {
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