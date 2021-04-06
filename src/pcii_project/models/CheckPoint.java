package pcii_project.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import pcii_project.models.data.DataGame;


/*
 * Class CheckPoint
 * Modele du checkpoint
 * */
public class CheckPoint {
	
	/* Constants */

	public static final int DISTANCE_BETWEEN_CHECKPOINTS = 4 * Model.HEIGHT_MAX; /* Distance minimale entre deux checkpoints */
	public static final int HEIGHT_CHECKPOINT = (int) ((double)(1  * (double)Model.HEIGHT_MAX) /12); /* Hauteur du checkpoint dans le repere */
	public static final int BONUS_TIME = 10; /* Temps bonus pour le joueur si le checkpoint est franchi */
	
	/* Attributes */
	
	private DataGame data; /* Donnees du joueur */
	private Game game; /* Modele de la partie*/
	private int y_checkpoint; /* Coordonnees y du checkpoint */
	private int side; /* Definition du cote du checkpoint sur la route : Gauche 0 et Droite 1*/
	private Random random; /* Generateur aleatoire */	
	private boolean complete = false; /* Test si le joueur est rentre en collision  avec le checkpoint */
	
	/* Constructors */
	
	/*
	 * @param DataGame data
	 * @param Game game
	 * */
	public CheckPoint(DataGame data,Game game) {
		this.data = data;
		this.game = game;
		side = 0;
		random = new Random();
	}
	
	/* Functions */
	
	/*
	 * @param void
	 * Met a jour le modele en fonction de la position du joueur et de letat du checkpoint
	 * */
	public void update() {
		if(data.getScorePlayer() > y_checkpoint) {
			if(complete) {
				System.out.println("[Bonus Temps] : +10s");
				game.setBonusTime(game.getBonusTime() + BONUS_TIME);
				data.setNbCheckpointsComplete(data.getNbCheckpointsComplete() + 1);
			}
			
			side = random.nextInt(2);
			y_checkpoint = data.getScorePlayer() + DISTANCE_BETWEEN_CHECKPOINTS;
			complete = false;
			System.out.println("[Nouveau Checkpoint] : " + y_checkpoint);
			System.out.println("[Nombre CheckPoint Franchis]  :"+data.getNbCheckpointsComplete());
		}
	}

	/*
	 * @param void
	 * Reinitialise la position du checkpoint 
	 * */
	public void reset() {
		y_checkpoint = data.getScorePlayer() + DISTANCE_BETWEEN_CHECKPOINTS;
	}
	
	/*
	 * @param ArrayList<Point> points
	 * @return boolean
	 * Test si le joueur est en colision avec le checkpoint
	 * */
	public boolean checkPointComplete(ArrayList<Point> points) {
		if(points == null) {
			return false;
		}
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		
		boolean est_dans_la_zone_x = false;
		boolean est_dans_la_zone_y = false;
		
		if(data.getScorePlayer() > y_checkpoint - HEIGHT_CHECKPOINT  && data.getScorePlayer() < y_checkpoint)
		{
			est_dans_la_zone_y = true;
		}
		if(data.getPositionPlayer() > p1.x && data.getPositionPlayer() < p2.x)
			est_dans_la_zone_x = true;
		
		if(est_dans_la_zone_x && est_dans_la_zone_y) {
			complete = true;
		}
		return est_dans_la_zone_x && est_dans_la_zone_y;
	}
	
	/*
	 * @param ArrayList<Point> leftSideRoad
	 * @param ArrayList<Point> middleSideRoad
	 * @param ArrayList<Point> rightSideRoad
	 * @param int score_player
	 * @param int max_height
	 * @param int max_width 
	 * @return null if checkpoint is more higher than graphical limits
	 * Retourne les coordonnees du checkpoint dans d'autre dimension
	 * */
	public ArrayList<Point> getCheckPointsCoords(ArrayList<Point> leftSideRoad, ArrayList<Point> middleSideRoad, ArrayList<Point> rightSideRoad,
												int score_player,int max_height,int max_width){
		
		int current_y_checkpoint = y_checkpoint - score_player;
		if(middleSideRoad.get(middleSideRoad.size() - 1).y < current_y_checkpoint) {
			return null;
		}
		
		ArrayList<Point> leftSide;
		ArrayList<Point> rightSide;
		
		/* On commence par choisir les deux liste de points qui nous servirons pour les calculs */
		if(side == 0) {
			leftSide = middleSideRoad;
			rightSide = rightSideRoad;
		}
		else {
			leftSide = leftSideRoad;
			rightSide = middleSideRoad;
		}
		
		/* on va choisir l'index des deux points tels que yP1 et yP2 encadre current_y*/
		int index1 = 0;
		int index2 = 1;
		for(int cpt = 0; cpt < middleSideRoad.size() ; cpt++) {
			if(middleSideRoad.get(cpt).y > current_y_checkpoint) {
				index1 = cpt - 1;
				index2 = cpt;
				break;
			}
		}		
		ArrayList<Point> result = new ArrayList<>();
		Point p1_left = leftSide.get(index1);
		Point p2_left = leftSide.get(index2);
		
		// Calcul du coefficient directeur
		double m =  (double) ((p2_left.y -  p1_left.y)) / (double)  (p2_left.x -  p1_left.x); 
		// Calcul de l'ordonnee a l'origine
		double p = (double) ( p1_left.y - (m *  p1_left.x));
		// Calcul de la coordonnee x sur la droite P1 - P2 : y = mx + p 
		int x_left  = (int) ((current_y_checkpoint - p) / m);
		
		
		
		Point p1_right = rightSide.get(index1);
		Point p2_right = rightSide.get(index2);
		
		// Calcul du coefficient directeur
		m =  (double) ((p2_right.y -  p1_right.y)) / (double)  (p2_right.x -  p1_right.x); 
		// Calcul de l'ordonnee a l'origine
		p = (double) ( p1_right.y - (m *  p1_right.x));
		// Calcul de la coordonnee x sur la droite P1 - P2 : y = mx + p 
		int x_right = (int) ((current_y_checkpoint - p) / m);
		
		
		double relative_x1 = (double) x_left / (double)(Model.WIDTH_MAX - Model.WIDTH_MIN);
		double relative_y1 = (double) current_y_checkpoint / (double)(Model.HEIGHT_MAX - Model.HEIGHT_MIN);
		int x1 = (int) (relative_x1 * max_width);
		int y1 = (int) (relative_y1 * max_height);
		
		double relative_x2 = (double) x_right / (double)(Model.WIDTH_MAX - Model.WIDTH_MIN);
		double relative_y2 = (double) current_y_checkpoint / (double)(Model.HEIGHT_MAX - Model.HEIGHT_MIN);
		int x2 = (int) (relative_x2 * max_width);
		int y2 = (int) (relative_y2 * max_height);
		
		
		result.add(new Point(x1,y1));
		result.add(new Point(x2,y2));
		
		return result;
	}
	
	/* 
	 * @param int max_height
	 * @return int
	 * Retourne la hauteur du checkpoint dans d'autre dimension
	 * */
	public int getHeighCheckPointWithPerspective(int max_height) {
		double y =  (double)HEIGHT_CHECKPOINT / (double) Model.HEIGHT_MAX;
		double yratecheckpoint = ((double)y_checkpoint - (double)data.getScorePlayer() )/ (double)Model.HEIGHT_MAX;
		return (int) ((y * max_height) * (1 - yratecheckpoint)) ;
	}
	
}
