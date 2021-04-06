package pcii_project.models;

/*
 * Class horizon
 * Modele de l'horizon
 * */
public class Horizon {

	/* Constants */
	
	public static final int HEIGHT_HORIZON = (int) ((Model.HEIGHT_MAX - Model.HEIGHT_MIN)* 4 / 5); /* Hauteur en ordonnees de l'horizon */
	
	/* functions */
	
	/*
	 * @param int height_max
	 * @return int
	 * Convertit la hauteur de l'horizon du modele dans d'autre dimension
	 * */
	public int get_horizon_with_dimension(int height_max) {
		double relative = HEIGHT_HORIZON / (double)(Model.HEIGHT_MAX - Model.HEIGHT_MIN);
		int height_horizon = (int) (height_max * relative);
		return height_horizon;
	}
}
