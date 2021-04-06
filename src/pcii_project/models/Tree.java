package pcii_project.models;

import java.awt.Point;
import java.util.ArrayList;

import pcii_project.models.data.DataGame;


/*
 * Class tree
 * Classe en developpement
 * */
public class Tree {

	
	public static final int WIDTH_MAX = 100;
	
	public static final int HEIGHT_MAX = 100;
	
	private int width;
	
	private int height;
	
	private int x;
	
	private int y;
	
	private int distance;
	
	private Road road;
	
	private DataGame data;
	
	public Tree(DataGame data,Road road,int x,int y) {
		this.road = road;
		this.data = data;
		this.x = x;
		this.y = y;
		
		//double relative_x = (double)((double) x / (double) Model.WIDTH_MAX);
		distance = 20;
		
		y -= data.getScorePlayer(); /* valeur de y sans le score joueur*/
		
		
		
		
		
		double relative_value = (double)((double) y / (double) Model.HEIGHT_MAX);
		double coef_aggrandissement = 1 - relative_value;
		
		width = (int)(WIDTH_MAX * coef_aggrandissement);
		height = (int) (HEIGHT_MAX * coef_aggrandissement);
		
	}
	
	public void update() {
		//y -= data.getScorePlayer(); /* valeur de y sans le score joueur*/
		
		double relative_value = (double)((double) y / (double) Model.HEIGHT_MAX);
		double coef_aggrandissement = 1 - relative_value;
		width = (int)(WIDTH_MAX * coef_aggrandissement);
		height = (int) (HEIGHT_MAX * coef_aggrandissement);
	}
	
	public void decreaseY() {
		y--;
	}
	
	public int getWidthWithDimension(int max_width) {
		double relative_value = (double)((double) width / (double) Model.WIDTH_MAX);
		return (int) (relative_value * max_width);
	}
	
	public int getHeightWithDimension(int max_height) {
		double relative_value = (double)((double) height / (double) Model.HEIGHT_MAX);
		return (int) (relative_value * max_height);
	}
	
	public int getXWithDimension(int max_width) {
		double relative_value = (double)((double) x / (double) Model.WIDTH_MAX);
		return (int) (relative_value * max_width);
	}

	public int getYWithDimension(int max_height) {
		double relative_value = (double)((double) y / (double) Model.HEIGHT_MAX);
		return (int) (relative_value * max_height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
