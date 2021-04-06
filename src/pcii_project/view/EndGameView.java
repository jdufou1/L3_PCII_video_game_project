package pcii_project.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pcii_project.models.Model;

/*
 * Class EndGameView 
 * Affichage du menu de fin de jeu 
 * */
public class EndGameView extends JFrame implements ActionListener{

	
	/* Constants */
	
	public static final int HEIGHT = 600; /* hauteur de la fenetre */
	public static final int WIDTH = 600; /* largeur de la fenetre */
	
	private static final long serialVersionUID = 1L;
	
	/* Attributes */
	
	private Model model; /* Modele */
	private JLabel score,checkpoints; /* JLabel des donnees */
	private JButton quitter,continuer; /* JButton des choix possibles*/
	private boolean displayed = false; /* Affichage de la fenetre */
	
	/* Constructors */
	
	/*
	 * @Param MainView mainView
	 * @Param Model model
	 * */
	public EndGameView(MainView mainView,Model model) {
		super();
		this.model = model;
		setTitle("FIN DE PARTIE");
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* LAYOUT */
		setLayout(new BorderLayout());
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		
		/* LABEL */
		score = new JLabel();
		checkpoints = new JLabel();
		
		jp.add(score,BorderLayout.NORTH);
		jp.add(checkpoints,BorderLayout.SOUTH);
		
		/* BUTTONS */
		JPanel jpButtons = new JPanel();
		quitter = new JButton("Quitter le jeu");
		continuer = new JButton("Nouvelle partie");
		
		/* AJOUT ECOUTEURS */
		
		quitter.addActionListener(this);
		continuer.addActionListener(this);
		
		/* AJOUT PANEL BUTTONS */
		jpButtons.add(quitter,BorderLayout.EAST);
		jpButtons.add(continuer,BorderLayout.WEST);
		
		
		/* AJOUT A LA FENETRE*/
		add(jp,BorderLayout.NORTH);
		add(jpButtons,BorderLayout.SOUTH);
		
	}
	
	/*
	 * @Param void
	 * Mise a jour et affichage des informations du modele dans la fenetre
	 * */
	public void display() {
		score.setText("Votre score final est de : "+model.getData().getScorePlayer()+" m");
		checkpoints.setText("Nombre de checkpoints franchi : " + model.getData().getNbCheckpointsComplete());
		pack();
		setVisible(true);
	}

	/*
	 * @Param void
	 * Gestion des boutons
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == quitter) {
			System.exit(0);
		}
		else if(source == continuer) {
			model.getGame().newGame();
			setVisible(false);
			displayed = false;
		}
		
	}

	/* getters et setters */
	
	public boolean getDisplayed() {
		return displayed;
	}
	
	public void setDisplayed(boolean displayed) {
		this.displayed = displayed;
	}
}
