package GUI;

import java.awt.*;
import javax.swing.*;


public class TestView {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestView window = new TestView();
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
	public TestView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 442);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		
		JPanel panelQuestions = new JPanel();
		frame.getContentPane().add(panelQuestions);
		panelQuestions.setLayout(new BorderLayout(0, 0));
		frame.setBackground(MainView.BEIGE);
		
		JPanel top = new JPanel();
		panelQuestions.add(top, BorderLayout.NORTH);
		top.setBackground(MainView.BEIGE);
		
		JLabel lblQuestion = new JLabel("¿Cuál es la capital de francia?");
		top.add(lblQuestion);
		
		JPanel optionsPanel = new JPanel();
		panelQuestions.add(optionsPanel, BorderLayout.CENTER);
		optionsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		panelQuestions.setBackground(MainView.BEIGE);
		
		JRadioButton rdbtnOpcion1 = new JRadioButton("Francia");
		optionsPanel.add(rdbtnOpcion1);
		rdbtnOpcion1.setBackground(MainView.BEIGE.brighter());
		
		JRadioButton rdbtnOpcion2 = new JRadioButton("Dublín");
		optionsPanel.add(rdbtnOpcion2);
		rdbtnOpcion2.setBackground(MainView.BEIGE.brighter());
		
		JRadioButton rdbtnOpcion3 = new JRadioButton("Paris");
		optionsPanel.add(rdbtnOpcion3);
		rdbtnOpcion3.setBackground(MainView.BEIGE.brighter());
		
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
		panelHeadline.setBackground(MainView.BEIGE);
		
		JLabel labelSeal = new JLabel("");
		labelSeal.setIcon(new ImageIcon("/home/maria/Escritorio/PDS/ProyectoPDS/src/main/resources/seal_looking_right.png"));
		panelHeadline.add(labelSeal);
		
		JProgressBar progressBar = new JProgressBar();
		panelHeadline.add(progressBar);
		progressBar.setBackground(MainView.BEIGE.brighter());
		
		JPanel down = new JPanel();
		frame.getContentPane().add(down);
		down.setBackground(MainView.BEIGE);
		
		JButton btnSiguiente = new RoundButton("Siguiente");
		btnSiguiente.setPreferredSize(new Dimension(92, 40));
		down.add(btnSiguiente);
	}

}
