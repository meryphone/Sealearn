package vistas;

import java.awt.*;
import javax.swing.*;

import controlador.Controlador;
import dominio.Estadistica;

public class EstadisticaView extends JFrame {
	
	  public static void main(String[] args) {
	        EventQueue.invokeLater(() -> {
	            try {
	                EstadisticaView frame = new EstadisticaView();
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	    }
	  
	  private static final long serialVersionUID = 1L;
	  private static final Color BEIGE = new Color(211, 204, 194);
	  private static final Color BUTTON_COLOR = new Color(8, 32, 50);
		
	  private Controlador controlador = Controlador.getInstance();


	  public EstadisticaView() {
	      inicializarVista();
	  }


   
    public void inicializarVista() {
        setTitle("Estadisticas");
        setBounds(100, 100, 600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BEIGE);
        setLayout(new BorderLayout(10, 10)); // A�adir margen externo
        
        // Panel superior con imagen
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setBackground(BEIGE);
        JLabel title = new JLabel(new ImageIcon(getClass().getResource("/imagenes/estadisticas.png")));
        top.add(title);
        add(top, BorderLayout.NORTH);

        DefaultListModel<StatItem> statModel = new DefaultListModel<>();
        Estadistica est = controlador.getEstadistica();

        
        statModel.addElement(new StatItem("Racha de d�as", String.valueOf(est.getRachaActual()), "/imagenes/meta.png"));
        statModel.addElement(new StatItem("Tiempo de uso", est.getTiempoTotalEstudio().toMinutes() + " minutos", "/imagenes/reloj.png"));
        statModel.addElement(new StatItem("Preguntas totales", String.valueOf(est.getTotalPreguntasRespondidas()), "/imagenes/preguntas.png"));
        statModel.addElement(new StatItem("Preguntas acertadas", String.valueOf(est.getTotalAciertos()), "/imagenes/correcto.png"));
        statModel.addElement(new StatItem("Preguntas falladas", String.valueOf(est.getTotalFallos()), "/imagenes/fallo.png"));


        JList<StatItem> statList = new JList<>(statModel);
        statList.setCellRenderer(new StatCellRenderer());
        statList.setBackground(BEIGE);
        JScrollPane scrollPane = new JScrollPane(statList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con bot�n
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(BEIGE);
        JButton backButton = new RoundButton("Atras");
        backButton.setBackground(BUTTON_COLOR);
        backButton.setForeground(Color.WHITE);
        bottom.add(backButton);
        add(bottom, BorderLayout.SOUTH);
        backButton.addActionListener(e ->{
        	Principal ventana = new Principal();
        	ventana.getFrame().setVisible(true);
        	dispose();
        });
    }

}

// Clase para representar una estadísstica
class StatItem {
    String nombre;
    String valor;
    String icono;

    public StatItem(String nombre, String valor, String icono) {
        this.nombre = nombre;
        this.valor = valor;
        this.icono = icono;
    }
}

// Renderer personalizado para las estadísticas
class StatCellRenderer extends JPanel implements ListCellRenderer<StatItem> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel iconoLabel;
    private JLabel nombreLabel;
    private JLabel valorLabel;

    public StatCellRenderer() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        iconoLabel = new JLabel();
        nombreLabel = new JLabel();
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        valorLabel = new JLabel();
        valorLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(nombreLabel);
        textPanel.add(valorLabel);

        add(iconoLabel, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends StatItem> list, StatItem item, int index, boolean isSelected, boolean cellHasFocus) {
        nombreLabel.setText(item.nombre);
        valorLabel.setText(item.valor);
        iconoLabel.setIcon(new ImageIcon(getClass().getResource(item.icono)));
        
        if (isSelected) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(Color.WHITE);
        }
        return this;
    }
}
