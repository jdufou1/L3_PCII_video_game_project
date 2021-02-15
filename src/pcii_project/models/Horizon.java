package pcii_project.models;

public class Horizon {

	/* The constants */
	
	public static final int HEIGHT_HORIZON = (int) ((Model.HEIGHT_MAX - Model.HEIGHT_MIN)* 2 / 3);
	
	/* The attributes */
	
	public int get_horizon_with_dimension(int height_max) {
		double relative = HEIGHT_HORIZON / (double)(Model.HEIGHT_MAX - Model.HEIGHT_MIN);
		int height_horizon = (int) (height_max * relative);
		return height_horizon;
	}
	
	
	/* Constructor */
	public Horizon() {
		
	}
	
	/* Methodes */
	
	
	/* getters and setters */
	
	
}
