package pcii_project.models;


/*
 * Class game
 * Modele du deroulement du jeu
 * */
public class Game {

	/* Constants */
	
	public static final double CHRONO_MAX = 30.0; /* Temps disponible avant la fin de partie */
		
	/* Attributes */
	
	private Model model; /* Modele */
	private Chrono chrono; /* Chrono */
	private ThreadGame threadGame; /* Thread de mise a jour du chrono */
	private int bonusTime; /* temps bonus gagne par le joueur en franchissant les checkpoints */
	private boolean endGame; /* fin de partie */
	
	/* Constructors */

	/*
	 * @param Model model
	 * */
	public Game(Model model) {
		this.model = model;
		this.chrono = new Chrono();
		this.threadGame = new ThreadGame(this);
		new Thread(threadGame).start();
		
		endGame = false;
		bonusTime = 0;
	}
	
	/* Functions */
	
	/*
	 * @param void
	 * Lancement d'une nouvelle partie
	 * */
	public void newGame() {
		/* on redefinit les valeurs d'une partie */
		System.out.println("[DEBUT DE PARTIE] : CHRONO INTIAL : " + CHRONO_MAX  + "s");
		model.reinitialize(); // On re initialise le modèle
		startChrono(); // On démarre le chrono
		threadGame.setActif(true);
		endGame = false;
	}
	
	/*
	 * @param void
	 * Met fin a la partie
	 * */
	public void endGame() {
		System.out.println("[FIN DE PARTIE]");
		chrono.stop();
		threadGame.setActif(false);
		endGame = true;
	}
	
	/*
	 * @param void
	 * Demarrage du chrono
	 * */
	public void startChrono() {
		chrono.start();
	}
	
	/* getters and setters */
	
	public boolean gameOver() {
		return endGame;
	}
	
	public Chrono getChrono() {
		return chrono;
	}
	
	public int getBonusTime() {
		return bonusTime;
	}
	
	public void setBonusTime(int bonusTime) {
		this.bonusTime = bonusTime;
	}
	
	public int getTimeremaining() {
		return (int)CHRONO_MAX + bonusTime - (int)chrono.getDureeSec();
	}
}

/*
 * Class threadGame
 * Mise a jour du chrono de la partie
 * */
class ThreadGame extends Thread{
	/* Constants */
	
	public static final int STEP = 10; /* Temps d'attente pour le run */
	
	/* Attributs */
	
	private Game game;
	private boolean actif;
	
	/* Constructors */
	
	/*
	 * @param Game game
	 * */
	public ThreadGame(Game game) {
		this.game = game;
		actif = false;
	}
	
	@Override 
	public void run() {
		try {
			while(true) {
				Thread.sleep(STEP);
				if(actif)
				{
					/* Test si le chrono arrive a terme */
					if(game.getChrono().getDureeSec() > Game.CHRONO_MAX + game.getBonusTime()) {
						game.endGame(); /* fin de partie */
					}
				}
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
	
	/* setters */
	
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	
	
	
}

