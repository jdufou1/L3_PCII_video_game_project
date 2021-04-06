package pcii_project.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import pcii_project.models.data.DataGame;

/*
 * Class Road
 * Modele de la route
 * */
public class Road {

	/* Constants */
	
	public static final int HEIGHT_BETWEEN_TWO_POINTS = (Horizon.HEIGHT_HORIZON) / 3; /* Hauteur entre deux points */
	public static final int WIDTH_BETWEEN_TWO_POINTS = (Model.WIDTH_MAX - Model.WIDTH_MIN) / 10; /* Largeur entre deux points */
	public static final int SPACE_BETWEEN_ROADBOUND = (int) (1.0 / 5.0 * (double) Model.WIDTH_MAX); /* Largeur de la route */
	public static final int MOVE_STEP_VALUE = 1; /* Valeur de progression du vehicule */
	
	/* Attributes */

	private ArrayList<Point> road_points; /* points de la route centrale */
	private ArrayList<Point> left_bound_road; /* points de la route a gauche */	
	private ArrayList<Point> right_bound_road; /* points de la route a droite */
	private Random random; /* generateur de nombre aleatoire */
	private MoveThread moveThread; /* thread de la route */
	private DataGame data; /* donnees du joueur */
	private Acceleration acceleration; /* modele de l'acceleration */
	private CheckPoint checkpoint; /* modele du checkpoint */
	private Game game; /* modele du jeu*/
	private Cars cars; /* modele de la voiture */
	private ArrayList<Tree> trees; /* en developpement*/
	
	/* Constructors */
	/*
	 * @param Model model
	 * @param DataGame data
	 * @param Game game
	 * @param Cars cars
	 * @param ArrayList<Tree> trees
	 * Initialisation de la route
	 * */
	public Road(Model model,DataGame data,Game game, Cars cars, ArrayList<Tree> trees) {
		this.data = data;
		this.game = game;
		this.cars = cars;
		this.trees = trees;
		this.checkpoint = new CheckPoint(data,game);
		this.acceleration = new Acceleration(data,model);
		this.moveThread = new MoveThread(this);
		this.random = new Random();	
		create_road();
		start_move();
		data.setPositionPlayer(0);
		
	}
	/*
	 * @return void
	 * Fait avancer le joueur sur la route
	 * */
	public void move() {
		data.setScorePlayer((int)(data.getScorePlayer() + (MOVE_STEP_VALUE * data.getFactorAcceleration())));
	}
	/*
	 * @return void
	 * Lance le thread pour actualiser les elements de la route
	 * */
	public void start_move() {
		new Thread(moveThread).start();
	}
	/*
	 * @return void
	 * @param boolean move
	 * Active les mise a jour de la route dans le thread
	 * */
	public void change_value_threadMove(boolean move) {
		moveThread.setMove(move);
	}
	
	
	/*
	 * @return void
	 * Creation de la route
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
	
	/*
	 * @param void
	 * Mise a jour de la route
	 * */
	public void update_road() {
		// Creation of the points
		int current_score = road_points.get(road_points.size() - 1).y ;

		/* ROAD */
		boolean check = current_score < (data.getScorePlayer() + Horizon.HEIGHT_HORIZON +  HEIGHT_BETWEEN_TWO_POINTS);
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
			if(road_points.get(cpt).y < data.getScorePlayer() - Model.HEIGHT_MIN - (HEIGHT_BETWEEN_TWO_POINTS) ) {
				road_points.remove(cpt);
				left_bound_road.remove(cpt);
				right_bound_road.remove(cpt);
			}
			cpt++;
		}
	}
	
	/*
	 * @param int x_cars
	 * @return boolean
	 * Test si la position x_cars est situee a droite ou a gauche de la route
	 * */
	public boolean is_offside(int x_cars) {
		return is_offside_left(x_cars) || is_offside_right(x_cars);	
	}
	
	/*
	 * @param int x_cars
	 * @return boolean
	 * Test si la position x_cars est situee a droite de la route droite
	 * */
	public boolean is_offside_right(int x_cars) {
		ArrayList<Point> points = right_bound_road;
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		
		double Yp1 =  p1.y - data.getScorePlayer();
		double rate1 = Yp1 / (double) Horizon.HEIGHT_HORIZON /*+ (2 * HEIGHT_BETWEEN_TWO_POINTS))*/;
		int p1x = p1.x - (int)((rate1) * 1.5 * WIDTH_BETWEEN_TWO_POINTS);
		
		
		
		double Yp2 =  p2.y - data.getScorePlayer();
		double rate2 = Yp2 / (double) Horizon.HEIGHT_HORIZON /*+ (2 * HEIGHT_BETWEEN_TWO_POINTS))*/;
		int p2x = p2.x - (int)((rate2) * 1.5 * WIDTH_BETWEEN_TWO_POINTS);
		
		// Calcul du coefficient directeur
		double m =  (double) ((p2.y - p1.y)) / (double)  (p2x - p1x); 
		// Calcul de l'ordonnee a l'origine
		double p = (double) (p1.y - (m * p1x));
		// Calcul de la coordonnee x sur la droite P1 - P2 : y = mx + p 
		
		int x = (int) (((data.getScorePlayer() ) - p) / m);

		if(x_cars + Cars.WIDTH_MAX_CARS > x) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/*
	 * @param int x_cars
	 * @return boolean
	 * Test si la position x_cars est situee a gauche de la route gauche
	 * */
	public boolean is_offside_left(int x_cars) {
		ArrayList<Point> points =  left_bound_road;
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		
		double Yp1 =  p1.y - data.getScorePlayer();
		double rate1 = Yp1 / (double) Horizon.HEIGHT_HORIZON /*+ (2 * HEIGHT_BETWEEN_TWO_POINTS))*/;
		int p1x = p1.x + (int)((rate1) * 1.5 * WIDTH_BETWEEN_TWO_POINTS);
		
		
		
		double Yp2 =  p2.y - data.getScorePlayer();
		double rate2 = Yp2 / (double) Horizon.HEIGHT_HORIZON /*+ (2 * HEIGHT_BETWEEN_TWO_POINTS))*/;
		int p2x = p2.x + (int)((rate2) * 1.5 * WIDTH_BETWEEN_TWO_POINTS);
		
		
		// Calcul du coefficient directeur
		double m =  (double) ((p2.y - p1.y)) / (double)  (p2x - p1x);
		// Calcul de l'ordonnee a l'origine
		double p = (double) (p1.y - (m * p1x));
		// Calcul de la coordonnee x sur la droite P1 - P2 : y = mx + p 
		int x = (int) (((data.getScorePlayer()) - p) / m);
		if(x_cars + Cars.WIDTH_MAX_CARS < x) {
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
	
	/*
	 * @param int score
	 * @return ArrayList<Point>
	 * Retourne les points de la route centrale avec les coordonnees du repere du modele en fonction d'un score
	 * */
	public ArrayList<Point> getRoad_points_with_score(int score){
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : road_points) {
			res.add(new Point(point.x , point.y - data.getScorePlayer()));			
		}
		return res;
	}
	
	/*
	 * @param int score
	 * @return ArrayList<Point>
	 * Retourne les points de la route de gauche avec les coordonnees du repere du modele en fonction d'un score
	 * */
	public ArrayList<Point> getLeftRoad_points_with_score(int score){
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : left_bound_road) {
			/* POUR DONNER UN EFFET DE PERSPECTIVE */
			double Yp =  point.y - data.getScorePlayer();
			double rate = Yp / (double) Horizon.HEIGHT_HORIZON /*+ (2 * HEIGHT_BETWEEN_TWO_POINTS))*/;
			/* */ 
			res.add(new Point(point.x  + (int)((rate) * 1.5 * WIDTH_BETWEEN_TWO_POINTS), point.y - data.getScorePlayer()));			
		}
		return res;
	}
	
	/*
	 * @param int score
	 * @return ArrayList<Point>
	 * Retourne les points de la route de droite avec les coordonnees du repere du modele en fonction d'un score
	 * */
	public ArrayList<Point> getRightRoad_points_with_score(int score){
		ArrayList<Point> res = new ArrayList<Point>();
		for(Point point : right_bound_road) {
			/* POUR DONNER UN EFFET DE PERSPECTIVE */
			double Yp =  point.y - data.getScorePlayer();
			double rate = Yp / (double) Horizon.HEIGHT_HORIZON /*+ (2 * HEIGHT_BETWEEN_TWO_POINTS))*/;
			/* */
			res.add(new Point(point.x - (int)((rate) * 1.5 * WIDTH_BETWEEN_TWO_POINTS), point.y - data.getScorePlayer()));			
		}
		return res;
	}
	
	/*
	 * @param int max_height
	 * @param int max_width
	 * @return ArrayList<Point>
	 * Retourne les points de la route centrale en fonction de nouvelles dimensions
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
	
	/*
	 * @param int max_height
	 * @param int max_width
	 * @return ArrayList<Point>
	 * Retourne les points de la route de gauche en fonction de nouvelles dimensions
	 * */
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
	
	/*
	 * @param int max_height
	 * @param int max_width
	 * @return ArrayList<Point>
	 * Retourne les points de la route de droite en fonction de nouvelles dimensions
	 * */
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

	
	/*
	 * @param int max_width
	 * @param int y
	 * @param int width_object
	 * @return int
	 * Retourne la largeur d'un objet en fonction de sa distance pour donner un effet de perspective
	 * */
	public int getWidthPerspective(int max_width,int y,int width_object) {
		double y_relative = (double) y / (double) max_width;
		/* POUR DONNER UN EFFET DE PERSPECTIVE */
		return (int) (width_object * y_relative);
	}
	
	public DataGame getData() {
		return data;
	}
	
	public Acceleration getAcceleration() {
		return acceleration;
	}
	
	public CheckPoint getCheckPoint() {
		return checkpoint;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Cars getCars() {
		return cars;
	}

	public ArrayList<Tree> getTrees(){
		return trees;
	}
	
	
}


/*
 * Class MoveThread
 * Permet la mise à jour de la route et de ses composants
 * */
class MoveThread extends Thread{
	
	/* Constants */
	
	public static final int STEP = 1; /* Temps d'attente pour le run */
	
	/* Attributes */
	
	private Road road; /* modele de la route */
	private boolean move; /* permet l'activation de la mise a jour */
	
	/* Constructors */
	
	/*
	 * @Param Road road
	 * */
	public MoveThread(Road road) {
		this.road = road;
		move = true; /* Activation de la mise a jour a l'initialisation */
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(STEP);
				if(move) {
					road.update_road(); /* mise a jour des points de la route */
					road.getCheckPoint().update(); /* mise a jour des checkpoints */
					if(road.is_offside(road.getData().getPositionPlayer())) { /* test si le joueur est hors-route */
						road.getAcceleration().decrease_slowly_acceleration(); /* le joueur decelere*/
						road.getCars().active_slow(); /* activation du mode lent du vehicule */
					}
					else {
						road.getCars().disabled_slow(); /* desactivation de mode lent du vehicule */
					}
				}
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
	
	/* setters */
	
	public void setMove(boolean move) {
		this.move = move;
	}
}