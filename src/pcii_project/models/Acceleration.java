package pcii_project.models;

import pcii_project.models.data.DataGame;

public class Acceleration {

	
	/* Constantes */

	public static final double ACCELERATION = 0.01;
	
	public static final int VITESSE_MAX_LVL1 = 20;
	
	public static final int VITESSE_MAX_LVL2 = 40;
	
	/* Attributes */
	
	private boolean vitesseLvl1Activated = true;
	
	private boolean vitesseLvl2Activated = false;
	
	private ThreadStartAcceleration threadAcc;
	
	private DataGame data;
	/* Constructors */
	
	public Acceleration(DataGame data) {
		this.data = data;
		threadAcc = new ThreadStartAcceleration(this,data);
		new Thread(threadAcc).start();
				
	}
	
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
	
	
	public void decrease_acceleration() {
		
		data.setFactorAcceleration( data.getFactorAcceleration() * (1-ACCELERATION));
		if(data.getFactorAcceleration() < 1 ) 
			data.setFactorAcceleration(1.0);
	}
	
	
	
	public void start_acceleration() {
		threadAcc.setIsActivated(true);
	}
	
	public void end_acceleration() {
		threadAcc.setIsActivated(false);
	}
	
	public void set_pause(boolean pause) {
		threadAcc.set_pause(pause);
	}
	
	/* VITESSE */
	
	public void lvl2_activation() {
		vitesseLvl2Activated = true;
	}
	
	
	/* getters and setters */
	
	/* private functions */
	
	
	
}

class ThreadStartAcceleration extends Thread{
	
	private Acceleration acceleration;
	
	private DataGame data;
	
	private boolean pause = false;
	
	private boolean isActivated;
	
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
				
				if(!pause) {
					if(isActivated) {
						acceleration.increase_acceleration();
					}
					else {
						acceleration.decrease_acceleration();
						if(data.getFactorAcceleration() > 1.1) {
							System.out.println("passage");
							System.out.println(data.get_Kilometer_per_hours() + " km/h");
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
	
	public void setIsActivated(boolean activation) {
		isActivated = activation;
	}
	
	public void set_pause(boolean pause) {
		this.pause = pause;
	}
	
}

