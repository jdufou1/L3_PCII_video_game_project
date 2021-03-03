package pcii_project.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import pcii_project.models.data.DataGame;

public class Road {

	/* The constants */
	
	public static final int HEIGHT_BETWEEN_TWO_POINTS = (Horizon.HEIGHT_HORIZON) / 3;
	
	public static final int WIDTH_BETWEEN_TWO_POINTS = (Model.WIDTH_MAX - Model.WIDTH_MIN) / 10;
	
	public static final int SPACE_BETWEEN_ROADBOUND = (int) (1.0 / 4.0 * (double) Model.WIDTH_MAX);
	/* The attributes */
	
	public static final int MOVE_STEP_VALUE = 1;
	
	private ArrayList<Point> road_points;
	
	private ArrayList<Point> left_bound_road;
	
	private ArrayList<Point> right_bound_road;
	
	//private Acceleration acceleration;
	
	private Random random;
	
	private MoveThread moveThread;
	
	private DataGame data;
	
	/* Constructor */
	public Road(DataGame data) {
		this.data = data;
		data.setPositionPlayer(0);
		
		random = new Random();
		///acceleration = new Acceleration();		
		create_road();
		moveThread = new MoveThread(this);
		start_move();
	}
	
	
	
	public void move() {
		data.setScorePlayer(data.getScorePlayer() + MOVE_STEP_VALUE);
	}
	
	public void start_move() {
		new Thread(moveThread).start();
	}
	
	public void change_value_threadMove(boolean move) {
		moveThread.setMove(move);
	}
	
	/* CRUD Road */
	
	/*
	 * First Update of the Road list
	 * */
	public void create_road() {
		road_points = new ArrayList<Point>(); // first creation of road_points
		left_bound_road = new ArrayList<Point>(); // first creation of left_bound_road
		right_bound_road = new ArrayList<Point>(); // first creation of right_bound_road
		
		Point start_point = new Point((int)((Model.WIDTH_MAX - Model.WIDTH_MIN) / 2),Model.HEIGHT_MIN); // first point
		road_points.add(start_point); // CENTRAL ROAD
		
		Point left_start_point = new Point((int)((Model.WIDTH_MAX - Model.WIDTH_MIN) / 2) - SPACE_BETWEEN_ROADBOUND,Model.HEIGHT_MIN); // first point
		left_bound_road.add(left_start_point); // LEFT ROAD
		
		Point right_start_point = new Point((int)((Model.WIDTH_MAX - Model.WIDTH_MIN) / 2) + SPACE_BETWEEN_ROADBOUND,Model.HEIGHT_MIN); // first point
		right_bound_road.add(right_start_point); // RIGHT ROAD
		
		
		int height_tmp = data.getScorePlayer() + HEIGHT_BETWEEN_TWO_POINTS; // placement of the other points without changing score_y 
		
		// boucle to add point up to Horizon
		while(height_tmp  < Horizon.HEIGHT_HORIZON + HEIGHT_BETWEEN_TWO_POINTS) {
			int width_alea = random.nextInt(WIDTH_BETWEEN_TWO_POINTS * 2) - WIDTH_BETWEEN_TWO_POINTS;	
			road_points.add(new Point(Model.getMiddleWidth() + width_alea, height_tmp)); // CENTRAL ROAD
			left_bound_road.add(new Point(Model.getMiddleWidth() + width_alea - SPACE_BETWEEN_ROADBOUND, height_tmp)); // LEFT ROAD
			right_bound_road.add(new Point(Model.getMiddleWidth() + width_alea + SPACE_BETWEEN_ROADBOUND, height_tmp)); // RIGHT ROAD
			height_tmp += HEIGHT_BETWEEN_TWO_POINTS;
		}
		
		
		
		
	}
	
	public void update_road() {
		// Creation of the points
		int current_score = road_points.get(road_points.size() - 1).y ;

		/* ROAD */
		boolean check = current_score < (data.getScorePlayer() + Horizon.HEIGHT_HORIZON + HEIGHT_BETWEEN_TWO_POINTS);
		while(check) {
			//System.out.println("passage : current_score : " + current_score + " score_y : "+ score_y + "modelHeight : " + (Horizon.HEIGHT_HORIZON ));
			int width_alea = random.nextInt(WIDTH_BETWEEN_TWO_POINTS * 2) - WIDTH_BETWEEN_TWO_POINTS;	
			current_score += HEIGHT_BETWEEN_TWO_POINTS;
			// CENTRAL ROAD
			road_points.add(new Point(Model.getMiddleWidth() + width_alea , current_score));
			
			// LEFT ROAD 
			left_bound_road.add(new Point(Model.getMiddleWidth() + width_alea - SPACE_BETWEEN_ROADBOUND, current_score));
			
			//RIGHT ROAD
			right_bound_road.add(new Point(Model.getMiddleWidth() + width_alea + SPACE_BETWEEN_ROADBOUND, current_score));
			
			check = current_score < (data.getScorePlayer() + Model.HEIGHT_MAX);
		}
		
		// Removing points
		int cpt = 0;
		while(cpt < road_points.size()) {
			if(road_points.get(cpt).y < data.getScorePlayer() - Model.HEIGHT_MIN - HEIGHT_BETWEEN_TWO_POINTS ) {
				road_points.remove(cpt);
				left_bound_road.remove(cpt);
				right_bound_road.remove(cpt);
			}
			cpt++;
		}
				
	}
	
	public boolean is_offside(int x_cars) {
		return is_offside_left(x_cars) || is_offside_right(x_cars);	
	}
	
	public boolean is_offside_right(int x_cars) {
		ArrayList<Point> points = getRightRoad_points();
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		// Calcul du coefficient directeur
		double m =  (double) ((p2.y - p1.y)) / (double)  (p2.x - p1.x); 
		// Calcul de l'ordonnee a l'origine
		double p = (double) (p1.y - (m * p1.x));
		// Calcul de la coordonnee x sur la droite P1 - P2 : y = mx + p 
		int x = (int) (((data.getScorePlayer()) - p) / m);
		
		if(x_cars + Cars.WIDTH_MAX_CARS > x) {
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean is_offside_left(int x_cars) {
		ArrayList<Point> points = getLeftRoad_points();
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		// Calcul du coefficient directeur
		double m =  (double) ((p2.y - p1.y)) / (double)  (p2.x - p1.x);
		// Calcul de l'ordonnee a l'origine
		double p = (double) (p1.y - (m * p1.x));
		// Calcul de la coordonnee x sur la droite P1 - P2 : y = mx + p 
		int x = (int) (((data.getScorePlayer()) - p) / m);
		//System.out.println("x : "+x+"p2 : "+p2.x);
		if(x_cars - Cars.WIDTH_MAX_CARS < x) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/* getters and setters */
	
	public ArrayList<Point> getRoad_points() {
		return road_points;
	}
	
	public ArrayList<Point> getLeftRoad_points() {
		return left_bound_road;
	}
	
	public ArrayList<Point> getRightRoad_points() {
		return right_bound_road;
	}
	
	public ArrayList<Point> getRoad_points_with_score(int score){
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : road_points) {
			res.add(new Point(point.x , point.y - data.getScorePlayer()));			
		}
		return res;
	}
	
	public ArrayList<Point> getLeftRoad_points_with_score(int score){
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : left_bound_road) {
			res.add(new Point(point.x , point.y - data.getScorePlayer()));			
		}
		return res;
	}
	
	public ArrayList<Point> getRightRoad_points_with_score(int score){
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : right_bound_road) {
			res.add(new Point(point.x , point.y - data.getScorePlayer()));			
		}
		return res;
	}
	
	/*
	 * Model points to graphics points
	 * */
	public ArrayList<Point> getRoad_points_with_dimension(int max_height, int max_width){
		ArrayList<Point> current_points = getRoad_points_with_score(data.getScorePlayer());
		ArrayList<Point> res = new ArrayList<Point>();

		for(Point point : current_points) {
			double relative_x = (double) point.x / (double)(Model.WIDTH_MAX - Model.WIDTH_MIN);
			double relative_y = (double) point.y / (double)(Model.HEIGHT_MAX - Model.HEIGHT_MIN);
			int x = (int) (relative_x * max_width);
			int y = (int) (relative_y * max_height);
			res.add(new Point(x,y));
			
		}
		return res;
	}
	
	public ArrayList<Point> getLeftRoad_points_with_dimension(int max_height, int max_width){
		ArrayList<Point> current_points = getLeftRoad_points_with_score(data.getScorePlayer());
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : current_points) {
			double relative_x = (double) point.x / (double)(Model.WIDTH_MAX - Model.WIDTH_MIN);
			double relative_y = (double) point.y / (double)(Model.HEIGHT_MAX - Model.HEIGHT_MIN);
			int x = (int) (relative_x * max_width);
			int y = (int) (relative_y * max_height);
			res.add(new Point(x,y));
		}
		return res;
	}
	
	public ArrayList<Point> getRightRoad_points_with_dimension(int max_height, int max_width){
		ArrayList<Point> current_points = getRightRoad_points_with_score(data.getScorePlayer());
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : current_points) {
			double relative_x = (double) point.x / (double)(Model.WIDTH_MAX - Model.WIDTH_MIN);
			double relative_y = (double) point.y / (double)(Model.HEIGHT_MAX - Model.HEIGHT_MIN);
			int x = (int) (relative_x * max_width);
			int y = (int) (relative_y * max_height);
			res.add(new Point(x,y));
		}
		return res;
	}

	public DataGame getData() {
		return data;
	}

	
}

class MoveThread extends Thread{
	/* Attributs */
	private Road road;
	
	private boolean move;
	
	/* Constructeurs */
	public MoveThread(Road road) {
		this.road = road;
		move = true;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(1);
				if(move) {
					//road.move();
					road.update_road();
					if(road.is_offside(road.getData().getPositionPlayer())) {
						//System.out.println("hors-route");
					}
				}
					
					
				/* DEBUG */
				//System.out.println(road.getRightRoad_points());
				//System.out.println("centre : " + road.getRoad_points().size() + "right : " + road.getRightRoad_points().size());
				
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
	
	/* getters and setters */
	
	public void setMove(boolean move) {
		this.move = move;
	}
}