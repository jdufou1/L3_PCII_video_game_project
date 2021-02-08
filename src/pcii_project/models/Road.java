package pcii_project.models;

import java.awt.Point;
import java.util.ArrayList;

public class Road {

	/* The constants */
	
	
	/* The attributes */
	
	private ArrayList<Point> road_points;
	
	private int score_y;
	
	/* Constructor */
	public Road() {
		score_y = 0;
	}
	
	
	/* getters and setters */
	
	public ArrayList<Point> getRoad_points() {
		return road_points;
	}
}
