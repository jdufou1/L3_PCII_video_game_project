package pcii_project.models;


/*
 * Class Bird
 * Modele de l'oiseau
 * */
public class Bird {

	/* Constants */
	
	public static final int NB_STEP = 1;
	public static final int WIDTH = (int)(Model.WIDTH_MAX / 15);
	public static final int HEIGHT = (int)(Model.HEIGHT_MAX / 15);
	
	/* Attributes */
	
	private int y; /* position y de l'oiseau */ 
	private int x_middle; /* position x de depart de l'oiseau */
	private boolean action; /* Mode 0 en aile vers le haut / 1 aile vers le bas */
	private ThreadBird threadBird; /* Thread qui permet de faire changer le mode de l'oiseau */
	public boolean direction; /* Mode qui donne la direction de l'oiseau */
	
	/* Constructors */
	
	/*
	 * @param int y
	 * @param int x_middle
	 * @param boolean direction
	 * @boolean action
	 * */
	public Bird(int y,int x_middle,boolean direction,boolean action) {
		this.y = y;
		this.x_middle = x_middle;
		this.direction = direction;
		this.action = action;
		threadBird = new ThreadBird(this);
	}
	
	/*
	 * @param void
	 * Activation du Thread de l'oiseau
	 * */
	public void active() {
		threadBird.start();
	}
	
	/* getters et setters */
	
	public boolean getDirection() {
		return direction;
	}
	
	public void changeDirection() {
		direction =! direction;
	}
	
	public int getX() {
		return x_middle;
	}
	
	public void setX(int x) {
		x_middle = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getXWithDimension(int max_width) {
		double relative_x = (double)((double)getX() / (double)Model.WIDTH_MAX);
		return (int) (relative_x * max_width);
	}
	
	public int getYWithDimension(int max_height) {
		double relative_y = (double)((double)getY() / (double)Model.HEIGHT_MAX);
		return (int) (relative_y * max_height);
	}
	
	public int getWidthWithDimension(int max_width) {
		double relative_value = (double)((double)Bird.WIDTH / (double)Model.WIDTH_MAX);
		return (int) (relative_value * max_width);
	}
	
	public int getHeightWithDimension(int max_height) {
		double relative_value = (double)((double)Bird.HEIGHT / (double)Model.HEIGHT_MAX);
		return (int) (relative_value * max_height);
	}
	
	public boolean getAction() {
		return action;
	}
	
	public void  changeAction() {
		action =! action;
	}
	
}


/*
 * Class ThreadBird
 * Mise a jour de l'oiseau
 * */
class ThreadBird extends Thread{
	
	/* Constants */
	
	public static final double TIME_ACTION = 0.1; /* Temps pour changer l'etat de l'oiseau */
	public static final double STEP = 20; /* Temps pour le run */
	
	/* Attributes */
	
	private Bird bird; /* Modele de l'oiseau */
	private Chrono chrono; /* Chrono */
	
	/* Constructors */
	
	/*
	 * @param Bird bird
	 * */
	public ThreadBird(Bird bird) {
		this.bird = bird;
		chrono = new Chrono();
	}
	
	@Override
	public void run() {
		try {
			chrono.start(); /* demarrage du chrono */
			while(true) {
				Thread.sleep(20);
				/* Change l'etat de l'oiseau */
				if(chrono.getDureeSec() > TIME_ACTION) {
					bird.changeAction();
					chrono.start();
				}
				
				/* Verifications si l'oiseau atteint les bors du modele  et change sa direction */
				
				if(bird.getDirection() && (bird.getX() + Bird.WIDTH) < Model.WIDTH_MAX) {
					bird.setX(bird.getX() + Bird.NB_STEP);
				}
				else if(!bird.getDirection() && (bird.getX() - Bird.WIDTH) > Model.WIDTH_MIN) {
					bird.setX(bird.getX() - Bird.NB_STEP);
				}
				else if(bird.getDirection() && (bird.getX() + Bird.WIDTH) >= Model.WIDTH_MAX) {
					bird.changeDirection();
				}
				else if(!bird.getDirection() && (bird.getX() - Bird.WIDTH) <= Model.WIDTH_MIN) {
					bird.changeDirection();
				}
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
}