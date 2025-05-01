package vistas;

import java.awt.*;
import java.time.Duration;

import javax.swing.*;

import controlador.Controlador;
import dominio.Estadistica;
import utils.Mensajes;

public class EstadisticaView extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Color BEIGE = new Color(211, 204, 194);
	private static final Color BUTTON_COLOR = new Color(8, 32, 50);
	private Estadistica estadistica;
	private Controlador controlador = Controlador.getInstance();

	public EstadisticaView(Estadistica stats) {
		this.estadistica = stats;
		setTitle("Estadísticas");
		setBounds(100, 100, 633, 574);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(BEIGE);
		getContentPane().setLayout(new BorderLayout(10, 10)); // Anadir margen externo

		// Panel superior con imagen
		JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		top.setBackground(BEIGE);
		JLabel title = new JLabel(new ImageIcon(getClass().getResource("/imagenes/estadisticas.png")));
		top.add(title);
		getContentPane().add(top, BorderLayout.NORTH);

		// Lista de estad�sticas con CellRenderer personalizado
		DefaultListModel<StatItem> statModel = new DefaultListModel<>();
		statModel.addElement(
				new StatItem("Racha de días", String.valueOf(estadistica.getRachaActual()), "/imagenes/meta.png"));
		statModel.addElement(new StatItem("Tiempo de uso", formatDuration(estadistica.getTiempoTotalEstudio()),
				"/imagenes/reloj.png"));
		statModel.addElement(new StatItem("Preguntas totales",
				String.valueOf(estadistica.getTotalPreguntasRespondidas()), "/imagenes/preguntas.png"));
		statModel.addElement(new StatItem("Preguntas acertadas", String.valueOf(estadistica.getTotalAciertos()),
				"/imagenes/correcto.png"));
		statModel.addElement(new StatItem("Preguntas falladas", String.valueOf(estadistica.getTotalFallos()),
				"/imagenes/fallo.png"));

		JList<StatItem> statList = new JList<>(statModel);
		statList.setCellRenderer(new StatCellRenderer());
		statList.setBackground(BEIGE);
		JScrollPane scrollPane = new JScrollPane(statList);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		// Panel inferior con botones
		JPanel bottom = new JPanel(new BorderLayout(10, 10));
		bottom.setBackground(BEIGE);

		// Botón "Atrás"
		JButton backButton = new RoundButton("Atrás");
		backButton.setBackground(BUTTON_COLOR);
		backButton.setForeground(Color.WHITE);
		backButton.setPreferredSize(new Dimension(70, 40));
		bottom.add(backButton, BorderLayout.WEST);

		// Botón "Restablecer estadísticas"
		JButton resetButton = new RoundButton("Restablecer");
		resetButton.setBackground(BUTTON_COLOR);
		resetButton.setForeground(Color.WHITE);
		resetButton.setPreferredSize(new Dimension(120, 40));
		bottom.add(resetButton, BorderLayout.EAST);

		getContentPane().add(bottom, BorderLayout.SOUTH);

		// Acción del botón "Atrás"
		backButton.addActionListener(e -> {
			dispose();
		});

		// Acción del botón "Restablecer estadísticas"
		resetButton.addActionListener(e -> {
			int confirm = Mensajes.mostrarSIoNO(this, "¿Seguro que desea elimiar las estadisticas?");
			if (confirm == JOptionPane.YES_OPTION) {
				controlador.restablecerEstadisticas();
				Mensajes.mostrarConfirmacion(this, "¡Estadísticas restablecidas correctamente!");
				dispose();
			}
		});

	}

	private String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		return String.format("%d horas %d minutos", hours, minutes);
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
		setBackground(Color.WHITE); // Fondo blanco para diferenciarlo de la ventana
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

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
	public Component getListCellRendererComponent(JList<? extends StatItem> list, StatItem item, int index,
			boolean isSelected, boolean cellHasFocus) {
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