package pcii_project.models;

import pcii_project.models.data.DataGame;

public class Acceleration {

	
	/* Constantes */

	public static final double ACCELERATION = 0.1;
	
	/* Attributes */

	private double factor_acceleration;
	
	private DataGame data;
	/* Constructors */
	
	public Acceleration(DataGame data) {
		this.data = data;
		
		factor_acceleration = 1.0;
	}
	
	/* getters and setters */
	
	public double getFactorAcceleration() {
		return factor_acceleration *= ACCELERATION;
	}
	
	/* private functions */
	
	private void start_acceleration() {
		
	}
	
	
	private void end_acceleration() {
		
	}
	
}

class ThreadStartAcceleration extends Thread{
	
	private Acceleration acceleration;
	
	private boolean isActivated;
	
	public ThreadStartAcceleration(Acceleration acceleration) {
		this.acceleration = acceleration;
		isActivated = false;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(1);
				
				
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
	
	
}

