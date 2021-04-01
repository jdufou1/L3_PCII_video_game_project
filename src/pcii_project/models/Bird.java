package pcii_project.models;

public class Bird {

	/* NOMBRE DE FRAME A FAIRE CHANGER */
	public static final int NB_STEP = 1;
	
	public static final int WIDTH = (int)(Model.WIDTH_MAX / 15);
	
	public static final int HEIGHT = (int)(Model.HEIGHT_MAX / 15);
	
	private int y;
	
	private int x_middle;
	
	private boolean action;
	
	private ThreadBird threadBird;
	
	/* false : left --- true : right*/
	public boolean direction;
	
	public Bird(int y,int x_middle,boolean direction,boolean action) {
		this.y = y;
		this.x_middle = x_middle;
		this.direction = direction;
		this.action = action;
		threadBird = new ThreadBird(this);
	}
	
	public void active() {
		threadBird.start();
	}
	
	
	
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

class ThreadBird extends Thread{
	
	public static final double TIME_ACTION = 0.1;
	
	private Bird bird;
	
	private Chrono chrono;
	/* Constructeurs */
	public ThreadBird(Bird bird) {
		this.bird = bird;
		chrono = new Chrono();
	}
	
	@Override
	public void run() {
		try {
			chrono.start();
			while(true) {
				Thread.sleep(20);
				if(chrono.getDureeSec() > TIME_ACTION) {
					bird.changeAction();
					chrono.start();
				}
				
				
				
				
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