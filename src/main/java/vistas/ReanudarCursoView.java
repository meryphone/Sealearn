package vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class ReanudarCursoView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Opcion opcionSeleccionada = Opcion.CANCELAR;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ReanudarCursoView dialog = new ReanudarCursoView(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public enum Opcion {
		RESTABLECER, REANUDAR, CANCELAR
	}

	/**
	 * Create the dialog.
	 */
	 public ReanudarCursoView(JFrame parent) {
	    super(parent, true);
		setBackground(Principal.BEIGE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setBackground(Principal.BEIGE);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblUstedHabaComenzado = new JLabel("Usted había comenzado este curso con anterioridad.");
		lblUstedHabaComenzado.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPanel.add(lblUstedHabaComenzado);

		JLabel lbldeseaComenzarloDe = new JLabel("¿Desea comenzarlo de nuevo o continuar por donde lo dejó?");
		lbldeseaComenzarloDe.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPanel.add(lbldeseaComenzarloDe);

		JLabel foca = new JLabel("");
		foca.setIcon(new ImageIcon(ReanudarCursoView.class.getResource("/imagenes/seal_looking_right_peque.png")));
		contentPanel.add(foca);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Principal.BEIGE);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton restablecerButton = new RoundButton("Restablecer");
		restablecerButton.setPreferredSize(new Dimension(110, 35));
		buttonPane.add(restablecerButton);
		getRootPane().setDefaultButton(restablecerButton);
		restablecerButton.addActionListener(e -> {
			opcionSeleccionada = Opcion.RESTABLECER;
			dispose();
		});

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(110, 0));
		buttonPane.add(horizontalStrut);

		JButton reanudarButton = new RoundButton("Reanudar");
		reanudarButton.setPreferredSize(new Dimension(110, 35));
		buttonPane.add(reanudarButton);
		reanudarButton.addActionListener(e -> {
			opcionSeleccionada = Opcion.REANUDAR;
			dispose();
		});

	}
	
	public Opcion getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

}
