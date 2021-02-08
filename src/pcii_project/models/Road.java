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
			int width_alea = random.nextInt(WIDTH_BETWEEN_TWO_POINTS * 2) - WIDTH_BETWEEN_TWO_POINTS;	
			road_points.add(new Point(Model.getMiddleWidth() + width_alea, height_tmp));
			height_tmp += HEIGHT_BETWEEN_TWO_POINTS;
		}	
	}
	
	public void update_road() {
		// Creation of the points
		int current_score = road_points.get(road_points.size() - 1).y ;

		boolean check = current_score < (score_y + Model.HEIGHT_MAX);
		while(check) {
			int width_alea = random.nextInt(WIDTH_BETWEEN_TWO_POINTS * 2) - WIDTH_BETWEEN_TWO_POINTS;	
			current_score += HEIGHT_BETWEEN_TWO_POINTS;
			road_points.add(new Point(width_alea , current_score));
			check = current_score < (score_y + Model.HEIGHT_MAX);
		}
		
		// Removing points
		int cpt = 0;
		check = true;
		while(cpt < road_points.size() && check) {
			if(road_points.get(cpt).y < score_y + Model.HEIGHT_MIN + HEIGHT_BETWEEN_TWO_POINTS) {
				road_points.remove(cpt);
			}
			else {
				check = false;
			}
			cpt++;
		}
				
	}
	
	
	/* getters and setters */
	
	public ArrayList<Point> getRoad_points() {
		return road_points;
	}
	
	public ArrayList<Point> getRoad_points_with_score(int score){
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : road_points) {
			res.add(new Point(point.x , point.y - score_y));			
		}
		return res;
	}
	
	
	/*
	 * Model points to graphics points
	 * */
	public ArrayList<Point> getRoad_points_with_dimension(int max_height, int max_width){
		ArrayList<Point> current_points = getRoad_points_with_score(score_y);
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : current_points) {
			double relative_x = point.x / (Model.WIDTH_MAX - Model.WIDTH_MIN);
			double relative_y = point.y / (Model.HEIGHT_MAX - Model.HEIGHT_MIN);
			
			int x = (int) relative_x * max_width;
			int y = (int) relative_y * max_height;
			
			res.add(new Point(x,y));
		}
		return res;
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
					road.update_road();
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
}