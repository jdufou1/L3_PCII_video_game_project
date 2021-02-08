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
	
	
	public static void main(String[] args) {
		Model model = new Model();
		System.out.println(model.cars.getPlacement_x());
	}
}
