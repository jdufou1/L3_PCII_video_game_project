package pcii_project.models;

import java.util.ArrayList;

import pcii_project.models.data.DataGame;

public class Model {

	
	/* The constants */
	
	/* display model */
	public static final int HEIGHT_MIN = 0;
	public static final int HEIGHT_MAX = 600;
	public static final int WIDTH_MIN = 0;
	public static final int WIDTH_MAX = 1000;
	
	
	
	/* bound of cars placement */
	public static final int LOWER_BOUND_CARS = (int) (WIDTH_MAX * 0.15);
	public static final int UPPER_BOUND_CARS = (int) (WIDTH_MAX * 0.85);
	
	/* The attributes */
	
	private DataGame data;
	private Road road;
	private Horizon horizon;
	private Cars cars;
	
	private Game game;
	
	private Bird b1,b2;
	
	private ArrayList<Tree> trees;
	
	/* Constructor */
	public Model() {
		game = new Game(this);
		data = new DataGame();
		horizon = new Horizon();
		cars = new Cars(data);
		
		//initializeTree();
		b1 = new Bird(560, 500, false,true);
		b1.active();
		
		b2 = new Bird(520, 700, true,false);
		b2.active();
		
		road = new Road(this,data,game,cars,trees);
		game.newGame();
	}
	
	/* functions */
	
	public void stop_progress() {
		road.getAcceleration().set_pause(true);
	}
	
	public void continue_progress() {
		road.getAcceleration().set_pause(false);
	}
	
	public void reinitialize() {
		data.reinitialize();
		road.create_road();
		road.getCheckPoint().reset();
	}
	
	public void initializeTree() {
		trees = new ArrayList<Tree>();
		Tree t1 = new Tree(data, road, 800, HEIGHT_MAX);
		//Tree t2 = new Tree(data, road, 200, HEIGHT_MAX);
		trees.add(t1);
		//trees.add(t2);
	}
	
	/* getters and setters */
	
	public Cars getCars() {
		return cars;
	}
	
	public Horizon getHorizon() {
		return horizon;
	}
	
	public Road getRoad() {
		return road;
	}
	
	public DataGame getData() {
		return data;
	}
	
	public Game getGame() {
		return game;
	}
	
	public ArrayList<Bird> getBirds(){
		ArrayList<Bird> birds = new ArrayList<Bird>();
		birds.add(b1);
		birds.add(b2);
		return birds;
	}
	
	public ArrayList<Tree> getTrees(){
		updateTrees();
		return trees;
	}
	
	public void updateTrees() {
		/*
		for(Tree tree : trees) {
			if(tree.getY() < 0) {
				trees.remove(tree);
				addTree();
			}
		}
		*/
	}
	
	public void addTree() {
		Tree t = new Tree(data, road, 800, HEIGHT_MAX);
		
		trees.add(t);
	}
	
	public void decreaseAllTrees() {
		for(Tree tree : trees)
			tree.decreaseY();
	}
	
	
	
	/* static */
	
	public static int getMiddleWidth() {
		return (WIDTH_MAX - WIDTH_MIN) / 2;
	}
	
	
	
	/* debbuging display */
	
	public void debbug_display() {
		System.out.println("PARAMETRE GENERAUX");
		System.out.println("hauteur minimale : " + HEIGHT_MIN);
		System.out.println("hauteur maximale : " + HEIGHT_MAX);
		System.out.println("largeur minimale : " + WIDTH_MIN);
		System.out.println("largeur maximale : " + WIDTH_MAX);
		
		System.out.println("Borne inferieur limite : " + LOWER_BOUND_CARS);
		System.out.println("Borne superieur limite : " + UPPER_BOUND_CARS);
		
		System.out.println("PARAMETRE VOITURE");
		System.out.println("position : " + data.getPositionPlayer());
		
		System.out.println("PARAMETRE PARCOURS");
		System.out.println("distance entre deux points hauteur : " + Road.HEIGHT_BETWEEN_TWO_POINTS);
		System.out.println("distance entre deux points largeur : " + Road.WIDTH_BETWEEN_TWO_POINTS);
		System.out.println("premier points : ");
		System.out.println(road.getRoad_points());
	}
	/*
	public static void main(String[] args) {
		Model model = new Model();
		model.debbug_display();
	}
	*/
}


