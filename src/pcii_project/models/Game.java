package pcii_project.models;

public class Game {

	/* constantes */
	
	public static final double CHRONO_MAX = 10.0;
		
	/* attributs */
	private Model model;
	
	private Chrono chrono;
	
	private ThreadGame threadGame;
	
	private int bonusTime;
	
	private boolean endGame;
	
	
	/* constructors */

	public Game(Model model) {
		this.model = model;
		chrono = new Chrono();
		threadGame = new ThreadGame(this);
		new Thread(threadGame).start();
		endGame = false;
		bonusTime = 0;
	}
	
	public void newGame() {
		/* on redefinit les valeurs d'une partie */
		System.out.println("[DEBUT DE PARTIE] : CHRONO INTIAL : " + CHRONO_MAX  + "s");
		model.reinitialize(); // On re initialise le modèle
		startChrono(); // On démarre le chrono
		threadGame.setActif(true);
		endGame = false;
	}
	
	public void endGame() {
		System.out.println("[FIN DE PARTIE]");
		chrono.stop();
		threadGame.setActif(false);
		endGame = true;
		
		
		//newGame();
	}
	
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

class ThreadGame extends Thread{
	
	/* Attributs */
	private Game game;
	
	private boolean actif;
	
	/* Constructors */
	public ThreadGame(Game game) {
		this.game = game;
		actif = false;
	}
	

	@Override 
	public void run() {
		try {
			while(true) {
				Thread.sleep(10);
				if(actif)
				{
					if(game.getChrono().getDureeSec() > Game.CHRONO_MAX + game.getBonusTime()) {
						game.endGame();
					}
				}
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
	
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	
	
	
}

