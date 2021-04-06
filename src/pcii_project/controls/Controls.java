package pcii_project.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import pcii_project.models.Model;
import pcii_project.models.TestModel;
import pcii_project.view.MainView;
import pcii_project.view.ContinueView;


/*
 * Class Controls
 * Implemente les controles du clavier
 * Implemente un Thread pour ameliorer le gameplay avec des controleurs simultanees
 * */
public class Controls  implements KeyListener {
	
	/* Attributs */
	
	private Model model; /* modele du jeu*/
	private ThreadControls threadControls; /* controle du jeu*/
	
	/* Constructors */
	
	/*
	 * Constructeur pour l'affichage du jeu
	 * @param  MainView view 
	 * @params Model model
	 * */
	public Controls(MainView view, Model model) {
		this.model = model;
		view.getWindows().addKeyListener(this);
		view.getWindows().addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){  
	          System.exit(0);  
			}
		});	
		this.threadControls = new ThreadControls(model);
		new Thread(threadControls).start();
	}
	
	/*
	 * Constructeur pour l'affichage test du jeu
	 * @param  MainView view 
	 * @params Model model
	 * */
	public Controls(TestModel view, Model model) {
		this.model = model;
		view.getWindows().addKeyListener(this); /* Ajout de l'ecouteur a la vue */
		view.getWindows().addWindowListener(new WindowAdapter(){ /* Ajout du controle pour fermer la fenetre du jeu */
			public void windowClosing(WindowEvent e){  
	          System.exit(0);  
			}
		});	
		this.threadControls = new ThreadControls(model); /* Creation du thread du controleur */
		new Thread(threadControls).start(); /* Lancement du Thread */
	}

	@Override
	public void keyTyped(KeyEvent evt) {	
		/* Not used */
	}

	/*
	 * Evenement : si une touche du clavier est pressee
	 * */
	@Override
	public void keyPressed(KeyEvent evt) {
		/* Recuperation du code renvoyee par l'evenement */
		int keyCode = evt.getKeyCode();
	    try {
	    	/* Traitement du code :
	    	 * La touche presse va etre memorisee dans le thread
	    	 * */
	    	if(keyCode == KeyEvent.VK_SPACE) {
	    		threadControls.set_space(); 
	    		ContinueView frame = new ContinueView(model);
				frame.setVisible(true);
	    	}	
	    	if(keyCode == KeyEvent.VK_RIGHT) {
	    		threadControls.set_right(true);
	    		model.getCars().activeRight();
	    	}
	    	if(keyCode == KeyEvent.VK_LEFT) {
	    		threadControls.set_left(true);
	    		model.getCars().activeLeft();
	    	}
	    	if(keyCode == KeyEvent.VK_UP) {
	    		threadControls.set_up(true);
	    	}
	    }
	    catch(Exception error) {
	    	System.out.println(error);
	    }
	}

	/*
	 * Evenement : si une touche du clavier est relachee
	 * */
	@Override
	public void keyReleased(KeyEvent evt) {
		/* Recuperation du code renvoyee par l'evenement */
		int keyCode = evt.getKeyCode();
	    try {
	    	/* Traitement du code :
	    	 * La touche relachee va etre memorisee dans le thread
	    	 * */
	    	if(keyCode == KeyEvent.VK_RIGHT) {
	    		threadControls.set_right(false);
	    		model.getCars().disabledSide();
	    	}
	    	if(keyCode == KeyEvent.VK_LEFT) {
	    		threadControls.set_left(false);
	    		model.getCars().disabledSide();
	    	}
	    	if(keyCode == KeyEvent.VK_UP) {
	    		threadControls.set_up(false);
	    	}    	
	    }
	    catch(Exception error) {
	    	System.out.println(error);
	    
	    }	
	}
	
	public void set_space() {
		this.threadControls.set_space();
	}
}


/*
 * Thread de la classe Controls
 * Permet d'utiliser des touches simultanement
 * */
class ThreadControls extends Thread{
	
	/* Constants */
	
	public static final int STEP = 10; /* Temps d'attente pour le run */
	
	/* Attributs */
	
	private Model model; /* Recuperation du model */
	
	private boolean up = false; /* Touche : fleche du haut */
	
	private boolean right = false; /* Touche : fleche de droite */
	
	private boolean left = false; /* Touche : fleche de gauche */
	
	private boolean space = false; /* Touche : barre d'espace */
	
	private boolean pause = false; /* Test pour le jeu en pause */
	
	/* Constructors */
	
	/*
	 * @param Model model
	 * */
	public ThreadControls(Model model) {
		this.model = model;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(STEP);
				/* On verifie si le jeu n'est pas en pause et si le jeu n'est pas termine */
				if(!pause && !model.getGame().gameOver()) {
					
					if(right) { 
						/* Deplacement du vehicule du joueur a droite */
			    		model.getCars().move_right();
			    	}
			    	if(left) {
			    		/* Deplacement du vehicule du joueur a gauche */
			    		model.getCars().move_left();
			    	}
			    	if(up) {
			    		/* Deplacement du vehicule du joueur vers le haut */
			    		model.getRoad().move();
			    		/* Activation de l'augmentation du facteur d'acceleration */
			    		model.getRoad().getAcceleration().start_acceleration();
			    	}
			    	if(!up) {
			    		/* Si la fleche du haut n'est pas presse: diminuer le facteur d'acceleration */
			    		model.getRoad().getAcceleration().end_acceleration();
			    	}
				}
				/* Si la barre d'espace est presse : deux option :
				 * - Activation du menu pause
				 * - Desactivation du menu pause
				 * */
		    	if(space) {
		    		if(pause) model.continue_progress(); /* Si le menu pause etait active, on desactive*/
		    		else model.stop_progress(); /* sinon on active le menu pause */
		    		/* reset */
		    		pause = !pause; 
		    		space = !space;
		    	}
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
	
	/* Setters */
	
	public void set_up(boolean up) {
		this.up = up;
	}
	
	public void set_right(boolean right) {
		this.right = right;
	}
	
	public void set_left(boolean left) {
		this.left = left;
	}
	
	public void set_space() {
		this.space =! space;
	}
	
	public void set_pause() {
		this.pause =! pause;
	}
}
