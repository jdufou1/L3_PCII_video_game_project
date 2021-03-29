package pcii_project.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pcii_project.models.Model;

import javax.swing.JButton;

public class ContinueView extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public ContinueView(Model model) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnQuit = new JButton("QUIT");
		btnQuit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setBounds(47, 74, 112, 67);
		contentPane.add(btnQuit);
		
		JButton btnConfirm = new JButton("CONTINUE");
		btnConfirm.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				model.continue_progress();
				dispose();
			}
		});
		btnConfirm.setBounds(251, 74, 112, 67);
		contentPane.add(btnConfirm);
		
	}
}
