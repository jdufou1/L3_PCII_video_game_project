package pcii_project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pcii_project.models.Model;

public class CarModel extends JPanel{

	/* constantes */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int HEIGHT = 800;
	
	public static final int WIDTH = 800;
	
	public static final int HEIGHT_HORIZON = (int) ((double) ((double) HEIGHT) * 4.0/5.0);
	
	/* attributs */
	
	private Model model;
	
	private JFrame windows = new JFrame("Test");
	
	private Thread threadmodel;
	
	/* constructors */
	
	public CarModel(Model model) {
		this.model = model;
		
		windows.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.add(this);
		threadmodel = new Thread(new ThreadTestModel(this));
	}
	
	public void show() {
		windows.pack();
		windows.setVisible(true);
		threadmodel.start();
	}
	
	int i=400;
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		int hauteur_courante = getHeight();
		int largeur_courante = getWidth();
		/* recuperation des elements du model pour affichge */
		
		/* DESSIN DE LA ROUTE */
		
		/* PISTE CENTRALE */
		Point old_point;
		
		ArrayList<Point> points = model.getRoad().getRoad_points_with_dimension(getGraphicalHorizonValue(hauteur_courante), largeur_courante);
		
		old_point = points.get(0); 
		for(Point point : points) {
			g.drawLine( old_point.x, (hauteur_courante - old_point.y), point.x, (hauteur_courante - point.y));
			old_point = point;
		}
		
		/* DESSIN DE L'ACCOTEMENT */
		/* GAUCHE */
		ArrayList<Point> leftpoints = model.getRoad().getLeftRoad_points_with_dimension(getGraphicalHorizonValue(hauteur_courante), largeur_courante);
		
		
		int[] xpoly1 = new int[leftpoints.size() + 2];
		int[] ypoly1 = new int[leftpoints.size() + 2];
		int cptpoly1 = 0;
		old_point = leftpoints.get(0); 
		for(Point point : leftpoints) {
			//g.drawLine( old_point.x, (hauteur_courante - old_point.y), point.x, (hauteur_courante - point.y));
			old_point = point;
			xpoly1[cptpoly1] = point.x;
			ypoly1[cptpoly1] = hauteur_courante - point.y;
			cptpoly1++;
		}
		// ajout des points en haut a gauche et en bas a gauche
		xpoly1[cptpoly1] = 0;
		ypoly1[cptpoly1] = hauteur_courante - model.getHorizon().get_horizon_with_dimension(hauteur_courante);
		cptpoly1++;
		xpoly1[cptpoly1] = 0;
		ypoly1[cptpoly1] = hauteur_courante;
				
		g2.setColor(Color.GREEN);
		g2.fillPolygon(xpoly1,ypoly1,cptpoly1 + 1);
		

		/* DROITE */
		ArrayList<Point> rightpoints = model.getRoad().getRightRoad_points_with_dimension(getGraphicalHorizonValue(hauteur_courante), largeur_courante);
		
		int[] xpoly2 = new int[rightpoints.size() + 2];
		int[] ypoly2 = new int[rightpoints.size() + 2];
		int cptpoly2 = 0;
		old_point = rightpoints.get(0); 
		for(Point point : rightpoints) {
			//g.drawLine( old_point.x, (hauteur_courante - old_point.y), point.x, (hauteur_courante - point.y));
			old_point = point;
			xpoly2[cptpoly2] = point.x;
			ypoly2[cptpoly2] = hauteur_courante - point.y;
			cptpoly2++;
		}
		// ajout des points en haut a droite et en bas a droite
		xpoly2[cptpoly2] = largeur_courante;
		ypoly2[cptpoly2] = hauteur_courante - model.getHorizon().get_horizon_with_dimension(hauteur_courante);
		cptpoly2++;
		xpoly2[cptpoly2] = largeur_courante;
		ypoly2[cptpoly2] = hauteur_courante;
		
		g2.setColor(Color.GREEN);
		g2.fillPolygon(xpoly2,ypoly2,cptpoly2 + 1);
		int ty=model.getRoad().getData().getScorePlayer();
			ty=ty-(ty/610)*610;
			g2.drawImage((new ImageIcon("tree.png")).getImage(),20, 160+ty, 150, 150, this);
			g2.drawImage((new ImageIcon("tree.png")).getImage(),0, 300+ty, 150, 150, this);
			g2.drawImage((new ImageIcon("tree.png")).getImage(),680, 200+ty, 70, 70, this);
			g2.drawImage((new ImageIcon("tree.png")).getImage(),710, 260+ty, 90, 90, this);
			g2.drawImage((new ImageIcon("tree.png")).getImage(),700, 460+ty, 110, 100, this);
			g2.drawImage((new ImageIcon("g.png")).getImage(),0, 560+ty, 110, 110, this);

		
		/* DESSIN DE LA VOITURE */
		g2.setColor(Color.BLACK);
		int placementX_cars = model.getData().getRelativePositionPlayer(largeur_courante);
		int placementY_cars = model.getCars().getRelativePlacementY(hauteur_courante);
		int width_cars = model.getCars().getRelativeWidth(largeur_courante);
		int height_cars = model.getCars().getRelativeWidth(hauteur_courante);
		g2.drawImage((new ImageIcon("car.png")).getImage(), placementX_cars, hauteur_courante - height_cars - placementY_cars, width_cars, height_cars, this);

		/* DESSIN DE L'HORIZON */
		
		int height_horizon = model.getHorizon().get_horizon_with_dimension(hauteur_courante);
		g.drawLine(0, hauteur_courante - height_horizon, largeur_courante, hauteur_courante - height_horizon);
		
		g.drawRect(0, hauteur_courante - height_horizon, largeur_courante, height_horizon);

		g2.drawImage((new ImageIcon("sky.jfif")).getImage(), 0, 0, largeur_courante,hauteur_courante - height_horizon, this);

		g.drawString("KILOMETRE:"+model.getRoad().getData().getScorePlayer(), 640, 50);
		/**/
	}

	
	private int getGraphicalHorizonValue(int max_height) {
		double relative_value = (double) HEIGHT_HORIZON / (double) (HEIGHT);
		int value = (int) (relative_value * max_height);
		return value;
	}
	
	/* getters and setters */
	
	public JFrame getWindows() {
		return windows;
	}
}
/*
 * Classe utilis� par Affichage qui permet de mettre a jour l'affichage 
 * 
 * */
class ThreadTestModel extends Thread{
	/* Constantes*/
	
	public static final int UPDATE_TIME = 10; // 1/100 de seconde
	
	/* Attributs */
	
	private CarModel vue;
	
	public ThreadTestModel(CarModel vue) {
		this.vue = vue;
	}
	
	@Override 
	public void run() {
		try {
			while(true) {
				Thread.sleep(10);
				this.vue.repaint();
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
}