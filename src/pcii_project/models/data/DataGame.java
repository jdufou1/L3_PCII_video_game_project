package pcii_project.models.data;

import pcii_project.models.*;

public class DataGame {

	/* Constantes */
	
	/* Attributs */
	
	private int score_player;
	
	private int position_player;
	
	private double factor_acceleration;
	
	private int nb_checkpoints_complete;
	
	/* Constructors */
	
	public DataGame() {
		factor_acceleration = 1.0;
		score_player = 0;
		nb_checkpoints_complete = 0;
		position_player = (int) ((Model.WIDTH_MAX - Model.WIDTH_MIN) / 2) - (Cars.WIDTH_MAX_CARS / 2);
	}
	
	/* functions */
	
	public void reinitialize() {
		factor_acceleration = 1.0;
		score_player = 0;
		nb_checkpoints_complete = 0;
		position_player = (int) ((Model.WIDTH_MAX - Model.WIDTH_MIN) / 2) - (Cars.WIDTH_MAX_CARS / 2);
	}
	
	/* getters and setters */
	
	public int getScorePlayer() {
		return score_player;
	}
	
	public int getPositionPlayer() {
		return position_player;
	}
	
	public double getFactorAcceleration() {
		return factor_acceleration;
	}
	
	public int getNbCheckpointsComplete() {
		return nb_checkpoints_complete;
	}
	
	public void setScorePlayer(int score_player) {
		this.score_player = score_player;
	}
	
	public void setPositionPlayer(int position_player)
	{
		this.position_player = position_player;
	}
	
	public void setFactorAcceleration(double factor_acceleration) {
		this.factor_acceleration = factor_acceleration;
	}
	
	public void setNbCheckpointsComplete(int nb_checkpoints_complete) {
		this.nb_checkpoints_complete =  nb_checkpoints_complete;
	}
	
	/* AFFICHAGE */
	public int get_Kilometer_per_hours() {
			return (int) factor_acceleration;
	}
	
	public int getRelativePositionPlayer(int max_width) {
		double relative_x = (double) getPositionPlayer() / (double)(Model.WIDTH_MAX - Model.WIDTH_MIN);
		int x = (int) (relative_x * max_width);
		return x;
	}
}
