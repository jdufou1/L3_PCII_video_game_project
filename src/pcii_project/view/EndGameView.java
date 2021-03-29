package pcii_project.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pcii_project.models.Model;

public class EndGameView extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int HEIGHT = 600;
	
	public static final int WIDTH = 600;
	
	/* attributs */
	
	private CarModel mainView;
	
	private Model model;
	
	private JLabel score_display;
	
	
	
	
	private boolean displayed = false;
	
	public EndGameView(CarModel mainView,Model model) {
		super();
		this.mainView = mainView;
		this.model = model;
		/*
		this.mainView = mainView;
		this.model = model;
		*/
		setTitle("FIN DE PARTIE");
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public boolean getDisplayed() {
		return displayed;
	}
	
	public void setDisplayed(boolean displayed) {
		this.displayed = displayed;
	}
	

	public void display() {
		
		add(new JLabel("Votre score final est de : "+model.getData().getScorePlayer()+" m"));
		
		add(new JLabel("Nombre de checkpoints franchi : " + model.getData().getNbCheckpointsComplete()));
		pack();
		setVisible(true);
	}

	
	
	
	
	/*
	public static void main(String[] args) {
		//EndGameView v = new EndGameView();
		//v.setVisible(true);
	}
	*/
	
	
	
	
}
