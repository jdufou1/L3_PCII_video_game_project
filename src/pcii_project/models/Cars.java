package pcii_project.models;

public class Cars {
	
	/* The constants */
	
	public static final int HEIGHT_MAX_CARS = 60;
	public static final int WIDTH_MAX_CARS = 80;
	
	public static final int STEP_VALUE = 2;
	
	/* The attributes */
	
	private int placement_x;
	
	
	/* Constructor */
	public Cars() {
		placement_x = (int) ((Model.WIDTH_MAX - Model.WIDTH_MIN) / 2) - (WIDTH_MAX_CARS / 2);
	}
	
	
	
	/* getters and setters */
	
	public int getPlacement_x() {
		return placement_x;
	}
	
	
	public void move_right() {
		placement_x += STEP_VALUE;
		if(placement_x > Model.UPPER_BOUND_CARS)
			placement_x = Model.UPPER_BOUND_CARS;
	}
	
	public void move_left() {
		placement_x -= STEP_VALUE;
		if(placement_x < Model.LOWER_BOUND_CARS)
			placement_x = Model.LOWER_BOUND_CARS;
	}
	
	public static void main(String[] args) {
	
	}
}
