package pcii_project.models;

import pcii_project.models.data.DataGame;

public class Cars {
	
	/* The constants */
	
	public static final int HEIGHT_MAX_CARS = 80;
	public static final int WIDTH_MAX_CARS = 75;
	
	public static final int PLACEMENTY_CARS = 0;
	
	public static final int STEP_VALUE = 2;
	
	/* The attributes */
	
	private DataGame data;
	
	private boolean right = false;
	
	private boolean left = false;
	
	private boolean slow_active = false;
	
	/* Constructor */
	public Cars(DataGame data) {
		this.data = data;
		data.setPositionPlayer( (int) ((Model.WIDTH_MAX - Model.WIDTH_MIN) / 2) - (WIDTH_MAX_CARS / 2));
	}
	
	/* getters and setters */


	public void move_right() {
		data.setPositionPlayer(data.getPositionPlayer() + STEP_VALUE);
		if(data.getPositionPlayer() > Model.UPPER_BOUND_CARS)
			data.setPositionPlayer(Model.UPPER_BOUND_CARS);
	}
	
	public void move_left() {
		data.setPositionPlayer(data.getPositionPlayer() - STEP_VALUE);
		if(data.getPositionPlayer() < Model.LOWER_BOUND_CARS - WIDTH_MAX_CARS )
			data.setPositionPlayer(Model.LOWER_BOUND_CARS - WIDTH_MAX_CARS);
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

	public void activeRight() {
		right = true;
	}
	
	public void activeLeft() {
		left = true;
	}
	
	public void active_slow() {
		slow_active = true;
	}
	
	public void disabledSide() {
		left = false;
		right = false;
	}
	
	public void disabled_slow() {
		slow_active = false;
	}
	
	public boolean getRight() {
		return right;
	}
	
	public boolean getLeft() {
		return left;
	}
	
	public boolean is_slowly() {
		return slow_active;
	}
}
