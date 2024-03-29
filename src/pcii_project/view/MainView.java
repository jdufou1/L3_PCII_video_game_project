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

import pcii_project.models.Bird;
import pcii_project.models.Model;

/*
 * Class MainView
 * Affichage de l'interface graphique du jeu
 * */
public class MainView extends JPanel{

	/* Constants */
	
	public static final int HEIGHT = 800;
	public static final int WIDTH = 800;
	public static final int HEIGHT_HORIZON = (int) ((double) ((double) HEIGHT) * 4.0/5.0);
	
	private static final long serialVersionUID = 1L;
	
	/* Attributes */
	
	private Model model; /* Modele */
	private JFrame windows = new JFrame("Jeu de course de voiture"); /* Fenetre du jeu */
	private Thread threadmodel; /* Thread de rafraichissement de l'interface graphique */
	private EndGameView endGameView; /* Fenetre de pause du jeu*/
	
	
	/* constructors */
	
	/*
	 * @Param Model model
	 * */
	public MainView(Model model) {
		this.model = model;
		endGameView = new EndGameView(this,model);
		windows.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.add(this);
		threadmodel = new Thread(new ThreadTestModel(this));
	}
	
	/*
	 * @Param void
	 * Affichage et rend visible l'interface graphique
	 * */
	public void show() {
		windows.pack();
		windows.setVisible(true);
		threadmodel.start();
	}
	
	/*
	 * @Param Graphics g
	 * Redessinage de l'interface graphique
	 * */
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
				
		g2.setColor(new Color(58, 137, 35));
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
		
		g2.setColor(new Color(58, 137, 35));;
		g2.fillPolygon(xpoly2,ypoly2,cptpoly2 + 1);
		int ty=model.getRoad().getData().getScorePlayer();
		ty=ty-(ty/610)*610;
		int a = (int)(0.8*largeur_courante);
		int b = (int)(0.1*largeur_courante);
		int treeHauteur = (int)(0.15*hauteur_courante);
		int treeLargeur = (int)(0.15*largeur_courante);

			/* PERSPECTIVE */
			
			int treeHauteurSmall = model.getRoad().getWidthPerspective(hauteur_courante, 150+ty, (int)(0.20*hauteur_courante));
			int treeLargeurSmall = model.getRoad().getWidthPerspective(hauteur_courante, 150+ty, (int)(0.20*largeur_courante));
			
			int treeHauteurBig = model.getRoad().getWidthPerspective(hauteur_courante, 150+ty, (int)(0.30*largeur_courante));
			int treeLargeurBig = model.getRoad().getWidthPerspective(hauteur_courante, 150+ty, (int)(0.30*largeur_courante));
			
			g2.drawImage((new ImageIcon("img/tree.png")).getImage(),20, 150+ty, treeLargeurBig,treeHauteurBig , this);
			g2.drawImage((new ImageIcon("img/tree.png")).getImage(),0, 400+ty, treeLargeurBig,treeHauteurBig, this);
			g2.drawImage((new ImageIcon("img/tree.png")).getImage(),10+a, 100+ty, treeLargeurSmall, treeHauteurSmall, this);
			g2.drawImage((new ImageIcon("img/tree.png")).getImage(),a, 260+ty, treeLargeurSmall, treeHauteurSmall, this);
			g2.drawImage((new ImageIcon("img/tree.png")).getImage(),20+a, 460+ty, treeLargeurSmall, treeHauteurSmall, this);
			
		/* DESSIN DE LA VOITURE */
		g2.setColor(Color.BLACK);
		int placementX_cars = model.getData().getRelativePositionPlayer(largeur_courante);
		int placementY_cars = model.getCars().getRelativePlacementY(hauteur_courante);
		int width_cars = model.getCars().getRelativeWidth(largeur_courante);
		int height_cars = model.getCars().getRelativeHeight(hauteur_courante);
		if(model.getCars().getLeft()) {
			if(model.getCars().is_slowly()) 
				g2.drawImage((new ImageIcon("img/voiture_left_feux_active.png")).getImage(), placementX_cars, hauteur_courante - height_cars - placementY_cars, width_cars, height_cars, this);
			else
				g2.drawImage((new ImageIcon("img/voiture_left.png")).getImage(), placementX_cars, hauteur_courante - height_cars - placementY_cars, width_cars, height_cars, this);
		}
		else if(model.getCars().getRight()) {
			if(model.getCars().is_slowly()) 
				g2.drawImage((new ImageIcon("img/voiture_right_feux_active.png")).getImage(), placementX_cars, hauteur_courante - height_cars - placementY_cars, width_cars, height_cars, this);
			else
				g2.drawImage((new ImageIcon("img/voiture_right.png")).getImage(), placementX_cars, hauteur_courante - height_cars - placementY_cars, width_cars, height_cars, this);
		}
		else {
			if(model.getCars().is_slowly()) 
				g2.drawImage((new ImageIcon("img/voiture_feux_active.png")).getImage(), placementX_cars, hauteur_courante - height_cars - placementY_cars, width_cars, height_cars, this);
			else
				g2.drawImage((new ImageIcon("img/voiture.png")).getImage(), placementX_cars, hauteur_courante - height_cars - placementY_cars, width_cars, height_cars, this);
		}
		
		/* DESSIN DE L'HORIZON */
		
		int height_horizon = model.getHorizon().get_horizon_with_dimension(hauteur_courante);
		g.drawLine(0, hauteur_courante - height_horizon, largeur_courante, hauteur_courante - height_horizon);
		
		g.drawRect(0, hauteur_courante - height_horizon, largeur_courante, height_horizon);

		g2.drawImage((new ImageIcon("img/sky.jfif")).getImage(), 0, 0, largeur_courante,hauteur_courante - height_horizon, this);
		/**/
		
		/*DESSIN speed & kilo*/
		if(hauteur_courante>500 && largeur_courante>500) {
			g.setColor(Color.WHITE);
			int rect_large = (int) (0.2*largeur_courante);
			int rect_hauteur = (int) (0.13*hauteur_courante);
			int rect_speed_x = (int) (0.03*largeur_courante);
			int rect_kilo_x = (int) (0.75*largeur_courante);
			int rect_y = (int) (0.8*hauteur_courante);
			g.fillRect(rect_speed_x, rect_y, rect_large , rect_hauteur);
			g.fillRect(rect_kilo_x, rect_y, rect_large, rect_hauteur);
		
			g.setColor(Color.black);
			int kilometre = (int)model.getRoad().getData().getScorePlayer();
			double speed_factor = model.getRoad().getData().getFactorAcceleration();
			int speed = 0;
			if(speed_factor > 1.1)
				speed = (int)speed_factor;
			int string_y = (int)(rect_y+(0.5*rect_hauteur));
			int string_speed_x = (int) (rect_speed_x + 0.1*rect_large);
			int string_kilo_x = (int) (rect_kilo_x + 0.1*rect_large);
			g.drawString("Metres:"+ kilometre +"m", string_kilo_x, string_y);
			g.drawString("Vitesse:"+(speed*4)+"km/h", string_speed_x, string_y);
		}
		/* DESSIN DU CHECKPOINT */
		
		ArrayList<Point> checkpoints = model.getRoad().getCheckPoint().getCheckPointsCoords(
				model.getRoad().getLeftRoad_points_with_score(model.getData().getScorePlayer()),
				model.getRoad().getRoad_points_with_score(model.getData().getScorePlayer()),
				model.getRoad().getRightRoad_points_with_score(model.getData().getScorePlayer()),
				model.getData().getScorePlayer(), hauteur_courante, largeur_courante
		
		);
		model.getRoad().getCheckPoint().checkPointComplete(checkpoints); /*TODO : A METTRE DANS LE MODEL */ 
		g2.setColor(Color.RED); 		
		if(checkpoints != null) {
			//g2.drawLine( checkpoints.get(0).x, (hauteur_courante - checkpoints.get(0).y), checkpoints.get(1).x, (hauteur_courante - checkpoints.get(1).y));
			g2.drawImage((new ImageIcon("img/checkpoint.png")).getImage(), 
					checkpoints.get(0).x, 
					(hauteur_courante - checkpoints.get(0).y),
					checkpoints.get(1).x - checkpoints.get(0).x, 
					model.getRoad().getCheckPoint().getHeighCheckPointWithPerspective(hauteur_courante),
					this);
		}
		/* DESSIN DES OISEAUX */
		
		for(int cptBird = 0 ; cptBird < model.getBirds().size(); cptBird++) {
			
			if(model.getBirds().get(cptBird).getDirection()) {
				
				if(model.getBirds().get(cptBird).getAction()) {
					g2.drawImage((new ImageIcon("img/oiseau_plan2_droite.png")).getImage(), 
							model.getBirds().get(cptBird).getXWithDimension(largeur_courante),
							(hauteur_courante - model.getBirds().get(cptBird).getYWithDimension(hauteur_courante)),
							model.getBirds().get(cptBird).getWidthWithDimension(largeur_courante),
							model.getBirds().get(cptBird).getHeightWithDimension(hauteur_courante),
							this
							);
				}
				else {
					g2.drawImage((new ImageIcon("img/oiseau_plan1_droite.png")).getImage(), 
							model.getBirds().get(cptBird).getXWithDimension(largeur_courante),
							(hauteur_courante - model.getBirds().get(cptBird).getYWithDimension(hauteur_courante)),
							model.getBirds().get(cptBird).getWidthWithDimension(largeur_courante),
							model.getBirds().get(cptBird).getHeightWithDimension(hauteur_courante),
							this
							);
				}
			}
			else {
				if(model.getBirds().get(cptBird).getAction()) {
					g2.drawImage((new ImageIcon("img/oiseau_plan2_gauche.png")).getImage(), 
							model.getBirds().get(cptBird).getXWithDimension(largeur_courante),
							(hauteur_courante - model.getBirds().get(cptBird).getYWithDimension(hauteur_courante)),
							model.getBirds().get(cptBird).getWidthWithDimension(largeur_courante),
							model.getBirds().get(cptBird).getHeightWithDimension(hauteur_courante),
							this
							);
				}
				else {
					g2.drawImage((new ImageIcon("img/oiseau_plan1_gauche.png")).getImage(), 
							model.getBirds().get(cptBird).getXWithDimension(largeur_courante),
							(hauteur_courante - model.getBirds().get(cptBird).getYWithDimension(hauteur_courante)),
							model.getBirds().get(cptBird).getWidthWithDimension(largeur_courante),
							model.getBirds().get(cptBird).getHeightWithDimension(hauteur_courante),
							this
							);
				}
				
			}
			
		}
	}
	
	
	/*
	 * @Param int max_heigth
	 * Retourne la heuteur de la vue dans une autre dimension 
	 * */
	private int getGraphicalHorizonValue(int max_height) {
		double relative_value = (double) HEIGHT_HORIZON / (double) (HEIGHT);
		int value = (int) (relative_value * max_height);
		return value;
	}
	
	/* getters and setters */
	
	public JFrame getWindows() {
		return windows;
	}
	
	public Model getModel() {
		return model;
	}
	
	public EndGameView getEndGameView() {
		return endGameView;
	}
}
/*
 * Class ThreadTestModel
 * Classe utilise par Affichage qui permet de mettre a jour l'affichage 
 * */
class ThreadTestModel extends Thread{
	
	/* Constants*/
	public static final int STEP = 10; /* temps pour le run */
	public static final int UPDATE_TIME = 10; // 1/100 de seconde
	
	/* Attributes */
	
	private MainView vue; /* vue */
	
	/* Constructors */
	
	public ThreadTestModel(MainView vue) {
		this.vue = vue;
	}
	
	@Override 
	public void run() {
		try {
			while(true) {
				Thread.sleep(STEP);
				
				/* AFFICHAGE DE LA PAGE DE FIN DE JEU */
				if(vue.getModel().getGame().gameOver() && !vue.getEndGameView().getDisplayed()){
					vue.getEndGameView().display();
					vue.getEndGameView().setDisplayed(true);
				}
				else {
					this.vue.repaint();
				}
			}
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
	}
}