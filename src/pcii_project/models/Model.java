package pcii_project.models;

public class Model {

	
	/* The constants */
	
	/* display model */
	public static final int HEIGHT_MIN = 0;
	public static final int HEIGHT_MAX = 600;
	public static final int WIDTH_MIN = 0;
	public static final int WIDTH_MAX = 800;
	
	
	
	/* bound of cars placement */
	public static final int LOWER_BOUND_CARS = (int) (WIDTH_MAX * 0.1);
	public static final int UPPER_BOUND_CARS = (int) (WIDTH_MAX * 0.9);
	
	/* The attributes */
	
	private Road road;
	private Horizon horizon;
	private Cars cars;
	
	/* Constructor */
	public Model() {
		road = new Road();
		horizon = new Horizon();
		cars = new Cars();
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
		System.out.println("position : " + cars.getPlacement_x());
		
		System.out.println("PARAMETRE PARCOURS");
		System.out.println("distance entre deux points hauteur : " + Road.HEIGHT_BETWEEN_TWO_POINTS);
		System.out.println("distance entre deux points largeur : " + Road.WIDTH_BETWEEN_TWO_POINTS);
		System.out.println("premier points : ");
		System.out.println(road.getRoad_points());
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		Model model = new Model();
		model.debbug_display();
	}
}
