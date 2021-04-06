package pcii_project.models;

import java.util.ArrayList;

import pcii_project.models.data.DataGame;

/*
 * Class Model
 * Point d'entree de toutes les donnees du modele
 * */
public class Model {
	/* Constants */
	
	public static final int HEIGHT_MIN = 0; /* Hauteur minimale */
	public static final int HEIGHT_MAX = 600; /* Hauteur maximale */
	public static final int WIDTH_MIN = 0; /* Largeur minimale */
	public static final int WIDTH_MAX = 1000; /* Largeur maximale */

	/* Emplacement des bornes pour eviter que la voiture sorte de modele */
	public static final int LOWER_BOUND_CARS = (int) (WIDTH_MAX * 0.15); /* Borne inferieur */
	public static final int UPPER_BOUND_CARS = (int) (WIDTH_MAX * 0.85); /* Borne superieur */
	
	/* Attributes */
	
	private DataGame data; /* donnees du jeu */
	private Road road; /* modele de la route */
	private Horizon horizon; /* modele de l'horizon */
	private Cars cars; /* modele de la voiture */
	private Game game; /* modele de la gestion de la partie */
	private Bird b1,b2; /* modele des oiseaux */
	
	private ArrayList<Tree> trees; /* fonctionnalite En developpement*/
	
	/* Constructors */
	public Model() {
		/* Generation de la partie */
		game = new Game(this);
		/* Generation des donnees du jeu */
		data = new DataGame();
		/* Generation de l'horizon */
		horizon = new Horizon();
		/* Generation de la voiture */
		cars = new Cars(data);
		/* Generation de la route */
		road = new Road(this,data,game,cars,trees);
		/* Generation des oiseaux */
		b1 = new Bird(560, 500, false,true);
		b2 = new Bird(520, 700, true,false);
		/* Mise en mouvement des oiseaux */
		b1.active();
		b2.active();
		/* Debut de la partie */
		game.newGame();
	}
	
	/* Functions */
	
	/*
	 * Mise en pause du modele
	 * */
	public void stop_progress() {
		road.getAcceleration().set_pause(true);
	}
	
	/*
	 * Relancement du modele
	 * */
	public void continue_progress() {
		road.getAcceleration().set_pause(false);
	}
	
	/*
	 * Reset des donnees du modele
	 * */
	public void reinitialize() {
		data.reinitialize();
		road.create_road();
		road.getCheckPoint().reset();
	}
	
	/*
	 * Fonctionnalitee en developpement
	 * */
	public void initializeTree() {
		trees = new ArrayList<Tree>();
		Tree t1 = new Tree(data, road, 800, HEIGHT_MAX);
		trees.add(t1);
	}
	
	/* En developpement */
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
	/* En developpement */
	public void addTree() {
		Tree t = new Tree(data, road, 800, HEIGHT_MAX);
		
		trees.add(t);
	}
	/* En developpement */
	public void decreaseAllTrees() {
		for(Tree tree : trees)
			tree.decreaseY();
	}
	
	/*
	 * @return int
	 *Le milieu de la largeur du modele
	 * */
	public static int getMiddleWidth() {
		return (WIDTH_MAX - WIDTH_MIN) / 2;
	}
	/*
	 * @return void
	 * Affiche un debbugage du modele
	 * */
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
	
	/* En developpement */
	public ArrayList<Tree> getTrees(){
		updateTrees();
		return trees;
	}
}


