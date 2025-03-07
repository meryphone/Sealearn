package vistas;

import java.awt.*;
import javax.swing.*;


public class TipoTestVIew {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TipoTestVIew window = new TipoTestVIew();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TipoTestVIew() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setTitle("SeaLearn");
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 423);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		
		JPanel panelQuestions = new JPanel();
		frame.getContentPane().add(panelQuestions);
		panelQuestions.setLayout(new BorderLayout(0, 0));
		frame.setBackground(Principal.BEIGE);
		
		JPanel top = new JPanel();
		panelQuestions.add(top, BorderLayout.NORTH);
		top.setBackground(Principal.BEIGE);
		
		JLabel lblQuestion = new JLabel("¿Cuál es la capital de francia?");
		top.add(lblQuestion);
		
		JPanel optionsPanel = new JPanel();
		panelQuestions.add(optionsPanel, BorderLayout.CENTER);
		optionsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		panelQuestions.setBackground(Principal.BEIGE);
		
		JRadioButton rdbtnOpcion1 = new JRadioButton("Francia");
		optionsPanel.add(rdbtnOpcion1);
		rdbtnOpcion1.setBackground(Principal.BEIGE.brighter());
		
		JRadioButton rdbtnOpcion2 = new JRadioButton("Dublin");
		optionsPanel.add(rdbtnOpcion2);
		rdbtnOpcion2.setBackground(Principal.BEIGE.brighter());
		
		JRadioButton rdbtnOpcion3 = new JRadioButton("Paris");
		optionsPanel.add(rdbtnOpcion3);
		rdbtnOpcion3.setBackground(Principal.BEIGE.brighter());
		
		buttonGroup.add(rdbtnOpcion1);
		buttonGroup.add(rdbtnOpcion2);
		buttonGroup.add(rdbtnOpcion3);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalGlue_1.setPreferredSize(new Dimension(25, 0));
		panelQuestions.add(horizontalGlue_1, BorderLayout.EAST);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setPreferredSize(new Dimension(25, 0));
		panelQuestions.add(horizontalGlue, BorderLayout.WEST);
		
		JPanel panelHeadline = new JPanel();
		frame.getContentPane().add(panelHeadline);
		panelHeadline.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelHeadline.setBackground(Principal.BEIGE);
		
		JLabel labelSeal = new JLabel("");
		labelSeal.setIcon(new ImageIcon(TipoTestVIew.class.getResource("/imagenes/seal_looking_right.png")));
		panelHeadline.add(labelSeal);
		
		JProgressBar progressBar = new JProgressBar();
		panelHeadline.add(progressBar);
		progressBar.setBackground(Principal.BEIGE.brighter());
		
		JPanel down = new JPanel();
		frame.getContentPane().add(down);
		down.setBackground(Principal.BEIGE);
		
		JButton btnSiguiente = new RoundButton("Siguiente");
		btnSiguiente.setPreferredSize(new Dimension(92, 40));
		down.add(btnSiguiente);
	}

}
