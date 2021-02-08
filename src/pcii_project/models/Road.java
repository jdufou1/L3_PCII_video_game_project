package pcii_project.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Road {

	/* The constants */
	
	public static final int HEIGHT_BETWEEN_TWO_POINTS = (Horizon.HEIGHT_HORIZON) / 5;
	
	public static final int WIDTH_BETWEEN_TWO_POINTS = (Model.WIDTH_MAX - Model.WIDTH_MIN) / 10;
	/* The attributes */
	
	public static final int MOVE_STEP_VALUE = 1;
	
	private ArrayList<Point> road_points;
	
	private int score_y;
	
	private Random random;
	
	/* Constructor */
	public Road() {
		score_y = 0;
		random = new Random();
		create_road();
		
		(new Thread(new MoveThread(this))).start();
	}
	
	
	
	public void move() {
		score_y += MOVE_STEP_VALUE;
	}
	
	/* CRUD Road */
	
	/*
	 * First Update of the Road list
	 * */
	public void create_road() {
		road_points = new ArrayList<Point>(); // first creation of road_points
		
		Point start_point = new Point((int)((Model.WIDTH_MAX - Model.WIDTH_MIN) / 2),Model.HEIGHT_MIN); // first point
		road_points.add(start_point);
		
		int height_tmp = score_y + HEIGHT_BETWEEN_TWO_POINTS; // placement of the other points without changing score_y 
		
		// boucle to add point up to Horizon
		while(height_tmp  < Horizon.HEIGHT_HORIZON + HEIGHT_BETWEEN_TWO_POINTS) {
			int with_alea = random.nextInt(WIDTH_BETWEEN_TWO_POINTS * 2) - WIDTH_BETWEEN_TWO_POINTS;	
			road_points.add(new Point(Model.getMiddleWidth() + with_alea, height_tmp));
			height_tmp += HEIGHT_BETWEEN_TWO_POINTS;
		}	
	}
	
	
	/* getters and setters */
	
	public ArrayList<Point> getRoad_points() {
		return road_points;
	}
	

}

class MoveThread extends Thread{
	/* Attributs */
	private Road road;
	
	/* Constructeurs */
	public MoveThread(Road road) {
		this.road = road;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
					Thread.sleep(10);
					road.move();
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
}