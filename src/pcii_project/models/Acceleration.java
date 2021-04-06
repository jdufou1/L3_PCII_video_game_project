package pcii_project.models;

import pcii_project.models.data.DataGame;


/*
 * Class Acceleration
 * Modele de l'acceleration
 * */
public class Acceleration {
	
	/* Constants */

	public static final double ACCELERATION = 0.01; /* Base du facteur d'acceleration a augmentee */
	public static final int VITESSE_MAX_LVL1 = 10; /* Vitesse maximale niveau 1*/
	public static final int VITESSE_MAX_LVL2 = 40; /* VItesse maximale niveau 2*/
	
	/* Attributes */
	
	private boolean vitesseLvl1Activated = true; /* activation de la limite de vitesse 1*/
	private boolean vitesseLvl2Activated = false; /* activation de la limite de vitesse 2*/
	private ThreadStartAcceleration threadAcc; /* Thread d'augmentation de l'acceleration */
	private DataGame data; /* Donnees du joueur */
	private Model model; /* Modele */
	
	/* Constructors */
	
	/*
	 * @param DataGame data
	 * @param Model model
	 * */
	public Acceleration(DataGame data,Model model) {
		this.model = model;
		this.data = data;
		threadAcc = new ThreadStartAcceleration(this,data);
		new Thread(threadAcc).start();
	}
	
	/* Functions*/
	
	/*
	 * @param void
	 * Augmente le facteur d'acceleration du joueur 
	 * */
	public void increase_acceleration() {
		
		if(vitesseLvl2Activated) {
			if(data.getFactorAcceleration() < VITESSE_MAX_LVL2) {
				data.setFactorAcceleration(data.getFactorAcceleration() * (1.0 + ACCELERATION));
			}
		}
		else if(vitesseLvl1Activated) {
			if(data.getFactorAcceleration() < VITESSE_MAX_LVL1) {
				data.setFactorAcceleration(data.getFactorAcceleration() * (1.0 + ACCELERATION));
			}
		}
		
	}
	
	/*
	 * @param void
	 * Diminue le facteur d'acceleration du joueur 
	 * */
	public void decrease_acceleration() {
		data.setFactorAcceleration( data.getFactorAcceleration() * (1-ACCELERATION));
		if(data.getFactorAcceleration() < 1 ) 
			data.setFactorAcceleration(1.0);
	}
	
	/*
	 * @param void
	 * Diminue lentement le facteur d'acceleration du joueur 
	 * */
	public void decrease_slowly_acceleration() {
		data.setFactorAcceleration( data.getFactorAcceleration() * 0.90);
		if(data.getFactorAcceleration() < 1 ) 
			data.setFactorAcceleration(1.0);
	}
	
	/*
	 * @param void
	 * Active le mode Acceleration dans le Thread
	 * */
	public void start_acceleration() {
		threadAcc.setIsActivated(true);
	}
	
	/*
	 * @param void
	 * Active le thread Acceleration
	 * */
	public void end_acceleration() {
		threadAcc.setIsActivated(false);
	}
	
	/*
	 * @param boolean pause
	 * Mode pause de l'acceleration
	 * */
	public void set_pause(boolean pause) {
		threadAcc.set_pause(pause);
	}
	
	/*
	 * @param void
	 * Activation de la deuxieme vitesse
	 * */
	public void lvl2_activation() {
		vitesseLvl2Activated = true;
	}
	
	/* getters and setters */
	
	public Model getModel() {
		return model;
	}
}

/*
 * Class ThreadStartAcceleration
 * Thread pour augmenter l'acceleration du joueur
 * */
class ThreadStartAcceleration extends Thread{
	
	/* Constants */
	
	public static final int STEP = 10; /* valeur de temps du run */
	
	/* Attributes */
	
	private Acceleration acceleration;
	private DataGame data;
	private boolean pause = false;
	private boolean isActivated;
	
	/* Constructors */
	
	/*
	 * @param Acceleration acceleration
	 * @param DataGame data
	 * */
	public ThreadStartAcceleration(Acceleration acceleration,DataGame data) {
		this.acceleration = acceleration;
		this.data = data;
		isActivated = false;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(10);
				/* Test pour verifier si le jeu n'est pas en pause */
				if(!pause) {
					/* test si le mode acceleration est activee */
					if(isActivated) {
						acceleration.increase_acceleration();
					}
					else {
						acceleration.decrease_acceleration();
						if(data.getFactorAcceleration() > 1.1) {
							data.setScorePlayer((int) (data.getScorePlayer() + (Road.MOVE_STEP_VALUE * data.getFactorAcceleration())));
						}
					}
				}
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
	
	/* Setters */
	
	public void setIsActivated(boolean activation) {
		isActivated = activation;
	}
	
	public void set_pause(boolean pause) {
		this.pause = pause;
	}
	
}

