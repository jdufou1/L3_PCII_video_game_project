package pcii_project.models;

import java.util.Random;

import pcii_project.models.data.DataGame;

public class CheckPoint {
	
	/* constantes */

	public static final int DISTANCE_BETWEEN_CHECKPOINTS = 10 * Model.HEIGHT_MAX;
	
	/* attributs */
	
	private DataGame data;
	
	private int y_checkpoint;
	
	private int side; // right : 0 / left : 1
	
	private Random random;
	
	public CheckPoint(DataGame data) {
		this.data = data;
		side = 0;
		random = new Random();
	}
	
	public void update() {
		if(data.getScorePlayer() > y_checkpoint) {
			side = random.nextInt(2);
			y_checkpoint = data.getScorePlayer() + DISTANCE_BETWEEN_CHECKPOINTS;
			System.out.println("Nouveau Checkpoint : " + y_checkpoint);
		}
	}
	
	public void reset() {
		y_checkpoint = data.getScorePlayer() + DISTANCE_BETWEEN_CHECKPOINTS;
	}
}
