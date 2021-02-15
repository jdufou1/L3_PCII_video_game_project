package pcii_project.models;

public class Cars {
	
	/* The constants */
	
	public static final int HEIGHT_MAX_CARS = 80;
	public static final int WIDTH_MAX_CARS = 80;
	
	public static final int PLACEMENTY_CARS = 0;
	
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
	
	public int getRelativePlacementX(int max_width) {
		double relative_x = (double) placement_x / (double)(Model.WIDTH_MAX - Model.WIDTH_MIN);
		int x = (int) (relative_x * max_width);
		return x;
	}
	
	public int getRelativePlacementY(int max_height) {
		double relative_y = (double) (PLACEMENTY_CARS)/ (double)(Model.WIDTH_MAX - Model.WIDTH_MIN);
		int y = (int) (relative_y * max_height);
		return y;
	}
	
	public int getRelativeHeight(int max_height) {
		double relative_height = (double) (HEIGHT_MAX_CARS) / (double)(Model.HEIGHT_MAX - Model.HEIGHT_MIN);
		int height = (int) (relative_height * max_height);
		return height;
	}
	
	public int getRelativeWidth(int max_width) {
		double relative_height = (double) WIDTH_MAX_CARS / (double)(Model.WIDTH_MAX - Model.WIDTH_MIN);
		int width = (int) (relative_height * max_width);
		return width;
	}
	
	public static void main(String[] args) {
	
	}
}
